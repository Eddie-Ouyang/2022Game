package msi.ship;

import msi.Global;
import msi.ammo.Projectile;
import msi.main.GameTest;
import msi.shapes.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import processing.core.PApplet;

public class Tank implements Serializable{
	private final double hMax;
	private double vx, vy, health, vMax;
	private RPolygon self;
	private Turret gun;
	private ArrayList<Projectile> bullets;
	public int team;
	public int ID = new Random().nextInt();
	
	public Tank(RPolygon r, double h, int s, Turret t) {
		hMax = h;
		health = h;
		self = r;
		gun = t;
		team = s;
		gun.scale(self.getLength());
		gun.move(self.getCenterX(), self.getCenterY());
		self.setStrokeWidth(5);
		self.setStrokeColor(0, 133, 168, 127);
		bullets = new ArrayList<Projectile>();
		setSpeed();
	}
	
	public void setFill(int a, int b, int c, int d) {
		self.setFillColor(a, b, c, d);
		self.setStrokeColor((int)(a*0.8), (int)(b*0.8), (int)(c*0.8), 127);
	}
	
	public void draw(PApplet p) {
		for(Projectile s : bullets) {
			s.draw(p);
		}
		if(health > 0) {
			gun.draw(p);
			self.draw(p);
			p.push();
			p.fill(0,255,0);
			p.rect((float)(self.getCenterX()-self.calcr()*3/5), (float)(self.getCenterY()-self.calcr()/5), (float)(self.calcr()*6/5*health/hMax), (float)self.calcr()/5);
			p.pop();
			p.push();
			p.fill(0,0,255);
			p.rect((float)(self.getCenterX()-self.calcr()*3/5), (float)self.getCenterY(), (float)(self.calcr()*1.2*gun.getICD()), (float)self.calcr()/5);
			p.pop();
		}
	}
	
	public void face(double x, double y) {
		self.setAngle(Math.atan2(y-self.getCenterY(), x-self.getCenterX())-Math.PI/2);
		gun.face(x,y);
	}
	
	public void friction(double f) {
		f = vMax*f;
		double t = Math.atan2(vy, vx);
		double dx = Math.signum(vx) * Math.cos(t) * Math.abs(f);
		double dy = Math.signum(vy) * Math.sin(t) * Math.abs(f);
		vx = Math.signum(vx - Math.signum(vx)*dx) == Math.signum(vx) ? vx - Math.signum(vx)*dx : 0;
		vy = Math.signum(vy - Math.signum(vy)*dy) == Math.signum(vy) ? vy - Math.signum(vy)*dy : 0;
	}
	
	public void move() {
		ArrayList<Projectile> kill = new ArrayList<Projectile>();
		for(Projectile s : bullets) {
			s.move();
			if(s.dead()) kill.add(s);
		}
		for(Projectile s : kill) {
			bullets.remove(s);
		}
		for(Tank e:Global.PLAYER_LIST) {
			if(e.team!=team) {
				for(Projectile p:e.bullets) {
					if(p.intersects(self)) {
						health -= p.dmg();
						p.kill();
					}
				}
			}
		}
		self.shift(vx, vy);
		gun.shift(vx, vy);
		gun.tick();
		
		if(!GameTest.world.contains(self.getCenterX(), self.getCenterY())) {
			boolean xOut = self.getCenterX() < GameTest.world.getX() || self.getCenterX() > GameTest.world.getX() + GameTest.world.getWidth();
			boolean yOut = self.getCenterY() < GameTest.world.getY() || self.getCenterY() > GameTest.world.getY() + GameTest.world.getHeight();
			self.shift((xOut?-1:0)*vx, (yOut?-1:0)*vy);
			gun.shift((xOut?-1:0)*vx, (yOut?-1:0)*vy);
			vx = xOut?0:vx;
			vy = yOut?0:vy;
		}
	}
	
	public void accelerate(double ax, double ay) { 
		vx = Math.abs(vx + ax) < vMax?vx+ax:(Math.abs(vx) > vMax && (Math.signum(vx) != Math.signum(ax))?vx+ax:vx);
		vy = Math.abs(vy + ay) < vMax?vy+ay:(Math.signum(vy)*vMax);
	}
	
	public void shoot() {
		gun.shoot(bullets, self.getFill());
		vx += gun.getRecoilX();
		vy += gun.getRecoilY();
	}
	
	public Shape shape() {
		return self;
	}
	
	public void setHP(double h) {
		health = h;
	}
	
	public double getHP() {
		return health;
	}
	
	public void setGun(Turret t) {
		gun = t;
		gun.scale(self.getLength());
		gun.move(self.getCenterX(), self.getCenterY());
		setSpeed();
	}
	
	public double x() {
		return self.getCenterX();
	}
	
	public double y() {
		return self.getCenterY();
	}
	
	public double vMax() {
		return vMax;
	}
	
	public double getAngle() {
		return self.getAngle();
	}
	
	public void setAngle(double a) {
		self.setAngle(a-Math.PI/2);
		gun.setAngle(a);
	}
	
	private void setSpeed() {
		vMax = 5 ;
	}
}
