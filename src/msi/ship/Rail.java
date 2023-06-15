package msi.ship;

import java.util.ArrayList;

import msi.Global;
import msi.ammo.Projectile;
import msi.ammo.Snipe;
import msi.shapes.Line;
import msi.shapes.VPolygon;

public class Rail extends Turret{

	public Rail() {
		super(2, 50, 1, 0, 0.5,new VPolygon(Global.models.get("rail")), new VPolygon(Global.models.get("railDetail")));
	}

	@Override
	public void tick() {
		icd -= icd>0?1:0;
	}

	@Override
	public double getICD() {
		return 1.0*(ICD-icd)/ICD;
	}

	@Override
	public void shoot(ArrayList<Projectile> e, int[] c) {
		if(icd > 0) return;
		icd = ICD;
		for (int i = 0; i < shots; i++) 
			fire(e,c);
	}

	@Override
	public void fire(ArrayList<Projectile> e, int[] c) {
		Line l = Line.angleLine(self.getCenterX(), self.getCenterY(), spreadAngle(), 30);
		l.setStrokeColor(c);
		l.setStrokeWidth(self.getScale()*0.2);
		rx += ratioRX()*7;
		ry += ratioRY()*7;
		e.add(new Snipe(l, 30, vxB(spreadAngle()), vyB(spreadAngle()), c));
		
	}

}
