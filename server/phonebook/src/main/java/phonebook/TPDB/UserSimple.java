package phonebook.TPDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.lang.reflect.Type;


public class UserSimple {
	private String column_userId = "_userId";
	private String column_phoneNo = "_phoneNo";
	private String column_firstName = "_firstName";
	private String colum_lastName = "_lastName";
	private String column_status = "_status";
	private String column_address = "_address";
	private String column_groupId = "_groupId";
	private String column_versionId = "_versionId";
	
	private final static String[] column = {"_userId", "_phoneNo", "_firstName", "_lastName", "_status", "_address", "_groupId", "_versionId"};

	public ArrayList<User> ResultSet2Object(ResultSet rs){
		ArrayList<User> users = new ArrayList<User>();
		try {
			while(rs.next()){
				User user = new User();
				user.set_userId(rs.getInt(column_userId));
				user.set_phoneNo(rs.getString(column_phoneNo));
				user.set_firstName(rs.getString(column_firstName));
				user.set_lastName(rs.getString(colum_lastName));
				user.set_address(rs.getString(column_address));
				user.set_status(rs.getInt(column_status));
				user.set_groupId(rs.getInt(column_groupId));
				user.set_versionId(rs.getDate(column_versionId));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public String Obj2Json(ArrayList<User> users){
		if(users.isEmpty())
			return "";
		
		Gson gson = new Gson();
		String json = gson.toJson(users);
		return json;
	}
	
	public ArrayList<User> Json2Obj(String json){
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<User>>(){}.getType();
		ArrayList<User> users = gson.fromJson(json, listType);
		return users;
	}

	public static String[] getColumn() {
		return column;
	}
	
}

