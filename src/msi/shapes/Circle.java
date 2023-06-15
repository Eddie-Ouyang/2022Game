package msi.shapes;
import processing.core.PApplet;

public class Circle extends Shape{
	private double r;
	
	/**
	 * Constructor
	 * 
	 * @param xi x coordinate of center
	 * @param yi y coordinate of center
	 * @param ri radius of circle 
	 */
	public Circle(double xi, double yi, double ri) {
		super(xi, yi, null, null, 1);
		r = ri;
	}
	
	/**
	 * Constructor
	 * 
	 * @param d d[0] = x coordinate of center
	 * 			d[1] = y coordinate of center
	 * 			d[2] = radius of circle
	 */
	public Circle(double[] d) {
		this(d[0],d[1],d[2]);
	}
	
	@Override
	/**
	 * Draw circle using PApplet
	 * 
	 * @param p PApplet plane to draw to
	 */
	public void draw(PApplet p) {
		p.push();
		super.draw(p);
		p.circle((float)x, (float)y, (float)r*2);
		p.pop();
	}
	
	/**
	 * Test intersection with circle
	 * 
	 * @param c other circle to test against
	 * @return true if intersecting
	 */
	public boolean intersects(Circle c) {
		return  Math.sqrt(Math.pow(x - c.x, 2) + Math.pow(y - c.y, 2)) < r + c.r;
	}
	
	/**
	 * Test if a point is within circle
	 * 
	 * @param x x coordinate of point
	 * @param y y coordinate of point
	 * @return true if point is inside circle
	 */
	public boolean contains(double x, double y) {
		return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2)) < r;
	}
	
	/**
	 * Tests containment of circle
	 * 
	 * @param c other circle to test against
	 * @return true if other circle is contained
	 */
	public boolean contains(Circle c) {
		return (Math.max(r, c.r) > Math.sqrt(Math.pow(x - c.x, 2) + Math.pow(y - c.y, 2)) + Math.min(r, c.r));
	}
	
	/**
	 * Tests touching of circle
	 * Touching is strictly defined as circles sharing a point on perimeter
	 * 
	 * @param c other circle to test against
	 * @return true if touching perimeters
	 */
	public boolean touches(Circle c) {
		return Math.sqrt(Math.pow(x - c.x, 2) + Math.pow(y - c.y, 2)) == r + c.r;
	}
	
	@Override
	/**
	 * @return area
	 */
	public double getArea() {
		return Math.PI*r*r;
	}
	
	@Override
	/**
	 * @return perimeter
	 */
	public double getPerimeter() {
		return Math.PI*2*r;
	}
	
	
	/**
	 * 
	 * @return radius
	 */
	public double getRadius() {
		return r;
	}
	
	/**
	 * Tests if 2 circles have equal size
	 * 
	 * @param c other circle to test against
	 * @return true if equal area
	 */
	public double equalSize(Circle c) {
		return r = c.r;
	}
	
	@Override
	/**
	 * @return (x, y) radius = r
	 */
	public String toString() {
		return "(" + x + ", " + y + ")" + "radius = " + r;
	}

	@Override
	public boolean intersects(Shape s) {
		if(s instanceof Circle)
			return intersects((Circle)s);
		else if(s instanceof Line) {
			Line l = (Line)s;
			double[] d = l.getEndPoint();
			double a = Math.atan2(d[1]-l.y, d[0]-l.x) + Math.PI/2;
			Line test = new Line(x + r * Math.cos(a),y + r * Math.sin(a),x - r * Math.cos(a),y - r* Math.sin(a));
			return (contains(s.x,s.y) != contains(d[0],d[1])) || test.intersects(l);
		} else {
			return s.intersects(this);
		}
	}

	@Override
	public Rectangle getBoundingRectangle() {
		return new Rectangle(x-r, y-r, r*2, r*2);
	}
	
	@Override 
	public double getCenterX() {
		return x;
	}
	
	@Override
	public double getCenterY() {
		return y;
	}
}
