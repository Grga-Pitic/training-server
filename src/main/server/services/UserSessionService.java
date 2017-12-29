package main.server.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import main.server.exception.AuthException;

public class UserSessionService {
	
	public void createSession(Socket socket) throws IOException, AuthException{
		DataInputStream  in  = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		
		String query    = in.readUTF().substring(5);
		String login;
		String password;
		
		int symbolCounter = 0;
		while(symbolCounter < query.length()){
			if(query.charAt(symbolCounter) == '&'){
				break;
			}
			symbolCounter++;
		}
		
		login    = query.substring(0, symbolCounter);
		password = query.substring(symbolCounter+1, query.length());
		
		
	}
	
	public void closeSession(){
		
	}
}
