package net.client;

import java.io.*;
import java.net.*;
import java.util.ConcurrentModificationException;

import msi.Global;
import net.*;

// a new thread is instantiated for every connection
public class ClientOutputHandler extends Thread {
	private InetAddress targetAddr;
	private int targetPort;
	private DatagramSocket socket; // client's socket
	
	public ClientOutputHandler (DatagramSocket s, InetAddress i, int p) {
		socket = s;
		targetAddr = i;
		targetPort = p;
		System.out.println("Client sending packets from port " + s.getLocalPort());
	}
	
	@Override
	public void run() {
		while (true) {
			synchronized (Global.CUR_PLAYER_TANK) {
				try {
					ToServer message = new ToServer();
					message.player = Global.CUR_PLAYER_TANK;

					ByteArrayOutputStream bStream = new ByteArrayOutputStream();
					ObjectOutput oo = new ObjectOutputStream(bStream); 
					synchronized (message) { oo.writeObject(message); }
					oo.close();

					byte[] serializedMessage = bStream.toByteArray();
					DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length, targetAddr, targetPort);
					
					socket.send(packet);
				}
				catch (ConcurrentModificationException e){
					System.out.println("threads broke smth, pray it doesn't happen again");
					continue;
				}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println("something broke, ClientOutputHandler");
					break;
				}	
			}
		}
	}
}
