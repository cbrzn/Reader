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

/**
 * Servlet implementation class DownloadChap
 */
@WebServlet("/DownloadChap")
public class DownloadChap extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadChap() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = new Database();
		String chapter = request.getParameter("chapter_id");
		int chapter_id = Integer.parseInt(chapter);
        response.setHeader("Content-disposition","attachment; filename="+db.chapter_path(chapter_id));
        // You must tell the browser the file type you are going to send
        // for example application/pdf, text/plain, text/html, image/jpg
        response.setContentType("file");

        // Make sure to show the download dialog

        // Assume file name is retrieved from database
        // For example D:\\file\\test.pdf		
        String path = db.chapter_path(chapter_id);
        System.out.println(db.checkImg(chapter_id));
        System.out.println(chapter_id);
        if (db.checkImg(chapter_id) == true) {
        	File my_file = new File("C:\\Users\\cesar\\Documents\\Proyectos Java\\WEB2\\Reader\\WebContent\\chapters\\"+path);
        	System.out.println(my_file);
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
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
