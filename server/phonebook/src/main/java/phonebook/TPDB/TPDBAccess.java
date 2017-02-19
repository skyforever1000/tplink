package phonebook.TPDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TPDBAccess {
    static Connection connect = null;
    static Statement statement = null;
    static PreparedStatement preparedStatement = null;
    static ResultSet rs = null;
    
    private static String DB_HOST = "localhost";
    private static String DB_PORT ="3306";
    private static String DB_NAME = "tplink";
    private static String DB_USER = "tplink";
    private static String DB_PWD = "123456";
    
    public TPDBAccess(){
    	
    }
    
    public TPDBAccess(String host, String port, String name, String user, String pwd){
    	DB_HOST = host;
    	DB_PORT = port;
    	DB_NAME = name;
    	DB_USER = user;
    	DB_PWD = pwd;
    }
    
    protected static void initConn(){
		try {
			Class.forName("com.mysql.jdbc.Driver");			
			connect = DriverManager.getConnection("jdbc:mysql://" + DB_HOST +":" +DB_PORT+"/" + DB_NAME +"?useSSL=false&"
                    			+ "user="+DB_USER+"&password="+ DB_PWD);
			statement = connect.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    protected static boolean closeConn(){
    	try {
			if(rs != null)
				rs.close();
			if(statement != null)
				statement.close();
			if(connect != null)
				connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    	rs = null;
    	statement = null;
    	connect = null;
    	
    	return true;
    }
}

