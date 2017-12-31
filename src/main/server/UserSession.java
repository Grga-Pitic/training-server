package main.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import main.database.DBConnection;
import main.dto.UserDTO;
import main.server.exception.AuthException;
import main.server.exception.RegisterDataException;
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
			while(!socket.isClosed()){
				String query = in.readUTF();
				if(query.substring(0, 4).equals("reg")){
					try {
						UserDTO user;
						user = UserSessionService.getInstance().registerUser(query);
						DBConnection.getInstance().getUsersDAO().executeInsertQuery(user);
					} catch (RegisterDataException e) {
						out.writeUTF("Invalid registration data");
						out.flush();
						e.printStackTrace();
					}
					continue;
				}
				
				if(query.substring(0, 4).equals("auth")){
					try {
						user = UserSessionService.getInstance().initSession(id, userMap, socket, query);
						out.writeUTF("auth success!");
						out.flush();
					} catch (AuthException e) {
						System.out.print("Invalid login/password\n");
						out.writeUTF("Invalid login/password");
						out.flush();
						e.printStackTrace();
					}
					continue;
				}
				socket.close();
				in.close();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
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
