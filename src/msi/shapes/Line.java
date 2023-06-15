package msi.shapes;
import processing.core.PApplet;

public class Line extends Shape{
	private double x2,y2,slope,minX,minY,maxX,maxY;
	private boolean point;
	/**Constructor
	 * 
	 * @param x x of 1st point
	 * @param y y of 1st point	
	 * @param x2 x of 2nd point
	 * @param y2 y of 2nd point
	 */
	public Line(double x, double y, double x2, double y2) {
		super(x, y, null, null, 1);
		this.x2 = x2;
		this.y2 = y2;
		setDetails();
	}
	
	/**
	 * Constructor
	 * 
	 * @param xi x of 1st point
	 * @param yi y of 1st point
	 * @param angle angle from positive x axis
	 * @param length length of line
	 * @return
	 */
	public static Line angleLine(double xi, double yi, double angle, double length) {
		return new Line(xi, yi, xi + length * Math.cos(angle), yi + length * Math.sin(angle));
	}
	
	/**
	 * Moves second point as declared in constructor
	 * 
	 * @param x2 new x 
	 * @param y2 new y
	 */
	public void setPoint2(double x2, double y2) {
		this.x2 = x2;
		this.y2 = y2;
		setDetails();
	}
	
	
	/**
	 * Draws line using PApplet. If the line is a point a circle will be drawn.
	 * 
	 * @param drawer PApplet plane to draw to
	 */
	public void draw(PApplet drawer) {
		drawer.push();
		super.draw(drawer);
		if(point) {
			drawer.point((float)x, (float)y);
			return;
		}
		drawer.line((float)x, (float)y, (float)x2, (float)y2);
		drawer.pop();
	}
	
	/**
	 * Returns x intersection of infinite lines
	 * 
	 * @param other line to intersect
	 * @return x coordinate of intersection, middle point of 2 collinear lines
	 */
	public double getIntersectionX(Line other) {
		if(pointIntersection(other)) return other.x;
		if(collinearIntersection(other)) {
			return (Math.min(minX, other.minX) + Math.max(maxX, other.maxX))/2;
		}
		double x3 = other.x;
		double y3 = other.y;
		double x4 = other.x2;
		double y4 = other.y2;
		return ((x*y2 - y*x2)*(x3 - x4) - (x3*y4 - y3*x4)*(x-x2))/((x-x2)*(y3-y4)-(y-y2)*(x3-x4));
	}

	/**
	 * Returns y intersection of infinite lines
	 * 
	 * @param other line to intersect
	 * @return y coordinate of intersection, middle point of 2 collinear lines
	 */
	public double getIntersectionY(Line other) {
		if(pointIntersection(other)) return other.y;
		if(collinearIntersection(other)) {
			return (Math.min(minY, other.minY) + Math.max(maxY, other.maxY))/2;
		}
		double x3 = other.x;
		double y3 = other.y;
		double x4 = other.x2;
		double y4 = other.y2;
		return ((x*y2 - y*x2)*(y3 - y4) - (x3*y4 - y3*x4)*(y-y2))/((x-x2)*(y3-y4)-(y-y2)*(x3-x4));
	}

	/**
	 * Checks if lines intersect
	 * 
	 * @param other line to test intersection
	 * @return true if intersecting
	 */
	public boolean intersects(Line other) {
		double interX = getIntersectionX(other);
		double interY = getIntersectionY(other);
		if(interX >= Math.max(minX, other.minX) && (interX <= Math.min(maxX, other.maxX) && (interY >= Math.max(minY, other.minY) && (interY <= Math.min(maxY, other.maxY))))) {
			return true;
		}
		return false;
	}
	
	// exception for points
	private boolean pointIntersection(Line other) {
		Line line = null,point = null;
		if(other.point && this.point) {
			if(other.x == this.x && other.y == this.y) return true;
		} else if(other.point) {
			point = other;
			line = this;
		} else if(this.point) {
			point = this;
			line = other;
		}
		if(line != null && (((point.y - line.y)/(point.x - line.x) == line.slope || (point.y - line.y == 0 && point.x - line.x == 0)))) {
			return true;
		}
		return false;
	}
	// collinear lines
	private boolean collinearIntersection(Line other) {
		double x3 = other.x;
		double y3 = other.y;
		double x4 = other.x2;
		double y4 = other.y2;
		if(other.slope == slope) {
			if((x == x3 && y == y3) || (x == x4 && y == y4) || (x2 == x3 && y2 == y3) || (x2 == x3 && y == y3)) return true;
			else if((y4-y)/(x4-x) == slope) return true;
		}
		return false;
	}
	//calculate details
	private void setDetails() {
		point = ((y2-y == 0) && (x2-x == 0));

		minX = Math.min(x, x2);
		maxX = Math.max(x, x2);
		minY = Math.min(y, y2);
		maxY = Math.max(y, y2);
		slope = (y2-y)/(x2-x);
	}
	/**
	 * Set start and endpoints of line
	 * 
	 * @param d d[0] = x of 1st point
	 * 			d[1] = y of 1st point
	 * 			d[2] = x of 2nd point
	 * 			d[3] = y of 2nd point
	 */
	public void setLine(double[] d) {
		x = d[0];
		y = d[1];
		this.x2 = d[2];
		this.y2 = d[3];
		setDetails();
	}
	/**
	 * 
	 * @return slope
	 */
	public double getSlope() {
		return slope;
	}

	@Override
	public boolean intersects(Shape s) {
		if(s instanceof Line)
			return intersects((Line)s);
		else 
			return s.intersects(this);
	}

	@Override
	public boolean contains(double x, double y) {
		return pointIntersection(new Line(x,y,x,y));
	}

	@Override
	public double getArea() {
		return 0;
	}

	@Override
	public double getPerimeter() {
		return Math.sqrt(Math.pow(maxX-minX, 2) + Math.pow(maxY-minY, 2));
	}

	@Override
	public Rectangle getBoundingRectangle() {
		return new Rectangle(minX, minY, maxX-minX, maxY-minY);
	}
	
	/**
	 * 
	 * @return [x2,y2]
	 */
	public double[] getEndPoint() {
		return new double[] {x2,y2};
	}
	
	public double getCenterX() {
		return (x2+x)/2;
	}
	
	public double getCenterY() {
		return (y2+y)/2;
	}
	
	public void move(double dx, double dy) {
		super.move(dx, dy);
		x2 = x2+(dx-x);
		y2 = y2+(dy-y);
	}
	
	public void shift(double nx, double ny) {
		super.shift(nx, ny);
		x2 += nx;
		y2 += ny;
	}
}
