package informationRetrievalProj1;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.mysql.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import twitter4j.Status;


public class storeToDB {
	java.sql.Connection conn = null;
	ResultSet res;
	String query;
	Statement state;
	
	public storeToDB(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306","3130073","Retrieval2016");
			state = (Statement) conn.createStatement();
			System.out.println("Conn to db established");
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.out.println("SQL Exception :" + e.getMessage());
			System.out.println("SQL Exception :" + e.getErrorCode());
		}
		
	}
	
	public String store (List<Status> l,String hash, String table){
		
		
		try{
			query = "INSERT IGNORE INTO " + table + " (id,usr,txt,tweetDate,hashtag) VALUES (?,?,?,?,?);";
			java.sql.PreparedStatement state = conn.prepareStatement(query);
			String queries = "USE twitter;";
			DateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
			int Update = state.executeUpdate(queries);
			for (Status s : l){
				state.setLong(1, s.getId());
				state.setString(2,s.getUser().getScreenName());
				state.setString(3,s.getText());
				java.util.Date dutil = s.getCreatedAt();
				java.sql.Date dsql = new java.sql.Date(dutil.getTime()); 
				state.setDate(4, dsql);
				state.setString(5, hash);
				Update = state.executeUpdate();
			}
			System.out.println("Hash "+ hash + "stored successfully");
			
		}
		catch(SQLException e){
			System.out.println("SQL Exception :" + e.getMessage());
			System.out.println("SQL Exception :" + e.getErrorCode());
			
		}
		
		
		return "Storage failed";
	}
	
	
}
