package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.*;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import main.database.DBConnection;
import main.dto.ContactDTO;
import main.dto.UserDTO;
import main.dto.base.IDataTransferObject;
import main.server.ServerSession;
import main.server.services.ServerSessionService;

public class Activator {
	public static void main(String [] args) throws SQLException{
		ApplicationContext context = new FileSystemXmlApplicationContext("mainContext.xml");
		DBConnection connection = (DBConnection)context.getBean("DBConnection");
		connection.open();
		Map<Object, UserDTO> users = connection.getUsersDAO().executeSelectQuery("select * from users");
		for(Object login:users.keySet()){
			System.out.print(users.get((String)login).toString()+"\n");
		}
		
		System.out.print("\n");
		Map <Object, ContactDTO> contacts = connection.getContactsDAO().executeSelectQuery("select * from contacts");
		for(Object id:contacts.keySet()){
			System.out.print(contacts.get((int)id).toString()+"\n");
		}
		
		try { // и тут я осознал, что плохая идея использовать слова server, session и service в одном месте
			ServerSession serverSession = ServerSession.getInstance();
			serverSession.setContactMap(contacts);
			serverSession.setUserMap(users);
			serverSession.setServerSocket(new ServerSocket(ServerSession.PORT));
			
			ServerSessionService serverService = new ServerSessionService(serverSession);
			serverService.run(serverSession);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
