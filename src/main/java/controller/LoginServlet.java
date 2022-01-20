package controller;

import java.io.IOException;

import entities.Message;
import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		UserService su = new UserService();
		if(su.validate(user)) {
			response.sendRedirect("index.jsp");
		}
		else {
			Message message = new Message("Compte n'existe pas!", "error", "danger");
			HttpSession s = request.getSession();
			s.setAttribute("msg", message);
			response.sendRedirect("login.jsp");
		}
		//PrintWriter out = response.getWriter();
		//out.println("vous etes: " + username + " " + password);
		//System.out.println(username + "/" + password);
		//doGet(request, response);
		//doGet(request, response);
	}

}
