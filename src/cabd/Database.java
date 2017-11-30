package cabd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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

	public JSONArray executeQuery(String query, Object... values) {
		try {
			this.pstmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			for (int i = 0; i < values.length; i++) {
				this.pstmt.setObject(i + 1, values[i]);
			}
			this.rs = this.pstmt.executeQuery();
		} catch (Exception e) {
			e.getStackTrace();
		} 
		return this.getTable(this.rs);
	}
	
	public void execute(String query, Object... values) {
		try {
			this.pstmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			for (int i = 0; i < values.length; i++) {
				this.pstmt.setObject(i + 1, values[i]);
			}

			this.pstmt.execute();
		} catch (Exception e) {
			e.getStackTrace();
		} 
	}
	
	public JSONArray getTable(ResultSet rs) {
		try {
			this.rsmd = rs.getMetaData();
			rs.last();
			table = new JSONArray();
			rs.beforeFirst();
			while (rs.next()) {
				row = new JSONObject();
				for (int i = 1; i <= this.rsmd.getColumnCount(); i++) {
					row.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				table.put(row);
			}
		}
		catch (Exception e) {
			e.getStackTrace();
		}
		return table;
	}
	public void closeCon() {
		try {
			this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public boolean insertUser(String email, String password, boolean admin) {
		boolean st = false;
		try {
			this.pstmt = con.prepareStatement("INSERT INTO users (email, password, admin) VALUES (?, ?, ?)");
			this.pstmt.setString(1, email);
			this.pstmt.setString(2, password);
			this.pstmt.setBoolean(3, admin);
			
			this.pstmt.executeUpdate();
					
				}
			 catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}
	
	public boolean insertImg(String name, String mail, String path) {
		boolean st = false;
		try {
			this.pstmt = con.prepareStatement("INSERT INTO series (name, user_mail, path) VALUES (?, ?, ?)");
			this.pstmt.setString(1, name);
			this.pstmt.setString(2, mail);
			this.pstmt.setString(3, path);
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
	
	public boolean isAdmin(String mail) {
		boolean st = false;
		try {
			this.pstmt = con.prepareStatement("SELECT admin FROM users WHERE email=?");
			this.pstmt.setString(1, mail);
			this.rs = this.pstmt.executeQuery();
			rs.next();
			if(this.rs.getString("admin").equals("t")) 
				st = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
		
	}
	

	
}
