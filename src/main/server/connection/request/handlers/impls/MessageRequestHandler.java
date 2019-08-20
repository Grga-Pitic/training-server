package main.server.connection.request.handlers.impls;

import main.server.connection.RequestQueue;
import main.server.connection.client.data.CrossClientCommunication;
import main.server.connection.client.data.UserData;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;
import main.server.connection.request.handlers.IRequestHandler;
import main.server.connection.request.impls.MessageRequest;
import main.server.connection.request.impls.StatusRequest;

public class MessageRequestHandler implements IRequestHandler {

	
	private RequestQueue outputQueue;
	private UserData userData;
	
	public MessageRequestHandler(RequestQueue outputQueue, UserData userData){
		this.outputQueue = outputQueue;
		this.userData = userData;
	}
	
	@Override
	public void Handle(IRequest request) {
		
		if(!userData.isAuthorized()){
			outputQueue.Enqueue(new StatusRequest(RequestType.AuthFailed));
			return;
		}
		
		MessageRequest castedRequest = (MessageRequest)request;
		MessageRequest requestToSend = new MessageRequest(castedRequest.getText(), userData.getLogin());
		CrossClientCommunication.getInstance().sendRequestTo(castedRequest.getToLogin(), requestToSend);
	}

}
