package cabd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Testing
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
		JSONArray json = new JSONArray();
		try {
			Database db = new Database("postgresql", "localhost", "5432", "Reader", "postgres", "cesar5683072");
			db.pstmt = db.con.prepareStatement("SELECT name, path FROM series");
			db.rs = db.pstmt.executeQuery();
			while (db.rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("name", db.rs.getString(1));
				obj.put("path", db.rs.getString(2));
				json.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(json);	
	}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		
	}
}
