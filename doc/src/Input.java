/**
 * Input: vectors of positions, errors; scaling and translation for [0,1]x[0,1]
 * 
 * @author psteger
 * @date Jun 2, 2010
 */

public class Input {

	private final int	n_;
	private final Vec[]	pos_;
	private final Vec[]	err_;
	private Vec		s_;
	private Vec		t_;

	/**
	 * default constructor: some sample random points
	 */
	public Input() {
		n_ = 4;
		pos_ = new Vec[n_];
		err_ = new Vec[n_];
		final double sp = 100.0; // scale position
		final double se = 5.0; // scale error
		final double min = 2.0;// minimal error
		for ( int i = 0; i < n_; ++i ) {
			pos_[i] = new Vec( sp * Math.random(), sp * Math.random() );
			err_[i] = new Vec(	min + se * Math.random(),
						min + se * Math.random() );
		}
		s_ = new Vec( 1, 1 ); // scaling with 1 in x and 1 in y
		// direction
		t_ = new Vec( 0, 0 ); // no translation
	}

	/**
	 * constructor with given number of input values
	 * 
	 * @param n number of input values
	 */
	public Input(final int n) {
		n_ = n;
		pos_ = new Vec[n_];
		err_ = new Vec[n_];
		final double sp = 100.0; // scale position
		final double se = 5.0; // scale error
		final double min = 2; // minimal error

		for ( int i = 0; i < n_; ++i ) {
			pos_[i] = new Vec( sp * Math.random(), sp * Math.random() );
			err_[i] = new Vec(	min + se * Math.random(),
						min + se * Math.random() );
		}
		s_ = new Vec( 1, 1 );
		t_ = new Vec( 0, 0 );
	}

	/**
	 * constructor for values near a given FForm, used to check network
	 * function
	 * 
	 * @param n
	 * @param type
	 * @param deg
	 */
	public Input(int n, int type, double deg) {
		n_ = n;
		pos_ = new Vec[n_];
		err_ = new Vec[n_];
		double sp = 1.0;
		double se = 0.05;
		double min = 0.02;
		FForm f = new FForm( type, deg );
		for ( int i = 0; i < n_; ++i ) {
			double x = (i + 0.5) / n_;
			pos_[i] = new Vec( sp * x, sp * f.eval( x ) );
			err_[i] = new Vec(	min + se * Math.random(),
						min + se * Math.random() );
		}
		s_ = new Vec( 1.0, 1.0 );
		t_ = new Vec( 0.0, 0.0 );
	}

	/**
	 * copy constructor
	 * 
	 * @param in Input
	 */
	public Input(final Input in) {
		n_ = in.n_;
		pos_ = in.pos_;
		err_ = in.err_;
		s_ = in.s_;
		t_ = in.t_;
	}

	@Override
	public String toString() {

		String s = "";
		for ( int i = 0; i < n_; ++i ) {
			s += "[" + pos_[i] + " \\pm " + err_[i] + "]\n";
		}
		return s;
	}

	public Vec getMax() {
		Vec max = new Vec( -1e100, -1e100 );
		for ( final Vec element : pos_ ) {
			max = Vec.compMax( max, element );
		}
		return max;
	}

	public Vec getMin() {
		Vec min = new Vec( 1e100, 1e100 );
		for ( final Vec element : pos_ ) {
			min = Vec.compMin( min, element );
		}
		return min;
	}

	public int getN() {
		return n_;
	}

	public Vec[] getPos() {
		return pos_;
	}

	public Vec getPos( final int i ) {
		return pos_[i];
	}

	public Vec[] getErr() {
		return err_;
	}

	public Vec getErr( final int i ) {
		return err_[i];
	}

	public Vec getS() {
		return s_;
	}

	public Vec getT() {
		return t_;
	}

	public void normalize() {
		final Vec min = new Vec( getMin() );
		final Vec max = new Vec( getMax() );
		final Vec range = new Vec( Vec.sub( max, min ) );
		// System.out.println( "range:" + range );
		s_ = Vec.inv( range );
		t_ = Vec.neg( min );
		for ( final Vec element : pos_ ) {
			element.set( Vec.mul( Vec.add( element, t_ ), s_ ) );
		}
		for ( final Vec element : err_ ) {
			element.set( Vec.mul( element, s_ ) );
		}
	}

	/**
	 * reverse nomalization
	 */
	public void original() {
		for ( final Vec element : pos_ ) {
			element.set( Vec.sub( Vec.div( element, s_ ), t_ ) );
		}
		for ( final Vec element : err_ ) {
			element.set( Vec.div( element, s_ ) );
		}
	}

	/**
	 * subtract a given function from dataset, holds residual afterwards
	 * 
	 * @param f SFunction
	 */
	public void subtract( final SFunction f ) {
		for ( int i = 0; i < n_; ++i ) {
			final double x = pos_[i].getX();
			final double y = pos_[i].getY();
			pos_[i].set( x, y - f.eval( x ) );
		}
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		final Input i = new Input( 12 );
		System.out.println( i );
		System.out.println( "max: " + i.getMax() );
		System.out.println( "min: " + i.getMin() );

		System.out.println( "normalization..." );
		i.normalize();

		System.out.println( "translate: " + i.t_ );
		System.out.println( "scale: " + i.s_ );

		System.out.println( i );
		System.out.println( "max: " + i.getMax() );
		System.out.println( "min: " + i.getMin() );

		System.out.println( "original..." );
		i.original();
		System.out.println( i );
	}

}
