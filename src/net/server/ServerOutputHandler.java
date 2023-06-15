package net.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import msi.Global;
import net.*;

public class ServerOutputHandler extends Thread {
	private DatagramSocket socket;
	
	public ServerOutputHandler(DatagramSocket s) {
		socket = s;
		System.out.println("Server is now sending from port " + socket.getLocalPort());
	}
	
	@Override
	public void run () {
		while (true) {
			synchronized (Global.PLAYER_LIST) {
				try {
					ToClient message = new ToClient();
					message.enemies = Global.PLAYER_LIST;
					
					ByteArrayOutputStream bStream = new ByteArrayOutputStream();
					ObjectOutput oo = new ObjectOutputStream(bStream); 
					oo.writeObject(message);
					oo.close();
					
					
					// send packet to all clients listening
					ArrayList<InetAddress> Addresses = Server.getClientAddresses();
					ArrayList<Integer> Ports = Server.getClientPorts();
					for (int i = 0; i < Math.min(Addresses.size(), Ports.size()); i++) {
						InetAddress targetAddr = Addresses.get(i);
						int targetPort = Ports.get(i);
						
						byte[] serializedMessage = bStream.toByteArray();
						DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length, targetAddr, targetPort);
						
						socket.send(packet);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println("something broke, ServerOutputHandler");
					break;
				}	
			}
		}
	}
}
