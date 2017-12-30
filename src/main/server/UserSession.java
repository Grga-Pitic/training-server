package main.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import main.dto.UserDTO;
import main.server.exception.AuthException;
import main.server.services.UserSessionService;

public class UserSession implements Runnable {
	private int     id;
	private String  key;
	private UserDTO user;
	
	private Socket           socket;
	private DataInputStream  in;
	private DataOutputStream out;
	
	public UserSession(int id, Socket socket) throws IOException{
		
		this.id     = id;
		this.socket = socket;
		
		this.in     = new DataInputStream(socket.getInputStream());
		this.out    = new DataOutputStream(socket.getOutputStream());
		
	}
	
	@Override
	public void run() {
		Map <Object, UserDTO> userMap = ServerSession.getInstance().getUserMap();
		try {
			try {
				user = UserSessionService.getInstance().initSession(id, userMap, socket);
			} catch (AuthException e) {
				System.out.print("Invalid login/password\n");
				e.printStackTrace();
			}
			out.writeUTF("you are connected");
			out.flush();
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initSession(Map<Object, UserDTO> userMap) throws IOException, AuthException{
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
		UserDTO user = userMap.get(login);
		
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
		
		
	}

	public DataInputStream getIn() {
		return in;
	}

	public DataOutputStream getOut() {
		return out;
	}

	public int getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

}
