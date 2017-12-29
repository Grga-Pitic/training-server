package main.database.dao.base;

import java.util.Map;

import main.dto.base.IDataTransferObject;

public interface IDataAccessObject {
	
	Map<Object, IDataTransferObject> executeSelectQuery(String query);
	void executeInsertQuery(IDataTransferObject abstractDTO);
	void executeDeleteQuery(IDataTransferObject abstractDTO);
	void executeUpdateQuery(IDataTransferObject abstractDTO);
	
}
