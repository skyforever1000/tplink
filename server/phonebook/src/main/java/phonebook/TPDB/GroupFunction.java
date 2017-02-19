package phonebook.TPDB;

import java.util.ArrayList;

import com.google.gson.Gson;

public class GroupFunction {
	
	public String getGroupList(){
		String query = "select * from tplink.Group where _status =1";
		GroupDao groupDao =  new GroupDao();
		String groups = groupDao.queryGroupCustomized(query);
		
		return groups;
	}
	
    public Boolean isGroupNameExist(String groupName){
		String query = "select * from tplink.Group where _groupName like '%" +groupName+"%'";
		GroupDao groupDao =  new GroupDao();
		ArrayList<Group> groups = groupDao.queryGroupCustomizedObj(query);
		
		return groups!=null&&groups.size()!=0;
    }
    
    public String addGroup(String jsonBody){
    	Gson gson = new Gson();
    	Group group = gson.fromJson(jsonBody, Group.class);
    	ArrayList<Group> groups = new ArrayList<Group>();
    	if(group != null && group.get_groupId() == -1)
    		groups.add(group);
    	else
    		return "insert wrong format";
    	
		GroupDao groupDao =  new GroupDao();
    	return groupDao.transGroup("insert", groups);
    }
    
    public String updateGroup(String jsonBody){
    	Gson gson = new Gson();
    	Group group = gson.fromJson(jsonBody, Group.class);
    	ArrayList<Group> groups = new ArrayList<Group>();
    	if(group != null && group.get_groupId() != -1)
    		groups.add(group);
    	else
    		return "updateUser wrong format";
    	
		GroupDao groupDao =  new GroupDao();
    	return groupDao.transGroup("update", groups);
    }
}
