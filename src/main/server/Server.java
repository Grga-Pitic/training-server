package main.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import main.server.connection.RequestQueue;
import main.server.connection.client.ClientConnection;
import main.server.connection.client.data.UserData;
import main.server.connection.handler.InputQueueHandler;
import main.server.connection.handler.OutputQueueHandler;
import main.server.connection.handler.base.HandlingData;
import main.server.connection.request.handlers.factory.RequestHandlerFactory;

public class Server {
	
	public static final int PORT = 6666;
	
	private int          userSessionCount;
	private ServerSocket serverSocket;
	
	private List <Thread> sessionThreadList;
	
	private static Server instance;
	
	public Server(){
		this.sessionThreadList = new LinkedList<Thread>();
		
		this.userSessionCount = 0;
	}
	
	public void run() {
		
		while(!serverSocket.isClosed()){
			try {
				System.out.print("Waiting for connection\n");
				Socket socket = serverSocket.accept();
				System.out.print("Connection complete\n");
				
				RequestQueue outputQueue = new RequestQueue();
				UserData userData = new UserData();
				
				RequestHandlerFactory factory = new RequestHandlerFactory(outputQueue, userData);
				OutputQueueHandler outHandler = new OutputQueueHandler(new HandlingData(outputQueue), 
						new DataOutputStream(socket.getOutputStream()));
				
				
				
				InputQueueHandler inHandler = new InputQueueHandler(new HandlingData(new RequestQueue()), 
																    factory);

				ClientConnection client = new ClientConnection(inHandler, outHandler, socket);
				
				Thread thread = new Thread(client);
				sessionThreadList.add(thread);
				thread.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public int getUserSessionCount(){
		return userSessionCount;
	}
	
	public void incUserSessionCount(){
		this.userSessionCount++;
	}
	
	public List<Thread> getSessionThreadList() {
		return sessionThreadList;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public synchronized static Server getInstance(){
		if(instance == null){
			instance = new Server();
		}
		return instance;
	}
	
}
