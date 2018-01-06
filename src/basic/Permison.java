package basic;

import org.json.JSONObject;

public interface Permison {

	public JSONObject admin(Boolean perm) throws Exception;
	
	public JSONObject not_admin(Boolean perm) throws Exception;

	public JSONObject logged(Boolean session) throws Exception;

	public JSONObject not_logged(Boolean session) throws Exception;
}
