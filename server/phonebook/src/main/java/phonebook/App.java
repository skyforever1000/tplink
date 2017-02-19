package phonebook;


import phonebook.TPDB.GroupFunction;
import phonebook.TPDB.UserFunction;
import phonebook.TPDB.UserGroupFunction;
import static spark.Spark.*;


public class App {

    public static void main(String[] args) {
    	enableCORS("*", "POST, GET, OPTIONS", "*");
    	
        get("/GetUserList", (req, res) -> {
        	UserGroupFunction userGroupFunction = new UserGroupFunction();
        	res.status(200);
        	res.type("application/json");
        	return userGroupFunction.getAvailableWholeList();
        });
        
        get("/GetGroupList", (req, res) ->{
        	res.status(200);
        	res.type("application/json");
        	GroupFunction groupFunction = new GroupFunction();
        	return groupFunction.getGroupList();
        });
        
        get("/GetUserList/:groupId", (req, res) -> {
        	String groupId = req.params(":groupId");
        	UserFunction userFunction = new UserFunction();
        	
        	if(groupId.isEmpty())
        		return "invalided groupid input";
        	else{
        		return userFunction.getUserList(groupId);
        	}
        });
        
        get("/isUserExist/:phoneNo", (req, res) ->{
        	UserFunction userFunction = new UserFunction();
        	String phoneNo = req.params(":phoneNo");
        	
        	if(phoneNo.isEmpty())
        		return "invalided phoneNo";
        	else{
            	Boolean condition = userFunction.isUserPhoneExist(phoneNo);
            	if(condition == null)
            		return "internal error";
            	else{
            		if(condition)
            			return "yes";
            		else
            			return "no";
            	}
        	}
        });
        
        get("/isUserExist/:groupName", (req, res) ->{
        	GroupFunction groupFunction = new GroupFunction();
        	String groupName = req.params(":groupName");
        	
        	if(groupName.isEmpty())
        		return "invalided groupName";
        	else{
            	Boolean condition = groupFunction.isGroupNameExist(groupName);
            	if(condition == null)
            		return "internal error";
            	else{
            		if(condition)
            			return "yes";
            		else
            			return "no";
            	}
        	}
        });
        
        post("/addUser" , (req, res) ->{
        	return new UserFunction().addUser(req.body());
        });
        
        post("/updateUser" , (req, res) ->{
        	return new UserFunction().updateUser(req.body());
        });
        
        post("/addGroup" , (req, res) ->{
        	return new GroupFunction().addGroup(req.body());
        });
        
        post("/updateGroup" , (req, res) ->{
        	return new GroupFunction().updateGroup(req.body());
        });
    }
	
	private static void enableCORS(final String origin, final String methods, final String headers) {

	    options("/*", (request, response) -> {

	        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
	        if (accessControlRequestHeaders != null) {
	            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
	        }

	        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
	        if (accessControlRequestMethod != null) {
	            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
	        }

	        return "OK";
	    });

	    before((request, response) -> {
	        response.header("Access-Control-Allow-Origin", origin);
	        response.header("Access-Control-Request-Method", methods);
	        response.header("Access-Control-Allow-Headers", headers);
	        // Note: this may or may not be necessary in your particular application
	        response.type("application/json");
	    });
	}
}


