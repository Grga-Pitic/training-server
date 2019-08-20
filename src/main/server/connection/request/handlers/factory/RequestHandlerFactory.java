package main.server.connection.request.handlers.factory;

import main.server.connection.RequestQueue;
import main.server.connection.client.ClientConnection;
import main.server.connection.client.data.UserData;
import main.server.connection.request.base.RequestType;
import main.server.connection.request.handlers.IRequestHandler;
import main.server.connection.request.handlers.impls.AddRequestHandler;
import main.server.connection.request.handlers.impls.AuthRequestHandler;
import main.server.connection.request.handlers.impls.ContactListRequestHandler;
import main.server.connection.request.handlers.impls.DefaultRequestHandler;
import main.server.connection.request.handlers.impls.InvalidRequestHandler;
import main.server.connection.request.handlers.impls.MessageRequestHandler;
import main.server.connection.request.handlers.impls.RegisterRequestHandler;

public class RequestHandlerFactory {
	
	private RequestQueue outputQueue;
	private UserData userData;
	private ClientConnection connenction;
	
	public RequestHandlerFactory(RequestQueue outputQueue, UserData userData){
		this.outputQueue = outputQueue;
		this.userData    = userData;
	}
	
	public IRequestHandler createByType(RequestType type){
		
		switch(type){
		case Auth:
			return new AuthRequestHandler(outputQueue, userData, connenction);
		case ContactList:
			return new  ContactListRequestHandler(outputQueue, userData);
		case Message:
			return new MessageRequestHandler(outputQueue, userData);
		case Register:
			return new RegisterRequestHandler(outputQueue, connenction);
		case Add:
			return new AddRequestHandler(outputQueue, userData);
		case InvalidRequest:
			return new InvalidRequestHandler(outputQueue);
		default:
			return new DefaultRequestHandler();
		}
		
	}
	
}
