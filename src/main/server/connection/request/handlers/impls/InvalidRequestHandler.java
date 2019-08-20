package main.server.connection.request.handlers.impls;

import main.server.connection.RequestQueue;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.handlers.IRequestHandler;

public class InvalidRequestHandler implements IRequestHandler {
	
	RequestQueue outputQueue;
	
	public InvalidRequestHandler(RequestQueue outputQueue){
		this.outputQueue = outputQueue;
	}
	
	@Override
	public void Handle(IRequest request) {
		outputQueue.Enqueue(request);
	}

}
