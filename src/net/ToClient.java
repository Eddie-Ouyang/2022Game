package net;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import msi.ship.Tank;

public class ToClient implements Serializable {
	private static final long serialVersionUID = 1L; // dunno why this is used but eclipse gets angry if you remove it
	
	public double[] scoreBoard;
	public ArrayList<Tank> enemies;
	
	
}
