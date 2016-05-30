import java.util.Vector;

/**
 * Neuron: simplest form of mathematical neuron. sums up input*weight, applies
 * output function if threshold overcome
 * 
 * @author psteger
 * @date Jun 5, 2010
 */
public class Neuron {
	// one FForm for each input channel
	private final Vector<FForm>	in_;
	private double			intern_;	// internal state
	private double			thresh_;	// fire threshold
	private FForm			out_;		// output function

	/**
	 * default constructor: empty input, 0 as internal state, 0.5 as
	 * threshold, identity as output function
	 */
	public Neuron() {
		in_ = new Vector<FForm>(); // identity
		intern_ = 0.0;
		thresh_ = 0.5;
		out_ = new FForm(); // identity
	}

	/**
	 * constructor with a given number of input channels
	 * 
	 * @param dim number of input channels
	 */
	public Neuron(final int dim) {
		in_ = new Vector<FForm>();
		intern_ = 0.0;
		thresh_ = 0.5;
		out_ = new FForm();
		for ( int i = 0; i < dim; ++i ) {
			setInput( new FForm() );
		}
	}

	public void setInput( final FForm f ) {
		in_.add( f );
	}

	public void setThresh( final double t ) {
		thresh_ = t;
	}

	public void setOut( final FForm f ) {
		out_ = f;
	}

	public double getIntern() {
		return intern_;
	}

	public double calcIntern( final Vector<Double> inw ) {
		intern_ = 0;
		for ( int i = 0; i < inw.size(); ++i ) {
			intern_ += in_.elementAt( i ).eval( inw.elementAt( i ) );
		}
		return intern_;
	}

	public void resetIntern() {
		intern_ = 0.0;
	}

	public void resetInput() {
		in_.clear();
	}

	/**
	 * let a neuron fire, depending on activation>threshold
	 * 
	 * @param inw input channels
	 * @return output(activation(input))
	 */
	public double fire( final Vector<Double> inw ) {
		calcIntern( inw );
		if ( intern_ > thresh_ ) {
			return out_.eval( intern_ );
		}
		return 0.0;
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		// TODO: simple activation
		final Neuron n = new Neuron();
		n.setThresh( 1.3 );
		// n.setOut( new FForm( 5, 1 ) );// sigmoid
		n.setOut( new FForm() ); // linear output
		final Vector<Double> in = new Vector<Double>();
		for ( int N = 0; N < 3; ++N ) {
			n.setInput( new FForm() );
			in.add( Math.random() );
			System.out.println( "Input " + N + ": " + in.elementAt( N ) );
		}
		System.out.println( n.fire( in ) );
	}
}
