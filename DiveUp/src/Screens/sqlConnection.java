package Screens;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlConnection {
	public static Connection conn;
public Connection Connect(){
    
	try {
		conn = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"\\DB\\DiveUpDB.db");
		return conn;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
    
}

public String runQuery(Connection conn,String query){
	Statement stmt;
	try {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();

		while (rs.next()) {
			
		    for(int i = 1; i < columnsNumber; i++)
		        System.out.print(rsmd.getColumnName(i) +"  "+rs.getString(i) + " ");
		    System.out.println();
		}
	    conn.close();

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        return "";
}
            
}
