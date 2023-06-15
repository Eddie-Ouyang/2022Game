package msi.ship;

import java.util.ArrayList;

import msi.Global;
import msi.ammo.Homing;
import msi.ammo.Projectile;
import msi.shapes.VPolygon;

public class Wire extends Turret{
	private boolean side;
	private int launch;
	private ArrayList<Projectile> ref;
	private int[] refC;
	
	public Wire() {
		super(2.5, 8, 6, 30, 0.9, new VPolygon(Global.models.get("wire")), new VPolygon(Global.models.get("wireDetail")));
		icd = ICD;
	}
	
	@Override
	public void shoot(ArrayList<Projectile> e, int[] c) {
		if(icd != ICD) return;
		launch = shots;
		ref = e;
		refC = c;
	}
	
	@Override
	public void tick() {
		if(icd < ICD && launch == 0) icd++;
		if(launch>0) {
			icd -= 5;
			if(icd%(ICD/shots) == 0) {
				launch--;
				fire(ref, refC);
			}
		}
	}
	
	@Override
	public double getICD() {
		return 1.0*icd/ICD;
	}

	@Override
	public void fire(ArrayList<Projectile> e, int[] c) {
		Homing b = new Homing(12,self.getCenterX() + self.getScale()*Math.cos(self.getAngle()+Math.PI*5/12*(side?1:-1)), self.getCenterY() + self.getScale() * Math.sin(self.getAngle()+Math.PI*5/12*(side?1:-1)), self.getScale()*1.3, 180, vBullet,3.5, msi.main.GameTest.mouse, c);
		b.setAngle(spreadAngle()+Math.PI/3*(side?1:-1));
		side = !side;
		e.add(b);
	}
	

	public void setFillColor(int[] c) {
		self.setFillColor(c);
	}
}
