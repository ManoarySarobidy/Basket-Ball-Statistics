package servlet;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import object.Match;
public class MatchServlet extends HttpServlet{
	@SuppressWarnings("unchecked")

	public void doGet( HttpServletRequest request , HttpServletResponse response ) throws ServletException , IOException{
		String id = String.valueOf(request.getParameter("idMatch")).trim();
		// azoko ny idMatch de setteko Match vaovao
		Match match = new Match();
		try{

			match = (Match)match.select(id).get(0);
			ServletContext context = request.getServletContext();
			context.setAttribute("match",match);
			RequestDispatcher dispatch = request.getRequestDispatcher("team");
			dispatch.forward(request , response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void doPost( HttpServletRequest request , HttpServletResponse response ) throws ServletException , IOException{
		String id = String.valueOf(request.getParameter("idMatch")).trim();
		// azoko ny idMatch de setteko Match vaovao
		Match match = new Match();
		try{

			match = (Match)match.select(id).get(0);
			ServletContext context = request.getServletContext();
			context.setAttribute("match",match);
			RequestDispatcher dispatch = request.getRequestDispatcher("team");
			dispatch.forward(request , response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}