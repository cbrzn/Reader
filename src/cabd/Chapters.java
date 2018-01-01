package cabd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Chapters
 */
@WebServlet("/Chapters")
public class Chapters extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Chapters() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				PrintWriter out = response.getWriter();
				JSONArray json = new JSONArray();
				try {
					Database db = new Database("postgresql", "localhost", "5432", "Reader", "postgres", "cesar5683072");
					db.pstmt = db.con.prepareStatement("SELECT * FROM chapters WHERE serie_id=?");
				    db.pstmt.setInt(1, 47);
				    db.rs = db.pstmt.executeQuery();
					while (db.rs.next()) {
						JSONObject obj = new JSONObject();
						obj.put("id", db.rs.getInt(1));
						obj.put("title", db.rs.getString(2));
						obj.put("serie_id", db.rs.getInt(3));
						obj.put("creation_time", db.rs.getTimestamp(4));
						obj.put("number", db.rs.getInt(5));
						obj.put("path", db.rs.getString(6));
						json.put(obj);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				out.println(json);	
				System.out.println(json);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		JSONArray json = new JSONArray();
		int serie_id = reqBody.getInt("serie_id");
		System.out.println(serie_id);
		try {
			Database db = new Database("postgresql", "localhost", "5432", "Reader", "postgres", "maricoelquelolea");
			db.pstmt = db.con.prepareStatement("SELECT * FROM chapters WHERE serie_id=?");
		    db.pstmt.setInt(1, serie_id);
		    db.rs = db.pstmt.executeQuery();
			while (db.rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("id", db.rs.getInt(1));
				obj.put("title", db.rs.getString(2));
				obj.put("serie_id", db.rs.getInt(3));
				obj.put("creation_time", db.rs.getTimestamp(4));
				obj.put("number", db.rs.getInt(5));
				obj.put("path", db.rs.getString(6));
				json.put(obj);
				System.out.println(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(json);	
			}

}
