package msi.ammo;

import msi.shapes.Line;
import msi.shapes.Shape;
import processing.core.PApplet;

public class Snipe extends Projectile{
	private Line trail;
	
	public Snipe(Shape s, int t, double x, double y, int[] c) {
		super(s, 100, t, x, y, 0, c);
		trail = new Line(s.getCenterX(), s.getCenterY(), s.getCenterX(), s.getCenterY());
		trail.setStrokeColor(c);
		trail.setStrokeWidth(s.getWidth()*1.3);
		trail.setAlphaBorder(40);
		particle = true;
	}
	
	public void draw(PApplet p) {
		trail.setPoint2(self.getCenterX(), self.getCenterY());
		trail.draw(p);
		super.draw(p);
	}
	
	public boolean dead() {
		return (vx == 0 && vy == 0) || time < 0;
	}
}
