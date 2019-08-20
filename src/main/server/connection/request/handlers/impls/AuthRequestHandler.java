package main.server.connection.request.handlers.impls;

import main.database.DBConnection;
import main.dto.UserDTO;
import main.server.connection.RequestQueue;
import main.server.connection.client.ClientConnection;
import main.server.connection.client.data.CrossClientCommunication;
import main.server.connection.client.data.UserData;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;
import main.server.connection.request.handlers.IRequestHandler;
import main.server.connection.request.impls.AuthRequest;
import main.server.connection.request.impls.StatusRequest;

public class AuthRequestHandler implements IRequestHandler {
	
	private RequestQueue outputQueue;
	private UserData userData;
	private ClientConnection connenction;
	
	public AuthRequestHandler(RequestQueue outputQueue, UserData userData, 
			ClientConnection connenction){
		this.outputQueue = outputQueue;
		this.userData    = userData;
		this.connenction = connenction;
	}
	
	@Override
	public void Handle(IRequest request) {
		if(request.getType() != RequestType.Auth){
			return;
		}
		AuthRequest castedRequest = (AuthRequest)request;
		DBConnection db = DBConnection.getInstance();
		UserDTO userInfo = db.getUserByLogin(castedRequest.getLogin());
		
		// if user wasn't found
		if(userInfo == null){
			outputQueue.Enqueue(new StatusRequest(RequestType.AuthFailed));
			return;
		}
		
		// if password is correct
		if(castedRequest.getPassword().equals(userInfo.getPassword())){
			outputQueue.Enqueue(new StatusRequest(RequestType.AuthSuccess));
			
			userData.setId(userInfo.getId());
			userData.setLogin(userInfo.getLogin());
			userData.setNickname(userInfo.getNickname());
			userData.setPassword(userInfo.getPassword());
			userData.setAuthorized(true);
			
			CrossClientCommunication.getInstance().addQueueByLogin(userInfo.getLogin(), outputQueue);
			
			return;
		}
		
		// if password is incorrect
		outputQueue.Enqueue(new StatusRequest(RequestType.AuthFailed));
		connenction.BreakHandling();
		return;
		
	}

}
