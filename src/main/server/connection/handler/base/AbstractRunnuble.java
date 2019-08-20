package main.server.connection.handler.base;

public abstract class AbstractRunnuble implements Runnable {
	private HandlingData data;
	
	public AbstractRunnuble(HandlingData data){
		this.data = data;
	}
	
	public HandlingData getData(){
		return this.data;
	}
}
