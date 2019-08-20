package main.server.connection;

import java.util.LinkedList;
import java.util.List;

import main.server.connection.request.base.IRequest;

public class RequestQueue {

	private volatile List<IRequest> queue;
	
	
	public RequestQueue() {
		this.queue = new LinkedList<IRequest>();
	}
	
	public synchronized void Enqueue(IRequest newRequest){
		queue.add(newRequest);
	}
	
	public synchronized IRequest Dequeue(){
		return queue.remove(0);
	}
	
	public synchronized void Clear(){
		queue.clear();
	}
	
	public synchronized boolean isEmpty(){
		
		if(queue.size() == 0){
			return true;
		}
		return false;
	
	}
	
}
