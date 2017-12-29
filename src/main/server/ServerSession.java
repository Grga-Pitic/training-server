package main.server;

import java.util.List;

import main.dto.ContactDTO;
import main.dto.UserDTO;

public class ServerSession {
	private List <UserDTO>     userList;
	private List <ContactDTO>  contactList; 
	private List <UserSession> userSessionList;
	
	private int userSessionCount;
	
	public ServerSession(List <UserDTO> userList, List <ContactDTO> contactList, 
			List <UserSession> userSessionList){
		this.userList        = userList;
		this.contactList     = contactList;
		this.userSessionList = userSessionList;
		
		this.userSessionCount = 0;
	}
	
	public List<UserDTO> getUserList() {
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
