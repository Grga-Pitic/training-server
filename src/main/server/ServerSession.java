package main.server;

import java.net.ServerSocket;
import java.util.List;
import java.util.Map;

import main.dto.ContactDTO;
import main.dto.UserDTO;

public class ServerSession {
	
	public static final int PORT = 6666;
	
	
	private Map  <String, UserDTO> userList;
	private List <ContactDTO>      contactList; 
	private List <UserSession>     userSessionList;
	
	private int          userSessionCount;
	private ServerSocket serverSocket;
	
	public ServerSession(Map  <String, UserDTO> userList, List <ContactDTO> contactList, 
			List <UserSession> userSessionList, ServerSocket serverSocket){
		this.userList        = userList;
		this.contactList     = contactList;
		this.userSessionList = userSessionList;
		this.serverSocket    = serverSocket;
		
		this.userSessionCount = 0;
	}
	
	public ServerSocket getServerSocket(){
		return this.serverSocket;
	}
	
	public void setServerSocket(ServerSocket serverSocket){
		this.serverSocket = serverSocket;
	}
	
	public Map <String, UserDTO> getUserList() {
		return userList;
	}

	public List<ContactDTO> getContactList() {
		return contactList;
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
	
}
