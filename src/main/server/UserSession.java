package main.server;

public class UserSession {
	private int    id;
	private String key;
	private int    userid;
	
	public UserSession(int id, String key, int userid){
		
		this.id     = id;
		this.key    = key;
		this.userid = userid;
	
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
	
	
}
