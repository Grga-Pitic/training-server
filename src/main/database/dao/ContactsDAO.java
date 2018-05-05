package main.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.database.DBConnection;
import main.database.dao.base.AbstractDAO;
import main.database.dao.base.IDataAccessObject;
import main.dto.ContactDTO;
import main.dto.UserDTO;
import main.dto.base.IDataTransferObject;
import main.dto.base.ValueDTO;
import main.dto.factories.ContactDTOFactory;
import main.dto.factories.UserDTOFactory;

public class ContactsDAO extends AbstractDAO implements IDataAccessObject{

	@Override
	public Map<Object, IDataTransferObject> executeSelectQuery(String query) {
		Map<Object, IDataTransferObject> contactMap = new HashMap<Object, IDataTransferObject>();
		
		List<IDataTransferObject> contactList =  super.executeSelectQuery(query, new ContactDTOFactory());
		
		for(IDataTransferObject contact:contactList){
			contactMap.put(((ContactDTO)contact).getId(), contact);
		}
		
		/*
		try {
			
			ResultSet resultSet = executeQuery(query);
			while(resultSet.next()){
				
				Map <String, ValueDTO> data = new HashMap<String, ValueDTO>();
				for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
					ValueDTO value = new ValueDTO(resultSet.getMetaData().getColumnTypeName(i));
					value.setValue(resultSet.getString(i));
					data.put(resultSet.getMetaData().getColumnName(i), value);
				}
				ContactDTO contact = new ContactDTO();
				contact.setSerializedData(data);
				contactMap.put(Integer.parseInt(data.get("id").getValue()), contact);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
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
