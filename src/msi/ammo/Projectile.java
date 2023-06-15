package msi.ammo;

import java.io.Serializable;

import msi.main.GameTest;
import msi.shapes.*;
import processing.core.PApplet;

public class Projectile implements Serializable{
	protected Shape self;
	protected final int Time;
	protected int time;
	protected boolean particle;
	protected double vx,vy,fr,dmg;
	
	public Projectile(Shape s, double d, int t, double x, double y, double f, int[] c) {
		dmg = d;
		self = s;
		time = t;
		Time = t;
		vx = x; 
		vy = y;
		fr = f;
		particle = true;
		self.setFillColor(c);
	}
	
	public void draw(PApplet p) {
		self.draw(p);
	}
	
	public void move() {
		double t = Math.atan2(vy, vx);
		double dx = Math.signum(vx) * Math.cos(t) * fr;
		double dy = Math.signum(vy) * Math.sin(t) * fr;
		vx = Math.signum(vx - Math.signum(vx)*dx) == Math.signum(vx)? vx - Math.signum(vx)*dx:0;
		vy = Math.signum(vy - Math.signum(vy)*dy) == Math.signum(vy)? vy - Math.signum(vy)*dy:0;	
		self.shift(vx, vy);
		time--;
		for(Shape s:GameTest.walls) {
			if(intersects(s)) {
				time = 0;
				if(particle)particle();
				return;
			}
		}
	}
	
	public void toggleParticle(boolean b) {
		particle = b;
	}
	
	protected void particle() {
		GameTest.spark.spawn(3, (float)(self.getCenterX()-vx), (float)(self.getCenterY()-vy), 5f, 5f, 5f, (float)(-0.05*vx), (float)(-0.05*vy), 0.1f, self.getFill());
	}
	
	
	public boolean dead() {
		return (vx == 0 && vy == 0) || time <= 0;
	}
	
	public boolean intersects(Shape s) {
		return (s.intersects(self) || s.contains(self.getCenterX(),self.getCenterY()) || s.intersects(new Line(self.getCenterX(),self.getCenterY(),self.getCenterX()-vx, self.getCenterY()-vy)));
	}
	
	public double dmg() {
		return dmg;
	}
	
	public void kill() {
		time = -5;
	}
	
	public Shape self() {
		return self;
	}
}
