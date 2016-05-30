import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Visualization: graphical output of Input and LinSup evaluated, with formula
 * draw Input in normalized mode, fits as well! give formula in original mode
 * 
 * @author psteger
 * @date Aug 1, 2010
 */
public class Visualization extends JPanel {
	private static final long	serialVersionUID	= 1L;
	private final Input		in_;
	private final Fitter		fit_;
	private final Vec		winsize_;
	private final int		border_;
	private final int		size_;

	/**
	 * default constructor
	 * 
	 * @param in Input, normalized
	 * @param fit Fitter object for normalized data
	 */
	Visualization(final Input in, final Fitter fit) {
		border_ = 50;
		size_ = 250;
		in_ = in;
		in_.normalize();
		System.out.println( in_ );
		final Vec s = in_.getS();
		double sx = 1 / s.getX();
		double sy = 1 / s.getY();
		sy = sy / sx * size_ + 2 * border_ + 80;// 80 for title
		// bar
		sx = size_ + 2 * border_;
		// winsize_ = new Vec( sx, sy );
		winsize_ = new Vec( size_ + 2 * border_, size_ + 2 * border_ + 80 );
		System.out.println( "winsize_ = " + winsize_ );
		fit_ = fit;
	}

	/**
	 * subroutine to draw an ellipse with semimajor axes a=b=r
	 * 
	 * @param g Graphics2D
	 * @param x position of center, x coordinate
	 * @param y position of center, y coordinate
	 * @param r radius
	 */
	static void drawCircle( final Graphics2D g, final int x, final int y, final int r ) {
		g.fillOval( x - r / 2, y - r / 2, 2 * r, 2 * r );
	}

	/**
	 * draw all points with error bars
	 * 
	 * @param g2d Graphics2D, plotting area
	 */
	void drawInput( final Graphics2D g2d ) {
		final int r = 5;

		// final Vec s = new Vec( in_.getS() );
		final double sx = size_;// 1 / s.getX();
		final double sy = size_;// 1 / s.getY();

		// draw error bars
		g2d.setColor( new Color( 202, 0, 0 ) );
		for ( int i = 0; i < in_.getN(); ++i ) {
			final Vec p = new Vec( in_.getPos( i ) );
			final Vec e = new Vec( in_.getErr( i ) );

			final int x = (int) (p.getX() * sx) + border_;
			final int y = (int) ((1 - p.getY()) * sy) + border_;
			final int ex = (int) (e.getX() * sx);
			final int ey = (int) (e.getY() * sy);
			final Polygon px = new Polygon();
			px.addPoint( x - ex, y );
			px.addPoint( x - ex, y - r / 2 );
			px.addPoint( x - ex, y + r / 2 );
			px.addPoint( x - ex, y );

			px.addPoint( x + ex, y );
			px.addPoint( x + ex, y - r / 2 );
			px.addPoint( x + ex, y + r / 2 );
			px.addPoint( x + ex, y );

			final Polygon py = new Polygon();
			py.addPoint( x, y - ey );
			py.addPoint( x - r / 2, y - ey );
			py.addPoint( x + r / 2, y - ey );
			py.addPoint( x, y - ey );

			py.addPoint( x, y + ey );
			py.addPoint( x - r / 2, y + ey );
			py.addPoint( x + r / 2, y + ey );
			py.addPoint( x, y + ey );
			g2d.drawPolygon( px );
			g2d.drawPolygon( py );
		}

		// draw data points
		g2d.setColor( new Color( 0, 0, 112 ) );
		for ( int i = 0; i < in_.getN(); ++i ) {
			final Vec v = new Vec( in_.getPos( i ) );
			// System.out.println( v.getX() );
			final int x = (int) (v.getX() * sx) + border_;
			// System.out.println( x );
			final int y = (int) ((1 - v.getY()) * sy) + border_;
			drawCircle( g2d, x - r / 2, y - r / 2, r );
		}
	}

	/**
	 * draw fitting formula
	 * 
	 * @param g2d Graphics2D object to be drawn to
	 */
	void drawFit( final Graphics2D g2d ) {
		g2d.setColor( new Color( 0, 90, 0 ) );
		// get x,y values
		// assumption: one FForm in LinSup
		// TODO: generalize later on
		// TODO: change coordinate system, x to right, y up
		// we plot in normalized mode, so plotting area is [0,1]x[0,1]
		// add together to Path2D
		final double sx = size_;
		final double sy = size_;
		final Path2D p = new Path2D.Double();

		System.out.println( "Path2D: " );
		final int startx = border_;
		final int starty = (int) (sy * (1 - fit_.getLs().eval( 0 )) + border_);
		p.moveTo( startx, starty );
		final double step = 0.1;
		for ( double i = step; i <= 1.0; i += step ) {
			final int x = (int) (sx * i) + border_;
			final int y = (int) (sy * (1 - fit_.getLs().eval( i )) + border_);
			System.out.println( "(" + x + "," + y + ")" );
			p.lineTo( x, y );
		}

		// actually draw function
		g2d.setColor( new Color( 0, 200, 10 ) );
		g2d.draw( p );
	}

	void drawFunction( Graphics2D g2d ) {
		g2d.setColor( new Color( 10, 10, 10 ) );
		int x = border_;
		int y = size_ + 2 * border_;
		g2d.drawString( "f(x)=0.00+1.00*(x*1.00+0.00)^2", x, y );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent( final Graphics g ) {
		super.paintComponent( g );
		final Graphics2D g2d = (Graphics2D) g;

		g2d.setBackground( new Color( 250, 250, 200 ) );
		g2d.setColor( new Color( 100, 100, 100 ) );
		g2d.drawRect( border_, border_, size_, size_ );

		drawInput( g2d );
		drawFit( g2d );
		drawFunction( g2d );

	}

	/**
	 * basic routine to open frame, show it
	 * 
	 * @param vis
	 */
	public void show( final Visualization vis ) {
		final JFrame frame = new JFrame( "Visualization" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.add( vis );
		frame.setSize( (int) winsize_.getX(), (int) winsize_.getY() );
		frame.setLocationRelativeTo( null );
		frame.setVisible( true );
	}

	/**
	 * test program
	 * 
	 * @param args none
	 */
	public static void main( final String[] args ) {
		System.out.println( "Input:" );
		final Input in = new Input( 10, 1, 2 );
		System.out.println( in );

		System.out.println( "Fitter with given function:" );
		final Fitter fit = new Fitter();
		System.out.println( "* SFunction: " );
		final SFunction sf = new SFunction( 1, 2 );
		System.out.println( sf );

		System.out.println( "* LinSup:" );
		final LinSup ls = new LinSup( sf );
		System.out.println( ls );

		fit.setLs( ls );
		final Visualization vis = new Visualization( in, fit );
		vis.show( vis );
	}
}
