package cabd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
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
				.put("email", session.getAttribute("email"));
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
		Database db = new Database("postgresql", "localhost", "5432", "Reader", "postgres", "cesar5683072");
		if(session.isNew()) {
			if(db.checkUser(reqBody.getString("email"), reqBody.getString("pass")) == true) {
					if (db.isAdmin(reqBody.getString("email"))) {
						storeValue(reqBody.getString("email"), reqBody.getString("pass"), true, session);
						json.put("status", "200");
					} else {
						storeValue(reqBody.getString("email"), reqBody.getString("pass"), false, session);
						json.put("status", "200");
					}
			} else {
					session.invalidate();
				}			
		} else {
			json.put("status", "you're already logged in");
		}	
		out.println(json.toString());		
		}
	
	private void storeValue(String email, String password, boolean admin, HttpSession session) {
		if(email == null) {
			session.setAttribute("email", "");
			session.setAttribute("password", "");
			session.setAttribute("admin", "");
		} else {
			session.setAttribute("email", email);
			session.setAttribute("password", password);
			session.setAttribute("admin", admin);
		}
	}
}
