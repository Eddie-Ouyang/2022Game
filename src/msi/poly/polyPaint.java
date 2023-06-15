package msi.poly;

import processing.core.PApplet;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.JOptionPane;

import msi.Global;
import msi.shapes.*;
import msi.shapes.Rectangle;

public class polyPaint extends PApplet {
	private final int[] size = new int[] {8,16,32,64,128};
	private ArrayList<ArrayList<double[]>> layers;
	private ArrayList<double[]> move;
	private Stack<Integer> addHistory;
	private Stack<double[]> history;
	private int scale,mode,hold,index,layer;
	private int[] center;
	private RPolygon ship;
	private Rectangle select;
	
	public polyPaint() {
		move = new ArrayList<double[]>();
		layers = new ArrayList<ArrayList<double[]>>();
		layers.add(new ArrayList<double[]>());
		history  = new Stack<double[]>();
		addHistory = new Stack<Integer>();
		scale = 0;
		hold = -1;
		center = new int[] {640,400};
		ship = new RPolygon(0,0,5,160);
		ship.setAngle(-Math.PI/2);
	}
	
	public void settings() {
		fullScreen();
	}
	
	public void keyPressed() {
		double gridSize = width/size[scale];
		switch(keyCode) {
		case 'W': for(double[] d:move) d[1] -= gridSize;
			break;
		case 'A': for(double[] d:move) d[0] -= gridSize;
			break;
		case 'S': for(double[] d:move) d[1] += gridSize;
			break;
		case 'D': for(double[] d:move) d[0] += gridSize;
			break;
		case UP: 
			scale = scale == size.length-1?size.length-1:scale+1;
			break;
		case DOWN:
			scale = scale == 0?0:scale-1;
			break;
		case RIGHT:
			layer = (layer+1)%layers.size();
			index = 0;
			break;
		case 'P': mode = 0;
			break;
		case 'E': mode = 2;
			break;
		case 'M': mode = 1;
			break;
		case 'I': mode = 3;
			break;
		case 'N': layers.add(new ArrayList<double[]>());
			break;
		case 'C':
			center = new int[] {(int) (Math.round(mouseX/gridSize)*gridSize), (int) (Math.round(mouseY/gridSize) * gridSize)};
			break;
		case DELETE:
			layers.get(layer).clear();
			index = 0;
			break;
		case 'Z':
			if(layers.get(layer).size()>0) {
				int i = addHistory.pop();
				double[] p = Arrays.copyOf(layers.get(layer).get(i), 3);
				layers.get(layer).remove(i);
				index--;
				p[2] = index;
				history.push(p);
			}
			break;
		case 'Y':
			if(history.size()>0) {
				double[] p = history.pop();
				layers.get(layer).add((int)p[2], new double[] {p[0],p[1]});
				addHistory.push(index);
				index++;
			}
			break;
		case ENTER:
			String text = "";
			for(double[] d:layers.get(layer)) {
				text += "{" + (d[0] - center[0])/160 + ", " + (d[1] - center[1])/160 + "},";
			}
			String name = "["+JOptionPane.showInputDialog("Enter name")+"]";
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(new StringSelection(name + " = {" + text.substring(0,text.length()-1) + "};"), null);	
			try {
				String read = "";
				String str = name+" = {" + text.substring(0,text.length()-1) + "};";
				read = new String(Files.readAllBytes(Paths.get("src/models.txt")), StandardCharsets.UTF_8);
				File file = new File("src/models.txt");
				FileWriter writer = new FileWriter(file);
				if(read.indexOf(name) != -1) {
					int ap = read.indexOf('[', read.indexOf(name)+name.length());
					read = read.substring(0,read.indexOf(name)) + str + (ap>=0?read.substring(ap):"");
				} else {
					read += "\n" + str;
				}
				writer.write(read);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case ' ':
			mousePressed();
			break;
		case 'R':
			try {
				String s = JOptionPane.showInputDialog("Load Shape\n" + Global.models.keySet());
				layers.get(layer).clear();
				index = 0;
				if(s.indexOf("name:")!=-1) {
					double[][] d = Global.models.get(s.substring(s.indexOf(':')+1));
					for(int i = 0; i < d.length; i++){
						layers.get(layer).add(new double[] {d[i][0]*160+center[0], d[i][1]*160+center[1]});
					}
				}
				s = s.substring(s.indexOf('{')+1);
				while(s.contains("}")) {
					String read = s.substring(s.indexOf('{')+1,s.indexOf('}'));
					s = s.substring(s.indexOf('}')+1);
					double x = Double.parseDouble(read.substring(0,read.indexOf(','))) * 160 + center[0];
					double y = Double.parseDouble(read.substring(read.indexOf(',')+1)) * 160 + center[1];
					layers.get(layer).add(new double[] {x,y});
				}
				index = layers.get(layer).size();
			} catch(Exception e) {
				return;
			}
			break;
		case 'T':
			try {
				String s = JOptionPane.showInputDialog("Load Template"  + Global.models.keySet());
				ArrayList<double[]> temp = new ArrayList<double[]>();
				layers.add(temp);
				if(s.indexOf("name:")!=-1) {
					double[][] d = Global.models.get(s.substring(s.indexOf(':')+1));
					for(int i = 0; i < d.length; i++){
						temp.add(new double[] {d[i][0]*160+center[0], d[i][1]*160+center[1]});
					}
				}
				s = s.substring(s.indexOf('{')+1);
				while(s.contains("}")) {
					String read = s.substring(s.indexOf('{')+1,s.indexOf('}'));
					s = s.substring(s.indexOf('}')+1);
					double x = Double.parseDouble(read.substring(0,read.indexOf(','))) * 160 + center[0];
					double y = Double.parseDouble(read.substring(read.indexOf(',')+1)) * 160 + center[1];
					temp.add(new double[] {x,y});
				}
			} catch(Exception e) {
				return;
			}
			break;
		}
	}
	
	public void mousePressed() {
		if(keyCode == CONTROL) {
			if(select==null)select = new Rectangle(mouseX,mouseY,mouseX,mouseY);
			else {
				move.clear();
				select.fix();
				for(double[] p:layers.get(layer)) {
					if(select.contains(p[0],p[1])) move.add(p);
				}
				select = null;
			}
			return;
		}
		double gridSize = width/size[scale];
		if(Math.abs(mouseX%gridSize - gridSize/2) < gridSize/4 || Math.abs(mouseY%gridSize - gridSize/2) < gridSize/4) return;
		double[] click = new double[] {Math.round(mouseX/gridSize)*gridSize, Math.round(mouseY/gridSize)*gridSize};
		switch(mode) {
		case 0:
			layers.get(layer).add(index,click);
			addHistory.push(index);
			index++;
			break;
		case 1:
			if(hold>=0 && hold < layers.get(layer).size()) {
				layers.get(layer).set(hold, click);
				hold = -1;
			} else {
				for(double[] d:layers.get(layer)) {
					if(d[0] == click[0] && d[1] == click[1]) hold = layers.get(layer).indexOf(d);
				}
			}
			break;
		case 2:
			for(int i = 0; i < layers.get(layer).size();i++) {
				double[] d = layers.get(layer).get(i);
				if(d[0] == click[0] && d[1] == click[1]) {
					layers.get(layer).remove(i);
					index = i!=0?i-1:0;
					return;
				}
			}
			break;
		case 3:
			for(double[] d:layers.get(layer)) {
				if(d[0] == click[0] && d[1] == click[1]) index = layers.get(layer).indexOf(d)+1;
			}
			mode = 0;
			break;
		}
	}
	
	/* Create VPolygons easily
	 * Change scale with up/down arrows, edit size[] for more grid sizes options. Small grids will break
	 * P for pen
	 * E for erase
	 * M for move
	 * I for insert
	 * C to move center point
	 * Z for undo 
	 * Y for redo
	 * R for load shape, paste double[][] or name:name
	 * T for template, same syntax as loading
	 * ENTER to print shape
	 * DELETE to clear layer
	 * CTRL to enable box select, WASD to move point
	 * 
	 */
	public void draw() {
		background(255);
		ship.move(center[0], center[1]);
		ship.draw(this);
		push();
		strokeWeight(3);
		stroke(mode==0?255:0,mode==1?255:0,mode==2?255:0);
		fill(0,0,0,0);
		circle(mouseX,mouseY,(float)width/size[scale]/2);
		pop();
		int gridSize = width/size[scale];
		for(int x = 0; x < width; x+=gridSize) {
			line(x,0,x,height);
		}
		for(int y = 0; y < height; y+=gridSize) {
			line(0,y,width,y);
		}
		for(int i = 0; i < layers.size(); i++) {
			push();
			beginShape();
			fill(i==layer?255:0,0,i==layer?0:255,i==layer?70:30);
			stroke(i==layer?255:0,0,i==layer?0:255);
			if(hold>=0 && hold < layers.get(layer).size()) {
				layers.get(layer).set(hold, new double[] {mouseX,mouseY});
			}
			for(double[] d:layers.get(i)) {
				if(i==layer)circle((float)d[0],(float)d[1],7);
				vertex((float)d[0],(float)d[1]);
			}
			endShape();
			pop();
		}
		if(select != null) {
			select.setX2(mouseX, mouseY);
			select.draw(this);
		}
		push();
		stroke(0,255,0);
		fill(0,255,0);
		for(double[] d:move) circle((float)d[0],(float)d[1],5);
		pop();
		push();
		fill(0,0,255);
		circle((float)center[0],(float)center[1],15);
		pop();
	}
	
	public static void main(String[] args) {
		PApplet.runSketch(new String[] {""}, new polyPaint());
	}

}
