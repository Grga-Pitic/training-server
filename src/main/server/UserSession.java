package main.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.database.DBConnection;
import main.dto.ContactDTO;
import main.dto.MessageDTO;
import main.dto.UserDTO;
import main.server.exception.AuthException;
import main.server.exception.MessageDataException;
import main.server.exception.RegisterDataException;
import main.server.services.UserSessionService;

public class UserSession implements Runnable {
	


	private volatile boolean isOutFree;
	private volatile boolean isInFree;
	
	private int     id;
	private String  key;
	private UserDTO user;

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
		Map <Object, UserDTO>    userMap    = ServerSession.getInstance().getUserMap();
		Map <Object, ContactDTO> contactMap = ServerSession.getInstance().getContactMap();
		try {
			while(!socket.isClosed()){
				String query;
				try {
					query = in.readUTF(); 
				} catch (EOFException e){
					e.printStackTrace();
					socket.close();
					in.close();
					out.close();
					break;
				} catch (SocketException e) {
					e.printStackTrace();
					socket.close();
					in.close();
					out.close();
					break;
				} catch (Exception e) {
					e.printStackTrace();
					socket.close();
					in.close();
					out.close();
					break;
				}
				
				if(query.substring(0, 3).equals("reg")){
					try {
						UserDTO user;
						user = UserSessionService.getInstance().registerUser(query);
						DBConnection.getInstance().getUsersDAO().executeInsertQuery(user);
						for(;;){
							if(isOutFree){
								isOutFree = false;
								out.writeUTF("You're registered!");
								out.flush();
								isOutFree = true;
								break;
							}
						}
						
					} catch (RegisterDataException e) {
						for(;;){
							if(isOutFree){
								isOutFree = false;
								out.writeUTF("Invalid registration data");
								out.flush();
								isOutFree = true;
								break;
							}
						}
						e.printStackTrace();
					}
					continue;
				}
				
				if(query.substring(0, 4).equals("auth")){
					try {
						user = UserSessionService.getInstance().initSession(id, userMap, socket, query);
						for(;;){
							if(isOutFree){
								isOutFree = false;
								out.writeUTF("auth success!");
								out.flush();
								isOutFree = true;
								break;
							}
						}
					} catch (AuthException e) {
						for(;;){
							if(isOutFree){
								isOutFree = false;
								out.writeUTF("Invalid login/password");
								out.flush();
								isOutFree = true;
								break;
							}
						}
						e.printStackTrace();
					}
					continue;
				}

				if(query.substring(0, 3).equals("msg")){
					try {
						MessageDTO message = UserSessionService.getInstance().getMessage(query, user);
						ServerSession.getInstance().getMessageList().add(message);
					} catch (MessageDataException e) {
						for(;;){
							if(isOutFree){
								isOutFree = false;
								out.writeUTF("Invalid message");
								out.flush();
								isOutFree = true;
								break;
							}
						}
						e.printStackTrace();
					}
					
					continue;
				}
				
				if(query.equals("contacts")){
					
					List <UserDTO> contactUserList = UserSessionService.getInstance().getUserContacts(contactMap, userMap, user);
					for(;;){
						if(isOutFree){
							isOutFree = false;
							for(UserDTO contact:contactUserList){
								out.writeUTF(UserSessionService.getInstance().getContactQuery(contact));
							}
							isOutFree = true;
							break;
						}
					}
				}
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
	
	public Socket getSocket() {
		return socket;
	}
	
	public UserDTO getUser() {
		return user;
	}
	
}
