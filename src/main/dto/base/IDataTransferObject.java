package main.dto.base;

import java.util.Map;

public interface IDataTransferObject {
	
	void setSerializedData(Map <String, ValueDTO> data);
	Map<String, ValueDTO> getSerializedData();
	
}
