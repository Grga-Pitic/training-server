package main.server.connection.client.data;

import java.util.HashMap;
import java.util.Map;

import main.server.connection.request.base.IRequest;
import main.server.connection.RequestQueue;

// The class necessary to send requests betveen a clients.
public class CrossClientCommunication {
	private static CrossClientCommunication instance;
	private Map<String, RequestQueue> outputQueueMap;
	
	public CrossClientCommunication(){
		outputQueueMap = new HashMap<String, RequestQueue>();
	}
	
	public boolean isClientOnline(String login){
		return outputQueueMap.keySet().contains(login);
	}
	
	public void addQueueByLogin(String login, RequestQueue outputQueue){
		outputQueueMap.put(login, outputQueue);
	}
	
	public void removeQueueByLogin(String login){
		outputQueueMap.remove(login);
	}
	
	public void sendRequestTo(String toLogin, IRequest request){
		RequestQueue queue = outputQueueMap.get(toLogin);
		
		if(queue == null){ // if user is offline
			return; // don't send request
		}
		
		queue.Enqueue(request);
	}
	
	public static CrossClientCommunication getInstance() {
		if(instance == null){
			instance = new CrossClientCommunication();
		}
		return instance;
	}

}
