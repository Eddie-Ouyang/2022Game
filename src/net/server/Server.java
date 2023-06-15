package net.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import msi.Global;

public class Server {
	private DatagramSocket serverSocket;
	
	// fields are public static to be visible, get/set should be done using static synchronized methods
	private static ArrayList<InetAddress> clientAddresses = new ArrayList<InetAddress>();
	private static ArrayList<Integer> clientPorts = new ArrayList<Integer>();
	
	public Server() throws IOException {
		try {
			serverSocket = new DatagramSocket(Global.SERVER_PORT);
			
			ServerInputHandler  Sin  = new ServerInputHandler(serverSocket);
			ServerOutputHandler Sout = new ServerOutputHandler(serverSocket);
			
			Sin.start();
			Sout.start();
			
			System.out.println("Server IO threads started successfully");
		}
		catch (Exception e) {
			System.out.println("something broke, Server");
			e.printStackTrace();
		}
	}
	
	
	public static synchronized ArrayList<InetAddress> getClientAddresses () { return clientAddresses; }
	public static synchronized ArrayList<Integer> getClientPorts () { return clientPorts; }
	public static synchronized void addClientAddress (InetAddress addr) { clientAddresses.add(addr); }
	public static synchronized void addClientport (int port) { clientPorts.add(port); }
	
	
	// run server from this file
	public static void main(String[] args) {try {Server S = new Server();} catch (IOException e) {e.printStackTrace();}}
}