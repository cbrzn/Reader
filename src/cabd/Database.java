package cabd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import org.json.JSONArray;
import org.json.JSONObject;

import basic.SingleDatabase;

public class Database {
	
	protected ResultSet rs;
	protected ResultSetMetaData rsmd;
	protected JSONArray table;
	protected JSONObject row;
	protected PreparedStatement pstmt;
	protected Connection con;
	
	public Database(){
		try {
			SingleDatabase dbase = SingleDatabase.getInstance();
			Class.forName(dbase.getDriver());
			con = DriverManager.getConnection(dbase.getUrl(), dbase.getUsername(), dbase.getPassword());
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
	
	public boolean checkImg(int name) {
		boolean st = false; 
		try {
			this.pstmt = con.prepareStatement("SELECT * FROM chapters WHERE id=?");
		    this.pstmt.setInt(1, name);
			this.rs = this.pstmt.executeQuery();
			st = this.rs.next();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return st;
	} 
	
	
	
	public boolean insertChapter(String title, int serie_id, int number, String path) {
		boolean st = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			this.pstmt = con.prepareStatement("INSERT INTO chapters (title, serie_id, creation_time, number, path) VALUES (?, ?, ?, ?, ?)");
			this.pstmt.setString(1, title);
			this.pstmt.setInt(2, serie_id);
			this.pstmt.setTimestamp(3, timestamp);
			this.pstmt.setInt(4, number);
			this.pstmt.setString(5, path);
			this.pstmt.executeUpdate();
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
	
	public String chapter_path(int id) {
		String path = "";
		try {
			this.pstmt = this.con.prepareStatement("SELECT path FROM chapters WHERE id=?");
		    this.pstmt.setInt(1, id);
			this.rs = this.pstmt.executeQuery();
				while (this.rs.next()) 		
					path = this.rs.getString("path");
				return path;

			} catch (Exception e) {
				e.printStackTrace();
			}
		return path;
	}
	
	public boolean comment_chapter(String comment, int chapter_id, int user_id) {
		boolean st = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			this.pstmt = con.prepareStatement("INSERT INTO comments_chapter (content, user_id, chapter_id, creation_time) VALUES (?, ?, ?, ?)");
			this.pstmt.setString(1, comment);
			this.pstmt.setInt(3, chapter_id);
			this.pstmt.setInt(2, user_id);
			this.pstmt.setTimestamp(4, timestamp);
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return st;
	}
	
	public boolean comment_serie(String comment, int serie_id, int user_id) {
		boolean st = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			this.pstmt = con.prepareStatement("INSERT INTO comments_serie (content, user_id, serie_id, creation_time) VALUES (?, ?, ?, ?)");
			this.pstmt.setString(1, comment);
			this.pstmt.setInt(3, serie_id);
			this.pstmt.setInt(2, user_id);
			this.pstmt.setTimestamp(4, timestamp);
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return st;
	}
	
	public boolean like_chapter(int chapter_id, int user_id) {
		boolean st = false;
		try {
			this.pstmt = con.prepareStatement("INSERT INTO likes_chapter (user_id, chapter_id) VALUES (?, ?)");
			this.pstmt.setInt(2, chapter_id);
			this.pstmt.setInt(1, user_id);
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return st;
	}
	
	public boolean like_serie(int serie_id, int user_id) {
		boolean st = false;
		try {
			this.pstmt = con.prepareStatement("INSERT INTO likes_serie (user_id, serie_id) VALUES (?, ?)");
			this.pstmt.setInt(2, serie_id);
			this.pstmt.setInt(1, user_id);
			this.pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return st;
	}
}
				
