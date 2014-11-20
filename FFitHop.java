/**
 * discrete Hopfield network; learning by a converging to a set of training
 * arrays
 *
 * @author Pascal Steger
 * @copyright GPL v3.0
 */

public class FFitHop extends Fitter {

	/**
	 * default constructor
	 */
	public FFitHop() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructor with a given Input
	 *
	 * @param in
	 */
	public FFitHop(final Input in) {
		super(
				in );
		// TODO Auto-generated constructor stub
	}

	// TODO: create training set

	// TODO: calculate energy map

	@Override
	public SFunction findFunction() {
		final SFunction f = new SFunction();
		// TODO: apply energy matrix on Input
		// TODO: find corresponding FForm
		// TODO: iterate
		// TODO: heating
		// TODO: find translation, scaling
		return f;
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		// TODO Auto-generated method stub

	}

}
