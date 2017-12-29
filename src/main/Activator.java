package main;

import java.sql.*;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import main.database.DBConnection;
import main.dto.base.IDataTransferObject;;

public class Activator {
	public static void main(String [] args) throws SQLException{
		ApplicationContext context = new FileSystemXmlApplicationContext("mainContext.xml");
		DBConnection connection = (DBConnection)context.getBean("DBConnection");
		connection.open();
		List <IDataTransferObject> users = connection.getUsersDAO().executeSelectQuery("select * from users");
		
		for(IDataTransferObject user:users){
			System.out.print(user.toString()+"\n");
		}
		
		connection.getUsersDAO().executeUpdateQuery(users.get(1));
		
		/*List <IDataTransferObject> contacts = connection.getContactsDAO().executeSelectQuery("select * from contacts");
		for(IDataTransferObject contact:contacts){
			System.out.print(contact.toString()+"\n");
		}
		System.out.print("\n");
		*/
	}
}
