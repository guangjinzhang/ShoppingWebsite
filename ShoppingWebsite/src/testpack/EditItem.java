package testpack;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditItem")
public class EditItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Integer uid;
	int iid;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		uid = (Integer) request.getSession().getAttribute("uid");
		if (uid == null) {
			// not logged in, send to Login with error message
			response.sendRedirect("Login?msg=have to login first...");
		} else {
			DB_Access db = new DB_Access();
			iid=Integer.parseInt(request.getParameter("id"));
			Item item=db.getItemInfo(iid, uid);
			request.setAttribute("iname", item.getName());
			request.setAttribute("iqty", item.getQty());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/edititem.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String iname=request.getParameter("iname");
		String iqty=request.getParameter("iqty");
		DB_Access db = new DB_Access();
		int res=db.changeItem(iid, uid, iname, iqty);
		if(res==0) {
			response.sendRedirect("Home?msg=success editing item");
		}else if(res==1){
			response.sendRedirect("EditItem?msg=item name was not given&id="+iid);
		}else if(res==2) {
			response.sendRedirect("EditItem?msg=item qty was either not given or not a valid int&id="+iid);
		}		
	}
}
