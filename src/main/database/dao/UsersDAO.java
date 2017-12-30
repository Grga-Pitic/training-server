package main.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.database.DBConnection;
import main.database.dao.base.AbstractDAO;
import main.database.dao.base.IDataAccessObject;
import main.dto.UserDTO;
import main.dto.base.IDataTransferObject;
import main.dto.base.ValueDTO;

public class UsersDAO extends AbstractDAO implements IDataAccessObject {
	
	@Override
	public Map<Object, UserDTO> executeSelectQuery(String query) {
		Map <Object, UserDTO> userMap = new HashMap<Object, UserDTO>();
		try {
			
			ResultSet resultSet = executeQuery(query);
			while(resultSet.next()){
				
				Map <String, ValueDTO> data = new HashMap<String, ValueDTO>();
				for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
					ValueDTO value = new ValueDTO(resultSet.getMetaData().getColumnTypeName(i));
					value.setValue(resultSet.getString(i));
					data.put(resultSet.getMetaData().getColumnName(i), value);
				}
				UserDTO user = new UserDTO();
				user.setSerializedData(data);
				userMap.put(data.get("login").getValue(), user);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userMap;
	}

	@Override
	public void executeInsertQuery(IDataTransferObject user) {
		Statement statement = DBConnection.getInstance().getStatement();
		try {
			statement.executeUpdate(getInsertQueryString(user, "users"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void executeDeleteQuery(IDataTransferObject user) {
		Statement statement = DBConnection.getInstance().getStatement();
		try {
			statement.executeUpdate(getDeleteQueryString(user, "users"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void executeUpdateQuery(IDataTransferObject user) {
		Statement statement = DBConnection.getInstance().getStatement();
		try {
			statement.executeUpdate(getUpdateQueryString(user, "users"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
