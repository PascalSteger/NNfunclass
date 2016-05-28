/**
 * FFitGen: Genetic programming for function fitting
 * 
 * @author psteger
 */
public class FFitGen extends Fitter {
	private Population	pop_;

	/**
	 * default constructor
	 */
	public FFitGen() {
		pop_ = new Population();
	}

	/**
	 * use input, where possible
	 * 
	 * @param in
	 */
	public FFitGen(final Input in) {
		super(
				in );
		// TODO
	}

	@Override
	public SFunction findFunction() {
		final SFunction f = new SFunction();
		// TODO
		// basic idea: gene consisting of
		return f;
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		final Fitter f = new FFitGen();
		System.out.println( f );
	}

}
