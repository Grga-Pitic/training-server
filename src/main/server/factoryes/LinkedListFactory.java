package main.server.factoryes;

import java.util.LinkedList;
import java.util.List;

import main.dto.ContactDTO;
import main.dto.UserDTO;
import main.server.UserSession;

public class LinkedListFactory {
	public List<UserDTO> createUserList(){
		return new LinkedList<UserDTO>();
	}
	
	public List<ContactDTO> createContactList(){
		return new LinkedList<ContactDTO>();
	} 
	
	public List<UserSession> createUserSessionList(){
		return new LinkedList<UserSession>();
	} 
}
