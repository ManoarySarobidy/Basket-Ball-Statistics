package object;
import java.util.*;

public class Datas extends BddObject{
	String nomJoueur;
	int marked;
	int attempt;

	public void init(){
		super.setTable("getScoreByAction");
	}
	public Datas(){
		this.init();
	}
	public Datas( String idMatch , String idAction){
		super.setTable("getScoreByAction("+super.surround(idMatch)+","+super.surround(idAction)+")");
	}
	public Vector<Datas> getDatasPerAction() throws Exception{
		Vector<Object> datas = this.select();
		Vector<Datas> converted = this.convert(datas);
		return converted;
	}
	public Vector<Datas> convert(Vector<Object> liste){
		Vector<Datas> datas = new Vector<Datas>();
		for( int i = 0 ; i < liste.size() ; i++ ){
			datas.add( (Datas) liste.get(i) );
		}
		return datas;
	}
	public String getNomJoueur(){
		return this.nomJoueur;
	}
	public int getMarked(){
		return this.marked;
	}
	public int getAttempt(){
		return this.attempt;
	}
	public void setNomJoueur(String nom){
		this.nomJoueur = nom;
	}
	public void setMarked(int v){
		this.marked = v;
	}
	public void setAttempt(int a){
		this.attempt = a;
	}
}