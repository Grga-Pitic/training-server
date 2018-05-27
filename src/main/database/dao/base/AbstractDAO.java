package main.database.dao.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import main.database.DBConnection;
import main.dto.base.IDataTransferObject;
import main.dto.base.ValueDTO;
import main.dto.factories.base.IDTOFactory;

public class AbstractDAO {
	
	protected List<IDataTransferObject> executeSelectQuery(String query, IDTOFactory factory) {
		List <IDataTransferObject> dtoList = new LinkedList<IDataTransferObject>();
		try {
			
			ResultSet resultSet = executeQuery(query);
			while(resultSet.next()){
				
				Map <String, ValueDTO> data = new HashMap<String, ValueDTO>();
				for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
					ValueDTO value = new ValueDTO(resultSet.getMetaData().getColumnTypeName(i));
					value.setValue(resultSet.getString(i));
					data.put(resultSet.getMetaData().getColumnName(i), value);
				}
				IDataTransferObject dto = factory.createDTO();
				dto.setSerializedData(data);
				dtoList.add(dto);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dtoList;
	}
	
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
