/**
 * Classificator: main program
 *
 * @author psteger
 * @date Jun 2, 2010
 * @copyright GPL v3.0
 */
public class Classificator {
	private Input	in_;
	private Fitter	fit_;

	/**
	 * default constructor
	 */
	public Classificator() {
		in_ = new Input();
		fit_ = new Fitter();
	}

	/**
	 * constructor to activate other fitters
	 *
	 * @param type type of fitter, 1: NN, 2: holoN, 3: genetic, 4: Cluster
	 */
	public Classificator(final int type) {
		in_ = new Input();
		switch ( type ) {
			case 1:
				fit_ = new FFitNN();
				break;
			case 2:
				fit_ = new FFitHN();
				break;
			case 3:
				fit_ = new FFitGen();
				break;
			case 4:
				fit_ = new FFitClust();
				break;
			default:
				fit_ = new FFitFFT();
		}
	}

	/**
	 * read in known data
	 *
	 * @param in input
	 */
	public void setInput( final Input in ) {
		in_ = new Input( in );
	}

	/**
	 * get the work started
	 */
	public void fit() {
		fit_.start();
	}

	/**
	 * show graphical representation of data, fit, and fitting formula
	 */
	public void visualize() {
		final Visualization vis = new Visualization( in_, fit_ );
		vis.show( vis );
	}

	@Override
	public String toString() {
		String s = "";
		s += in_;
		s += " is best described with ";
		s += fit_;
		// TODO:output fitting formula, with variables and errors

		return s;
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		final Input in = new Input();
		final Classificator c = new Classificator( 1 );
		c.setInput( in );
		c.fit();
		c.visualize();
		System.out.println( c );
	}
}
