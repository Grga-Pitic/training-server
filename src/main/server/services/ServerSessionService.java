package main.server.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import main.server.ServerSession;
import main.server.UserSession;
import main.server.exception.AuthException;

public class ServerSessionService {
	public void run(ServerSession session){
		ServerSocket serverSocket = session.getServerSocket();
		List <UserSession> UserSessionList = session.getUserSessionList();
		while(!serverSocket.isClosed()){
			try {
				Socket socket = serverSocket.accept();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	private String generateKey(){
		return "0000";
	}
}
