package cabd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
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
			db.pstmt = db.con.prepareStatement("SELECT name, path, id FROM series");
			db.rs = db.pstmt.executeQuery();
			while (db.rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("name", db.rs.getString(1));
				obj.put("path", db.rs.getString(2));
				obj.put("id", db.rs.getInt(3));
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
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		JSONObject json = new JSONObject();
		int serie_id = reqBody.getInt("serie_id");
		System.out.println(serie_id);
		try {
			Database db = new Database("postgresql", "localhost", "5432", "Reader", "postgres", "cesar5683072");
			db.pstmt = db.con.prepareStatement("SELECT * FROM series WHERE id=?");
		    db.pstmt.setInt(1, serie_id);
		    db.rs = db.pstmt.executeQuery();
			while (db.rs.next()) {
				json.put("id", db.rs.getInt(1))
					.put("user_id", db.rs.getInt(2))
					.put("name", db.rs.getString(3))
					.put("creation_time", db.rs.getTimestamp(4))
					.put("synopsis", db.rs.getString(5))
					.put("path", db.rs.getString(6));
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(json);	
	}
		
	}

