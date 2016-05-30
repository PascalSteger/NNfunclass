/**
 * Simple Function
 * 
 * @author psteger
 * @date Jun 2, 2010
 */
public class SFunction {
	private FForm	f_;
	private Vec	s_;
	private Vec	t_;

	/**
	 *default constructor
	 */
	public SFunction() {
		f_ = new FForm( 1, 1 );
		s_ = new Vec( 1, 1 );
		t_ = new Vec( 0, 0 );
	}

	/**
	 * constructor for SFunction with known FForm
	 * 
	 * @param type
	 * @param deg
	 */
	public SFunction(final int type, final int deg) {
		f_ = new FForm( type, deg );
		s_ = new Vec( 1, 1 );
		t_ = new Vec( 0, 0 );
	}

	/**
	 * copy constructor
	 * 
	 * @param sf
	 */
	public SFunction(SFunction sf) {
		f_ = new FForm( sf.f_ );
		s_ = new Vec( sf.s_ );
		t_ = new Vec( sf.t_ );
	}

	/**
	 * find value f(x)
	 * 
	 * @param x point to evaluate at
	 * @return f(x)
	 */
	public double eval( final double x ) {
		final Vec v = new Vec( x, f_.eval( x ) );
		v.set( Vec.add( Vec.mul( v, s_ ), t_ ) );
		return v.getY();
	}

	@Override
	public String toString() {
		return f_.toString() + "*" + s_ + "+" + t_;
	}

	public void setFForm( final FForm f ) {
		f_ = f;
	}

	public void setScale( final Vec s ) {
		s_ = s;
	}

	public void setTranslation( final Vec t ) {
		t_ = t;
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		final SFunction sf_ = new SFunction( 0, 1 );
		final double x = Math.E;
		System.out.println( "function f(x):=" + sf_ );
		System.out.println( "f(" + x + ") = " + sf_.eval( x ) );

	}

}
