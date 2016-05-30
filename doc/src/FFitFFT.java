import java.util.Vector;

/**
 * FFitFFT: chi^2 fitting from comparison with given FFT of functions
 * 
 * @author psteger
 */
public class FFitFFT extends Fitter {
	private Double[]	freq_;

	/**
	 * default constructor
	 */
	public FFitFFT() {
		freq_ = new Double[30];
	}

	/**
	 * use given input
	 * 
	 * @param in
	 */
	public FFitFFT(final Input in) {
		super(
				in );
		// TODO: calculate freq_ for given input via FFT (see Numerical
		// Recipes)
	}

	public Vector<Double[]> getFFTofFForm() {
		final Vector<Double[]> output = new Vector<Double[]>();
		// TODO: step through all FForm, through all deg
		// TODO: sample with 10 points
		// TODO: normalize to [0,1]x[0,1]
		// TODO: FFT
		// TODO: save frequencies in output
		return output;
	}

	public Double difference( final Input f1, final Input f2 ) {
		final Double e = 0.0;
		// TODO: find nearest point, measured as x-difference
		// TODO: add Delta_y^2 to error
		// TODO: return error
		return e;
	}

	@Override
	public SFunction findFunction() {
		final SFunction f = new SFunction();
		// TODO: get FFT of all FForms

		// TODO: find minimal difference

		// TODO: find transformation and scaling

		// TODO: return SFunction
		return f;
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		final Fitter f = new FFitFFT();
		// TODO: output possibility
		System.out.println( f );
		// TODO: test case
	}

}
