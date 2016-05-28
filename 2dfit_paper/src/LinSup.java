import java.util.Vector;

/**
 * LinSup: linear superposition of functions
 * 
 * @author psteger
 * @date Jun 2, 2010
 */
public class LinSup {
	// holds all functions
	// most important in first slot
	// all following in next slots
	Vector<SFunction>	f_;

	/**
	 * default constructor
	 */
	public LinSup() {
		f_ = new Vector<SFunction>();
	}

	/**
	 * constructor with one known function
	 * 
	 * @param sf
	 */
	public LinSup(final SFunction sf) {
		f_ = new Vector<SFunction>();
		f_.add( sf );
	}

	/**
	 * copy constructor
	 * 
	 * @param l other LinSup
	 */
	public LinSup(final LinSup l) {
		f_ = new Vector<SFunction>();
		f_ = l.f_;
	}

	/**
	 * attach another SFunction
	 * 
	 * @param f SFunction
	 */
	public void add( final SFunction f ) {
		f_.add( f );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "";
		for ( final SFunction element : f_ ) {
			s += " + " + element;
		}
		return s;
	}

	/**
	 * evaluate all functions at a given point and sum up
	 * 
	 * @param x point @ which functions are evaluated
	 * @return value of f1(x)+f2(x)+..._fn(x)
	 */
	public double eval( final double x ) {
		double y = 0;
		for ( final SFunction element : f_ ) {
			y += element.eval( x );
		}
		return y;
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		final LinSup l = new LinSup();
		l.add( new SFunction( 4, 1 ) );
		l.add( new SFunction( 1, 0 ) );
		System.out.println( "f(x) " + l );
		final double x = 0.6;
		System.out.println( "f(" + x + ")=" + l.eval( x ) );
	}

}
