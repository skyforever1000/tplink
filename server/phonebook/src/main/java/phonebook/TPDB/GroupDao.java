package phonebook.TPDB;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.google.gson.Gson;

public class GroupDao {
    protected String transGroup(String trans, ArrayList<Group> groups){
    	TPDBAccess.initConn();
    	ArrayList<Integer> ids = new ArrayList<Integer>();
    	if(TPDBAccess.connect == null && TPDBAccess.statement == null)
    		return null;
    	
    	for(Group group : groups){
    		String query = "";
    		if(trans.equalsIgnoreCase("insert"))
    			query = GroupInsertStatement(group);
    		else
    			query = GroupUpdateStatement(group);
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
    
    protected String queryGroup(int _status){
    	TPDBAccess.initConn();
    	String query = "select * from tplink.Group where _status = " + _status;
    	GroupSimple groupSimple = new GroupSimple();
    	String rlt = "";
    	ArrayList<Group> groups;
    	
    	if(TPDBAccess.connect != null && TPDBAccess.statement != null ){
    		try {
    			TPDBAccess.rs = TPDBAccess.statement.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		groups = groupSimple.ResultSet2Object(TPDBAccess.rs);
    		rlt = groupSimple.Obj2Json(groups);
    	}
    	if(TPDBAccess.closeConn()){
    		return rlt;
    	}else{
    		return "";
    	}
    }
    
    protected ArrayList<Group> queryGroupObj(int _status){
    	TPDBAccess.initConn();
    	String query = "select * from tplink.Group where _status = " + _status;
    	GroupSimple groupSimple = new GroupSimple();
    	ArrayList<Group> groups = new ArrayList<Group>();
    	
    	if(TPDBAccess.connect != null && TPDBAccess.statement != null ){
    		try {
    			TPDBAccess.rs = TPDBAccess.statement.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		groups = groupSimple.ResultSet2Object(TPDBAccess.rs);
    	}
    	if(TPDBAccess.closeConn()){
    		return groups;
    	}else{
    		return null;
    	}
    }
    
    protected String queryGroupCustomized(String query){
    	TPDBAccess.initConn();
    	GroupSimple groupSimple = new GroupSimple();
    	String rlt = "";
    	ArrayList<Group> groups;
    	
    	if(TPDBAccess.connect != null && TPDBAccess.statement != null ){
    		try {
    			TPDBAccess.rs = TPDBAccess.statement.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		groups = groupSimple.ResultSet2Object(TPDBAccess.rs);
    		rlt = groupSimple.Obj2Json(groups);
    	}
    	if(TPDBAccess.closeConn()){
    		return rlt;
    	}else{
    		return "";
    	}
    }
    
    protected ArrayList<Group> queryGroupCustomizedObj(String query){
    	TPDBAccess.initConn();
    	GroupSimple groupSimple = new GroupSimple();
    	ArrayList<Group> groups = new ArrayList<Group>();
    	
    	if(TPDBAccess.connect != null && TPDBAccess.statement != null ){
    		try {
    			TPDBAccess.rs = TPDBAccess.statement.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		groups = groupSimple.ResultSet2Object(TPDBAccess.rs);
    	}
    	if(TPDBAccess.closeConn()){
    		return groups;
    	}else{
    		return null;
    	}
    }
    
    
    private String GroupInsertStatement(Group group){
    	String query = "insert into tplink.Group values(null, '"
    					+ group.get_groupName() +"', default, default)";
    	return query;
    }
    
    private String GroupUpdateStatement(Group group){
    	String query = "update tplink.Group set _groupName = '"+ group.get_groupName() +"', _status = "+group.get_status()
    					+" where _groupId = " + group.get_groupId();
    	return query;
    }
}

