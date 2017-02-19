package phonebook.TPDB;

import java.util.ArrayList;
import com.google.gson.Gson;

public class UserGroupFunction {
	private String getWholeList(int status){
		String rlt = "";
		ArrayList<UserGroup> userGroups = new ArrayList<UserGroup>();
		
		ArrayList<Group> groups = new GroupDao().queryGroupObj(status);
		for(Group group : groups){
			UserGroup usergroup = new UserGroup();
			usergroup.set_groupId(group.get_groupId());
			usergroup.set_groupName(group.get_groupName());
			usergroup.set_versionId(group.get_versionId());
			usergroup.setUsers(new UserDao().queryUserObj(group.get_groupId(), status));
			userGroups.add(usergroup);
		}
		
		Gson gson = new Gson();
		rlt = gson.toJson(userGroups);

    	if(TPDBAccess.closeConn()){
    		return rlt;
    	}else{
    		return "";
    	}
	}
	
	public String getAvailableWholeList(){
		return getWholeList(1);
	}
	
	public String getAllDBWholeList(){
		return getWholeList(0);
	}
}
