package main.server.connection.request.impls;

import main.server.connection.request.base.AbstractRequest;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;

public class MessageRequest extends AbstractRequest implements IRequest {

	public static int TEXT = 0;
	public static int TO_LOGIN = 1;
	
	private static final int PARAMETERS_QUANTITY = 2;
	private static final String TYPE = new String("msg");
	
	public MessageRequest(String text, String toLogin) {
		super(PARAMETERS_QUANTITY, TYPE);
		getParams().set(TEXT, text);
		getParams().set(TO_LOGIN, toLogin);
	}
	
	public String getToLogin(){
		return getParams().get(TO_LOGIN);
	}
	
	public String getText(){
		return getParams().get(TEXT);
	}

	@Override
	public RequestType getType() {
		return RequestType.Message;
	}

	
	
}
