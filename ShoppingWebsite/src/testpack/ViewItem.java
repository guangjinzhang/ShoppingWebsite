package testpack;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewItem")
public class ViewItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer uid = (Integer) request.getSession().getAttribute("uid");
		if (uid == null) {
			// not logged in, send to Login with error message
			response.sendRedirect("Login?msg=have to login first...");
		} else {
			DB_Access db = new DB_Access();
			int iid=Integer.parseInt(request.getParameter("id"));
			Item item=db.getItemInfo(iid, uid);
			request.setAttribute("iid", iid);
			request.setAttribute("iname", item.getName());
			request.setAttribute("iqty", item.getQty());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/viewitem.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
