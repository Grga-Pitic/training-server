package main.database.dao.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.database.DBConnection;
import main.dto.UserDTO;
import main.dto.base.IDataTransferObject;
import main.dto.base.ValueDTO;

public class AbstractDAO {
	
	protected int executeUpdate(String query) throws SQLException{
		
		Statement statement = DBConnection.getInstance().getStatement();
		return statement.executeUpdate(query);
		
	}
	
	protected ResultSet executeQuery(String query) throws SQLException{
		
		Statement statement = DBConnection.getInstance().getStatement();
		statement.executeQuery(query);
		ResultSet resultSet = statement.executeQuery(query);
		return resultSet;
		
	}
	
	protected String getInsertQueryString(IDataTransferObject abstractDTO, String tableName){
		String query      = "";
		String columnSet  = "";
		String valueSet   = "";
		
		Map <String, ValueDTO> data = abstractDTO.getSerializedData();
		
		for(String column:data.keySet()){
			if(column.equals("id")){
				continue;
			}
			columnSet += "`"+column+"`, ";
			if(data.get(column).getType().equalsIgnoreCase("VARCHAR")){
				valueSet += "'" +data.get(column).getValue()+ "', ";
				continue;
			}
			valueSet  += data.get(column).getValue()+", "; 
		}
		columnSet = columnSet.substring(0, columnSet.length()-2);
		valueSet  = valueSet.substring (0, valueSet.length() -2);
		query = "INSERT INTO `"+tableName+"`("+columnSet+") VALUES ("+valueSet+")"; 
		return query;
	}
	
	protected String getUpdateQueryString(IDataTransferObject abstractDTO, String tableName){
		Map <String, ValueDTO> data = abstractDTO.getSerializedData();
		String query = "";
		String bodyQuery = "";
		
		for(String column:data.keySet()){
			if(column.equals("id")){
				continue;
			}
			bodyQuery += '`'+column+'`' + "=";
			if(data.get(column).getType().equalsIgnoreCase("VARCHAR")){
				bodyQuery += "'"+data.get(column).getValue()+"', ";
				continue;
			}
			
			bodyQuery += data.get(column).getValue()+", ";
		}
		
		query = "UPDATE "+tableName+" SET "+bodyQuery.substring(0, bodyQuery.length()-2)+
				" WHERE `id`="+data.get("id").getValue(); 
		return query;
	}
	
	protected String getDeleteQueryString(IDataTransferObject abstractDTO, String tableName){
		Map <String, ValueDTO> data = abstractDTO.getSerializedData();
		String query = "DELETE FROM `"+tableName+"` WHERE `id`="+data.get("id").getValue(); 
		return query;
	}
}
