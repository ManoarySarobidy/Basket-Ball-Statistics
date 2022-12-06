package object;
import java.util.Vector;
import java.sql.Connection;
import sql.Connect;
public class DataMatch{
	
	Match currMatch;
	Vector<Joueur> equipe1;
	Vector<Joueur> equipe2;
	Joueur current;
	int nPasse;
	boolean fail;
	boolean success;
	boolean rebond;
	Vector<Joueur> passeur;

	public DataMatch(Match match) throws Exception{
		this.setMatch(match);
		this.setCurrent(null);
		this.setEquipe1();
		this.setEquipe2();
		this.setRebond(false);
		this.setNombrePasse(0);
		this.setPasseur();
	}

	public boolean lastActionWasRebond(){
		return this.rebond;
	}
	public void setRebond( boolean b ){
		this.rebond = b;
	}

	public void setMatch(Match match) throws Exception{
		if( match == null ){
			throw new Exception("Ce match est null");
		}
		this.currMatch = match;
	}
	public Match getMatch(){
		return this.currMatch;
	}

	public void setNombrePasse( int n ){
		this.nPasse = n;
	}
	public int getNombrePasse(){
		return this.nPasse;
	}

	public void updatePass(){
		this.setNombrePasse( this.getNombrePasse() + 1 );
	}

	public void setCurrent( Joueur joueur ){
		this.current = joueur;
	}
	public Joueur getCurrent() {
		return this.current;
	}

	public void setPasseur(){
		this.passeur = new Vector<Joueur>();
	}
	public Vector<Joueur> getPasseur(){
		return this.passeur;
	}
	
	public void addPasseur(){
		if( this.getCurrent() != null ){
			this.getPasseur().add( this.getCurrent() );
		}
		 
	}

	public void setFail( boolean f){
		this.fail = f;
	}
	public boolean getFail(){
		return this.fail;
	}

	public void setEquipe1() throws Exception{
		
			this.equipe1 = this.getMatch().getEquipe1();
			if( equipe1.size() == 0 ){
				throw new Exception("Exception dans DataMatch : " + this.getMatch().toString() );
			}
		
	}
	public Vector<Joueur> getEquipe1() throws Exception{
		if( this.equipe1 == null ){
			throw new Exception("Les Joueur pour l'equipe " + this.getMatch().getIdEquipe1() + " ne sont pas disponibles :"   );
		}
		return this.equipe1;
	}

	public void setEquipe2() throws Exception{
		this.equipe2 = this.getMatch().getEquipe2();
	}
	public Vector<Joueur> getEquipe2(){
		return this.equipe2;
	}

	public void insert( String id ) throws Exception{
		Joueur joueur = new Joueur(); //pour le joueur ayant tirer en dernier
		Action action = new Action( this.getCurrent().getIdJoueur() , "ACT0002" , 1 , this.getMatch().getIdMatch() ); 
		if(joueur.select(id).size() == 0 ) return;
		joueur = (Joueur)joueur.select(id).get(0);
		if( this.getCurrent().getIdEquipe().trim().equals( joueur.getIdEquipe().trim() ) ){
			action.setActions("ACT0003");
		}else{
			action.setActions("ACT0002");
		}
		this.save( action );
	}

	public void save(Action toSave){
		Connection connection = null;
		try{
			connection = (new Connect()).getPostgres();
			toSave.setIdAction( toSave.createPrimaryKey(connection) );
			toSave.insert(connection);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if( connection != null ){
				try{
					connection.close();
				}catch (Exception e) {
					e.printStackTrace();					
				}
			}
		}
	}

	public String getLastShootFailed( ) throws Exception{
		Shoots shoot = new Shoots(); // instanciation de la classe shoot
		String query = "select * from shoots where points = 0 and idMatch = "+ shoot.surround( this.getMatch().getIdMatch() );
		Vector<Object> object = shoot.selectOperation( query ); // azoko ny shoot tsy maty de alaiko ny farany
		Vector<Shoots> shoots = shoot.convert(object); // azoko ny shoot tsy maty de alaiko ny farany
		Shoots lastFailedShoot = shoots.get(0); // azoko ny pass Farany de alaiko ny idJoueur an'iny de ataoko mamorona
		return lastFailedShoot.getIdJoueur();
	}
	
	public void rebonds( ) throws Exception{ 
		String idJoueur = this.getLastShootFailed();
		this.insert(idJoueur);
		this.setRebond(true);
	}
	
}