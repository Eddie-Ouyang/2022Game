package msi.ship;

import java.util.ArrayList;
import msi.Global;
import msi.ammo.Projectile;
import msi.shapes.*;

public class Spread extends Turret{

	public Spread() {
		super(1.2, 12, 8, 7, 0.9, new VPolygon(Global.models.get("spread")), new VPolygon(Global.models.get("spreadDetail")));
	}

	@Override
	public void fire(ArrayList<Projectile> e, int[] c) {
		double fAngle = spreadAngle();
		double vb = (Math.abs(Global.RANDOM.nextInt(500)+500.0)/1000+0.8);
		Circle b = new Circle(self.getCenterX(), self.getCenterY(), self.getScale()/5);
		b.setStrokeColor(0, 0, 0, 0);
		rx += ratioRX()*0.5;
		ry += ratioRY()*0.5;
		e.add(new Projectile(b, 7, 240, vxB(fAngle)*vb, vyB(fAngle)*vb, 0.1,c));
	}
	
	public void tick() {
		icd -= icd>0?1:0;
		double ra = 1.0*icd/ICD;
		self.setRCenter(Double.NaN, ra < 0.8?0:1.5*(ra-0.8));
	}
	
	public double getICD() {
		return (1.0*ICD-icd)/ICD;
	}
	
	public void shoot(ArrayList<Projectile> e, int[] c){
		if(icd != 0) return;
		icd = ICD;
		self.setRCenter(Double.NaN,0.25);
		for(int i = 0; i < shots; i++) {
			fire(e,c);
		}
	}
}
