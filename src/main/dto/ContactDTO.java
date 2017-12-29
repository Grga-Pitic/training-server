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
//		this.id      = Integer.parseInt(data.get("id"));
//		this.userid1 = Integer.parseInt(data.get("userid1"));
//		this.userid2 = Integer.parseInt(data.get("userid2"));
	
	}
	
	@Override
	public Map<String, ValueDTO> getSerializedData(){
	
	//	put("id",      String.valueOf(id));
	//	put("userid1", String.valueOf(id));
	//	put("userid2", String.valueOf(id));
		return super.getSerializedData();
		
	}
	
	@Override
	public String toString(){
		return "id = "+id+" userid1 = " + userid1+" userid2 = " + userid2;
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
