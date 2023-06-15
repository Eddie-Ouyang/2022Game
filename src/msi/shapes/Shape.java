package msi.shapes;

import java.io.Serializable;

import processing.core.PApplet;

public abstract class Shape implements Serializable{
	protected double x,y,strokeWidth;
	private int[] fillColor, strokeColor;
	
	/**Tests whether this shape intersects another shape. All shape pairs function 
	 * 
	 * @param s The other shape to test against
	 * @return True if overlapping perimeters
	 */
	public abstract boolean intersects(Shape s);
	
	/**
	 * Tests whether this shape contains a point
	 * 
	 * @param x X coordinate of Point
	 * @param y Y coordinate of Point
	 * @return True if point is inside or on the perimeter of shape
	 */
	public abstract boolean contains(double x, double y);
	
	/**
	 * Return area of shape
	 * 
	 * @return Area, Area is 0 for lines
	 */
	public abstract double getArea();
	
	/**
	 * Return perimeter of shape
	 * 
	 * @return Perimeter
	 */
	public abstract double getPerimeter();
	
	/**
	 * Bounding rectangle that exactly surrounds the shape
	 * 
	 * @return Bounding Rectangle
	 */
	public abstract Rectangle getBoundingRectangle();
	
	/**
	 * 
	 * @return Center x coordinate
	 */
	public abstract double getCenterX();
	
	/**
	 * 
	 * @return Center y coordinate
	 */
	public abstract double getCenterY();
	
	public void draw(PApplet p) {
		p.fill(fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
		p.stroke(strokeColor[0], strokeColor[1], strokeColor[2], strokeColor[3]);
		p.strokeWeight((float)strokeWidth);
	}
	
	/**
	 * Check subclass documentation for details
	 * 
	 * @return String representation
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	/**
	 * 
	 * @return X
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * 
	 * @return Y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Change fill color
	 * 
	 * @param r 0-255 Red
	 * @param g 0-255 Green
	 * @param b 0-255 Blue
	 * @param a 0-255 Alpha
	 */
	public void setFillColor(int r, int g, int b, int a) {
		fillColor = new int[] {r,g,b,a};
	}
	
	/**
	 * Change border color
	 * 
	 * @param r 0-255 Red
	 * @param g 0-255 Green
	 * @param b 0-255 Blue
	 * @param a 0-255 Alpha
	 */
	public void setStrokeColor(int r, int g, int b, int a) {
		strokeColor = new int[] {r,g,b,a};
	}
	
	public void setAlphaFill(int a) {
		fillColor[3] = a;
	}
	
	public void setAlphaBorder(int a) {
		strokeColor[3] = a;
	}
	
	/**
	 * Change border width
	 * @param i Width
	 */
	public void setStrokeWidth(double i) {
		strokeWidth = i;
	}
	
	/**
	 * Translate the shape
	 * 
	 * @param dx Delta x
	 * @param dy Delta y
	 */
	public void shift(double dx, double dy) {
		x += dx;
		y += dy;
	}
	
	/**
	 * Moves the shape
	 * 
	 * @param dx New x
	 * @param dy New y
	 */
	public void move(double dx, double dy) {
		x = dx;
		y = dy;
	}
	
	/**
	 * Constructor
	 * 
	 * @param xi X coordinate of shape
	 * @param yi Y coordinate of shape
	 * @param fill Fill color (r,g,b,a) 0-255
	 * @param border Border color (r,g,b,a) 0-255
	 * @param stroke Border width
	 */
	public Shape(double xi, double yi, int[] fill, int[] border, int stroke) {
		x = xi;
		y = yi;
		fillColor = fill == null? new int[] {0,0,0,0}:fill;
		strokeColor = border == null? new int[] {0,0,0,255}:border;
		strokeWidth = stroke;
	}
	
	public int[] getFill() {
		return new int[] {fillColor[0], fillColor[1], fillColor[2], fillColor[3]};
	}
	
	public int[] getStroke() {
		return new int[] {strokeColor[0], strokeColor[1], strokeColor[2], strokeColor[3]};
	}
	
	public void setFillColor(int[] a) {
		fillColor = new int[] {a[0], a[1], a[2], a[3]};
	}
	
	public void setStrokeColor(int[] a) {
		strokeColor = new int[] {a[0], a[1], a[2], a[3]};
	}
	
	public double getWidth() {
		return strokeWidth;
	}
}
