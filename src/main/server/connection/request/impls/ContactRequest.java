package main.server.connection.request.impls;

import main.server.connection.request.base.AbstractRequest;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;

public class ContactRequest extends AbstractRequest implements IRequest {

	public static final int LOGIN = 0;
	public static final int NICKNAME = 1;
	
	private static final int PARAMETERS_QUANTITY = 2;
	private static final String TYPE = new String("contact");
	
	public ContactRequest(String login, String nickname) {
		super(PARAMETERS_QUANTITY, TYPE);
		
		getParams().set(LOGIN, login);
		getParams().set(NICKNAME, nickname);
	}
	
	public String getLogin(){
		return getParams().get(LOGIN);
	}
	
	public String getNickname(){
		return getParams().get(NICKNAME);
	}

	@Override
	public RequestType getType() {
		return RequestType.Contact;
	}

}
