package testpack;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Home")
public class Home extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer uid = (Integer) request.getSession().getAttribute("uid");
		if(uid == null) {
			// not logged in, send to Login with error message
			response.sendRedirect("Login?msg=have to login first...");
		}
		else {
			// show the home page
			DB_Access db = new DB_Access();
			String uname = db.getUserName(uid); request.setAttribute("name", uname);
			ArrayList<Item> allItems = db.getAllUserItems(uid);
			request.setAttribute("allItems", allItems);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/home.jsp");
			rd.forward(request, response);			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
