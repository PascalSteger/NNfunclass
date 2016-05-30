/**
 * Gen: hold all routines on gen-manipulation
 * 
 * @author psteger
 */
public class Gen {
	private final int	dim_;
	private final int[]	chrom_;

	/**
	 * default constructor
	 */
	public Gen() {
		dim_ = 10;
		chrom_ = new int[dim_];
	}

	/**
	 * constructor used with a given gen size
	 * 
	 * @param dim size of gen
	 */
	public Gen(final int dim) {
		dim_ = dim;
		chrom_ = new int[dim_];
	}

	/**
	 * copy constructor
	 * 
	 * @param x gen to be replicated
	 */
	public Gen(final Gen x) {
		dim_ = x.dim_;
		chrom_ = x.chrom_;
	}

	public int getDim() {
		return dim_;
	}

	public void setChrom( final int[] chrom ) {
		for ( int i = 0; i < dim_; ++i ) {
			chrom_[i] = chrom[i];
		}
	}

	/**
	 * cross *this gen with another one do not forget to update other gen as
	 * well!
	 * 
	 * @param y other gen
	 * @param pos position of cut
	 * @return
	 */
	public Gen cross( final Gen y, final int pos ) {
		if ( dim_ != y.getDim() ) {
			return this;
		} else {
			// TODO
			return this;
		}
	}

	/**
	 * mutation of a gene at a given position
	 * 
	 * @param pos position inside gen that is mutated
	 * @return this gen, after mutation
	 */
	public Gen mutate( final int pos ) {
		if ( pos >= dim_ ) {
			return this;
		} else {
			// TODO
			return this;
		}
	}

	/**
	 * @param args
	 */
	public static void main( final String[] args ) {
		final Gen g = new Gen();
		final Gen h = new Gen();
		final Gen tmp = new Gen( g );
		g.mutate( 3 );
		g.cross( h, 2 );
		h.cross( tmp, 2 );
		System.out.println( g );

	}

}
