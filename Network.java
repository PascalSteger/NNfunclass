import java.text.DecimalFormat;
import java.util.Vector;

import com.sun.org.apache.xerces.internal.impl.dv.DatatypeException;

/**
 * Network: first we create a network with two hidden layers then we look for
 * generalization
 *
 * @author psteger
 * @date Jun 5, 2010
 * @copyright GPL v3.0
 */
public class Network {
	// number of neurons:
	private final int[]		dim_;
	// input values from teacher
	protected Vector<Matrix>	tt_in_;
	// output values from teacher
	private Vector<Matrix>		tt_out_;

	// internal state after firing: input layer on input, each following
	// layer as sigmoid(w_.upperLayer)
	private final Vector<Matrix>	out_;
	// target: corresponding output to out_.elementAt(0)
	private Matrix			tar_;
	// Weight of connections: w_[i][j] gives connection strength from neuron
	// i to j
	private final Vector<Matrix>	w_;
	// corrections to w
	private final Vector<Matrix>	dw_;
	// sigmoid parameter
	private double			a_;

	/**
	 * default constructor: one neuron on each layer
	 */
	public Network() {
		a_ = 1.0;
		dim_ = new int[4];
		dim_[0] = 1;
		dim_[1] = 1;
		dim_[2] = 1;
		dim_[3] = 1;

		// prepare teacher variables
		tt_in_ = new Vector<Matrix>();
		tt_out_ = new Vector<Matrix>();
		tar_ = new Matrix();

		// set out_, dw_ (0 not set, 1,2,3 like out_)
		out_ = new Vector<Matrix>();
		dw_ = new Vector<Matrix>();
		for ( int i = 0; i < 4; ++i ) {
			final Matrix tmp = new Matrix( dim_[i], 1 );
			out_.add( tmp );
			if ( i > 0 ) {
				dw_.add( tmp );
			}
		}

		// set w_
		w_ = new Vector<Matrix>();
		for ( int i = 0; i < 3; ++i ) {
			final Matrix tmp = new Matrix( dim_[i + 1], dim_[i] );
			for ( int m = 0; m < dim_[i + 1]; ++m ) {
				for ( int n = 0; n < dim_[i]; ++n ) {
					tmp.set( m, n, Math.random() );
				}
			}
			w_.add( tmp );
		}

	}

	/**
	 * constructor: given number of neurons on each layer
	 */
	public Network(final int dim_in, final int dim_1, final int dim_2,
			final int dim_out) {
		a_ = 1.0;
		dim_ = new int[4];
		dim_[0] = dim_in;
		dim_[1] = dim_1;
		dim_[2] = dim_2;
		dim_[3] = dim_out;

		// prepare teacher variables
		tt_in_ = new Vector<Matrix>();
		tt_out_ = new Vector<Matrix>();
		tar_ = new Matrix();

		// set out_
		out_ = new Vector<Matrix>();
		dw_ = new Vector<Matrix>();
		for ( int i = 0; i < 4; ++i ) {
			final Matrix tmp = new Matrix( dim_[i], 1 );
			out_.add( tmp );
			if ( i > 0 ) {
				dw_.add( tmp );
			}
		}

		// set w_
		w_ = new Vector<Matrix>();
		for ( int i = 0; i < 3; ++i ) {
			final Matrix tmp = new Matrix( dim_[i + 1], dim_[i] );
			for ( int m = 0; m < dim_[i + 1]; ++m ) {
				for ( int n = 0; n < dim_[i]; ++n ) {
					// TODO: normalization
					tmp.set( m, n, Math.random() );// /
					// dim_[
					// i] );
				}
			}
			w_.add( tmp );
		}
	}

	/**
	 * reset connection weights, "forget" learned values
	 */
	public void reset() {
		for ( int i = 0; i < 3; ++i ) {
			for ( int m = 0; m < dim_[i + 1]; ++m ) {
				for ( int n = 0; n < dim_[i]; ++n ) {
					w_.elementAt( i ).set( m, n, Math.random() );
				}
			}
		}
	}

	/**
	 * read in training data
	 *
	 * @param tt_in
	 * @param tt_out
	 */
	public void setTT( Matrix tt_in, Matrix tt_out ) {
		tt_in_.add( tt_in );
		tt_out_.add( tt_out );
	}

	/**
	 * get total number of neurons
	 *
	 * @return dim of network
	 */
	public int getDim() {
		return dim_[0] + dim_[1] + dim_[2] + dim_[3];
	}

	/**
	 * set input weights in input layer; x,y on odd/even places
	 *
	 * @param in Input (n times [1,2] matrix)
	 */
	public void setInput( final Input in ) {
		final Vec[] pos = in.getPos();
		// generate vectors
		for ( int i = 0; i < in.getN(); ++i ) {
			// for x/y coordinates: out_.elementAt( 0 ).set( 2 * i,
			// 0, pos[i].getX() );
			// out_.elementAt( 0 ).set( 2 * i + 1, 0, pos[i].getY()
			// );
			out_.elementAt( 0 ).set( i, 0, pos[i].getY() );
		}
		// out_.elementAt( 0 ).set( 2 * in.getN(), 0, 1.0 ); // Werbos
	}

	/**
	 * set input weights from direct input, not Input procedure from FFitNN
	 *
	 * @param in Vector<Double> of length dim_in
	 */
	public void setInput( final Matrix in ) {
		for ( int i = 0; i < in.getM(); ++i ) {
			out_.elementAt( 0 ).set( i, 0, in.get( i, 0 ) );
		}
	}

	/**
	 * read in wished output
	 *
	 * @param tar
	 */
	public void setTarget( Matrix tar ) {
		tar_ = new Matrix( tar );
	}

	/**
	 * apply neural network on input (read in before)
	 *
	 * @param a parameter in sigmoid output function
	 * @return output Matrix
	 */
	public Matrix fireCascade( double a ) {
		// outhidhid=sigmoid[whh.in];
		// outhid=sigmoid[wh.outhidhid];
		// out=sigmoid[wo.outhid];

		for ( int i = 0; i < 3; ++i ) {
			Matrix tmp = new Matrix( w_.elementAt( i ) );
			tmp.dot( out_.elementAt( i ) );
			tmp.sigmoid( a );

			out_.set( i + 1, tmp );
		}

		return out_.elementAt( 3 );
	}

	/**
	 * first step for learning: determine errors backwards
	 */
	public void getErr() {
		// e=t-out;
		Matrix M = new Matrix();
		Matrix e = new Matrix( M.sub( tar_, out_.elementAt( 3 ) ) );
		// outdelta=e out(1-out);

		{
			Matrix H = new Matrix();
			Matrix tmp = new Matrix( H.ones( dim_[3], 1 ) );
			tmp.sub( out_.elementAt( 3 ) );
			tmp.mul( out_.elementAt( 3 ) );
			tmp.mul( e );
			dw_.set( 2, tmp );
		}

		// hiddelta=outhid(1-outhid) Transpose[wo].outdelta;
		{
			Matrix H = new Matrix();
			Matrix tmp = new Matrix( H.ones( dim_[2], 1 ) );
			tmp.sub( out_.elementAt( 2 ) );
			tmp.mul( out_.elementAt( 2 ) );
			Matrix tmp2 = new Matrix( w_.elementAt( 2 ) );
			tmp2.transpose();
			tmp2.dot( dw_.elementAt( 2 ) );
			tmp.mul( tmp2 );
			dw_.set( 1, tmp );
		}

		{
			// hidhiddelta=outhidhid(1-outhidhid)
			// Transpose[wh].hiddelta;
			Matrix H = new Matrix();
			Matrix tmp = new Matrix( H.ones( dim_[1], 1 ) );
			tmp.sub( out_.elementAt( 1 ) );
			tmp.mul( out_.elementAt( 1 ) );
			Matrix tmp2 = new Matrix( w_.elementAt( 1 ) );
			tmp2.transpose();
			tmp2.dot( dw_.elementAt( 1 ) );
			tmp.mul( tmp2 );
			dw_.set( 0, tmp );
		}
	}

	/**
	 * learn weights
	 *
	 * @param eta learning parameter
	 */
	public void correctW( Double eta ) {

		// wo+= eta Outer[Times, outdelta, outhid];
		// wh+= eta Outer[Times, hiddelta, outhidhid];
		// whh+= eta Outer[Times, hidhiddelta, in];
		for ( int k = 0; k < 3; ++k ) {
			Matrix tmp = new Matrix( dim_[3 - k], dim_[2 - k] );
			tmp = tmp.outer( dw_.elementAt( 2 - k ), out_.elementAt( 2 - k ) );
			tmp.mul( eta );
			w_.elementAt( 2 - k ).add( tmp );
		}
	}

	/**
	 * train network with a given number of iterations, learning rate and
	 * output function parameter
	 *
	 * @param nitmax
	 * @param a
	 * @param eta
	 */
	public void train( int nitmax, double a, double eta ) {
		// System.out.println( "training with " + nitmax + " cycles..."
		// );
		for ( int nit = 0; nit < nitmax; ++nit ) {
			final int i = (int) (tt_out_.size() * Math.random());
			// System.out.println( "random: " + i );
			setInput( tt_in_.elementAt( i ) );
			setTarget( tt_out_.elementAt( i ) );
			fireCascade( a );
			getErr();
			correctW( eta );
		}
	}

	/**
	 * apply network on a given input
	 *
	 * @param in
	 * @param a
	 * @return
	 */
	public Matrix apply( Matrix in, double a ) {
		setInput( in );
		return fireCascade( a );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final DecimalFormat thdf = new DecimalFormat( "#0.00" );
		String s = "";
		// TODO: sensible output of network
		s += "Network: \n";
		s += "dim: ";
		for ( int i = 0; i < 4; ++i ) {
			s += dim_[i];
			s += " ";
		}
		return s;
	}

	/**
	 * test routine: learn simple logic functions
	 *
	 * @param args
	 * @throws DatatypeException
	 */
	public static void main( final String[] args ) {
		final DecimalFormat thdf = new DecimalFormat( "#0.0000" );

		double a = 1.0;
		double eta = 1.0;
		for ( int i = 0; i < 16; ++i ) {
			final Network n = new Network( 2, 8, 8, 1 );
			// System.out.println( n );

			// set training data for XOR
			// System.out.println( "set training data for " + i );

			for ( int j = 0; j < 4; ++j ) {
				Matrix M = new Matrix( 2, 1 );
				Matrix N = new Matrix( 1, 1 );
				M.set( 0, 0, (double) (j / 2) );
				M.set( 1, 0, (double) (j % 2) );
				N.set( 0, 0, (double) (i / (int) Math.pow( 2, j ) % 2) );
				n.setTT( M, N );
				// System.out.println( M );
				// System.out.println( N );
			}
			// learn data
			// System.out.println(
			// "training network with 10000 steps..." );
			n.train( 10000, a, eta );

			// System.out.println( "applying..." );
			Matrix vtar = new Matrix( 4, 1 );
			Matrix vout = new Matrix( 4, 1 );
			for ( int j = 0; j < 4; ++j ) {
				double tar = n.tt_out_.elementAt( j ).get( 0, 0 );
				vtar.set( j, 0, tar );
				double out = n.apply( n.tt_in_.elementAt( j ), a ).get( 0, 0 );
				vout.set( j, 0, out );

				String o = "";
				o += tar;
				o += " & ";
				System.out.print( o );
			}
			Matrix M = new Matrix();
			System.out.print( thdf.format( M.totalErr( vtar, vout ) ) );
			System.out.println( "\\\\" );

		}
	}
}
