package main.server.connection.request.factory;

import main.dto.UserDTO;
import main.server.connection.request.base.AbstractRequest;
import main.server.connection.request.base.IRequest;
import main.server.connection.request.impls.AddRequest;
import main.server.connection.request.impls.AuthRequest;
import main.server.connection.request.impls.ContactListRequest;
import main.server.connection.request.impls.ContactRequest;
import main.server.connection.request.impls.InvalidRequest;
import main.server.connection.request.impls.MessageRequest;
import main.server.connection.request.impls.RegisterRequest;

public class RequestFactory {
	private static RequestFactory instance;
	
	// TODO вынести проверку на валидность запроса в отдельный класс/метод
	// TODO обработку односложных (вроде ContactList) запросов и составных (Register, Message, etc) тоже как то отделить
	public IRequest createRequestByString(String requestString){
		IRequest request;
		
		if(requestString.equals(ContactListRequest.REQUEST_STRING)){
			return new ContactListRequest();
		}
		
		int splitterIndex = requestString.indexOf(AbstractRequest.TYPE_SPLITTER);
		
		if(splitterIndex == -1){   // If request is one word.
			return new InvalidRequest(); 
		}
		
		String requestType = requestString.substring(0,  splitterIndex);
		String requestBody = requestString.substring(1 + splitterIndex);
		
		String [] requestParams = requestBody.split(""+AbstractRequest.PARAMS_SPLITTER); // TODO костыль
		
		switch(requestType){
		case "auth":
			if(requestParams.length != 2){
				request = new InvalidRequest();
				break;
			}
			request = new AuthRequest(requestParams[AuthRequest.LOGIN], 
									  requestParams[AuthRequest.PASSWORD]);
			break;
		case "add":
			if(requestParams.length != 1){
				request = new InvalidRequest();
				break;
			}
			request = new AddRequest(requestParams[AddRequest.LOGIN]);
			break;
		case "reg":
			if(requestParams.length != 3){
				request = new InvalidRequest();
				break;
			}
			request = new RegisterRequest(requestParams[RegisterRequest.LOGIN],
					 					  requestParams[RegisterRequest.PASSWORD],
					 					  requestParams[RegisterRequest.NICKNAME]);
			break;
		case "msg":
			if(requestParams.length != 2){
				request = new InvalidRequest();
				break;
			}
			request = new MessageRequest(requestParams[MessageRequest.TEXT], 
										 requestParams[MessageRequest.TO_LOGIN]);
			break;
		default:
			request = new InvalidRequest();
			break;
		}	
		
		return request;
	}
	
	public ContactRequest createContactRequest(UserDTO dto){
		return new ContactRequest(dto.getLogin(), dto.getNickname());
	}
	
	public static RequestFactory getInstance(){
		if(instance == null){
			instance = new RequestFactory();
		}
		return instance;
	}
}
