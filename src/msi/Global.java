package msi;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import msi.ship.Tank;

public class Global {
	public static final String SERVER_IP = "192.168.0.24"; // server's ip
	public static final int SERVER_PORT = 6066; // port number of socket on server side
	public static int FRAMES_PER_SECOND = 60;
	public static Random RANDOM = new Random(2738255);
	public static int[] gunColor = new int[] {127, 127, 127, 255};
	public static int[] gunOverlay = new int[] {177, 177, 177, 255};
	
	public static volatile ArrayList<Tank> PLAYER_LIST = new ArrayList<Tank>(); // populated locally
	public static volatile Tank CUR_PLAYER_TANK;
	
	// VPoly Models
	public static HashMap<String, double[][]> models = new HashMap<String, double[][]>();
	static {
		try {
			String read = new String(Files.readAllBytes(Paths.get("src/models.txt")), StandardCharsets.UTF_8);
			while(read.indexOf('[')!=-1) {
				String extract = read.substring(read.indexOf('['),read.indexOf(';'));
				read = read.substring(read.indexOf(';')+1);
				String key = extract.substring(1,extract.indexOf(']'));
				extract = extract.substring(extract.indexOf('{')+1, extract.length()-1);
				int count = -1;
				for(int i = 0; i != -1;) {
					i = extract.indexOf('}',i+1);
					if(i!=1)count++;
				}
				double[][] temp = new double[count][2];
				count = 0;
				while(extract.indexOf('}')!= -1) {
					String points = extract.substring(extract.indexOf('{')+1,extract.indexOf('}'));
					extract = extract.indexOf('}')+1==extract.length()?"":extract.substring(extract.indexOf('}')+1);
					temp[count][0] = Double.parseDouble(points.substring(0,points.indexOf(',')));
					temp[count][1] = Double.parseDouble(points.substring(points.indexOf(',')+1));
					count++;
				}
				models.put(key, temp);	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}	