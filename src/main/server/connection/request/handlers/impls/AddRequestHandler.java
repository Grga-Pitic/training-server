package main.server.connection.request.handlers.impls;

import main.database.DBConnection;
import main.dto.ContactDTO;
import main.dto.UserDTO;
import main.server.connection.RequestQueue;
import main.server.connection.client.data.UserData;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;
import main.server.connection.request.factory.RequestFactory;
import main.server.connection.request.handlers.IRequestHandler;
import main.server.connection.request.impls.AddRequest;
import main.server.connection.request.impls.StatusRequest;

public class AddRequestHandler implements IRequestHandler {
	
	private RequestQueue outputQueue;
	private UserData userData;
	
	public AddRequestHandler(RequestQueue outputQueue, UserData userData){
		this.outputQueue = outputQueue;
		this.userData    = userData;
	}
	
	@Override
	public void Handle(IRequest request) {
		AddRequest castedRequest = (AddRequest) request;
		String login = castedRequest.getLogin();
		DBConnection db = DBConnection.getInstance();
		
		UserDTO addedUser = db.getUserByLogin(login);
		
		if(addedUser == null){  // if user not found
			outputQueue.Enqueue(new StatusRequest(RequestType.AddFailed));
			return;
		}
		
		ContactDTO contactDTO = new ContactDTO();
		contactDTO.setUserid1(userData.getId());
		contactDTO.setUserid2(addedUser.getId());
		
		db.addContact(contactDTO);
		outputQueue.Enqueue(new StatusRequest(RequestType.AddSuccess));
		outputQueue.Enqueue(RequestFactory.getInstance().createContactRequest(addedUser));
		
	}

}
