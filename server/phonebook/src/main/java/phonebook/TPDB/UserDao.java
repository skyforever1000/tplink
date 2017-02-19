package phonebook.TPDB;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;

public class UserDao {
	protected String queryUserCustomized(String query){
    	TPDBAccess.initConn();
    	UserSimple userSimple = new UserSimple();
    	String rlt = "";
    	ArrayList<User> users;
    	
    	if(TPDBAccess.connect != null && TPDBAccess.statement != null ){
    		try {
    			TPDBAccess.rs = TPDBAccess.statement.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		users = userSimple.ResultSet2Object(TPDBAccess.rs);
    		rlt = userSimple.Obj2Json(users);
    	}
    	if(TPDBAccess.closeConn()){
    		return rlt;
    	}else{
    		return "";
    	}
    }
    
	protected ArrayList<User> queryUserCustomizedObj(String query){
    	TPDBAccess.initConn();
    	UserSimple userSimple = new UserSimple();
    	ArrayList<User> users = new ArrayList<User>();
    	
    	if(TPDBAccess.connect != null && TPDBAccess.statement != null ){
    		try {
    			TPDBAccess.rs = TPDBAccess.statement.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		users = userSimple.ResultSet2Object(TPDBAccess.rs);
    	}
    	if(TPDBAccess.closeConn()){
    		return users;
    	}else{
    		return null;
    	}
    }
    
	protected String queryUser(int groupId, int _status){
    	TPDBAccess.initConn();
    	String query = "select * from tplink.User where _status = " +_status+" and _groupId = " + groupId;
    	UserSimple userSimple = new UserSimple();
    	String rlt = "";
    	ArrayList<User> users;
    	
    	if(TPDBAccess.connect != null && TPDBAccess.statement != null ){
    		try {
    			TPDBAccess.rs = TPDBAccess.statement.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		users = userSimple.ResultSet2Object(TPDBAccess.rs);
    		rlt = userSimple.Obj2Json(users);
    	}
    	if(TPDBAccess.closeConn()){
    		return rlt;
    	}else{
    		return "";
    	}
    }
    
	protected ArrayList<User> queryUserObj(int groupId, int _status){
    	TPDBAccess.initConn();
    	String query = "select * from tplink.User where _status = " +_status+" and _groupId = " + groupId;
    	UserSimple userSimple = new UserSimple();
    	ArrayList<User> users = new ArrayList<User>();
    	
    	if(TPDBAccess.connect != null && TPDBAccess.statement != null ){
    		try {
    			TPDBAccess.rs = TPDBAccess.statement.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		users = userSimple.ResultSet2Object(TPDBAccess.rs);
    	}
    	if(TPDBAccess.closeConn()){
    		return users;
    	}else{
    		return null;
    	}
    }
    
	protected String transUser(String trans, ArrayList<User> users){
    	TPDBAccess.initConn();
    	if(TPDBAccess.connect == null && TPDBAccess.statement == null)
    		return null;
    	
    	ArrayList<Integer> ids = new ArrayList<Integer>();
    	
    	for(User user : users){
    		String query = "";
    		if(trans.equalsIgnoreCase("insert"))
    			query = UserInsertStatement(user);
    		else
    			query = UserUpdateStatement(user);
    		try {
				TPDBAccess.statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
				TPDBAccess.rs = TPDBAccess.statement.getGeneratedKeys();
				if(TPDBAccess.rs.next()){
					int id = TPDBAccess.rs.getInt(1);
					ids.add(id);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
    	}
    	Gson gson = new Gson();
		String json = gson.toJson(ids);
    	
    	if(TPDBAccess.closeConn())
    		return json;
    	else
    		return null;
    }
    
    
    private String UserInsertStatement(User user){
    	String query = "insert into tplink.User values(null, '"+
    				user.get_phoneNo()+"', '"+
    				user.get_firstName()+"', '"+
    				user.get_lastName()+"', default, '"+
    				user.get_address()+"', "+
    				user.get_groupId()+", default)";
    	return query;
    }
    
    private String UserUpdateStatement(User user){
    	String query = "update tplink.User set _phoneNo = '"+user.get_phoneNo()+
    			"', _firstName ='"+user.get_firstName() +
    			"', _lastName ='" + user.get_lastName() +
    			"', _status =" + user.get_status() +
    			", _address = '" + user.get_address() + 
    			"', _groupId = " + user.get_groupId() + 
    			" where _userId =" + user.get_userId();
    	return query;
    }
	
}

