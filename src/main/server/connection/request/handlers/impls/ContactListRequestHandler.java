package main.server.connection.request.handlers.impls;

import java.util.Map;

import main.database.DBConnection;
import main.dto.UserDTO;
import main.server.connection.RequestQueue;
import main.server.connection.client.data.UserData;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;
import main.server.connection.request.factory.RequestFactory;
import main.server.connection.request.handlers.IRequestHandler;
import main.server.connection.request.impls.StatusRequest;

public class ContactListRequestHandler implements IRequestHandler {
	
	private RequestQueue outputQueue;
	private UserData userData;
	
	public ContactListRequestHandler(RequestQueue outputQueue, UserData userData){
		this.outputQueue = outputQueue;
		this.userData = userData;
	}
	
	@Override
	public void Handle(IRequest request) {
		
		if(!userData.isAuthorized()){
			outputQueue.Enqueue(new StatusRequest(RequestType.AuthFailed));
			return;
		}
		
		// Gettings map of UserDTO of user contacts.
		Map <Object, UserDTO> contactsData = DBConnection.getInstance().getContactsByUserLogin(userData.getLogin());
		
		// Sends user contacts to user.
		for(Object key:contactsData.keySet()){
			outputQueue.Enqueue(RequestFactory.getInstance().createContactRequest(contactsData.get(key)));
		}
	}

}
