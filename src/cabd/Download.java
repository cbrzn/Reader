package cabd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class GetFile
 */
@WebServlet("/Download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Download() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Find this file id in database to get file name, and file type

        response.setContentType("file");
		String path = request.getParameter("path");
		String serie = request.getParameter("serie_id");

        // Make sure to show the download dialog
        response.setHeader("Content-disposition","attachment; filename="+path);
		Database db = new Database();

        // Assume file name is retrieved from database
        // For example D:\\file\\test.pdf
		int serie_id = Integer.parseInt(serie);
        if (db.checkImg(serie_id) == true) {
        	File my_file = new File("C:\\Users\\cesar\\Documents\\Proyectos Java\\WEB2\\Reader\\WebContent\\Series"+path);
	        // This should send the file to browser
	        OutputStream out = response.getOutputStream();
	        FileInputStream in = new FileInputStream(my_file);
	        byte[] buffer = new byte[4096];
	        int length;
	        while ((length = in.read(buffer)) > 0){
	           out.write(buffer, 0, length);
	        }
	        in.close();
	        out.flush();
        }
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}