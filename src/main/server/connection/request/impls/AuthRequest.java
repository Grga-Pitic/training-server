package main.server.connection.request.impls;

import main.server.connection.request.base.AbstractRequest;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;

public class AuthRequest extends AbstractRequest implements IRequest {
	
	public static final int LOGIN = 0;
	public static final int PASSWORD = 1;
	
	private static final int PARAMETERS_QUANTITY = 2;
	private static final String TYPE = new String("auth");
	
	public AuthRequest(String login, String password) {
		super(PARAMETERS_QUANTITY, TYPE);
		
		getParams().set(LOGIN, login);
		getParams().set(PASSWORD, password);
	}
	
	public String getLogin(){
		return getParams().get(LOGIN);
	}
	
	public String getPassword(){
		return getParams().get(PASSWORD);
	}

	@Override
	public RequestType getType() {
		return RequestType.Auth;
	}
	
}
