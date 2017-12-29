package main.dto.base;

import java.util.Map;

public class AbstractDTO {
	private Map <String, ValueDTO> data;

	protected void put(String columnName, String newValue){
		ValueDTO value = data.get(columnName);
		value.setValue(newValue);
		this.data.put(columnName, value);
	}
	
	public void setSerializedData(Map <String, ValueDTO> data){
		this.data = data;
	}
	
	public Map<String, ValueDTO> getSerializedData(){
		return this.data;
	}
}
