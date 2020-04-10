package Server.ServerController;

import Server.ServerModel.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerCommunication {
	private ServerSocket serverSocket; // server socket
	private Socket aSocket; // socket to which client is binded to
	private BufferedReader socketIn; // msg to write to client
	private PrintWriter socketOut; // msg to read from socket
	private ExecutorService pool; // threadpool
	// private Model model;

	public ServerCommunication(int port) {
		try {
			serverSocket = new ServerSocket(port);
			pool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// model = new Model();
	}

	public void initializeServer() {
		try {
			aSocket = serverSocket.accept();
			System.out.println("Connection accepted by server!");
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
	}

	public void runServer() {
		try {
			while (true) {
				String line = socketIn.readLine();
				String[] inputs = line.split(" ");
				int choice = Integer.parseInt(inputs[0]);
				switch (choice) {
					case 1:
						socketOut.println("Show student courses");
						break;
					case 2:
						socketOut.println("Show all courses");
						break;
					case 3:
						socketOut.println("Remove");
						break;
					case 4:
						socketOut.println("Add");
						break;
					case 5:
						socketOut.println("Search");
						break;
					default:
						socketOut.println("DEFAULT");
						break;

				}
			}
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public void closeConnection() {
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public static void main(String[] args) {
		ServerCommunication serverCom = new ServerCommunication(9898);
		serverCom.initializeServer();
		serverCom.runServer();
	}
}