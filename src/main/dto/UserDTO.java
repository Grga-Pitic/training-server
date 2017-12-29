package main.dto;

import java.util.Map;

import main.dto.base.AbstractDTO;
import main.dto.base.IDataTransferObject;
import main.dto.base.ValueDTO;

public class UserDTO extends AbstractDTO implements IDataTransferObject {
	private int    id;
	private String login;
	private String nickname;
	private String password;
	
	@Override
	public void setSerializedData(Map <String, ValueDTO> data){

		super.setSerializedData(data);
		this.id       = Integer.parseInt((String)data.get("id").getValue());
		this.login    = (String)data.get("login").getValue();
		this.password = (String)data.get("password").getValue();
		this.nickname = (String)data.get("nickname").getValue();
	
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	@Override
	public Map<String, ValueDTO> getSerializedData(){
	
		put("id", String.valueOf(id));
		put("login", login);
		put("nickname", nickname);
		put("password", password);
		return super.getSerializedData();
		
	}
	
	@Override
	public String toString(){
		return "id: "+String.valueOf(id)+"; login: "+login+"; nick: "+nickname+"; password: "+password;
	}
}
