package msi.ship;

import java.util.ArrayList;

import msi.Global;
import msi.ammo.HomingLock;
import msi.ammo.Projectile;
import msi.shapes.VPolygon;

public class Arti extends Turret{
	private boolean side;
	private int launch;
	private ArrayList<Projectile> ref;
	private int[] refC;
	
	public Arti() {
		super(5, 4, 10, 0, 0.5,new VPolygon(Global.models.get("artillery")), new VPolygon(Global.models.get("artilleryDetail")));
		icd = ICD;
	}

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
	public void shoot(ArrayList<Projectile> e, int[] c) {
		if(icd != ICD) return;
		launch = shots;
		ref = e;
		refC = c;
	}

	@Override
	public void fire(ArrayList<Projectile> e, int[] c) {
		HomingLock b = new HomingLock(36,self.getCenterX() + self.getScale()*Math.cos(self.getAngle()+Math.PI*1.2*(side?1:-1)), self.getCenterY() + self.getScale() * Math.sin(self.getAngle()+Math.PI*1.2*(side?1:-1)), self.getScale()*1.3, 500, vBullet, 3, new double[] {msi.main.GameTest.mouse[0],msi.main.GameTest.mouse[1]}, c);
		b.setAngle(spreadAngle()+Math.PI/3*(side?3.5:-3.5));
		side = !side;
		e.add(b);
	}

}
