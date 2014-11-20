/**
 * FForm: basic function forms
 *
 * @author psteger
 * @date Jun 2, 2010
 * @copyright GPL v3.0
 */
public class FForm {
	private final int	type_;	// type of function
	private final double	deg_;	// degree (subtype)

	/**
	 * default constructor: linear function
	 */
	public FForm() {
		type_ = 1;
		deg_ = 1;
	}

	/**
	 * constructor to be used when form and degree known
	 */
	public FForm(final int type, final double deg) {
		type_ = type;
		deg_ = deg;
	}

	/**
	 * copy constructor
	 *
	 * @param f_
	 */
	public FForm(FForm f) {
		type_ = f.type_;
		deg_ = f.deg_;
	}

	/**
	 * evaluation at a given point x\in[0,1]
	 *
	 * @param x \in[0,1]
	 * @return f(x)
	 */
	public double eval( final double x ) {
		switch ( type_ ) {
			case 1:
				return Math.pow( x, deg_ );
			case 2:
				return (Math.exp( x * deg_ ) - 1.0) / (Math.exp( deg_ ) - 1.0);
			case 3:
				return Math.pow( x, 1.0 / (deg_ + 1) );
			case 4:
				return Math.log( deg_ * x );
			case 5:
				return Math.cos( deg_ * Math.PI * x ) * 0.5 + 0.5;
			case 6:
				return (Math.exp( -Math.pow( 2.0 * deg_ * (x - 0.5), 2 ) ) - Math.exp( -Math.pow( 1.0 * deg_, 2 ) )) / (1.0 - Math.exp( -Math.pow( 1.0 * deg_, 2 ) ));
			default:
				return 0;
		}
	}

	@Override
	public String toString() {
		switch ( type_ ) {
			case 1:
				return "x^" + deg_;
			case 2:
				return "exp(x*" + deg_ + ")";
			case 3:
				return "x^(1/" + (deg_ + 1) + ")";
			case 4:
				return "log(" + deg_ + "*x)";
			case 5:
				return "cos(" + deg_ + "x*pi)*0.5+0.5";
			case 6:
				return "exp(-(2*" + deg_ + "*(x-0.5))^2)";
			default:
				return "()";
		}
	}

	/**
	 * test routine
	 *
	 * @param args
	 */
	public static void main( final String[] args ) {
		for ( int i = 1; i <= 6; ++i ) {
			final FForm f_ = new FForm( i, 2.0 );
			System.out.println( "f(x)=" + f_ );
			final double x = 0.2;
			System.out.println( "f(" + x + ") = " + f_.eval( x ) );
			System.out.println( "" );
		}

	}

}
