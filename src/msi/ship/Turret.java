package msi.ship;
import java.io.Serializable;
import java.util.ArrayList;
import msi.Global;
import msi.ammo.Projectile;
import msi.shapes.VPolygon;
import processing.core.PApplet;

public abstract class Turret implements Serializable{
	protected VPolygon self, overlay;
	protected final int ICD,vBullet,shots;
	protected final double spread, weight;
	protected int icd;
	protected double rx,ry;
	
	public Turret(double cd,int vb,int sh, double sp, double w, VPolygon s, VPolygon o) {
		ICD = (int)(cd*Global.FRAMES_PER_SECOND);
		vBullet = vb;
		self = s;
		overlay = o;
		shots = sh;
		weight = w;
		spread = Math.toRadians(sp);
		self.setFillColor(Global.gunColor);
		self.setAlphaBorder(0);
		overlay.setFillColor(Global.gunOverlay);
		overlay.setAlphaBorder(0);
	}
	
	public void draw(PApplet p) {
		self.draw(p);
		overlay.draw(p);
	}
	
	public void face(double x, double y) {
		self.face(x, y);
		overlay.face(x, y);
	}
	
	public void setAngle(double a) {
		self.setAngle(a);
		overlay.setAngle(a);
	}
	
	public void shift(double dx, double dy) {
		self.shift(dx, dy);
		overlay.shift(dx, dy);
	}
	
	public void move(double nx, double ny) {
		self.move(nx, ny);
		overlay.move(nx, ny);
	}
	
	public void scale(double i) {
		self.scale(i);
		overlay.scale(i);
	}
	
	public abstract void tick();
	
	public abstract double getICD();

	public abstract void shoot(ArrayList<Projectile> e, int[] c);
	
	public abstract void fire(ArrayList<Projectile> e, int[] c);
	
	public double getRecoilX() {
		double c = rx;
		rx = 0;
		return c;
	}
	
	public double ratioRX() {
		return Math.cos(self.getAngle()-Math.PI);
	}
	
	public double getRecoilY() {
		double c = ry;
		ry = 0;
		return c;
	}
	
	public double ratioRY() {
		return Math.sin(self.getAngle()-Math.PI);
	}
	
	public double spreadAngle() {
		return self.getAngle() + Global.RANDOM.nextDouble() * 2 * spread - spread;
	}
	
	public double vxB(double a) {
		return Math.cos(a)*vBullet;
	}
	
	public double vyB(double a) {
		return Math.sin(a)*vBullet;
	}
	
	public double weight() {
		return weight;
	}
}
