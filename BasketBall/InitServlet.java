package servlet;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jsp.Web;
import object.Match;
import frame.FramesPanel;
public class InitServlet extends HttpServlet{
	public void doPost( HttpServletRequest request , HttpServletResponse response ) throws ServletException,IOException{
		// alaina ny parametres rehetra rehetra
		String idEquipe1 = String.valueOf( request.getParameter("equipe1") ).trim();
		String idEquipe2 = String.valueOf( request.getParameter("equipe2") ).trim();

		if( idEquipe1.equalsIgnoreCase(idEquipe2) ){
			request.setAttribute("message", "Choisisez deux equipe differentes.");
			response.sendRedirect("Error.jsp");
		}
		Web web = null;
		Match match = null;
		try{

			web = new Web( idEquipe1 , idEquipe2 );
			match = web.getMatch();
			ServletContext context = request.getServletContext();
			context.setAttribute("match" , match);
			web.beginMatch();
			context.setAttribute("datamatch",web.getGame().getFrame().getDataMatch());
			RequestDispatcher dispatch = request.getRequestDispatcher("team");
			dispatch.forward(request,response);

		}catch(Exception e){
			PrintWriter out = response.getWriter();
			out.println(match);
			e.printStackTrace(out);

		}
		
		// efa vita insert izy eto de andao atao amoronana JFrame amzay 

	}

}