package main.database.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.database.DBConnection;
import main.database.dao.base.AbstractDAO;
import main.database.dao.base.IDataAccessObject;
import main.dto.ContactDTO;
import main.dto.base.IDataTransferObject;
import main.dto.factories.ContactDTOFactory;

public class ContactsDAO extends AbstractDAO implements IDataAccessObject{

	@Override
	public Map<Object, IDataTransferObject> executeSelectQuery(String query) {
		Map<Object, IDataTransferObject> contactMap = new HashMap<Object, IDataTransferObject>();
		
		List<IDataTransferObject> contactList =  super.executeSelectQuery(query, new ContactDTOFactory());
		
		for(IDataTransferObject contact:contactList){
			contactMap.put(((ContactDTO)contact).getId(), contact);
		}

		return contactMap;
	}

	@Override
	public void executeInsertQuery(IDataTransferObject contact) {
		
		Statement statement = DBConnection.getInstance().getStatement();
		try {
			statement.executeUpdate(getInsertQueryString(contact, "contacts"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void executeDeleteQuery(IDataTransferObject contact) {
		
		Statement statement = DBConnection.getInstance().getStatement();
		try {
			statement.executeUpdate(getDeleteQueryString(contact, "contacts"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void executeUpdateQuery(IDataTransferObject contact) {

		Statement statement = DBConnection.getInstance().getStatement();
		try {
			statement.executeUpdate(getUpdateQueryString(contact, "contacts"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
