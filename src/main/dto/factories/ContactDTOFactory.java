package main.dto.factories;

import main.dto.ContactDTO;
import main.dto.base.IDataTransferObject;
import main.dto.factories.base.IDTOFactory;

public class ContactDTOFactory implements IDTOFactory {

	@Override
	public IDataTransferObject createDTO() {
		return new ContactDTO();
	}

}
