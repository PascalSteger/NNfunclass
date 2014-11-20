/**
 * FFitClust: Fitting by clustering Fourier components
 *
 * @author psteger
 * @copyright GPL v.3
 */
public class FFitClust extends Fitter {
	private Double[]	freq_;

	/**
	 * default constructor
	 */
	public FFitClust() {
		freq_ = new Double[30];
	}

	/**
	 * @param in
	 */
	public FFitClust(final Input in) {
		super(
				in );
		// TODO
	}

	@Override
	public SFunction findFunction() {
		final SFunction f = new SFunction();
		// TODO: learn most prominent frequency
		return f;
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		final Fitter f = new FFitClust();
		// TODO: output
		System.out.println( f );
		// TODO: test case

	}

}
