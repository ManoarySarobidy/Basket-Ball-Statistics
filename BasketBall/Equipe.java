package object;
import java.util.*;

public class Equipe extends BddObject{
	String idEquipe;
	String nomEquipe;

	public Equipe(){
		this.init();
	}
	public Equipe( String id , String nom ){
		this.setIdEquipe(id);
		this.setNomEquipe(nom);
		this.init();
	}

	public void init(){
		super.setTable( "Equipe" );
		super.setPrefix( "EQP" );
		super.setPrimaryKey("idEquipe");
		super.setPrimaryValue(this.getIdEquipe());
	}

	public void setIdEquipe( String idE ){
		this.idEquipe = idE;
	}
	public String getIdEquipe(){
		return this.idEquipe;
	}

	public void setNomEquipe( String nom ){
		this.nomEquipe = nom;
	}
	public String getNomEquipe(){
		return this.nomEquipe;
	}

	public Vector<Equipe> getAllEquipe() throws Exception{
		Vector<Object> lists = this.select();
		if( lists.size() == 0 ){
			throw new Exception("Equipe indisponible");
		}
		Vector<Equipe> equipes = this.convert(lists);
		return equipes;
	}

	public Vector<Equipe> convert(Vector<Object> liste){
		Vector<Equipe> eqp = new Vector<Equipe>();
		for( int i = 0 ; i < liste.size() ; i++ ){
			eqp.add( (Equipe) liste.get(i) );
		}
		return eqp;
	}
}