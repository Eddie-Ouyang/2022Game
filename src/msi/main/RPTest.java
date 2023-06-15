package msi.main;
import java.util.Arrays;

import msi.shapes.*;
import processing.core.PApplet;

public class RPTest extends PApplet{
	private RPolygon a,b;
	
	public RPTest() {
		a = new RPolygon(200,300,4,50);
		b = new RPolygon(400,300,4,50);
		a.setFillColor(255, 0, 0, 255);
		b.setFillColor(0, 0, 255, 255);
	}
	
	public void keyPressed() {
		switch(keyCode) {
		case 'W': a.setSides(a.getSides()+1);
			break;
		case 'S': a.setSides(a.getSides()-1);
			break;
		case 'P': b.setSides(b.getSides()+1);
			break;
		case 'L': b.setSides(b.getSides()-1);
			break;
		case 'E': a.setAngle(Math.atan2(mouseY-a.getCenterY(), mouseX-a.getCenterX())-Math.PI/2);
			break;
		case 'O': b.setAngle(Math.atan2(mouseY-b.getCenterY(), mouseX-b.getCenterX())-Math.PI/2);
			break;
		}
	}
	
	public void mousePressed() {
		if(keyCode == '1') {
			a.move(mouseX, mouseY);
		} else if(keyCode == '2'){
			b.move(mouseX, mouseY);
		}
	}
	
	public void mouseDragged() {
		if(keyCode == '1') {
			a.setR(Math.sqrt(Math.pow(mouseX-a.getCenterX(), 2) + Math.pow(mouseY-a.getCenterY(), 2)));
		} else if(keyCode == '2') {
			b.setR(Math.sqrt(Math.pow(mouseX-b.getCenterX(), 2) + Math.pow(mouseY-b.getCenterY(), 2)));
		}
	}
	
	public void settings() {
		setSize(600,600);
	}
	
	public void draw() {
		background(255);
		a.draw(this);
		b.draw(this);
//		VPolygon v = new VPolygon(300,300,0,0, new double[][]{{-1.25, 0.0},{-1.0, -0.75},{-0.75, -0.75},{-1.0, -0.25},{0.0, 0.0},{0.25, -1.5},{0.75, -1.75},{0.5, -0.5},{0.75, -0.25},{2.75, -0.25},{3.25, 0.0},{2.75, 0.25},{0.75, 0.25},{0.5, 0.5},{0.75, 1.75},{0.25, 1.5},{0.0, 0.0},{-1.0, 0.25},{-0.75, 0.75},{-1.0, 0.75}});
//		v.scale(100);
//		v.draw(this);
//		System.out.println(Arrays.toString(v.getLines()));
		fill(0);
		textSize(30);
		textAlign(CENTER,CENTER);
		text(a.intersects(b)+"",width/2,100);
	}
	
	
	public static void main(String[] args) {
		PApplet.runSketch(new String[]{""}, new RPTest());
	}
}
