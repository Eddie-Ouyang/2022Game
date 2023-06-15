package msi.ship;

import java.util.ArrayList;

import msi.Global;
import msi.ammo.*;
import msi.shapes.VPolygon;

public class Flame extends Turret{
	private int shoot;
	
	public Flame() {
		super(4, 9, 1, 10, 1.2,new VPolygon(Global.models.get("flame")), new VPolygon(Global.models.get("flameDetail")));
		icd = ICD;
	}
	
	public void shoot(ArrayList<Projectile> e,  int[] c) {
		if(icd < 0)return;
		icd -= 6;
		if(icd < 45) shoot = 30;
		for (int i = 0; i < shots; i++) {
			fire(e,c);
		}
	}
	
	public void tick() {
		if(icd < ICD) icd+= shoot>0?0:2;
		shoot--;
	}
	
	public double getICD() {
		return icd >= 0?(double)(icd)/ICD:0;
	}

	public void fire(ArrayList<Projectile> e, int[] c) {
		double fAngle = spreadAngle();
		Fire b = new Fire(4,self.getCenterX() + Math.cos(self.getAngle())*1.75*self.getScale(),self.getCenterY() + Math.sin(self.getAngle())*1.75*self.getScale(),self.getScale()*0.8,35,vxB(fAngle),vyB(fAngle),0.05,c);
		b.setAngle(fAngle);
		rx += ratioRX()*0.2;
		ry += ratioRY()*0.2;
		e.add(b);
	}
}
