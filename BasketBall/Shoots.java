package object;
import java.util.Vector;
public class Shoots extends BddObject {
	String idJoueur;
	String idEquipe;
	int points;
	String idMatch;

	public void init(){
		super.setTable("Shoots");
		super.setPrimaryKey("points");
	} 
	public Shoots(){
		this.init();
	}

	public Shoots( String idJ , String idE , int point , String idM ){
		this.setIdJoueur(idJ);
		this.setIdEquipe(idE);
		this.setPoints(point);
		this.setIdMatch(idM);
		this.init();
	}

	public void setIdJoueur( String id ){
		this.idJoueur = id;
	}
	public void setIdEquipe( String id ){
		this.idEquipe = id;
	}
	public void setPoints( int p ){
		this.points = p;
	}
	public void setIdMatch( String id ){
		this.idMatch = id;
	}

	public String getIdJoueur(){
		return this.idJoueur;
	}
	public String getIdEquipe(){
		return this.idEquipe;
	}
	public int getPoints(){
		return this.points;
	}
	public String getIdMatch(){
		return this.idMatch;
	}

	public Vector<Shoots> getShoots() throws Exception{
		Vector<Object> objects = this.select();
		return this.convert(objects);
	}

	public Vector<Shoots> convert( Vector<Object> list ){
		Vector<Shoots> shoots = new Vector<Shoots>();
		for( int i = 0; i < list.size(); i++ ){
			shoots.add( (Shoots) list.get(i) );
		}
		return shoots;
	}


}