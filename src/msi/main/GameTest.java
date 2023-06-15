package msi.main;

import msi.Global;
import msi.effects.Sparkle;
import msi.shapes.*;
import msi.ship.*;
import net.client.Client;
import processing.core.PApplet;

public class GameTest extends PApplet{
	public static Shape[] walls = new Shape[] {
											   new Rectangle(0,0,1280,30),
											   new Rectangle(0,0,30,720),
											   new Rectangle(1250,0,30,720),
											   new Rectangle(0,690,1280,30)};
	public static Sparkle spark = new Sparkle();
	public static double[] mouse;
	public static Rectangle world = new Rectangle(50, 50, 1180, 620);
	private float scale;
	private Tank tank;
	private int[] move;
	private boolean auto,spin;
	private int gun;
	private double angle;
	
	public GameTest() {
		tank = new Tank(new RPolygon(640,360,5,36),100,0,new Spread());
		tank.setFill(255, 179, 0, 255);
		Global.PLAYER_LIST.add(tank);
		Global.CUR_PLAYER_TANK = tank;
		Tank enemy = new Tank(new RPolygon(200,200,5,100),100,1,new Spread());
		enemy.setFill(0, 179, 255, 255);
		enemy.face(500, 200);
		Global.PLAYER_LIST.add(enemy);
		move = new int[5];
		scale = 2f;
		mouse = new double[2];
		world.setFillColor(222, 222, 222, 255);
		world.setAlphaBorder(0);
		for(Shape s:walls) {
			s.setAlphaBorder(0);
			s.setFillColor(20, 20, 20, 255);
		}
		
		@SuppressWarnings("unused") // everything is managed in the constructor
		Client C = new Client();
	}
	
	public void settings() {
		fullScreen();
	}
	
	public void keyPressed() {
		switch(keyCode) {
		case 'W': move[0] = -1;
			break;
		case 'A': move[2] = -1;
			break;
		case 'S': move[1] = 1;
			break;
		case 'D': move[3] = 1;
			break;
		case ' ': move[4] = 1;
			break;
		case 'E': auto = !auto;
			break;
		case 'C': spin = !spin;
			angle = tank.getAngle()+Math.PI/2;
			break;
		case 'H': for(Tank t:Global.PLAYER_LIST) t.setHP(100);
			break;
		case 'G':
			gun++;
			switch(gun % 7) {
			case 0: tank.setGun(new Spread());
				break;
			case 1: tank.setGun(new Flame());
				break;
			case 2: tank.setGun(new Wire());
				break;
			case 3: tank.setGun(new Rail());
				break;
			case 4: tank.setGun(new Beacon());
				break;
			case 5: tank.setGun(new Penta());
				break;
			case 6: tank.setGun(new Arti());
				break;
			}
			break;
		}
	}
	
	public void keyReleased() {
		switch(keyCode) {
		case 'W': move[0] = 0;
			break;
		case 'A': move[2] = 0;
			break;
		case 'S': move[1] = 0;
			break;
		case 'D': move[3] = 0;
			break;
		case ' ': move[4] = 0;
			break;
		}
	}
	
	public void mousePressed() {
		switch(mouseButton) {
		case LEFT:tank.shoot();
			break;
		}
	}
	
	public void draw() {
		frameRate(Global.FRAMES_PER_SECOND);
		background(72);
		scale(scale);
		if(!spin) {
			mouse[0] = (mouseX)/scale;
			mouse[1] = (mouseY)/scale;}
		else {
			angle+=Math.PI/180;
			mouse[0] = tank.x() + 300*Math.cos(angle);
			mouse[1] = tank.y() + 300*Math.sin(angle);
		}
		tank.face(mouse[0], mouse[1]);
		tank.accelerate((move[2]+move[3])*tank.vMax()/10, (move[0]+move[1])*tank.vMax()/10);
		if(move[4] == 1 || (mousePressed&&mouseButton==LEFT) || auto) tank.shoot();
		for(Tank t:Global.PLAYER_LIST) {
			t.move();
		}
		tank.friction(0.03);
		spark.move();
		
		for(Shape s:walls) {
			s.draw(this);
		}
		for(Tank t:Global.PLAYER_LIST) {
			if(t.getHP() < 0) t.setHP(100);
			t.draw(this);
		}
		spark.draw(this);	
	}
	
	public static void main(String[] args) {
		PApplet.runSketch(new String[]{""}, new GameTest());
	}
}
