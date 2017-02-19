package phonebook.TPDB;

import java.util.ArrayList;

import com.google.gson.Gson;

public class UserFunction {
    public Boolean isUserPhoneExist(String phoneNo){
    	UserDao userDao = new UserDao();
		String query = "select * from tplink.User where _phoneNo like '%" +phoneNo+"%'";
		ArrayList<User> users = userDao.queryUserCustomizedObj(query);

		return users!=null&&users.size()!=0;
    }
    
    public String addUser(String jsonBody){
    	Gson gson = new Gson();
    	User user = gson.fromJson(jsonBody, User.class);
    	ArrayList<User> users = new ArrayList<User>();
    	if(user != null && user.get_userId() == -1)
    		users.add(user);
    	else
    		return "insert wrong format";
    	
    	UserDao userDao = new UserDao();
    	return userDao.transUser("insert", users);
    }
    
    public String updateUser(String jsonBody){
    	Gson gson = new Gson();
    	User user = gson.fromJson(jsonBody, User.class);
    	ArrayList<User> users = new ArrayList<User>();
    	if(user != null && user.get_userId() != -1)
    		users.add(user);
    	else
    		return "updateUser wrong format";
    	
    	UserDao userDao = new UserDao();
    	return userDao.transUser("update", users);
    }
    
    public String getUserList(String groupId){
    	UserDao userDao = new UserDao();
    	return userDao.queryUser(Integer.valueOf(groupId), 1);
    }
}
