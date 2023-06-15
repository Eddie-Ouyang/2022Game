package msi.effects;
import java.util.*;
import processing.core.PApplet;

public class Sparkle {
	private ArrayList<Particle> particles, kill;
	private float x,y,spreadX,spreadY,s,ax,ay,d;
	private Random rand;
	private int[] c;
	
	// (x,y) position, (x,y) deviation, particle size, (x,y) acceleration, decay rate
	public Sparkle(float xi, float yi, float spreadx, float spready, float size, float axi, float ayi, float di) {
		x = xi;
		y = yi;
		spreadX = spreadx;
		spreadY = spready;
		s = size;
		ax = axi;
		ay = ayi;
		rand = new Random();
		particles = new ArrayList<Particle>();
		kill = new ArrayList<Particle>();
		c = new int[] {255,255,255};
		d = di;
	}
	
	// no args cons
	public Sparkle() {
		rand = new Random();
		particles = new ArrayList<Particle>();
		kill = new ArrayList<Particle>();
	}
		
	//draw particle system
	public void draw(PApplet pa) {
		for(Particle p:particles) {
			p.draw(pa);
		}
	}
	
	//apply motion to particles
	public void move() {
		for(Particle p:particles) {
			p.move();
			if(p.dead()) kill.add(p);
		}
		for(Particle p:kill) {
			particles.remove(p);
		}
		kill.clear();
	}
	
	//set new particles' color
	public void setColor(int a, int b, int c) {
		this.c = new int[] {a,b,c};
	}
	
	//set spawn location
	public void setLocation(float xi, float yi) {
		x = Float.isNaN(xi) ? x:xi;
		y = Float.isNaN(yi) ? y:yi;
	}
	
	//set acceleration
	public void setAcceleration(float xi, float yi) {
		ax = Float.isNaN(xi) ? ax:xi;
		ay = Float.isNaN(yi) ? ay:yi;
	}
	
	//set spread
	public void setSpread(float xi, float yi) {
		spreadX = Float.isNaN(xi) ? spreadX:xi;
		spreadY = Float.isNaN(yi) ? spreadY:yi;
	}
	
	//set particle size
	public void setSize(float si) {
		s = si;
	}
	
	//set particle decay rate
	public void setDecay(float di) {
		d = di;
	}
	
	//spawn i new particles
	public void spawn(int i) {
		for(;i > 0; i--)particles.add(new Particle(x+rand.nextFloat()-0.5f,y+rand.nextFloat()-0.5f,rand.nextFloat()*spreadX*2-spreadX, rand.nextFloat()*spreadY*2-spreadY, ax, ay, (rand.nextInt(30) + 70)/100.0f * s, Arrays.copyOf(c,c.length), d));
	}
	
	//overload
	public void spawn(int i, float x, float y) {
		for(;i > 0; i--)particles.add(new Particle(x+rand.nextFloat()-0.5f,y+rand.nextFloat()-0.5f,rand.nextFloat()*spreadX*2-spreadX, rand.nextFloat()*spreadY*2-spreadY, ax, ay, (rand.nextInt(30) + 70)/100.0f * s, Arrays.copyOf(c,c.length), d));
	}
	
	//overload 
	public void spawn(int i, float x, float y, float spreadX, float spreadY, float s, float ax, float ay, float d, int[] c) {
		for(;i > 0; i--)particles.add(new Particle(x+rand.nextFloat()-0.5f,y+rand.nextFloat()-0.5f,rand.nextFloat()*spreadX*2-spreadX, rand.nextFloat()*spreadY*2-spreadY, ax, ay, (rand.nextInt(30) + 70)/100.0f * s, Arrays.copyOf(c,c.length), d));
	}
}
