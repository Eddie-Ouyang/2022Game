package msi.ammo;

import msi.main.GameTest;
import msi.shapes.*;

public class Fire extends Projectile{

	public Fire(int d, double x, double y, double l, int t, double vx, double vy, double f, int[]c) {
		super(new RPolygon(x,y,4,l), d, t, vx, vy, f, c);
		self.setAlphaBorder(0);
	}

	public void move() {
		int t = time;
		super.move();
		self.setAlphaFill((int)((time*1.0/Time*255)));
		if(t>0 && time == 0) {
			time = t;
			particle();
			time = 0;
		}
		((RPolygon)self).rotate(Math.PI/9);
		((RPolygon)self).setLength(((RPolygon)self).getLength()*0.95);
	}
	
	public void setAngle(double a) {
		((RPolygon)self).setAngle(a+Math.PI/2);
	}
	
	public double dmg() {
		return dmg*(1.0*time/Time);
	}
	
	protected void particle() {
		GameTest.spark.spawn(3, (float)(self.getCenterX()-vx), (float)(self.getCenterY()-vy), 5f, 5f, 10f*time/Time, (float)(-0.05*vx), (float)(-0.05*vy), 0.1f, self.getFill());
	}
}
