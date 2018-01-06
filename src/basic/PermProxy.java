package basic;

import org.json.JSONObject;

public class PermProxy implements Permison {

	protected Permison perm = new GivePerm();
	protected Boolean log = false;
	JSONObject test = new JSONObject();
	@Override
	public JSONObject admin(Boolean log) throws Exception {
		if (log.equals(true)) {
			test = perm.admin(log);
		}else if (log == false) {
			test = perm.not_admin(log);
		} 
		return test;
	}
	
	@Override
	public JSONObject not_admin(Boolean test) throws Exception {
		if (log != true) {
			perm.not_admin(log);
		}else {
			throw new Exception("you aint admin");
		}
		return null;
	}
	
	@Override
	public JSONObject logged(Boolean session) throws Exception {
		if (log.equals(true)) {
			test = perm.logged(session);
		} else {
			test = perm.not_logged(session);
		}
		return test;
	}

	@Override
	public JSONObject not_logged(Boolean session) throws Exception {
		if (log.equals(false)) {
			test = perm.logged(session);
		} else {
			test = perm.not_logged(session);
		}
		return test;
	}
}
