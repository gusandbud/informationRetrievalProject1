package Analysis;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import twitter4j.Status;

public class DBfunctions {
	
	
	public static Connection connect(){
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306","3130073","Retrieval2016");
			System.out.println("Conn to db established");
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.out.println("SQL Exception :" + e.getMessage());
			System.out.println("SQL Exception :" + e.getErrorCode());
		}
		return conn;
		
	}
	
	public static List<Analysis.Status> read(Connection conn, String table) throws SQLException{
		List<Analysis.Status> l = new ArrayList<Analysis.Status>();
		Analysis.Status s;
		String usr, txt, h;
		long id;
		java.sql.Date d;
		ResultSet rs;
		String query = "USE twitter;";
		java.sql.PreparedStatement state = conn.prepareStatement(query);
		state.executeUpdate();
		query = "SELECT * FROM "+ table;
		state = conn.prepareStatement(query);
		rs = state.executeQuery();
		while(rs.next()){
			id = rs.getLong("id");
			usr = rs.getString("usr");
			txt = rs.getString("txt");
			h = rs.getString("hashtag");
			d = rs.getDate("tweetDate");
			s = new Analysis.Status(id, usr, txt, d, h);
			l.add(s);
		}
		return l;
	}
		
	
	
}
