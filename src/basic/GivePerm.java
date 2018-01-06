package basic;

import org.json.JSONObject;

public class GivePerm implements Permison {

	@Override
	public JSONObject admin(Boolean log) {
		JSONObject json = new JSONObject();
		json.put("admin", "true");
		System.out.println(json);
		return json;
	}

	@Override
	public JSONObject not_admin(Boolean test) throws Exception {
		JSONObject json = new JSONObject();
		json.put("admin", "false");
		System.out.println(json);
		return json;
	}
	
	@Override
	public JSONObject logged(Boolean session) throws Exception {
		JSONObject json = new JSONObject();
		json.put("logged", true);
		return json;
	}

	@Override
	public JSONObject not_logged(Boolean session) {
		JSONObject json = new JSONObject();
		json.put("logged", false);
		return json;
	}
}
