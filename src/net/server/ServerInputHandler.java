package net.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import msi.Global;
import net.*;

public class ServerInputHandler extends Thread {
	private DatagramSocket socket; // client's socket
	
	public ServerInputHandler (DatagramSocket s) {
		socket = s;
		System.out.println("Server is now listening on port " + s.getLocalPort());
	}
	
	private void updateClientInfo (DatagramPacket P) {
		ArrayList<InetAddress> Addresses = Server.getClientAddresses();
		ArrayList<Integer> Ports = Server.getClientPorts();
		
		
		for (int i = 0; i < Math.min(Addresses.size(), Ports.size()); i++) 
			if (Addresses.get(i).toString().equals(P.getAddress().toString()) && Ports.get(i).equals(P.getPort())) 
				return;
			
		
		System.out.println("New connection from " + P.getAddress() + " with a port #" + P.getPort());
		Server.addClientAddress(P.getAddress());
		Server.addClientport(P.getPort());
	}
	
	@Override
	public void run() {
		while (true) {
			synchronized (Global.PLAYER_LIST) {
				try {
					byte[] buf = new byte[10000];
					DatagramPacket packet = new DatagramPacket(buf, buf.length);
					socket.receive(packet); // note: blocking				 
					
					ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
					ToServer received = (ToServer) iStream.readObject();
					iStream.close();
					
					updateClientInfo(packet);
					
					for (int i = 0; i < Global.PLAYER_LIST.size(); i++) if (Global.PLAYER_LIST.get(i).ID == received.player.ID) {
						Global.PLAYER_LIST.remove(i);
						break;
					}
					Global.PLAYER_LIST.add(received.player);
				}
				catch (EOFException e) {
					continue;
				}
				catch (Exception e){
					e.printStackTrace();
					System.out.println("something broke, ServerInputHandler");
					break;
				}
			}
		}
	}
}
