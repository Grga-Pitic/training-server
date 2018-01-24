package main.dto;

import java.util.Map;

import main.dto.base.AbstractDTO;
import main.dto.base.IDataTransferObject;
import main.dto.base.ValueDTO;

public class ContactDTO extends AbstractDTO implements IDataTransferObject {
	private int id;
	private int userid1;
	private int userid2;
	
	@Override
	public void setSerializedData(Map <String, ValueDTO> data){
		
		super.setSerializedData(data);
		this.id      = Integer.parseInt(data.get("id").getValue());
		this.userid1 = Integer.parseInt(data.get("userid1").getValue());
		this.userid2 = Integer.parseInt(data.get("userid2").getValue());
		
	}
	
	@Override
	public Map<String, ValueDTO> getSerializedData(){
	
		put("id",      String.valueOf(id));
		put("userid1", String.valueOf(userid1));
		put("userid2", String.valueOf(userid2));
		return super.getSerializedData();
		
	}
	
	@Override
	public String toString(){
		return "id = "+id+" userid1 = " + userid1+" userid2 = " + userid2;
	}
	
	@Override 
	public boolean equals(Object object) {
		if(this == object){
			return true;
		}
		if(object == null){
			return false;
		}
		if(getClass() != object.getClass()){
			return false;
		}
		ContactDTO contact = (ContactDTO) object;
		if(this.userid1 != contact.userid1){
			return false;
		}
		if(this.userid2 != contact.userid2){
			return false;
		}
		return true;
	}

	public int getUserid1() {
		return userid1;
	}

	public void setUserid1(int userid1) {
		this.userid1 = userid1;
	}

	public int getUserid2() {
		return userid2;
	}

	public void setUserid2(int userid2) {
		this.userid2 = userid2;
	}

	public int getId() {
		return id;
	}
}
