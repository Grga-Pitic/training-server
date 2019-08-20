package main.server.connection.handler.base;

import main.server.connection.RequestQueue;

public class HandlingData {
	private RequestQueue queue;
	private boolean isRun;
	
	public HandlingData(RequestQueue queue){
		this.queue = queue;
		this.isRun = false;
	}
	
	public RequestQueue getQueue(){
		return this.queue;
	}
	
	public void setRun(boolean newRun){
		this.isRun = newRun;
	}
	
	public boolean isRun() {
		return this.isRun;
	}
	
}
