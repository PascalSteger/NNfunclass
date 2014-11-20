/**
 * FFitHN: holographic neuron network
 *
 * @author psteger
 * @copyright GPL v3.0
 */
public class FFitHN extends Fitter {
	private final HoloNeuron	hn_;

	/**
	 * default constructor
	 */
	public FFitHN() {
		hn_ = new HoloNeuron();
	}

	/**
	 * use input, if already set
	 *
	 * @param in
	 */
	public FFitHN(final Input in) {
		super(
				in );
		hn_ = new HoloNeuron();
		// TODO
	}

	@Override
	public SFunction findFunction() {
		final SFunction f = new SFunction();
		// TODO
		return f;
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		final Fitter f = new FFitHN();
		System.out.println( f );
		// TODO: fitting, output
	}

}
