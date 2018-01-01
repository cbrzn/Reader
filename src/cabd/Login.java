package cabd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Testing
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();	
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();
		if(session.isNew()) {
			json.put("status", "not logged in");
			session.invalidate();
		} else {
			json.put("admin", session.getAttribute("admin"))
				.put("status", "logged in")
				.put("password", session.getAttribute("password"))
				.put("email", session.getAttribute("email"))
				.put("user_id", session.getAttribute("user_id"))
				.put("serie_id", session.getAttribute("serie_id"));
		}
		out.print(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		JSONObject json = new JSONObject();
		Database db = new Database("postgresql", "localhost", "5432", "Reader", "postgres", "maricoelquelolea");
		if(session.isNew()) {
				String email = reqBody.getString("email");
				int user_id = db.user_id(email);
				System.out.println(user_id);
				if(db.checkUser(reqBody.getString("email"), reqBody.getString("pass")) == true) {
					if (db.isAdmin(reqBody.getString("email"))) {
						storeValue(reqBody.getString("email"), reqBody.getString("pass"), true, user_id, session);
						json.put("status", "200");
					} else {
						storeValue(reqBody.getString("email"), reqBody.getString("pass"), false, user_id, session);
						json.put("status", "200");
					}
				} else 
					session.invalidate();
		} else {
			json.put("status", "you're already logged in");
		}	
		out.println(json.toString());	
		}
	
	private void storeValue(String email, String password, boolean admin, int id, HttpSession session) {
		if(email == null) {
			session.setAttribute("email", "");
			session.setAttribute("password", "");
			session.setAttribute("admin", "");
			session.setAttribute("user_id", "");
		} else {
			session.setAttribute("email", email);
			session.setAttribute("password", password);
			session.setAttribute("admin", admin);
			session.setAttribute("user_id", id);
}
	}
}






