package testpack;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteItem")
public class DeleteItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		if (sess.getAttribute("uid") == null) {
			response.sendRedirect("Login?msg=must login first");
		} else {
			int iid = -1;
			String itemId = "-1";
			if (request.getParameter("id") != null) {
				itemId = request.getParameter("id");
			}
			try {
				iid = Integer.parseInt(itemId);
			} catch (Exception e) {
				e.printStackTrace();
			}			
				int uid = (Integer) sess.getAttribute("uid");
				DB_Access db = new DB_Access();
				int res = db.deleteItem(iid, uid);
				if(res==1) {
					response.sendRedirect("Home?msg=success delete");
				}else {
					response.sendRedirect("Home?msg=fail delete");
				}			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
