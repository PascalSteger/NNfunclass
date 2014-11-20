/**
 * Population: implementation of a population of genes
 *
 * @author psteger
 * @copyright GPL v3.0
 */
public class Population {
	private final Gen[]	pool_;
	private final boolean	elitism_;
	private float		probcross_;
	private float		probmut_;

	/**
	 * default constructor
	 */
	public Population() {
		pool_ = new Gen[100];
		elitism_ = true;
	}

	/**
	 * constructor of a given number of individuals
	 *
	 * @param size
	 */
	public Population(final int size) {
		pool_ = new Gen[size];
		elitism_ = true;
	}

	/**
	 * constructor with dim/elitism flag
	 *
	 * @param size
	 * @param elitism
	 */
	public Population(final int size, final boolean elitism) {
		pool_ = new Gen[size];
		elitism_ = elitism;
	}

	// TODO: interaction between gens,...

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		final Population pop = new Population( 20 );
		System.out.println( pop );

	}

}
