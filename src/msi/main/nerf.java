package msi.main;
import msi.shapes.*;
import processing.core.PApplet;

public class nerf extends PApplet{
	private VPolygon v;
	private Circle c;
	private double vx,vy;
	
	public nerf() {
//		v = new VPolygon(100,300,5,5,new double[][] {{0,0},{60,0},{60,10},{5,10},{-5,30},{-10,25}});
		int l = 20;
		v = new VPolygon(200,200,0,0, new double[][]{{0.0, -0.25},{0.75, -0.5},{1.0, -0.25},{0.75, 0.0},{0.0, -0.25},{-0.75, 0.0},{-1.0, -0.25},{-0.75, -0.5}});
		v.scale(30);
	}
	
	public void settings() {
		setSize(800,600);
	}
	
	public void mousePressed() {
		double a = Math.atan2(mouseY-v.getCenterY(), mouseX-v.getCenterX());
		c = new Circle(v.getCenterX()+Math.cos(a)*60,v.getCenterY()+Math.sin(a)*60,5);
		vx = 19*Math.cos(a);
		vy = 19*Math.sin(a);
	}
	
	public void draw() {
		background(255);
		v.face(mouseX,mouseY);
		v.draw(this);
		if(c!=null) {
			c.draw(this);
			c.shift(vx, vy);
		}
		vy += .981;
	}
	
	public static void main(String[] args) {
		PApplet.runSketch(new String[]{""}, new nerf());
	}
}
