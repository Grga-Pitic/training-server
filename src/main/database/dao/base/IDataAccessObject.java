package main.database.dao.base;

import java.util.List;

import main.dto.UserDTO;
import main.dto.base.IDataTransferObject;

public interface IDataAccessObject {
	
	List <IDataTransferObject> executeSelectQuery(String query);
	void executeInsertQuery(IDataTransferObject abstractDTO);
	void executeDeleteQuery(IDataTransferObject abstractDTO);
	void executeUpdateQuery(IDataTransferObject abstractDTO);
	
}
