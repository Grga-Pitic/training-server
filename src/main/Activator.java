package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import main.database.DBConnection;
import main.dto.ContactDTO;
import main.dto.UserDTO;
import main.server.ServerSession;
import main.server.managers.ConsoleManager;
import main.server.managers.base.IManager;

public class Activator {
	public static void main(String [] args) throws SQLException{
		ApplicationContext context = new FileSystemXmlApplicationContext("mainContext.xml");
		DBConnection connection = (DBConnection)context.getBean("DBConnection");
		connection.open();
		
		Map<Object, UserDTO>    users       = connection.getUsersDAO().executeSelectQuery("select * from users");
		Map<Object, ContactDTO> contacts    = connection.getContactsDAO().executeSelectQuery("select * from contacts");
		List<ContactDTO>        contactList = new LinkedList<ContactDTO>();
		
		for(Object id:contacts.keySet()){
			contactList.add(contacts.get(id));
		}
		
		try { // и тут я осознал, что плохая идея использовать слова server, session и service в одном месте
			ServerSession serverSession = ServerSession.getInstance();
			serverSession.setContactMap(contacts);
			serverSession.setUserMap(users);
			serverSession.setContactList(contactList);
			serverSession.setServerSocket(new ServerSocket(ServerSession.PORT));
			
			IManager manager = new ConsoleManager(serverSession);
			manager.run();
			/*
			ServerSessionService serverService = new ServerSessionService(serverSession);
			serverService.run(serverSession);
			*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
