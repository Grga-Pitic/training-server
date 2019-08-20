package main.server.connection.request.impls;

import main.server.connection.request.base.IRequest;
import main.server.connection.request.base.RequestType;

public class StatusRequest implements IRequest{

	private static int AUTH_SUCCESS = 0;
	private static int AUTH_FAILED = 1;
	private static int REG_SUCCESS = 2;
	private static int REG_FAILED = 3;
	private static int INVALID_MESSAGE = 4;
	private static int ADD_SUCCESS = 5;
	private static int ADD_FAILED = 6;
	
	private static String [] REQUEST_STRING = {"auth success!", 
											   "Invalid login/password",
											   "You're registered!",
											   "Invalid registration data",
											   "Invalid message",
											   "The user was added",
											   "The user not found"};
	private static String INVALID_STATUS = "Invalid status message.";
	
	private RequestType status;
	
	public StatusRequest(RequestType status){
		this.status = status;
	}
	
	@Override
	public String getRequestString() {
		switch(status){
		case AuthSuccess:
			return REQUEST_STRING[AUTH_SUCCESS];
		case AuthFailed:
			return REQUEST_STRING[AUTH_FAILED];
		case RegistationSuccess:
			return REQUEST_STRING[REG_SUCCESS];
		case RegistationFailed:
			return REQUEST_STRING[REG_FAILED];
		case InvalidMessage:
			return REQUEST_STRING[INVALID_MESSAGE];
		case AddSuccess:
			return REQUEST_STRING[ADD_SUCCESS];
		case AddFailed:
			return REQUEST_STRING[ADD_FAILED];
		default:
			return INVALID_STATUS;
		}
	}

	@Override
	public RequestType getType() {
		return status;
	}

	
	
}
