package main.server.services;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
				deleteDublicate(userSession, userSessionList);
				userSessionList.add(userSession);
				
				Thread thread = new Thread(userSession);
				sessionThreadList.add(thread);
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void deleteDublicate(UserSession session, List<UserSession> userSessionList){
		for(int i = 0; i < userSessionList.size(); i++) {
			if(userSessionList.get(i).isToDelete()){
				userSessionList.remove(i);
			}
		}
	}
	
	private void sendMessage(MessageDTO message, UserSession session) throws IOException{
		
		String query = "msg:"+message.getMessage()+"&"+message.getFromLogin();
		sendQuery(query, session);
		
	}
	
	
	
	private void checkAndSendMessage() throws InterruptedException, IOException{
		List <UserSession> sessionList = session.getUserSessionList();
		List <MessageDTO>  messageList = session.getMessageList();
		
		for(int i = 0; i < messageList.size(); i++){
			MessageDTO message = messageList.get(i);
			
			if(message.isSent()){
				continue;
			}
			
			for(UserSession userSession:sessionList){
				if(userSession.getSocket().isClosed()){
					userSession.setToDelete(true);
					continue;
				}
				
				try {
					if(userSession.getUser().getLogin().equals(message.getToLogin())){
						
						sendMessage(message, userSession);
						message.setSent(true);
						continue;
					}
				} catch(Exception ex) {
					userSession.setToDelete(true);
				}
				
			}
			Thread.sleep(1);
		}
		Thread.sleep(1);
		
		for(int i = 0; i < messageList.size(); i++){
			if(messageList.get(i).isSent()){
				messageList.remove(i);
			}
		}
		
	}
	
	private void sendQuery(String message, UserSession session) throws IOException {
		
		DataOutputStream out = session.getOut();
		
		int length = message.length();
		byte [] toSend = message.getBytes();
		byte [] lengthByte = new byte[4];
		
		lengthByte[0] = (byte) (length & 0xff);
		lengthByte[1] = (byte)((length >> 8) & 0xff);
		lengthByte[2] = (byte)((length >> 16) & 0xff);
		lengthByte[3] = (byte)((length >> 24) & 0xff);
		
		out.write(lengthByte);
		out.write(toSend);
		
//		for(;;){
//			if(session.isOutFree()){
//				session.setOutFree(false);
//				out.write(lengthByte);
//				out.write(toSend);
//				session.setOutFree(true);
//				break;
//			}
//		}
		
	}

}
