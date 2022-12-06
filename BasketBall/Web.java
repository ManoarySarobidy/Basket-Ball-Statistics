package jsp;
import object.*;
import frame.*;
import java.sql.*;
import sql.Connect;
public class Web{
	FramesPanel frames;
	Match match;
	// Mila alaina daholo aloha ny equipe rehetra
	public Web( String idE1 , String idE2 ) throws Exception{
		this.setMatch(idE1,idE2);
	}
	public void setMatch(String one,String two) throws Exception{ //mi instancier match vaovao alefa any anaty
		Connection connection = null;
		try{
			this.match = new Match(one,two);
			connection = new Connect().getPostgres();
			this.getMatch().setIdMatch( this.getMatch().createPrimaryKey(connection) );
			this.getMatch().insert(connection);
			connection.close();
		}catch(Exception e){
			throw new Exception("Desole ce match sera null et " + e.getMessage());
		}
	}
	// ndao ito avadika formulaire generaliser de 
	public Match getMatch(){
		return this.match;
	}
	public void beginMatch() throws Exception{
		this.frames = new FramesPanel( this.getMatch() );
		Thread thread = new Thread(this.frames);
		thread.run();
	}
	public FramesPanel getGame(){
		return this.frames;
	}
}