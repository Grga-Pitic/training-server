package main.server.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import main.dto.UserDTO;
import main.server.exception.AuthException;

public class UserSessionService {
	
	private static UserSessionService instance;
	
	public synchronized UserDTO initSession(int id, Map<Object, UserDTO> map, Socket socket) throws IOException, AuthException{
		
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
		UserDTO user = map.get(login);
		
		if(user == null){
			out.writeUTF("Invalid login/password");
			out.flush();
			throw new AuthException();
		}
		if(!user.getPassword().equals(password)){
			out.writeUTF("Invalid login/password");
			out.flush();
			throw new AuthException();
		}
		
		return user;
		
	}
	
	public synchronized static UserSessionService getInstance(){
		if(instance != null){
			return instance;
		}
		instance = new UserSessionService();
		return instance;
	}
	
	public void closeSession(){
		
	}
}
