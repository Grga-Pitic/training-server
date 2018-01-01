package main.server;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.dto.ContactDTO;
import main.dto.MessageDTO;
import main.dto.UserDTO;

public class ServerSession {
	
	public static final int PORT = 6666;
	
	
	private Map  <Object, UserDTO>    userMap;
	private Map  <Object, ContactDTO> contactMap; 
	private List <UserSession>        userSessionList;
	private List <MessageDTO>         messageList; 
	
	private int          userSessionCount;
	private ServerSocket serverSocket;
	
	private static ServerSession instance;
	
	public ServerSession(){
		this.userSessionList = new ArrayList<UserSession>();
		this.messageList     = new ArrayList<MessageDTO>();
		
		this.userSessionCount = 0;
	}
	
	public List<MessageDTO> getMessageList() {
		return messageList;
	}

	public void setUserMap(Map<Object, UserDTO> userMap) {
		this.userMap = userMap;
	}

	public void setContactMap(Map<Object, ContactDTO> contactMap) {
		this.contactMap = contactMap;
	}

	public Map<Object, UserDTO> getUserMap() {
		return userMap;
	}

	public Map<Object, ContactDTO> getContactMap() {
		return contactMap;
	}

	public ServerSocket getServerSocket(){
		return this.serverSocket;
	}
	
	public void setServerSocket(ServerSocket serverSocket){
		this.serverSocket = serverSocket;
	}

	public List<UserSession> getUserSessionList() {
		return userSessionList;
	}
	
	public int getUserSessionCount(){
		return userSessionCount;
	}
	
	public void incUserSessionCount(){
		this.userSessionCount++;
	}
	
	public synchronized static ServerSession getInstance(){
		if(instance != null){
			return instance;
		}
		instance = new ServerSession();
		return instance;
	}
	
}
