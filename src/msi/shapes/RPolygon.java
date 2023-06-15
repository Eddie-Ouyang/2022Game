package msi.shapes;

import processing.core.PApplet;

public class RPolygon extends Shape{
	private int sides;
	private double length,angle;
	
	/**
	 * Creates a regular polygon, all sides are equal length all internal angles are equal degrees
	 * 
	 * @param xi center x
	 * @param yi center y
	 * @param s number of sides
	 * @param li length of sides
	 */
	public RPolygon(double xi, double yi, int s, double li) {
		super(xi, yi, null, null, 1);
		sides = s;
		x = xi;
		y = yi;
		length = Math.abs(li);
	}
	
	/**
	 * Draw regular polygon
	 */
	public void draw(PApplet p) {
		p.push();
		super.draw(p);
		double a = Math.PI*2/sides;
		double r = length/2/Math.sin(a/2);
		double c = Math.PI/2 * (sides-2)/sides;
		p.beginShape();
		for(double i = 0, nx = 0, ny = 0; i < sides; i++) {
			nx = x + r*Math.cos(i*a+c+angle);
			ny = y + r*Math.sin(i*a+c+angle);
			p.vertex((float)nx,(float)ny);
		}
		p.endShape(PApplet.CLOSE);
		p.pop();
	}

	/**
	 * Tests for intersection against other shape
	 * @param s Shape to test against
	 * @return True if intersecting
	 */
	public boolean intersects(Shape s) {
		if(s instanceof RPolygon) {
			for(Line l:getLines()) {
				for(Line k: ((RPolygon)s).getLines()) if(l.intersects(k)) return true;
			}
		} else if (s instanceof VPolygon) {
			for(Line l:getLines()) {
				for(Line k: ((VPolygon)s).getLines()) if(l.intersects(k)) return true;
			}
		} else {
			for(Line l:getLines()) {
				if(s.intersects(l)) return true;
			}
		}
		return false;
	}
	
	/**
	 * Rotate the polygon 
	 * @param a Rotation in radians clockwise
	 */
	 
	public void rotate(double a) {
		angle += a;
	}
	
	/**
	 * Set the angle of the polygon
	 * @param a Angle in radians
	 */
	public void setAngle(double a) {
		angle = a;
	}

	/**
	 * Checks if a point is inside the polygon
	 * @return True if inside
	 */
	public boolean contains(double xi, double yi) {
		Line test = new Line(x,y,xi,yi);
		if(Math.sqrt(xi*xi+yi*yi) > calcR())return false;
		for(Line l:getLines()) {
			if(test.intersects(l)) return false;
		}
		return true;
	}

	/**
	 * @return Area of polygon
	 */
	public double getArea() {
		return calcR()*calcR()*sides/2*Math.sin(Math.PI*2/sides);
	}

	/** 
	 * @return Perimeter of polygon
	 */
	public double getPerimeter() {
		return length*sides;
	}

	/**
	 * @return Bounding rectangle of the average of the inscribed circle and circumscribed circle
	 */
	public Rectangle getBoundingRectangle() {
		return new Circle(x,y,(calcr()+calcR())/2).getBoundingRectangle();
	}

	/**
	 * @return Center x
	 */
	public double getCenterX() {
		return x;
	}

	/**
	 * @return Center y
	 */
	public double getCenterY() {
		return y;
	}
	
	/** 
	 * @return Inner angle of vertex
	 */
	public double vertexAngle() {
		return (sides-2.0)/sides*Math.PI;
	}
	
	/**
	 * 
	 * @return Radius of inscribed circle
	 */
	public double calcr() {
		return length/2/Math.tan(Math.PI/sides);
	}
	
	/**
	 * 
	 * @return Radius of circumscribed circle
	 */
	public double calcR() {
		return length/2/Math.sin(Math.PI/sides);
	}
	
	/**
	 * Change length to fit circumscribed circle
	 * @param R Radius of circumscribed circle
	 */
	public void setR(double R) {
		length = R*2*Math.sin(Math.PI/sides);
	}
	
	/**
	 * 
	 * @return angle of rotation
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * 
	 * @param s Number of sides
	 */
	public void setSides(int s) {
		sides = s;
	}
	
	/**
	 * 
	 * @return Number of sides
	 */
	public int getSides() {
		return sides;
	}
	
	/**
	 * 
	 * @param l Length of side
	 */
	public void setLength(double l) {
		length = l;
	}
	
	/**
	 * 
	 * @return Length of side
	 */
	public double getLength() {
		return length;
	}
	
	/**
	 * 
	 * @return Array of lines that make up the shape's perimeter
	 */
	public Line[] getLines() {
		Line[] l = new Line[sides];
		double a = Math.PI*2/sides;
		double r = length/2/Math.sin(a/2);
		double c = Math.PI/2 * (sides-2)/sides;
		for(double i = 0, px = x + r*Math.cos(c+angle), py = y + r*Math.sin(c+angle), nx = 0, ny = 0; i < sides; i++) {
			nx = x + r*Math.cos((i+1)*a+c+angle);
			ny = y + r*Math.sin((i+1)*a+c+angle);
			l[(int)i] = new Line(px,py,nx,ny);
			px = nx;
			py = ny;
		}
		return l;
	}
}
