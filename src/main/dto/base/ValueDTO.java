package main.dto.base;

public class ValueDTO {
	private String type;
	private String value;
	
	public ValueDTO(String type){
		this.type  = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
