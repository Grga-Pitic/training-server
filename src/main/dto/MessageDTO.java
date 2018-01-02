package main.dto;

import java.util.Map;

import main.dto.base.AbstractDTO;
import main.dto.base.IDataTransferObject;
import main.dto.base.ValueDTO;

public class MessageDTO extends AbstractDTO implements IDataTransferObject {
	private String message;
	private String fromLogin;
	private String toLogin;
	
	@Override
	public void setSerializedData(Map <String, ValueDTO> data){
		super.setSerializedData(data);
		this.message = data.get("message").getValue();
		this.fromLogin  = data.get("fromLogin").getValue();
		this.toLogin    = data.get("toLogin").getValue();
	}
	
	public Map<String, ValueDTO> getSerializedData(){
		put("message", message);
		put("fromLogin", String.valueOf(fromLogin));
		put("toLogin", String.valueOf(toLogin));
		return getSerializedData();
	}
	
	public String getMessage() {
		return message;
	}
	public String getFromLogin() {
		return fromLogin;
	}
	public String getToLogin() {
		return toLogin;
	}
	
	@Override
	public String toString(){
		return "text: "+this.message;
	}
	
}
