package msi.ammo;

import msi.shapes.*;
import msi.Global;
import msi.main.GameTest;

public class Homing extends Projectile{
	private double vMag, turnCap;
	protected double explosion;
	protected boolean boom;
	protected double[] target;
	
	public Homing(int d, double x, double y, double l, int t, double v, double a, double[] target, int[] c) {
		super(new VPolygon(x,y,0,0,Global.models.get("rocket")),d, t, 0, 0, 1, c);
		((VPolygon)self).scale(l);
		self.setAlphaBorder(100);
		self.setStrokeWidth(2);
		vMag = v;
		this.target = target;
		turnCap = a;
		explosion = 1;
	}
	
	public void move() {
		double da = Math.atan2(target[1] - self.getCenterY(), target[0] - self.getCenterX()) - ((VPolygon)self).getAngle();
		if(Math.abs(da) > Math.PI) da = -Math.signum(da) * (Math.PI*2-Math.abs(da));
		((VPolygon)self).setAngle(Math.abs(da) < Math.toRadians(turnCap)?((VPolygon)self).getAngle() + da:((VPolygon)self).getAngle() + Math.signum(da)*Math.toRadians(turnCap));
		vx = Math.cos(((VPolygon)self).getAngle()) * vMag;
		vy = Math.sin(((VPolygon)self).getAngle()) * vMag;
		super.move();
		self.setAlphaFill(boom?100:255);
		if(dead() && !boom) {
			double d = ((VPolygon)self).getScale();
			int[] i = ((VPolygon)self).getFill();
			self = new VPolygon(self.getCenterX(),self.getCenterY(),0,0, Global.models.get("rocketSplash"));
			self.setFillColor(i);
			self.setAlphaBorder(0);
			((VPolygon)self).setAngle(Global.RANDOM.nextDouble()*Math.PI/2);
			((VPolygon)self).scale(d*explosion);
			time = 5;
			vMag = 0.01;
			boom = true;
			dmg /= 2;
			return;
		}
		if(dead() && boom) particle();
	}
	
	public void setAngle(double a) {
		((VPolygon)self).setAngle(a);
	}
	
	public void particle() {
		GameTest.spark.spawn(7, (float)(self.getCenterX()-vx), (float)(self.getCenterY()-vy), 3f, 3f, (float)((VPolygon)self).getScale()/4, 0, 0, 0.1f, self.getFill());		

	}
	
	public boolean dead() {
		return time <= 0;
	}
	
	public void kill() {
		super.kill();
		boom = true;
	}
}
