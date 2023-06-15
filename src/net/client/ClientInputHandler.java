package net.client;

import java.io.*;
import java.net.*;
import msi.Global;
import net.*;

public class ClientInputHandler extends Thread {
	private DatagramSocket socket; // client's socket
	
	public ClientInputHandler (DatagramSocket s) {
		socket = s;
		System.out.println("Client taking packets at port " + s.getLocalPort());
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				byte[] buf = new byte[10000];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet); // note: blocking
				
				ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
				ToClient received = (ToClient) iStream.readObject();
				iStream.close();
				
				for (int i = 0; i < received.enemies.size(); i++) if (received.enemies.get(i).ID == Global.CUR_PLAYER_TANK.ID) 
					received.enemies.remove(i);
				
				
				Global.PLAYER_LIST = received.enemies;
				Global.PLAYER_LIST.add(Global.CUR_PLAYER_TANK);
			}
			catch (EOFException e) {
				System.out.println("eof");
				continue;
			}
			catch (Exception e){
				e.printStackTrace();
				System.out.println("something broke, ClientInputHandler");
				break;
			}
		}
	}
}
