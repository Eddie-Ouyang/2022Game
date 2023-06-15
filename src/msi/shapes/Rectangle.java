package msi.shapes;
import processing.core.PApplet;

public class Rectangle extends Shape{
	private double x2,y2;
	
	/**
	 * Constructor
	 * 
	 * @param xA x coordinate of top left corner
	 * @param yA y coordinate of top left corner
	 * @param width width of rectangle
	 * @param height height of rectangle
	 */
	public Rectangle(double xA, double yA, double width, double height) {
		super(width < 0?xA + width:xA,height < 0?yA + height:yA,null,null,1);
		x2 = Math.max(xA, xA + width);
		y2 = Math.max(yA, yA + height);
	}
	
	/**
	 * Constructor
	 * 
	 * @param p p[0] =  x coordinate of top left corner
	 *			p[1] = y coordinate of top left corner
	 * 			p[2] = width of rectangle
	 * 			p[3] =  height of rectangle
	 */
	public Rectangle(double[] p) {
		this(p[0], p[1], p[2], p[3]);
	}
	
	/**
	 * Get points
	 * 
	 * @return Double array with all corners NW, NE, SE, SW
	 */
	public double[][] getPoints() {
		return new double[][] {{x,y},{x2,y},{x2,y2},{x,y2}};
	}
	
	@Override
	/**
	 * Draw rectangle using PApplet
	 * 
	 * @param p PApplet plane to draw to
	 */
	public void draw(PApplet p) {
		p.push();
		super.draw(p);
		p.rect((float)x, (float)y, (float)(x2 - x), (float)(y2 - y));
		p.pop();
	}
	
	/**
	 * Test intersection with other rectangle
	 * 
	 * @param other rectangle to test against
	 * @return true if intersecting
	 */
	public boolean intersects(Rectangle other) {
		return !(x >= other.x2 |x2 <= other.x |y >= other.y2 |y2 <= other.y);
	}
	
	/**
	 * Test touching with other rectangle
	 * Touching is defined strictly as sharing a border
	 * 
	 * @param other rectangle to test against
	 * @return true if touching perimeters
	 */
	public boolean touches(Rectangle other) {
		return (x2 - x + other.x2 - other.x) == Math.max(x2, other.x2) - Math.min(x, other.x) && !(y > other.y2 |y2 < other.y) |(y2 - y + other.y2 - other.y) == Math.max(y2, other.y2) - Math.min(y, other.y) && !(x > other.x2 |x2 < other.x);
//		return x == other.x |x == other.x2 |x2 == other.x |x2 == other.x2 |y == other.y |y == other.y2 |y2 == other.y |other.y2 == other.y2;
	}
	
	/**
	 * Test if a point is within the rectangle
	 * 
	 * @param x x coordinate of point
	 * @param y y coordinate of point
	 * @return true if point is contained
	 */
	public boolean contains(double xi, double yi) {
		return (xi >= x && xi <= x2) && (yi >= y && yi <= y2); 
	}
	
	/**
	 * Contain a rectangle entirely within this rectangle
	 * Borders can not be shared to be contained
	 * 
	 * @param other rectangle to test against
	 * @return true if rectangle is contained
	 */
	public boolean contains(Rectangle other) {
		return !(x > other.x |x2 < other.x2 |y > other.y |y2 < other.y2);
	}
	
	/**
	 * Checks if two rectangles have equal size
	 * 
	 * @param other rectangle to test against
	 * @return true if area is equal
	 */
	public boolean equalSize(Rectangle other) {
		return (other.x2 - other.x == x2 - x) && (other.y2 - other.y == y2 - y);
	}
	
	@Override
	/**
	 * Move the rectangle
	 * 
	 * @param dx change in x
	 * @param dy change in y
	 */
	public void shift(double dx, double dy) {
		super.shift(dx, dy);
		x2 += dx;
		y2 += dy;
	}
	
	@Override
	/**
	 * @return perimeter
	 */
	public double getPerimeter() {
		return 2 * (x2 - x) + 2 * (y2 - y);
	}
	
	@Override
	/**
	 * @return area
	 */
	public double getArea() {
		return (x2 - x) * (y2 - y);
	}
	
	@Override
	/**
	 * @return (x, y) (x2, y2)
	 */
	public String toString() {
		return super.toString() + " , (" + x2 + ", " + y2 + ")"; 
	}
	
	@Override
	public boolean intersects(Shape s) {
		if(s instanceof Rectangle)
				return intersects((Rectangle)s);
		else if(s instanceof Line || s instanceof Circle) {
			Line[] box = new Line[] {new Line(x,y,x2,y), new Line(x2,y,x2,y2), new Line(x,y2,x2,y2), new Line(x,y,x,y2)};
			for(Line l:box) {
				if(s.intersects(l)) return true;
			}
		} else {
			return s.intersects(this);
		}
		return false;
	}
	
	@Override
	public Rectangle getBoundingRectangle() {
		return this;
	}
	
	public double getWidth() {
		return x2-x;
	}
	
	public double getHeight() {
		return y2-y;
	}
	
	public double getCenterX() {
		return (x+x2)/2;
	}
	
	public double getCenterY() {
		return (y+y2)/2;
	}
	/**
	 * WNES = 0123
	 * 4 for corner
	 * @param x2
	 * @param y2
	 * @return
	 */
	public int exitCode(double x0, double y0) {
		double a = -1*Math.atan2(y0-(y2+y)/2, x0-(x2+x)/2);
		double a1 = -1*Math.atan(getHeight()/getWidth());
		double a2 = -1*Math.PI - a1;
		int xCode = (a <= -a2 && a > a1)?1:0;
		int yCode = (a > a2 && a <= -a1)?11:0;
//		if(Math.abs(a) == Math.abs(a1) || Math.abs(a) == Math.abs(a2)) return 4;
		return Math.abs(Integer.parseInt(yCode-xCode+"",2));
	}
	
	public void setX2(double nx, double ny) {
		x2 = nx;
		y2 = ny;
	}
	
	public void fix() {
		double xi = x, yi = y, xf = x2, yf = y2;
		x = Math.min(xf, xi);
		y = Math.min(yf, yi);
		x2 = Math.max(xf, xi);
		y2 = Math.max(yf, yi);
	}
}
