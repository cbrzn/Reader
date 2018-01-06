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

import basic.PermProxy;
import basic.Permison;

/**
 * Servlet implementation class Testing
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JSONObject json_id;
      
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
		HttpSession session = request.getSession();
		Permison perm = new PermProxy();
		try {
			Database db = new Database();
			db.pstmt = db.con.prepareStatement("SELECT name, path, id FROM series");
			db.rs = db.pstmt.executeQuery();
			while (db.rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("name", db.rs.getString(1));
				obj.put("path", db.rs.getString(2));
				obj.put("id", db.rs.getInt(3));
				json.put(obj);
			}
			
			if (session.isNew()) {
				JSONObject a = perm.logged(session.isNew());
				session.invalidate();
				json.put(a);
			} else {
				boolean test = (boolean) request.getSession(false).getAttribute("admin");
				JSONObject a = perm.admin(test);
				json.put(a);
				System.out.println(json);
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
        HttpSession session = request.getSession();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		JSONArray json = new JSONArray();
		int serie_id = reqBody.getInt("serie_id");
		try {
			Database db = new Database();
			Permison perm = new PermProxy();
			db.pstmt = db.con.prepareStatement("SELECT * FROM series WHERE id=?");
		    db.pstmt.setInt(1, serie_id);
		    db.rs = db.pstmt.executeQuery();
			while (db.rs.next()) {
				JSONObject b = new JSONObject();
				b.put("id", db.rs.getInt(1))
			 	 .put("user_id", db.rs.getInt(2))
			 	 .put("name", db.rs.getString(3))
				 .put("creation_time", db.rs.getTimestamp(4))
				 .put("synopsis", db.rs.getString(5))
				 .put("path", db.rs.getString(6));
				 json.put(b);
			}
			
			if (session.isNew()) {
				JSONObject a = perm.logged(session.isNew());
				session.invalidate();
				json.put(a);
			} else {
				boolean test = (boolean) request.getSession(false).getAttribute("admin");
				int id = (int) request.getSession(false).getAttribute("user_id");
				JSONObject a = perm.admin(test);
				JSONObject c = new JSONObject();
				JSONObject b = perm.not_logged(session.isNew());
				c.put("current_user", id);
				json.put(b)
					.put(c)
					.put(a);
				System.out.println(json);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(json);
		out.println(json);	
	}
		
	}

