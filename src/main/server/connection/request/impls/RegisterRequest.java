package main.server.connection.request.impls;

import main.server.connection.request.base.AbstractRequest;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;

public class RegisterRequest extends AbstractRequest implements IRequest {
	
	public static final int LOGIN = 0;
	public static final int PASSWORD = 1;
	public static final int NICKNAME = 2;
	
	private static final int PARAMETERS_QUANTITY = 3;
	private static final String TYPE = new String("reg");
	
	public RegisterRequest(String login, String password, String nickname) {
		super(PARAMETERS_QUANTITY, TYPE);
		
		getParams().set(LOGIN, login);
		getParams().set(PASSWORD, password);
		getParams().set(NICKNAME, nickname);
	}
	
	public String getLogin(){
		return getParams().get(LOGIN);
	}
	
	public String  getPassword() {
		return getParams().get(PASSWORD);
	}

	public String getNickname() {
		return getParams().get(NICKNAME);
	}
	
	@Override
	public RequestType getType() {
		// TODO Auto-generated method stub
		return RequestType.Register;
	}

}
