package main.database;

import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import main.database.dao.ContactsDAO;
import main.database.dao.UsersDAO;
import main.database.dao.base.IDataAccessObject;
import main.dto.base.IDataTransferObject;

public class DBConnection {
	private static final String ADDRESS = "127.0.0.1";
	private static final String LOGIN = "root";
	private static final String PASSWORD  = "";
	private static final String PORT = "3306";
	
	
	private String login;
	private String password;
	private String address;
	private int    port;
	private static DBConnection instance;
	
	private Connection connection;
	private Statement  statement;
	
	private IDataAccessObject usersDAO;
	private IDataAccessObject contactsDAO;
	
	public void open() throws SQLException{
		connection = DriverManager.getConnection("jdbc:mysql://"+ADDRESS+":"+PORT+"/my_messenger_db",
				LOGIN, PASSWORD);
		statement = connection.createStatement();
	}
	
	public synchronized static DBConnection getInstance(){
		if(instance != null){
			return instance;
		}
		instance = new DBConnection();
		return instance;
	}

	public Statement getStatement() {
		return statement;
	}
	
	public IDataAccessObject getUsersDAO(){
		if(usersDAO != null){
			return this.usersDAO;
		}
		usersDAO = new UsersDAO();
		return this.usersDAO;
	}
	
	public IDataAccessObject getContactsDAO(){
		if(contactsDAO != null){
			return this.contactsDAO;
		}
		contactsDAO = (IDataAccessObject) new ContactsDAO();
		return this.contactsDAO;
	}
}
