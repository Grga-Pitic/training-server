package main.server.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import main.dto.UserDTO;
import main.dto.base.ValueDTO;
import main.server.exception.AuthException;
import main.server.exception.RegisterDataException;

public class UserSessionService {
	
	private static UserSessionService instance;
	
	public synchronized UserDTO initSession(int id, Map<Object, UserDTO> map, Socket socket, String query) throws IOException, AuthException{
		
		String login;
		String password;
		
		int symbolCounter = 0;
		
		query = query.substring(5);
		
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
			throw new AuthException();
		}
		if(!user.getPassword().equals(password)){
			throw new AuthException();
		}
		
		return user;
		
	}
	
	public synchronized UserDTO registerUser(String query) throws RegisterDataException {
		UserDTO user = new UserDTO();
		query = query.substring(4);
		
		Map <String, ValueDTO> data = new HashMap<String, ValueDTO>();
		String []              arrayData = query.split("&");
		
		if(arrayData.length != 3){
			throw new RegisterDataException();
		}
		
		ValueDTO column = new ValueDTO("VARCHAR");
		column.setValue(arrayData[0]);
		data.put("login", column);
		
		column = new ValueDTO("VARCHAR");
		column.setValue(arrayData[1]);
		data.put("password", column);
		
		column = new ValueDTO("VARCHAR");
		column.setValue(arrayData[2]);
		data.put("nickname", column);
		
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
