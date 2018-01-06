package basic;

public class SingleDatabase {

	private String username = "postgres";
	private String password = "cesar5683072";
	private String url = "jdbc:postgresql://localhost:5432/Reader";
	private String driver = "org.postgresql.Driver";
		
	private static SingleDatabase instance = new SingleDatabase();
	
	public static SingleDatabase getInstance(){
	return instance;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public void msg() {
		System.out.println(driver);
	}
}

