package main.server;

import java.net.Socket;

public class UserSession implements Runnable {
	private int    id;
	private String key;
	private int    userid;
	
	private Socket socket;
	
	public UserSession(int id, Socket socket){
		
		this.id     = id;
		this.key    = key;
		this.userid = userid;
		this.socket = socket;
		
	}

	public int getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public int getUserid() {
		return userid;
	}

	@Override
	public void run() {
		
	}
}
