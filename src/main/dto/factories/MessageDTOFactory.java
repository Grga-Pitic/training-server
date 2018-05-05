package main.dto.factories;

import main.dto.MessageDTO;
import main.dto.base.IDataTransferObject;
import main.dto.factories.base.IDTOFactory;

public class MessageDTOFactory implements IDTOFactory {

	@Override
	public IDataTransferObject createDTO() {
		return new MessageDTO();
	}
	
}
