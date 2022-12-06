package object;
import java.util.Vector;
public class Match extends BddObject{

	String idMatch;
	String idEquipe1;
	String idEquipe2;

	public void init(){
		super.setTable("Match");
		super.setPrefix("MTH");
		super.setPrimaryKey("idMatch");
		super.setSeqName("getIdMatch");
		super.setPrimaryValue(this.getIdMatch());
	}

	public Match(){
		this.init();
	}

	public Match(String idM , String idE1 , String idE2){
		this.setIdMatch(idM);
		this.setIdEquipe1(idE1);
		this.setIdEquipe2(idE2);
		this.init();
	}

	public Match( String idE1 , String idE2 ){
		this.setIdEquipe1(idE1);
		this.setIdEquipe2(idE2);
		this.init();
	}

	public void setIdMatch( String id ){
		this.idMatch = id;
	}
	public String getIdMatch(){
		return this.idMatch;
	}

	public Vector<Joueur> getEquipe1() throws Exception{
		Joueur temp = new Joueur();
		temp.setPrimaryKey("idEquipe");
		try{
			Vector<Object> joueurs = temp.select(this.getIdEquipe1());
			Vector<Joueur> players = temp.convert( joueurs );
		return players;

		}catch (Exception e) {
			throw new Exception(e);
		}
		
	}
	public Vector<Joueur> getEquipe2() throws Exception{
		Joueur temp = new Joueur();
		temp.setPrimaryKey("idEquipe");
		try{
			Vector<Object> joueurs = temp.select(this.getIdEquipe2());
			Vector<Joueur> players = temp.convert( joueurs );
			return players;
		}catch(Exception e){
			throw new Exception("Impossible de recuperer les joueurs de l'equipe 2" );
		}
	}

	public void printEquipe( Vector<Joueur> players ){
		for( int i = 0 ; i < players.size() ; i++ ){
			System.out.println(players.get(i).toString());
		}
	}

	public void setIdEquipe1( String id ){
		this.idEquipe1 = id;
	}
	public String getIdEquipe1(){
		return this.idEquipe1;
	}

	public void setIdEquipe2( String id ){
		this.idEquipe2 = id;
	}
	public String getIdEquipe2(){
		return this.idEquipe2;
	}

}