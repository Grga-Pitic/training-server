package main.server.connection.request.handlers.impls;

import main.database.DBConnection;
import main.dto.UserDTO;
import main.server.connection.RequestQueue;
import main.server.connection.client.ClientConnection;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;
import main.server.connection.request.handlers.IRequestHandler;
import main.server.connection.request.impls.RegisterRequest;
import main.server.connection.request.impls.StatusRequest;

public class RegisterRequestHandler implements IRequestHandler {
	
	private RequestQueue outputQueue;
	private ClientConnection connenction;
	
	public RegisterRequestHandler(RequestQueue outputQueue, ClientConnection connenction){
		this.outputQueue = outputQueue;
		this.connenction = connenction;
	}
	
	@Override
	public void Handle(IRequest request) {
		RegisterRequest castedRequest = (RegisterRequest)request;
		UserDTO newUser = new UserDTO();
		
		newUser.setLogin(castedRequest.getLogin());
		newUser.setNickname(castedRequest.getNickname());
		newUser.setPassword(castedRequest.getPassword());
		
		DBConnection db = DBConnection.getInstance();
		
		if(db.isLoginFree(castedRequest.getLogin())){ // if login not free
			db.addNewUser(newUser);
			outputQueue.Enqueue(new StatusRequest(RequestType.RegistationSuccess));
			return;
		}
		
		outputQueue.Enqueue(new StatusRequest(RequestType.RegistationFailed));
		connenction.BreakHandling();
		
	}
	
	
	
}
