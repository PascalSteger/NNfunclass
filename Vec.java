import java.text.DecimalFormat;

/**
 * Vector
 *
 * @author psteger
 * @date Jun 2, 2010
 * @copyright GPL v3.0
 */
public class Vec {
	// holds x and y coordinates
	private double	x_;
	private double	y_;

	/**
	 * default constructor: no values set, all start with random numbers
	 * from 1..100
	 */
	public Vec() {
		x_ = 0.0;
		y_ = 0.0;
	}

	/**
	 * generate new Vec with known x,y-values
	 *
	 * @param x position in first dimension
	 * @param y position in second dimension
	 */
	public Vec(final double x, final double y) {
		x_ = x;
		y_ = y;
	}

	/**
	 * copy constructor
	 *
	 * @param a value for new vector
	 */
	public Vec(final Vec a) {
		x_ = a.x_;
		y_ = a.y_;
	}

	/**
	 * set new x,y values
	 *
	 * @param x position in first dimension
	 * @param y position in second dimension
	 */
	public void set( final double x, final double y ) {
		x_ = x;
		y_ = y;
	}

	/**
	 * @param a new vector
	 */
	public void set( final Vec a ) {
		x_ = a.x_;
		y_ = a.y_;
	}

	public double getX() {
		return x_;
	}

	/**
	 * set x value separately
	 *
	 * @param x
	 */
	public void setX( final double x ) {
		x_ = x;
	}

	public double getY() {
		return y_;
	}

	/**
	 * set y value separately
	 *
	 * @param y new y value
	 */
	public void setY( final double y ) {
		y_ = y;
	}

	public double norm() {
		return Math.sqrt( x_ * x_ + y_ * y_ );
	}

	@Override
	public String toString() {
		final DecimalFormat thdf = new DecimalFormat( "#0.000" );
		return "(" + thdf.format( (float) x_ ) + "," + thdf.format( (float) y_ ) + ")";
	}

	public Vec translate( final double dx, final double dy ) {
		x_ += dx;
		y_ += dy;
		return this;
	}

	public Vec scale( final double s ) {
		x_ *= s;
		y_ *= s;
		return this;
	}

	/**
	 * scalar product between two Vecs in cartesian coordinates
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public double scalar( final Vec a, final Vec b ) {
		return a.x_ * b.x_ + a.y_ * b.y_;
	}

	public static Vec add( final Vec a, final Vec b ) {
		final Vec c = new Vec();
		c.x_ = a.x_ + b.x_;
		c.y_ = a.y_ + b.y_;
		return c;
	}

	public static Vec sub( final Vec a, final Vec b ) {
		final Vec c = new Vec();
		c.x_ = a.x_ - b.x_;
		c.y_ = a.y_ - b.y_;
		return c;
	}

	/**
	 * compute component maximum
	 *
	 * @param a first vector
	 * @param b seconde vector
	 * @return vector consisting of each maximum
	 */
	public static Vec compMax( final Vec a, final Vec b ) {
		final Vec max = new Vec();
		if ( a.x_ > b.x_ ) {
			max.x_ = a.x_;
		} else {
			max.x_ = b.x_;
		}
		if ( a.y_ > b.y_ ) {
			max.y_ = a.y_;
		} else {
			max.y_ = b.y_;
		}
		return max;
	}

	/**
	 * compute component minimum
	 *
	 * @param a first vector
	 * @param b second vector
	 * @return vector consisting of each minimum
	 */
	public static Vec compMin( final Vec a, final Vec b ) {
		final Vec min = new Vec();
		if ( a.x_ < b.x_ ) {
			min.x_ = a.x_;
		} else {
			min.x_ = b.x_;
		}
		if ( a.y_ < b.y_ ) {
			min.y_ = a.y_;
		} else {
			min.y_ = b.y_;
		}
		return min;
	}

	/**
	 * @param args runtime arguments, none used
	 */
	public static void main( final String[] args ) {
		final Vec p = new Vec();
		p.set( 3, 5 );
		final Vec q = new Vec( 8, 10 );
		System.out.println( p );
		final Vec r = new Vec( p );
		System.out.println( r );
		System.out.println( p.norm() );
		System.out.println( p.scale( 2 ) );
		System.out.println( p.translate( 1, 2 ) + " " + q );
		System.out.println( "max:" + Vec.compMax( p, q ) );
		System.out.println( "min:" + Vec.compMin( p, q ) );
	}

	/**
	 * component wise division
	 *
	 * @param a Vector
	 * @param s scale
	 * @return a./s
	 */
	public static Vec div( final Vec a, final Vec s ) {
		a.x_ /= s.x_;
		a.y_ /= s.y_;
		return a;
	}

	/**
	 * component wise inversion
	 *
	 * @param r vector to be inverted
	 * @return 1./r
	 */
	public static Vec inv( final Vec r ) {
		return div( new Vec( 1, 1 ), r );
	}

	/**
	 * negation of each component
	 *
	 * @param t vector to be negated
	 * @return -t
	 */
	public static Vec neg( final Vec t ) {
		return sub( new Vec( 0, 0 ), t );
	}

	/**
	 * component wise multiplication
	 *
	 * @param a vector to be multiplied
	 * @param s scale
	 * @return a.*a
	 */
	public static Vec mul( final Vec a, final Vec s ) {
		final Vec c = new Vec();
		c.x_ = a.x_ * s.x_;
		c.y_ = a.y_ * s.y_;
		return c;
	}

}
