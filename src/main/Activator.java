package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import main.database.DBConnection;
import main.server.Server;
import main.server.managers.ConsoleManager;
import main.server.managers.base.IManager;

public class Activator {
	public static void main(String [] args) throws SQLException{
		ApplicationContext context = new FileSystemXmlApplicationContext("mainContext.xml");
		DBConnection connection = (DBConnection)context.getBean("DBConnection");
		connection.open();
		
		try { // и тут я осознал, что плохая идея использовать слова server, session и service в одном месте
			Server serverSession = Server.getInstance();
			serverSession.setServerSocket(new ServerSocket(Server.PORT));
			
			IManager manager = new ConsoleManager(serverSession);
			manager.run();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
