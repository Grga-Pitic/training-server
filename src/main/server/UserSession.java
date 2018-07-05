package main.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.database.DBConnection;
import main.dto.ContactDTO;
import main.dto.MessageDTO;
import main.dto.UserDTO;
import main.dto.base.ValueDTO;
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
	
	private boolean isToDelete;
	
	public UserSession(int id, Socket socket) throws IOException{
		
		this.id     = id;
		this.socket = socket;
		
		this.in           = new DataInputStream(socket.getInputStream());
		this.out          = new DataOutputStream(socket.getOutputStream());
		this.messageList  = new ArrayList<MessageDTO>();
		
		this.isOutFree    = true;
		this.isToDelete   = false;
	}
	
	@Override
	public void run() {
		Map <Object, UserDTO>    userMap     = ServerSession.getInstance().getUserMap();
		Map <Object, ContactDTO> contactMap  = ServerSession.getInstance().getContactMap();
		List <ContactDTO> 		 contactList = ServerSession.getInstance().getContactList(); 
		
		try {
			while(!socket.isClosed()){
				String query;
				try {
					
					byte[] lenBytes = new byte[4];
					in.read(lenBytes, 0, 4);
					int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
			                   ((lenBytes[1] & 0xff) << 8)  |  (lenBytes[0] & 0xff));

					byte[] receivedBytes = new byte[len];
					in.read(receivedBytes, 0, len);
					query = new String(receivedBytes, 0, len);
			//		System.out.print(query+"\n");
				} catch (EOFException e){
					e.printStackTrace();
					socket.close();
					in.close();
					out.close();
					break;
				} catch (SocketException e) {
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
				
				if(query.length() == 0) {
					break;
				}
				
				if(query.substring(0, 3).equals("reg")){
					try {
						UserDTO user;
						user = UserSessionService.getInstance().registerUser(query);
						if(userMap.keySet().contains((Object) user.getLogin())){
							continue;
						}
						DBConnection.getInstance().getUsersDAO().executeInsertQuery(user);
						userMap.put(user.getLogin(), user);
						
						sendMessage(out, "You're registered!");
						
					} catch (RegisterDataException e) {
						
						sendMessage(out, "Invalid registration data");
						
						e.printStackTrace();
						
					}
					continue;
				}
				
				if(query.substring(0, 4).equals("auth")){
					try {
						user = UserSessionService.getInstance().initSession(id, userMap, socket, query);
						
						sendMessage(out, "auth success!");
						
						
					} catch (AuthException e) {
						sendMessage(out, "Invalid login/password");
						e.printStackTrace();
					}
					continue;
				}

				if(query.substring(0, 3).equals("msg")){
					try {
						MessageDTO message = UserSessionService.getInstance().getMessage(query, user);
						ServerSession.getInstance().getMessageList().add(message);
					} catch (MessageDataException e) {
						
						sendMessage(out, "Invalid message");
						
						e.printStackTrace();
					}
					
					continue;
				}
				if(query.substring(0, 3).equals("add")){
					
					ContactDTO contact = new ContactDTO();
					
					Map <String, ValueDTO> data = new HashMap<String, ValueDTO>();
					ValueDTO value1 = new ValueDTO("int");
					value1.setValue(String.valueOf(this.user.getId()));
					ValueDTO value2 = new ValueDTO("int");
					UserDTO friend  = userMap.get((Object) query.substring(4));
					
					if(friend == null){
						continue;
					}
					
					value2.setValue(String.valueOf(friend.getId()));
					ValueDTO value3 = new ValueDTO("int");
					value3.setValue("-1");
					data.put("userid1", value1);
					data.put("userid2", value2);
					data.put("id",      value3);
					contact.setSerializedData(data);
					boolean cont = false;
					for(ContactDTO iterator:contactList){
						if(contact.equals(iterator)){
							cont = true;
							continue;
						}
					}
					
					if(cont){
						cont = false;
						continue;
					}
					
					DBConnection.getInstance().getContactsDAO().executeInsertQuery(contact);
					contactList.add(contact);
					
					sendMessage(out, UserSessionService.getInstance().getContactQuery(friend));
					continue;
					
				}
				
				if(query.equals("contacts")){
					
					List <UserDTO> contactUserList = UserSessionService.getInstance().getUserContacts(contactMap, userMap, user);
					
					for(UserDTO contact:contactUserList){
						sendMessage(out, UserSessionService.getInstance().getContactQuery(contact));
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
	
	public boolean isToDelete() {
		return isToDelete;
	}

	public void setToDelete(boolean isToDelete) {
		this.isToDelete = isToDelete;
	}

	private void sendMessage(DataOutputStream out, String message) throws IOException {
		
		int length = message.length();
		byte [] toSend = message.getBytes();
		byte [] lengthByte = new byte[4];
		
		lengthByte[0] = (byte)(length & 0xff);
		lengthByte[1] = (byte)((length >> 8) & 0xff);
		lengthByte[2] = (byte)((length >> 16) & 0xff);
		lengthByte[3] = (byte)((length >> 24) & 0xff);
		
		out.write(lengthByte);
		out.write(toSend);
		
//		for(;;){
//			if(isOutFree){
//				isOutFree = false;
//				out.write(lengthByte);
//				out.write(toSend);
//				isOutFree = true;
//				break;
//			}
//		}
		
	}
	
	
}
