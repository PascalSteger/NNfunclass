/**
 * Fitter: superclass for all fitting methods
 *
 * @author psteger
 * @date Jun 3, 2010
 * @copyright GPL v3.0
 */

public class Fitter {
	private LinSup	ls_;
	protected Input	in_;	// changes during n-step fitting

	/**
	 * default constructor
	 */
	public Fitter() {
		ls_ = new LinSup();
		in_ = new Input();
	}

	/**
	 * if given input, use it
	 *
	 * @param in
	 */
	public Fitter(final Input in) {
		ls_ = new LinSup();
		Input in2 = new Input( in );
		in.normalize();
		in_ = new Input( in2 );
	}

	/**
	 * copy constructor
	 *
	 * @param fit_
	 */
	public Fitter(final Fitter fit) {
		ls_ = new LinSup( fit.ls_ );
		in_ = new Input( fit.in_ );
	}

	public LinSup getLs() {
		return ls_;
	}

	/**
	 * for debug purposes only: override fitting, set given LinSup
	 *
	 * @param ls
	 */
	public void setLs( final LinSup ls ) {
		ls_ = ls;
	}

	/**
	 * method that should be implemented by different AI procedures
	 *
	 * @return FForm of most dominant part of curve
	 */
	public SFunction findFunction() {
		// intentionally left blank
		final SFunction f = new SFunction();
		return f;
	}

	/**
	 * subtract fitting formula from data points, renormalize
	 *
	 * @param f
	 */
	public void residual( final SFunction f ) {
		ls_.add( f );
		in_.subtract( f );
		in_.normalize();
		// TODO: carry along scaling, translation
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = new String( "" );
		s += "Input is:\n";
		s += in_;
		s += "Fitting formula:\n";
		s += ls_;
		return s;

	}

	/**
	 * start fitting procedure
	 */
	public void start() {
		// intentionally left blank, should be implemented by
		// subroutines
	}

	/**
	 * test routine
	 *
	 * @param args
	 */
	public static void main( final String[] args ) {
		final Fitter f1 = new Fitter();
		System.out.println( f1 );
	}

}
