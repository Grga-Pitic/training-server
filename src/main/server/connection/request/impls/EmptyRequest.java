package main.server.connection.request.impls;

import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;

public class EmptyRequest implements IRequest {
	
	public static String REQUEST_STRING = "empty";
	
	@Override
	public String getRequestString() {
		return REQUEST_STRING;
	}

	@Override
	public RequestType getType() {
		// TODO Auto-generated method stub
		return RequestType.Empty;
	}
	
}
