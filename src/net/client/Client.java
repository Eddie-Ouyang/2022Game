package net.client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import msi.Global;
import msi.ship.Tank;
import net.*;

public class Client {
	private DatagramSocket clientSocket;
	
	public Client () {
		try {
			clientSocket = new DatagramSocket();
			
			ClientInputHandler  Cin  = new ClientInputHandler(clientSocket);
			ClientOutputHandler Cout = new ClientOutputHandler(clientSocket, InetAddress.getByName(Global.SERVER_IP), Global.SERVER_PORT);
			
			Cin.start();
			Cout.start();
			
			System.out.println("Client IO threads started successfully");
		}
		catch (Exception e) {
			System.out.println("something broke, Client");
			e.printStackTrace();
		}
	}


	// run client from this file
//	public static void main(String[] args) {Client C = new Client();}
}