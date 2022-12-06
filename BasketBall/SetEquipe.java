package servlet;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import object.*;
import java.util.*;
public class SetEquipe extends HttpServlet{
	@SuppressWarnings("unchecked")
	public void doPost( HttpServletRequest request , HttpServletResponse response ) throws ServletException , IOException{
		Match match = (Match)request.getServletContext().getAttribute("match");
		Equipe e1 = new Equipe();
		Equipe e2 = new Equipe();
		
		try{

			e1 = (Equipe)e1.select( match.getIdEquipe1() ).get(0);
			e2 = (Equipe)e1.select( match.getIdEquipe2() ).get(0);
			ServletContext context = request.getServletContext();
			context.setAttribute("equipe1" , e1);
			context.setAttribute("equipe2" , e2);

			response.sendRedirect("Statistique.jsp");
		}catch (Exception e) {
			
		}

	}
	public void doGet( HttpServletRequest request , HttpServletResponse response ) throws ServletException ,IOException{
		Match match = (Match)request.getServletContext().getAttribute("match");
		Equipe e1 = new Equipe();
		Equipe e2 = new Equipe();
		Datas tir = new Datas( match.getIdMatch() , "ACT0003" );
		Datas tir2 = new Datas( match.getIdMatch() , "ACT0002" );

		try{
			Vector<Datas> datas = tir.getDatasPerAction();
			Vector<Datas> datas2 = tir2.getDatasPerAction();
			e1 = (Equipe)e1.select( match.getIdEquipe1() ).get(0);
			e2 = (Equipe)e1.select( match.getIdEquipe2() ).get(0);
			ServletContext context = request.getServletContext();
			context.setAttribute("equipe1" , e1);
			context.setAttribute("equipe2" , e2);
			int totalRebond = getTotalRebond( datas ,datas2);
			context.setAttribute("somme" , totalRebond);
			response.sendRedirect("Statistique.jsp");
		}catch (Exception e) {
			
		}

	}
	public int getTotalRebond( Vector<Datas> rebonds1 , Vector<Datas> rebonds2 ){
		int total = 0;
		for( int i = 0 ; i < rebonds1.size() ; i++ ){
			total += rebonds1.get(i).getAttempt();
		}
		for( int i = 0 ; i < rebonds2.size() ; i++ ){
			total += rebonds2.get(i).getAttempt();
		}
		return total;

	}
}
