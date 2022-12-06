package object;
import java.util.*;

public class Joueur extends BddObject{
	
	String idJoueur; // primary key
	String nomJoueur; // nom joueur
	String idEquipe; // foreign key

	public void init(){
		super.setTable("Joueur");
		super.setPrefix("JOU");
		super.setPrimaryKey("idJoueur");
		super.setPrimaryValue(this.getIdEquipe());
	}

	public Joueur(){
		this.init();
	}
	public Joueur( String idJoueur , String nomJoueur , String idEquipe ){
		this.setIdJoueur(idJoueur);
		this.setNomJoueur(nomJoueur);
		this.setIdEquipe(idEquipe);
		this.init();
	}

	public void getAllJoueur(){
		
	}

	public Vector<Joueur> convert(Vector<Object> listes){
		Vector<Joueur> joueurs = new Vector<Joueur>();
		for( int i = 0; i < listes.size() ; i++){
			joueurs.add( (Joueur)listes.get(i) );
		}
		return joueurs;
	}

	public Joueur getMeneur(String idEquipe) throws Exception{
		this.setTable("idEquipe");
		Vector<Object> j = this.select(idEquipe);
		Joueur mene = (Joueur)j.get(0);
		return mene;
	}

	public void setIdJoueur( String id ){
		this.idJoueur = id;
	}
	public String getIdJoueur(){
		return this.idJoueur;
	}

	public void setNomJoueur(String nom){
		this.nomJoueur = nom;
	}
	public String getNomJoueur(){
		return this.nomJoueur;
	}

	public void setIdEquipe( String id ){
		this.idEquipe = id;
	}
	public String getIdEquipe(){
		return this.idEquipe;
	}



}