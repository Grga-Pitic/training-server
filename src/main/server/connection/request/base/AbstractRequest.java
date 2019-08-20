package main.server.connection.request.base;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractRequest implements IRequest {
	
	public static char TYPE_SPLITTER = ':';
	public static char PARAMS_SPLITTER = '&';
	
	private List<String> requestParams;
	private String type;
	
	public AbstractRequest(int size, String type){
		this.type = type;
		this.requestParams = new LinkedList<String>();
		
		for(int i = 0; i < size; i++){
			requestParams.add(new String());
		}
	}
	
	public String getRequestString(){
		String requestString = type+TYPE_SPLITTER;
		
		for(String param:requestParams){
			requestString += param+PARAMS_SPLITTER; 
		}
		
		return requestString.substring(0, requestString.length()-1);
	}
	
	protected List<String> getParams(){
		return requestParams;
	}
}
