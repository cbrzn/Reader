package cabd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import basic.PermProxy;
import basic.Permison;

/**
 * Servlet implementation class Session
 */
@WebServlet("/Session")
public class Session extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Session() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();	
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		if (session.isNew()) {
			session.invalidate();
			json.put("status", "not_logged");
		} else {
		boolean test = (boolean) request.getSession(false).getAttribute("admin");
		Permison perm = new PermProxy();
		try {
			JSONObject a = perm.admin(test);
			json = a;
		} catch (Exception e) {
			e.printStackTrace();
			}
		}
		out.println(json.toString());
		System.out.println("json=" +json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
