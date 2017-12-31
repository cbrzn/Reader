package cabd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import org.json.JSONArray;
import org.json.JSONObject;

public class Database {
	
	protected ResultSet rs;
	protected ResultSetMetaData rsmd;
	protected JSONArray table;
	protected JSONObject row;
	protected PreparedStatement pstmt;
	protected Connection con;
	
	public Database(String jdbc,String host,String port, String db, String user, String pass){
		try {
			Class.forName("org.postgresql.Driver");
			this.con= DriverManager.getConnection("jdbc:"+jdbc+"://"+host+":"+port+"/"+db,user,pass);
		}
		catch(Exception e){
			e.getStackTrace();
		}
	}
	
	public boolean checkUser(String email, String password) {
		boolean st = false; 
		try {
				this.pstmt = con.prepareStatement("SELECT * FROM users WHERE email=? and password=?");
			    this.pstmt.setString(1, email);
				this.pstmt.setString(2, password);
				this.rs = this.pstmt.executeQuery();
				st = this.rs.next();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return st;
	}
	
	public boolean checkNewUser(String email) {
		boolean st = false; 
		try {
				this.pstmt = con.prepareStatement("SELECT * FROM users WHERE email=?");
			    this.pstmt.setString(1, email);
				this.rs = this.pstmt.executeQuery();
				st = this.rs.next();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return st;
	}
	
	public boolean insertUser(String email, String username, String password, boolean admin) {
		boolean st = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			this.pstmt = con.prepareStatement("INSERT INTO users (email, username, password, creation_time, admin) VALUES (?, ?, ?, ?, ?)");
			this.pstmt.setString(1, email);
			this.pstmt.setString(2, username);
			this.pstmt.setString(3, password);
			this.pstmt.setTimestamp(4, timestamp);
			this.pstmt.setBoolean(5, admin);
			
			this.pstmt.executeUpdate();
					
				}
			 catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}
	
	public boolean insertImg(String name, int user_id, String synopsis, String path) {
		boolean st = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			this.pstmt = con.prepareStatement("INSERT INTO series (name, user_id, creation_time, synopsis, path) VALUES (?, ?, ?, ?, ?)");
			this.pstmt.setString(1, name);
			this.pstmt.setInt(2, user_id);
			this.pstmt.setTimestamp(3, timestamp);
			this.pstmt.setString(4, synopsis);
			this.pstmt.setString(5, path);
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return st;
	}
	
	public boolean checkImg(String name) {
		boolean st = false; 
		try {
			this.pstmt = con.prepareStatement("SELECT * FROM series WHERE name=?");
		    this.pstmt.setString(1, name);
			this.rs = this.pstmt.executeQuery();
			st = this.rs.next();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return st;
	} 
	
	public boolean isAdmin(String email) {
		boolean st = false;
		try {
			this.pstmt = con.prepareStatement("SELECT admin FROM users WHERE email=?");
			this.pstmt.setString(1, email);
			this.rs = this.pstmt.executeQuery();
			rs.next();
			if(this.rs.getString("admin").equals("t")) 
				st = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
		
	}
	
	public int user_id(String email) {
		int id = 0;
		try {
			this.pstmt = this.con.prepareStatement("SELECT id FROM users WHERE email=?");
		    this.pstmt.setString(1, email);
			this.rs = this.pstmt.executeQuery();
				while (this.rs.next()) 		
					id = this.rs.getInt("id");
				return id;

			} catch (Exception e) {
				e.printStackTrace();
			}
		return id;
	}
}
				
