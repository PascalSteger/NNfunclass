import java.text.DecimalFormat;
import java.util.Date;
import java.util.Vector;

/**
 * FFitNN: feeds network with input/target pairs, invokes training, applies on
 * Input, returns most important function with scaling/transformation
 * 
 * @author psteger
 * @date Jun 5, 2010
 */
public class FFitNN extends Fitter {
	private Network	n_;
	private int	sam_;	// # of sampling points of [0,1]
	private int	ntype_;
	private int	ndeg_;

	/**
	 * default constructor
	 */
	public FFitNN() {
		sam_ = 20;
		// TODO: n_ = new Network( 23, 50, 50, 6 );
		n_ = new Network( 10, 20, 20, 2 );
		ntype_ = 6;
		ndeg_ = 4;
	}

	/**
	 * constructor with given input
	 * 
	 * @param in
	 */
	public FFitNN(Input in) {
		super(
				in ); // sets in_ in Fitter
		sam_ = in_.getN();
		n_ = new Network( sam_,
		// 2 * (2 * sam_ + 1) + 1,
					(2 * sam_),
					(2 * sam_),
					// TODO: 6 );
					2 );
		n_.setInput( in_ );
		System.out.println( in_ );
		ntype_ = 6;
		ndeg_ = 4;
	}

	public FFitNN(int nn) {
		sam_ = 10;
		n_ = new Network( 10, nn, nn, 2 );
		ntype_ = 6;
		ndeg_ = 4;
	}

	/**
	 * generate training data
	 */
	public void generateTraining() {
		// sampling with sam_+1 points
		// step through all function templates
		for ( int i = 1; i <= ntype_; ++i ) {
			for ( int j = 1; j <= ndeg_; ++j ) {
				final FForm f = new FForm( i, 1.0 * j );
				final Vector<Vec> v = new Vector<Vec>();
				// calculate y values, normalization later on
				// with ymin,
				// ymax
				double ymin = 1.0e99;
				double ymax = -1.0e99;
				for ( int k = 0; k < sam_; ++k ) {
					// with random Delta x double x = (k +
					// 0.5 + 0.1 * Math.random()) * 1.0 /
					// sam_;
					double x = (k + 0.5) / sam_;
					double y = f.eval( x );
					if ( y < ymin ) {
						ymin = y;
					} else if ( y > ymax ) {
						ymax = y;
					}
					v.add( new Vec( x, y ) );
				}
				// normalize in y direction to [0,1]
				double sy = 1.0;
				if ( Math.abs( ymax - ymin ) > 1e-10 ) {
					sy = 1.0 / (ymax - ymin);
				}
				double ty = ymin;
				for ( int k = 0; k < sam_; ++k ) {
					final double y = v.elementAt( k ).getY();
					v.elementAt( k ).setY( (y - ty) * sy );
				}

				// cast to Matrix for Network input
				Matrix tt_in = new Matrix( sam_, 1 );
				for ( int k = 0; k < sam_; ++k ) {
					// for x- and y- input tt_in.set( 2 * k,
					// 0, v.elementAt( k ).getX() );
					// tt_in.set( 2 * k + 1, 0, v.elementAt(
					// k ).getY() );
					tt_in.set( k, 0, v.elementAt( k ).getY() );
				}

				// generate wished network output:
				// holds fform type, deg, ( scale, translation )
				// TODO:Matrix tt_out = new Matrix( 6, 1 );
				Matrix tt_out = new Matrix( 2, 1 );
				tt_out.set( 0, 0, i / 6.0 ); // for range [0..1]
				tt_out.set( 1, 0, j / 4.0 );
				// scale
				// TODO:tt_out.set( 2, 0, 1.0 );
				// TODO:tt_out.set( 3, 0, 1.0 );
				// translation, scaled by t_
				// TODO:tt_out.set( 4, 0, 0.0 );
				// TODO:tt_out.set( 5, 0, 0.0 );

				// store input/output pair in network
				n_.setTT( tt_in, tt_out );
				// System.out.println( tt_in );
			}
		}
	}

	public void setInput( Input in ) {
		n_.setInput( in );
	}

	public void showApplication( double a ) {
		final DecimalFormat thdf = new DecimalFormat( "#0.000" );
		// apply on input
		for ( int i = 1; i <= ntype_; ++i ) {
			for ( int j = 1; j <= ndeg_; ++j ) {
				n_.setInput( n_.tt_in_.elementAt( (i - 1) * 4 + j - 1 ) );
				// System.out.println( n_.fireCascade( a ) );
				Matrix fin = new Matrix( n_.fireCascade( a ) );
				double type = fin.get( 0, 0 ) * 6.0;
				double deg = fin.get( 1, 0 ) * 4.0;
				System.out.println( i + " & " + j + " & " + thdf.format( type ) + " & " + thdf.format( deg ) + " \\\\" );
			}
		}
	}

	/**
	 * show error as function of nit
	 * 
	 * @param nnit
	 * @param nitmax
	 * @param a
	 * @param eta
	 */
	public void errorEvolution( int nnit, int nitmax, double a, double eta ) {
		Matrix E1 = new Matrix( nnit, 24 );
		Matrix E2 = new Matrix( nnit, 24 );
		for ( int k = 0; k < nnit; ++k ) {
			System.out.println( "training the " + k + "th time..." );
			// train network for the next nitmax steps
			n_.train( nitmax, a, eta );
			// determine errors e1, e2 for all functions
			for ( int i = 0; i < 6; ++i ) {
				for ( int j = 0; j < 4; ++j ) {
					n_.setInput( n_.tt_in_.elementAt( i * 4 + j ) );
					Matrix fin = new Matrix( n_.fireCascade( a ) );
					double type = fin.get( 0, 0 ) * 6.0;
					double deg = fin.get( 1, 0 ) * 4.0;
					double e1 = Math.pow( type - (i + 1), 2 ) + Math.pow( deg - (j + 1), 2 );
					double e2 = Math.pow( Math.round( type ) - (i + 1), 2 ) + Math.pow( Math.round( deg ) - (j + 1), 2 );
					E1.set( k, i * 4 + j, e1 );
					E2.set( k, i * 4 + j, e2 );
				}
			}
		}
		System.out.println( E1 );
		System.out.println( " " );
		System.out.println( E2 );
	}

	public void errorNoise( int nnit, int nitmax, double a, double eta ) {
		Matrix E1 = new Matrix( 10, 24 );
		Matrix E2 = new Matrix( 10, 24 );
		for ( int i = 0; i < nnit; ++i ) {
			System.out.println( "learn step " + i + "/9" );
			n_.train( nnit * nitmax, a, eta );
		}
		for ( int k = 0; k < 10; ++k ) {
			// determine errors e1, e2 for all functions
			for ( int i = 0; i < 6; ++i ) {
				for ( int j = 0; j < 4; ++j ) {
					Matrix noise = new Matrix( 10, 1 );
					// errors from 10% to 1%
					noise.randomize().mul( 0.1 / (k + 1.0) );
					n_.setInput( noise.add( n_.tt_in_.elementAt( i * 4 + j ) ) );
					Matrix fin = new Matrix( n_.fireCascade( a ) );
					double type = fin.get( 0, 0 ) * 6.0;
					double deg = fin.get( 1, 0 ) * 4.0;
					double e1 = Math.pow( type - (i + 1), 2 ) + Math.pow( deg - (j + 1), 2 );
					double e2 = Math.pow( Math.round( type ) - (i + 1), 2 ) + Math.pow( Math.round( deg ) - (j + 1), 2 );
					E1.set( k, i * 4 + j, e1 );
					E2.set( k, i * 4 + j, e2 );
				}
			}
		}
		System.out.println( E1 );
		System.out.println( " " );
		System.out.println( E2 );
	}

	/**
	 * show error as function of learning rate
	 */
	public void errorLearningRate() {
		double a = 0.1;
		int nitmax = 10000;
		Matrix E = new Matrix( 10, 10 );
		for ( int ta = 0; ta < 10; ++ta ) {
			double eta = 0.1 * ta + 0.1;
			System.out.println( "learning with eta = " + eta );
			n_.reset();
			for ( int nnit = 0; nnit < 10; ++nnit ) {
				System.out.println( " training step " + nnit + "/9" );
				n_.train( nitmax, a, eta );
				double e = 0.0;
				for ( int i = 0; i < 6; ++i ) {
					for ( int j = 0; j < 4; ++j ) {
						n_.setInput( n_.tt_in_.elementAt( i * 4 + j ) );
						Matrix fin = new Matrix( n_.fireCascade( a ) );
						double type = fin.get( 0, 0 ) * 6.0;
						double deg = fin.get( 1, 0 ) * 4.0;
						double e1 = Math.pow( type - (i + 1), 2 ) + Math.pow( deg - (j + 1), 2 );
						e += e1;
					}
				}
				E.set( ta, nnit, e );
			}
		}
		System.out.println( E );
	}

	/**
	 * show error as function of sigmoid function
	 */
	public void errorA() {
		double eta = 0.9;
		int nitmax = 100000;
		Matrix E = new Matrix( 20, 1 );
		for ( int k = 0; k < 20; ++k ) {
			double a = 0.05 * k + 0.05;
			System.out.println( "learning with a = " + a );
			n_.reset();
			n_.train( nitmax, a, eta );
			double e = 0.0;
			for ( int i = 0; i < 6; ++i ) {
				for ( int j = 0; j < 4; ++j ) {
					n_.setInput( n_.tt_in_.elementAt( i * 4 + j ) );
					Matrix fin = new Matrix( n_.fireCascade( a ) );
					double type = fin.get( 0, 0 ) * 6.0;
					double deg = fin.get( 1, 0 ) * 4.0;
					double e1 = Math.pow( type - (i + 1), 2 ) + Math.pow( deg - (j + 1), 2 );
					e += e1;
				}
			}
			E.set( k, 0, e );
		}
		System.out.println( E );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Fitter#learnFunction()
	 */
	@Override
	public SFunction findFunction() {
		double a = 0.2;
		double eta = 0.9;
		int nitmax = 1000;
		int nnit = 10;

		generateTraining();

		// errorEvolution( nnit, nitmax, a, eta );
		// errorLearningRate();
		// errorA();
		errorNoise( nnit, nitmax, a, eta );

		showApplication( a );

		// old,only for finding function with Classificator :
		// n_.setInput( in_ );
		// test on known pattern type 4, deg 2
		// n_.setInput( n_.tt_in_.elementAt( 4 * (4 - 1) + 2 - 1 ) );

		// System.out.println( n_.fireCascade( a ) );
		// Matrix fin = new Matrix( n_.fireCascade( a ) );
		// double type = fin.get( 0, 0 ) * 6.0;
		// double deg = fin.get( 1, 0 ) * 4.0;
		// System.out.println( "type: " + type + " (" + 4 + "), deg: " +
		// deg + " (" + 2 + ")" );

		final SFunction f = new SFunction();
		// f.setFForm( new FForm( (int) Math.round( type ), (int)
		// Math.round( deg ) ) );
		// old, not learned: Vec s = new Vec( fin.get( 2, 0 ), fin.get(
		// 3, 0 ) );
		// Vec s = new Vec( 1.0, 1.0 );
		// f.setScale( s );
		// old, not learned: Vec t = new Vec( fin.get( 4, 0 ), fin.get(
		// 5, 0 ) );
		// Vec t = new Vec( 0.0, 0.0 );
		// f.setTranslation( t );
		return f;
	}

	public double findError() {
		double a = 0.2;
		double eta = 0.9;
		int nitmax = 100000;
		double e = 0.0;
		generateTraining();
		n_.train( nitmax, a, eta );
		for ( int i = 0; i < 6; ++i ) {
			for ( int j = 0; j < 4; ++j ) {
				n_.setInput( n_.tt_in_.elementAt( i * 4 + j ) );
				Matrix fin = new Matrix( n_.fireCascade( a ) );
				double type = fin.get( 0, 0 ) * 6.0;
				double deg = fin.get( 1, 0 ) * 4.0;
				double e1 = Math.pow( type - (i + 1), 2 ) + Math.pow( deg - (j + 1), 2 );
				e += e1;
			}
		}
		return e;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Fitter#toString()
	 */
	@Override
	public String toString() {
		String s = new String( "" );
		s += n_.toString();
		return s;
	}

	/**
	 * test routine
	 * 
	 * @param args
	 */
	public static void main( final String[] args ) {
		// test fitting of curve
		Input in = new Input( 10, 4, 2 );

		Matrix E = new Matrix( 20, 2 );
		for ( int k = 0; k < 20; ++k ) {
			double e = 0.0;
			double tstart = 0.0;
			Date dstart = new Date();
			tstart = 1.0 * dstart.getTime();
			System.out.println( "trying network with " + (k + 1) + " hidden neurons, starting @ " + tstart );
			FFitNN f = new FFitNN( k + 1 );
			e = f.findError();
			E.set( k, 0, e );
			Date dend = new Date();
			double tend = 1.0 * dend.getTime();
			E.set( k, 1, tend - tstart );
		}
		System.out.println( E );
		// SFunction s = new SFunction( f.findFunction() );
		// System.out.println( s.toString() );
	}
}
