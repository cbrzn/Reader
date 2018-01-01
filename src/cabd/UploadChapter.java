package cabd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class UploadChapter
 */
@MultipartConfig
@WebServlet("/UploadChapter")
public class UploadChapter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadChapter() {
    	
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("hola");

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Part> files = (ArrayList<Part>) request.getParts();
		InputStream filecontent = null;
		HttpSession session = request.getSession();
		OutputStream os = null;
		Database db = new Database("postgresql", "localhost", "5432", "Reader", "postgres", "maricoelquelolea");
		String name = request.getParameter("names");
		String number = request.getParameter("numbers");
		int index = Integer.parseInt(number);
		int ses = (int) request.getSession(false).getAttribute("serie_id");
		try {
			String baseDir = "C:\\Users\\cesar\\Documents\\Proyectos Java\\WEB2\\Reader\\WebContent\\chapters";
			for (int i=0; i < files.size(); i++) {
				db.insertChapter(name+i, ses, index+i, this.getFileName(files.get(i)));
			}
			System.out.println(files);
			for (Part file : files) {
				filecontent = file.getInputStream();
				os = new FileOutputStream(baseDir + "/" + this.getFileName(file));
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = filecontent.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
				if (filecontent != null) {
					filecontent.close();
				}
				if (os != null) {
					os.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}