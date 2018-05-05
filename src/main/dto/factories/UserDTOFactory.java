package main.dto.factories;

import main.dto.UserDTO;
import main.dto.base.IDataTransferObject;
import main.dto.factories.base.IDTOFactory;

public class UserDTOFactory implements IDTOFactory {

	@Override
	public IDataTransferObject createDTO() {
		return new UserDTO();
	}
	
}
