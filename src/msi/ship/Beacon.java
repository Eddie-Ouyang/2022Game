package msi.ship;

import java.util.ArrayList;

import msi.Global;
import msi.ammo.Pigeon;
import msi.ammo.Projectile;
import msi.shapes.VPolygon;

public class Beacon extends Turret{
	public Beacon() {
		super(0.2, 6, 1, 13, 1, new VPolygon(Global.models.get("beacon")), new VPolygon(Global.models.get("beaconDetail")));
	}

	@Override
	public void tick() {
		icd -= icd>0?1:0;
	}

	@Override
	public double getICD() {
		return (1.0*(ICD-icd)/ICD);
	}

	@Override
	public void shoot(ArrayList<Projectile> e, int[] c) {
		if(icd > 0) return;
		icd = ICD;
		for(int i = 0; i < shots; i++) {
			fire(e,c);
		}
	}

	@Override
	public void fire(ArrayList<Projectile> e, int[] c) {
		double r = self.getScale()*(1.5+Global.RANDOM.nextDouble());
		Pigeon p = new Pigeon(13, self.getCenterX() + r * Math.cos(spreadAngle()), self.getCenterY() + r * Math.sin(spreadAngle()), 
								 self.getCenterX() + (r+500) * Math.cos(spreadAngle()), self.getCenterY() + (r+500) * Math.sin(spreadAngle()), vBullet, c);
		p.scale(self.getScale());
		e.add(p);
	}

}
