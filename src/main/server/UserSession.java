package main.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.database.DBConnection;
import main.dto.MessageDTO;
import main.dto.UserDTO;
import main.server.exception.AuthException;
import main.server.exception.MessageDataException;
import main.server.exception.RegisterDataException;
import main.server.services.ServerSessionService;
import main.server.services.UserSessionService;

public class UserSession implements Runnable {
	
	private volatile boolean isOutFree;
	private volatile boolean isInFree;
	
	private int     id;
	private String  key;
	private UserDTO user;
	public UserDTO getUser() {
		return user;
	}

	private List<MessageDTO> messageList;
	
	private Socket           socket;
	private DataInputStream  in;
	private DataOutputStream out;
	
	public UserSession(int id, Socket socket) throws IOException{
		
		this.id     = id;
		this.socket = socket;
		
		this.in           = new DataInputStream(socket.getInputStream());
		this.out          = new DataOutputStream(socket.getOutputStream());
		this.messageList  = new ArrayList<MessageDTO>();
		
		this.isOutFree    = true;
	}
	
	@Override
	public void run() {
		Map <Object, UserDTO> userMap = ServerSession.getInstance().getUserMap();
		try {
			while(!socket.isClosed()){
				String query;
				try {
					query = in.readUTF(); 
				} catch (EOFException e){
					e.printStackTrace();
					break;
				}
				
				if(query.substring(0, 3).equals("reg")){
					try {
						UserDTO user;
						user = UserSessionService.getInstance().registerUser(query);
						DBConnection.getInstance().getUsersDAO().executeInsertQuery(user);
						isOutFree = false;
						out.writeUTF("You're registered!");
						out.flush();
						isOutFree = true;
					} catch (RegisterDataException e) {
						isOutFree = false;
						out.writeUTF("Invalid registration data");
						out.flush();
						isOutFree = true;
						e.printStackTrace();
					}
					continue;
				}
				
				if(query.substring(0, 4).equals("auth")){
					try {
						user = UserSessionService.getInstance().initSession(id, userMap, socket, query);
						isOutFree = false;
						out.writeUTF("auth success!");
						out.flush();
						isOutFree = true;
					} catch (AuthException e) {
						System.out.print("Invalid login/password\n");
						isOutFree = false;
						out.writeUTF("Invalid login/password");
						out.flush();
						isOutFree = true;
						e.printStackTrace();
					}
					continue;
				}

				if(query.substring(0, 3).equals("msg")){
					try {
						MessageDTO message = UserSessionService.getInstance().getMessage(query, user);
						ServerSession.getInstance().getMessageList().add(message);
					} catch (MessageDataException e) {
						isOutFree = false;
						out.writeUTF("Invalid message");
						out.flush();
						isOutFree = true;
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
	
	
	public List<MessageDTO> getMessageList() {
		return messageList;
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

	public synchronized boolean isOutFree() {
		return isOutFree;
	}

	public synchronized void setOutFree(boolean isOutFree) {
		this.isOutFree = isOutFree;
	}

	public synchronized boolean isInFree() {
		return isInFree;
	}

	public synchronized void setInFree(boolean isInFree) {
		this.isInFree = isInFree;
	}
	
}
