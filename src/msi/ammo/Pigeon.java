package msi.ammo;

import msi.Global;
import msi.shapes.Shape;
import msi.shapes.VPolygon;
import processing.core.PApplet;

public class Pigeon extends Projectile{
	private VPolygon target;

	public Pigeon(double d, double x, double y, double px, double py, double v, int[] c) {
		super(new VPolygon(px,py,0,0,Global.models.get("pigeon")), d, (int)Math.abs((px-x)/(v*Math.cos(Math.atan2(y-py, x-px)))), 0, 0, 0, c);
		((VPolygon)self).face(x, y);
		double a = Math.atan2(y-py, x-px);
		vx = v * Math.cos(a);
		vy = v * Math.sin(a);
		target = new VPolygon(x,y,0,0,Global.models.get("pigeonTarget"));
		target.setFillColor(222, 222, 222, 255);
		
	}
	
	public void draw(PApplet p) {
		target.draw(p);
		super.draw(p);
	}
	
	public void scale(double d) {
		((VPolygon)self).scale(d);
		target.scale(d*0.75);
	}
	
	public void move() {
		self.shift(vx, vy);
		time--;
	}
}
