package testpack;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddItem")
public class AddItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Integer uid;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		uid = (Integer) request.getSession().getAttribute("uid");
		if (uid == null) {
			// not logged in, send to Login with error message
			response.sendRedirect("Login?msg=have to login first...");
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/additem.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// insert item
		String itemName = request.getParameter("iname");
		String itemQty = request.getParameter("iqty");
		DB_Access db = new DB_Access();
		int res = db.addItem(itemName, itemQty, uid);
		if (res == 0) {
			// success inserting
			response.sendRedirect("Home?msg=success inserting item");
		} else if (res == 1) {
			// failure inserting
			response.sendRedirect("AddItem?msg=item name was not given");
		} else if (res == 2) {
			response.sendRedirect("AddItem?msg=item qty was either not given or not a valid int");
		}

	}
}
