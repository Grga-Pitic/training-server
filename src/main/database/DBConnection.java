package main.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import main.database.dao.ContactsDAO;
import main.database.dao.UsersDAO;
import main.database.dao.base.IDataAccessObject;
import main.dto.ContactDTO;
import main.dto.UserDTO;

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
		if(instance == null){
			instance = new DBConnection();
		}
		return instance;
	}

	public Statement getStatement() {
		return statement;
	}
	
	private IDataAccessObject getUsersDAO(){
		if(usersDAO == null){
			usersDAO = new UsersDAO();
		}
		return this.usersDAO;
	}
	
	public UserDTO getUserByID(int id){
		String sqlQuery = "select * from users where id = " + String.valueOf(id);
		Map <Object, UserDTO> users = getUsersDAO().executeSelectQuery(sqlQuery);
		for (Object key:users.keySet()) {
			UserDTO user = users.get(key);
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}
	
	public UserDTO getUserByLogin(String login){
		String sqlQuery = "select * from users where login = '"+login+"'";
		return (UserDTO) getUsersDAO().executeSelectQuery(sqlQuery).get(login);
	}
	
	private IDataAccessObject getContactsDAO(){
		if(contactsDAO == null){
			contactsDAO = (IDataAccessObject) new ContactsDAO();
		}
		return this.contactsDAO;
	}
	
	public Map <Object, UserDTO> getContactsByUserLogin(String login){
		String sqlQuery = "select * from users where id in (select userid2 from contacts where userid1 in (select users.id from users where login = '"+login+"')) ";
		Map <Object, UserDTO> result = getUsersDAO().executeSelectQuery(sqlQuery);
		
		return result;
	}
	
	public void addNewUser(UserDTO user){
		getUsersDAO().executeInsertQuery(user);
	}
	
	public void addContact(ContactDTO contact){
		getContactsDAO().executeInsertQuery(contact);
	}
	
	public boolean isLoginFree(String login){
		String sqlQuery = "select * from users where login = '" + login+"'";
		Map <Object, UserDTO> result = getUsersDAO().executeSelectQuery(sqlQuery);
		if(result.size() != 0){
			return false;
		}
		return true;
	}
	
}
