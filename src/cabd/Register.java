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

import org.json.JSONObject;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		Database db = new Database();
		if(session.isNew()) {
			if(db.checkNewUser(reqBody.getString("email")) == true) {
				json.put("status", "you have an account already, please log in");
				storeValue(reqBody.getString("email"), reqBody.getString("username"), reqBody.getString("pass"), reqBody.getBoolean("admin"), session);
				session.invalidate();
			} else {
				json.put("status", "200");
				storeValue(reqBody.getString("email"), reqBody.getString("username"), reqBody.getString("pass"), reqBody.getBoolean("admin"), session);
				db.insertUser(reqBody.getString("email"), reqBody.getString("username"), reqBody.getString("pass"), reqBody.getBoolean("admin")); 
			}
		} else {
			json.put("status", "you are registered already");
			storeValue(reqBody.getString("email"), reqBody.getString("username"), reqBody.getString("pass"), reqBody.getBoolean("admin"), session);
			session.invalidate(); 
		}
		out.println(json.toString());
	}
	
	private void storeValue(String email, String username, String password, boolean admin, HttpSession session) {
		if(email == null) {
			session.setAttribute("email", "");
			session.setAttribute("username", "");
			session.setAttribute("password", "");
			session.setAttribute("admin", "");
		} else {
			session.setAttribute("email", email);
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			session.setAttribute("admin", admin);
		}
	}
}

