/**
 * HoloNeuron: holographic neuron, with complex phase
 * 
 * @author psteger
 */
public class HoloNeuron {
	// store one complex number in polar coordinates
	private final double	r_;	// radius
	private final double	phi_;	// complex phase

	/**
	 * default constructor
	 */
	public HoloNeuron() {
		r_ = 1.0;
		phi_ = 0.0;
	}

	/**
	 * copy constructor
	 * 
	 * @param h other HoloNeuron
	 */
	public HoloNeuron(final HoloNeuron h) {
		r_ = h.r_;
		phi_ = h.phi_;
	}

	/**
	 * learning phase
	 * 
	 * @param in input
	 */
	public void learn( final Input in ) {
		// TODO
	}

	/**
	 * production phase
	 * 
	 * @param in Input
	 * @return activation level
	 */
	public double apply( final Input in ) {
		final double out = 0.0;
		// TODO
		return out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String out = "(";
		out += r_;
		out += ",";
		out += phi_;
		out += ")";
		return out;
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		final HoloNeuron hn = new HoloNeuron();
		System.out.println( hn );
		final Input in = new Input();
		hn.learn( in );
		System.out.println( hn.apply( in ) );
	}
}
