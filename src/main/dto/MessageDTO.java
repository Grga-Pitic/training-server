package main.dto;

import java.util.Map;

import main.dto.base.AbstractDTO;
import main.dto.base.IDataTransferObject;
import main.dto.base.ValueDTO;

public class MessageDTO extends AbstractDTO implements IDataTransferObject {
	private String message;
	private int    fromID;
	private int    toID;
	
	@Override
	public void setSerializedData(Map <String, ValueDTO> data){
		super.setSerializedData(data);
		this.message = data.get("message").getValue();
		this.fromID  = Integer.parseInt(data.get("fromID").getValue());
		this.toID    = Integer.parseInt(data.get("toID").getValue());
	}
	
	public Map<String, ValueDTO> getSerializedData(){
		put("message", message);
		put("fromID", String.valueOf(fromID));
		put("toID", String.valueOf(toID));
		return getSerializedData();
	}
	
	public String getMessage() {
		return message;
	}
	public int getFromID() {
		return fromID;
	}
	public int getToID() {
		return toID;
	}
	
	
}
