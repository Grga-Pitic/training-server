package main.server.connection.request.impls;

import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;

public class ContactListRequest implements IRequest {
	
	public static String REQUEST_STRING = "contacts";
	
	@Override
	public String getRequestString() {
		return REQUEST_STRING;
	}

	@Override
	public RequestType getType() {
		return RequestType.ContactList;
	}
}
