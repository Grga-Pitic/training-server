package main.server.services;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import main.dto.MessageDTO;
import main.server.ServerSession;
import main.server.UserSession;

public class ServerSessionService {
	
	private UserSessionService userService;
	private ServerSession      session;
	
	public ServerSessionService(ServerSession session){
		this.userService = UserSessionService.getInstance();
		this.session     = session;
	}
	
	public void run(ServerSession session) {
		ServerSocket serverSocket          = session.getServerSocket();
		List <UserSession> userSessionList = session.getUserSessionList();
		List <Thread> sessionThreadList    = session.getSessionThreadList();
		
		Thread checkingThread = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					for(;;){
						checkAndSendMessage();
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		checkingThread.start();
		session.setCheckingThread(checkingThread);
		
		while(!serverSocket.isClosed()){
			try {
				System.out.print("Waiting for connection\n");
				Socket socket = serverSocket.accept();
				System.out.print("Connection complete\n");
				UserSession userSession = new UserSession(session.getUserSessionCount(), socket);
				userSessionList.add(userSession);
				
				Thread thread = new Thread(userSession);
				sessionThreadList.add(thread);
				thread.start();
		//		System.out.print("Started new thread\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sendMessage(MessageDTO message, DataOutputStream out) throws IOException{
		String query = "msg:"+message.getMessage()+"&"+message.getFromLogin();
		out.writeUTF(query);
	}
	
	private void checkAndSendMessage() throws InterruptedException, IOException{
		List <UserSession> sessionList = session.getUserSessionList();
		List <MessageDTO>  messageList = session.getMessageList();
		for(int i = 0; i < messageList.size(); i++){
			MessageDTO message = messageList.get(i);
			for(UserSession userSession:sessionList){
				if(userSession.getSocket().isClosed()){
					continue;
				}
				if(userSession.getUser().getLogin().equals(message.getToLogin())){
					while(true){
						if(userSession.isOutFree()){
							userSession.setOutFree(false);
							sendMessage(message, userSession.getOut());
							messageList.remove(i);
							userSession.setOutFree(true);
							break;
						}
						Thread.sleep(1);
					}
					
					break;
				}
			}
			Thread.sleep(1);
		}
		Thread.sleep(1);
	}

}
