package main.server.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import main.server.ServerSession;
import main.server.UserSession;

public class ServerSessionService {
	
	private UserSessionService userService;
	private ServerSession      session;
	
	public ServerSessionService(ServerSession session){
		this.userService = UserSessionService.getInstance();
		this.session     = session;
	}
	
	public void run(ServerSession session){
		ServerSocket serverSocket = session.getServerSocket();
		List <UserSession> userSessionList = session.getUserSessionList();
		while(!serverSocket.isClosed()){
			try {
				System.out.print("Waiting for connection\n");
				Socket socket = serverSocket.accept();
				System.out.print("Connection complete\n");
				UserSession userSession = new UserSession(session.getUserSessionCount(), socket);
				userSessionList.add(userSession);
				new Thread(userSession).start();
				System.out.print("Started new thread\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	private ServerSocket createServerSocket() throws IOException{
		return new ServerSocket(ServerSession.PORT);
	}

	private String generateKey(){
		return "0000";
	}
}
