package msi.ship;

import java.util.ArrayList;

import msi.Global;
import msi.ammo.Projectile;
import msi.shapes.Circle;
import msi.shapes.VPolygon;

public class Penta extends Turret{
	private int barrel;
	
	public Penta() {
		super(0.05, 6, 1, 15, 0.7,new VPolygon(Global.models.get("penta")), new VPolygon(Global.models.get("pentaDetail")));
	}

	@Override
	public void tick() {
		icd -= icd>0?1:0;
	}

	@Override
	public double getICD() {
		return 1;
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
		double fa = spreadAngle() + Math.PI*6*barrel/5;
		barrel = (barrel+1)%5;
		Circle b = new Circle(self.getCenterX(), self.getCenterY(), self.getScale()*0.1);
		b.setAlphaBorder(0);
		rx += Math.cos(fa)*3;
		ry += Math.sin(fa)*3;
		e.add(new Projectile(b,3,300,vxB(fa), vyB(fa), 0.05, c));
	}

}
