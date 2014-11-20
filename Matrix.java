import com.sun.org.apache.xerces.internal.impl.dv.DatatypeException;

/**
 * Matrix: provides all basic matrix manipulation tasks
 *
 * @author psteger
 * @date Aug 4, 2010
 * @copyright GPL v3.0
 */
public class Matrix {
	private Double[][]	M_;
	private int		m_;
	private int		n_;

	/**
	 * default constructor
	 */
	public Matrix() {
		m_ = 1;
		n_ = 1;
		M_ = new Double[1][1];
		M_[0][0] = 0.0;
	}

	public Matrix(int m, int n) {
		m_ = m;
		n_ = n;
		M_ = new Double[m_][n_];
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[i][j] = 0.0;
			}
		}
	}

	public Matrix(Double[][] M, int m, int n) {
		m_ = m;
		n_ = n;
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[i][j] = M[i][j];
			}
		}
	}

	public Matrix(Matrix A) {
		m_ = A.m_;
		n_ = A.n_;
		M_ = new Double[m_][n_];
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[i][j] = A.M_[i][j];
			}
		}
	}

	public Double[][] toArray() {
		return M_;
	}

	public int getM() {
		return m_;
	}

	public int getN() {
		return n_;
	}

	public void set( int i, int j, Double val ) {
		M_[i][j] = val;
	}

	public Double get( int i, int j ) {
		return M_[i][j];
	}

	public Matrix dot( final Matrix A, final Matrix B ) {

		int m = A.getM();
		int q1 = A.getN();
		int q2 = B.getM();
		int n = B.getN();

		if ( q1 != q2 ) {
			System.out.println( "Matrix inner dimensions do not agree!" );
		}

		Matrix C = new Matrix( m, n );
		for ( int i = 0; i < m; ++i ) {
			for ( int j = 0; j < n; ++j ) {
				Double s = 0.0;
				for ( int k = 0; k < q1; ++k ) {
					s += A.get( i, k ) * B.get( k, j );
				}
				C.set( i, j, s );
			}
		}
		return C;
	}

	public Matrix dot( final Matrix M ) {
		int m = m_;
		int q1 = n_;
		int q2 = M.getM();
		int n = M.getN();

		if ( q1 != q2 ) {
			System.out.println( "Inner matrix dimensions do not agree!" );
		}

		Matrix tmp = new Matrix( this );
		m_ = m;
		n_ = n;
		M_ = new Double[m][n];
		for ( int i = 0; i < m; ++i ) {
			for ( int j = 0; j < n; ++j ) {
				Double s = 0.0;
				for ( int k = 0; k < q1; ++k ) {
					s += tmp.get( i, k ) * M.get( k, j );
				}
				M_[i][j] = s;
			}
		}
		return this;
	}

	public Matrix outer( final Matrix a, final Matrix b ) {
		final Matrix out = new Matrix( a.getM(), b.getM() );
		for ( int i = 0; i < a.getM(); ++i ) {
			for ( int j = 0; j < b.getM(); ++j ) {
				out.set( i, j, a.get( i, 0 ) * b.get( j, 0 ) );
			}
		}
		return out;
	}

	public Matrix sub( final Matrix a, final Matrix b ) {
		if ( a.m_ != b.m_ || a.n_ != b.n_ ) {
			System.out.println( "matrix dimension mismatch" );
		}
		final Matrix out = new Matrix( a.getM(), a.getN() );
		for ( int i = 0; i < a.getM(); ++i ) {
			for ( int j = 0; j < a.getN(); ++j ) {
				out.set( i, j, a.get( i, j ) - b.get( i, j ) );
			}
		}
		return out;
	}

	public Matrix sub( final Matrix a ) {
		if ( a.m_ != m_ || a.n_ != n_ ) {
			System.out.println( "matrix dimension mismatch" );
		}
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[i][j] = M_[i][j] - a.get( i, j );
			}
		}
		return this;
	}

	public Matrix add( final Matrix a, final Matrix b ) {
		if ( a.m_ != b.m_ || a.n_ != b.n_ ) {
			System.out.println( "matrix dimension mismatch" );
		}
		final Matrix out = new Matrix( a.getM(), a.getN() );
		for ( int i = 0; i < a.getM(); ++i ) {
			for ( int j = 0; j < a.getN(); ++j ) {
				out.set( i, j, a.get( i, j ) + b.get( i, j ) );
			}
		}
		return out;
	}

	public Matrix add( final Matrix a ) {
		if ( a.m_ != m_ || a.n_ != n_ ) {
			System.out.println( "matrix dimension mismatch" );
		}
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[i][j] = M_[i][j] + a.get( i, j );
			}
		}
		return this;
	}

	public Matrix mul( final Matrix a, final Matrix b ) {
		if ( a.m_ != b.m_ || a.n_ != b.n_ ) {
			System.out.println( "matrix dimension mismatch" );
		}
		final Matrix out = new Matrix( a.getM(), a.getN() );
		for ( int i = 0; i < a.getM(); ++i ) {
			for ( int j = 0; j < a.getN(); ++j ) {
				out.set( i, j, a.get( i, j ) * b.get( i, j ) );
			}
		}
		return out;
	}

	public Matrix mul( final Matrix a ) {
		if ( a.m_ != m_ || a.n_ != n_ ) {
			System.out.println( "matrix dimension mismatch" );
		}
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[i][j] = M_[i][j] * a.get( i, j );
			}
		}
		return this;
	}

	public Matrix mul( final Double a, final Matrix b ) {
		final Matrix out = new Matrix( b.getM(), b.getN() );
		for ( int i = 0; i < b.getM(); ++i ) {
			for ( int j = 0; j < b.getN(); ++j ) {
				out.set( i, j, a * b.get( i, j ) );
			}
		}
		return out;
	}

	public Matrix mul( final Double a ) {
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[i][j] = M_[i][j] * a;
			}
		}
		return this;
	}

	public Matrix transpose() {
		Matrix tmp = new Matrix( this );
		m_ = tmp.getN();
		n_ = tmp.getM();
		M_ = new Double[m_][n_];
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[i][j] = tmp.get( j, i );
			}
		}
		return this;
	}

	public Matrix transpose( Matrix A ) {
		Matrix tmp = new Matrix( A );
		m_ = tmp.getN();
		n_ = tmp.getM();
		M_ = new Double[m_][n_];
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[j][i] = tmp.get( i, j );
			}
		}
		return this;
	}

	public Matrix ones( final int dim ) {
		Matrix out = new Matrix( dim, 1 );
		for ( int i = 0; i < dim; ++i ) {
			out.set( i, 0, 1.0 );
		}
		return out;
	}

	public Matrix ones( int m, int n ) {
		Matrix out = new Matrix( m, n );
		for ( int i = 0; i < m; ++i ) {
			for ( int j = 0; j < n; ++j ) {
				out.set( i, j, 1.0 );
			}
		}
		return out;
	}

	public Matrix randomize() {
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[i][j] = Math.random();
			}
		}
		return this;
	}

	public int KroneckerDelta( int i, int j ) {
		if ( i == j ) {
			return 1;
		}
		return 0;
	}

	public Matrix diag( int dim ) {
		Matrix out = new Matrix( dim, dim );
		for ( int i = 0; i < dim; ++i ) {
			for ( int j = 0; j < dim; ++j ) {
				out.set( i, j, 1.0 * KroneckerDelta( i, j ) );
			}
		}
		return out;
	}

	public Double sigmoid( Double a, Double x ) {
		return 1.0 / (1.0 + Math.exp( -a * x ));
	}

	public Matrix sigmoid( Matrix in ) {
		for ( int i = 0; i < in.getM(); ++i ) {
			in.set( i, 0, sigmoid( 1.0, in.get( i, 0 ) ) );
		}
		return in;
	}

	public Matrix sigmoid() {
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[i][j] = sigmoid( 1.0, M_[i][j] );
				// TODO: set a to 1.0
			}
		}
		return this;
	}

	public Matrix sigmoid( Double a ) {
		for ( int i = 0; i < m_; ++i ) {
			for ( int j = 0; j < n_; ++j ) {
				M_[i][j] = sigmoid( a, M_[i][j] );
			}
		}
		return this;

	}

	public double totalErr( Matrix A, Matrix B ) {
		if ( A.m_ != B.m_ || A.n_ != B.n_ ) {
			System.out.println( "Matrix dimensions mismatch" );
		}
		double err = 0.0;
		for ( int i = 0; i < A.m_; ++i ) {
			for ( int j = 0; j < A.n_; ++j ) {
				err += Math.pow( (A.get( i, j ) - B.get( i, j )), 2 );
			}
		}
		return err;
	}

	@Override
	public String toString() {
		String out = new String();
		for ( int i = 0; i < m_; ++i ) {
			if ( i > 0 ) {
				out += "\n";
			}
			out += "( ";
			for ( int j = 0; j < n_; ++j ) {
				out += M_[i][j] + " ";
			}
			out += ")";
		}
		return out;
	}

	/**
	 * test routine, containing some basic linear algebra
	 *
	 * @param args
	 * @throws DatatypeException
	 */
	public static void main( String[] args ) {
		System.out.println( "A = " );
		Matrix A = new Matrix();
		A = A.diag( 3 );
		System.out.println( A );

		System.out.println( "B = " );
		Matrix B = new Matrix();
		B = B.ones( 3, 2 );
		System.out.println( B );

		System.out.println( "C = " );
		Matrix C = new Matrix( 3, 2 );
		C.set( 0, 0, 0.0 );
		C.set( 0, 1, 1.0 );
		C.set( 1, 0, 0.4 );
		C.set( 1, 1, 0.0 );
		C.set( 2, 0, 0.0 );
		C.set( 2, 1, -0.6 );
		System.out.println( C );

		System.out.println( "A.B = " );
		System.out.println( A.dot( B ) );

		System.out.println( "A.B + C = " );
		System.out.println( A.add( C ) );

		System.out.println( "transpose of C = " );
		System.out.println( C.transpose() );

		System.out.println( "V = " );
		Matrix V = new Matrix( 3, 1 );
		V.randomize();
		System.out.println( V );

		System.out.println( "W = " );
		Matrix W = new Matrix( 2, 1 );
		W.randomize();
		System.out.println( W );

		System.out.println( "outer(V,W) = " );
		System.out.println( A.outer( V, W ) );

		System.out.println( "Finished successfully!" );
	}

}
