package main.server.connection.request.impls;

import main.server.connection.request.base.AbstractRequest;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;

public class AddRequest extends AbstractRequest implements IRequest {

	public static final int LOGIN = 0;
	
	private static final int PARAMETERS_QUANTITY = 1;
	private static final String TYPE = new String("add");
	
	public AddRequest(String login) {
		super(PARAMETERS_QUANTITY, TYPE);
		getParams().set(LOGIN, login);
	}
	
	public String getLogin(){
		return getParams().get(LOGIN);
	}
	
	@Override
	public RequestType getType() {
		return RequestType.Add;
	}

}
