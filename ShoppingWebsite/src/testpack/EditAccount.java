package testpack;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditAccount")
public class EditAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Integer uid;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		uid = (Integer) request.getSession().getAttribute("uid");
		if(uid == null) {
			// not logged in, send to Login with error message
			response.sendRedirect("Login?msg=have to login first...");
		}
		else {
			DB_Access db = new DB_Access();
			String uname = db.getUserName(uid);
			request.setAttribute("name", uname);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/editaccount.jsp");
			rd.forward(request, response);			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String uname = request.getParameter("uname");
		String lpass = request.getParameter("lpass");
		String cpass = request.getParameter("cpass");
		DB_Access db = new DB_Access();
		User u=new User();
		u.setName(uname);
		u.setLoginPass1(lpass);
		u.setLoginPass2(cpass);
		u.setUid(uid);
		int res = db.updateAccount(u);
		if(res==0) {
			response.sendRedirect("Home?msg=success editing account");
		}else if(res==1){
			response.sendRedirect("EditAccount?msg=too long");
		}else if(res==2) {
			response.sendRedirect("EditAccount?msg=fail to update");
		}else if(res==3) {
			response.sendRedirect("EditAccount?msg=empty value");
		}else if(res==4) {
			response.sendRedirect("EditAccount?msg=password does not match");
		}
	}

}
