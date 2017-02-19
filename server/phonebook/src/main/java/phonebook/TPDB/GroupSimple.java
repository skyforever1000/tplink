package phonebook.TPDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.lang.reflect.Type;

public class GroupSimple {
	private String column_groupId = "_groupId";
	private String column_groupName = "_groupName";
	private String column_status = "_status";
	private String column_versionId = "_versionId";

	private final static String[] column = {"_groupId", "_groupName", "_status", "_versionId"};

	public ArrayList<Group> ResultSet2Object(ResultSet rs){
		ArrayList<Group> groups = new ArrayList<Group>();
		try {
			while(rs.next()){
				Group group = new Group();
				group.set_groupId(rs.getInt(column_groupId));
				group.set_groupName(rs.getString(column_groupName));
				group.set_status(rs.getInt(column_status));
				group.set_versionId(rs.getDate(column_versionId));
				groups.add(group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return groups;
	}
	
	public String Obj2Json(ArrayList<Group> groups){
		if(groups.isEmpty())
			return "";
		
		Gson gson = new Gson();
		String json = gson.toJson(groups);
		return json;
	}
	
	public ArrayList<Group> Json2Obj(String json){
		Gson gson = new Gson();
		Type listType = new TypeToken<ArrayList<Group>>(){}.getType();
		ArrayList<Group> groups = gson.fromJson(json, listType);
		return groups;
	}

	public static String[] getColumn() {
		return column;
	}

}

