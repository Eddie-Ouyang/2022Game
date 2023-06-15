package msi.ammo;

import msi.Global;
import msi.main.GameTest;
import msi.shapes.VPolygon;
import processing.core.PApplet;

public class HomingLock extends Homing{
	private VPolygon home;
	public HomingLock(int d, double x, double y, double l, int t, double v, double a, double[] target, int[] c) {
		super(d, x, y, l, t, v, a, target, c);
		home = new VPolygon(target[0],target[1],0,0,Global.models.get("pigeonTarget"));
		home.setFillColor(255,255,255,255);
		home.scale(((VPolygon)self).getScale()/2);
		explosion = 2;
	}
	
	public void draw(PApplet p) {
		home.draw(p);
		super.draw(p);
	}
	
	public void move() {
		if(Math.abs(self.getCenterX()-target[0]) < 10 && Math.abs(self.getCenterY()-target[1]) < 10) {
			time = 0;
		}
		super.move();
	}
	
	public void particle() {
		GameTest.spark.spawn(5, (float)(self.getCenterX()-vx), (float)(self.getCenterY()-vy), 3f, 3f, (float)((VPolygon)self).getScale()/(boom?8:4), 0, 0, 0.1f, self.getFill());		
	}
	
	public void kill() {}
	
	public double dmg() {
		return time > 5?0:dmg;
	}
}
