package msi.shapes;

import processing.core.PApplet;

public class VPolygon extends Shape{
	private double[][] vertex,master;
	private double angle,scale,xr,yr;

	public VPolygon(double xi, double yi, double xr, double yr, double[][] v) {
		super(xi, yi, null, null, 1);
		vertex = new double[v.length][2];
		master = new double[v.length][2];
		for(int x = 0, y = 0; y < vertex.length; y += x%2, x = (x+1)%2) {
			vertex[y][x] = v[y][x]-(x==0?yr:xr);
			master[y][x] = v[y][x];
		}
		this.xr = xr;
		this.yr = yr;
		scale = 1;
	}
	
	public VPolygon(double[][] v) {
		super(0,0,null,null,1);
		vertex = new double[v.length][2];
		master = new double[v.length][2];
		for(int x = 0, y = 0; y < vertex.length; y += x%2, x = (x+1)%2) {
			vertex[y][x] = v[y][x];
			master[y][x] = v[y][x];
		}
		scale = 1;
	}
	
	public void draw(PApplet p) {
		super.draw(p);
		p.beginShape();
		for(double[] v:vertex) {
			p.vertex((float)(v[0]+x),(float)(v[1]+y));
		}
		p.vertex((float)(vertex[0][0]+x), (float)(vertex[0][1]+y));
		p.endShape();
	}

	public boolean intersects(Shape s) {
		if(s instanceof RPolygon) {
			for(Line l:getLines()) {
				for(Line k: ((RPolygon)s).getLines()) if(l.intersects(k)) return true;
			}
		} else if (s instanceof VPolygon) {
			for(Line l:getLines()) {
				for(Line k: ((VPolygon)s).getLines()) if(l.intersects(k)) return true;
			}
		} else {
			for(Line l:getLines()) {
				if(s.intersects(l)) return true;
			}
		}
		return false;
	}

	@Override
	public boolean contains(double xi, double yi) {
		Line test = new Line(x,y,xi,yi);
		for(Line l:getLines()) {
			if(test.intersects(l)) return true;
		}
		return false;
	}
	
	@Override
	public double getArea() {
		return 0;
	}

	@Override
	public double getPerimeter() {
		double p = 0;
		for(int i = 0; i < vertex.length; i++) {
			p += Math.sqrt(Math.pow(vertex[(i+1)%vertex.length][0] - vertex[i][0],2) + Math.pow(vertex[(i+1)%vertex.length][1] - vertex[i][1],2));
		}
		return p;
	}

	@Override
	public Rectangle getBoundingRectangle() {
		double minX = 0,maxX = 0,minY = 0,maxY = 0;
		for(double[] d:vertex) {
			minX = Math.min(d[0], minX);
			minY = Math.min(d[1], minY);
			maxX = Math.max(d[0], maxX);
			maxY = Math.max(d[1], maxY);
		}
		return new Rectangle(minX,minY,maxX-minX,maxY-minY);
	}

	@Override
	public double getCenterX() {
		return x;
	}

	@Override
	public double getCenterY() {
		return y;
	}
	
	public Line[] getLines() {
		Line[] l = new Line[vertex.length];
		for(int i = 0; i < vertex.length; i++) {
			l[i] = new Line(vertex[i][0]+x,vertex[i][1]+y,vertex[(i+1)%vertex.length][0]+x,vertex[(i+1)%vertex.length][1]+y);
		}
		return l;
	}
	
	public void setAngle(double a) {
		resetAngle();
		angle = a;
		for(double[] v:vertex) {
			double ar = a + Math.atan2(v[1], v[0]);
			double r = Math.sqrt(Math.pow(v[0], 2) + Math.pow(v[1], 2));
			v[0] = Math.cos(ar)*r;
			v[1] = Math.sin(ar)*r;
		}
	}
	
	public void resetAngle() {
		angle = 0;
		for(int x = 0, y = 0; y < vertex.length; y += x%2, x = (x+1)%2) {
			vertex[y][x] = (master[y][x]-(x==0?yr:xr)) * scale;
		}
	}
	
	public void face(double x, double y) {
		angle = Math.atan2(y-getCenterY(), x-getCenterX());
		setAngle(angle);
	}
	
	public void scale(double i) {
		scale = i;
		for(int x = 0, y = 0; y < vertex.length; y += x%2, x = (x+1)%2) {
			vertex[y][x] *= scale;
		}
	}
	
	public double getAngle() {
		return angle;
	}
	
	public double getScale() {
		return scale;
	}
	
	public void setRCenter(double xi, double yi) {
		xr = Double.isNaN(xi)?xr:xi;
		yr = Double.isNaN(yi)?yr:yi;
	}
}
