package object;

public class Action extends BddObject{
	
	String idAction;
	String idJoueur;
	String actions;
	int points;
	String idMatch;

	public void init(){
		super.setTable("Action");
		super.setPrefix("ACN");
		super.setSeqName("getidaction");
		super.setPrimaryKey("idAction");
		super.setPrimaryValue(this.getIdAction());
	}

	public Action(){
		this.init();
	}

	public Action( String idj , String act , int point , String mat  ){
		this.setIdJoueur( idj );
		this.setActions( act );
		this.setPoints( point );
		this.setIdMatch( mat );
		this.init();
	}

	public Action( String idA , String idJ , String act , int point , String idM ){
		this.setIdAction( idA );
		this.setIdJoueur( idJ );
		this.setActions( act );
		this.setPoints( point );
		this.setIdMatch( idM );
		this.init();
	}

	public void setIdAction(String id){
		this.idAction = id;
	}
	public String getIdAction(){
		return this.idAction;
	}

	public void setIdJoueur( String id ){
		this.idJoueur = id;
	}
	public String getIdJoueur(){
		return this.idJoueur;
	}

	public void setActions( String action ){
		this.actions = action;
	}
	public String getActions(){
		return this.actions;
	}

	public void setPoints( int point ){
		this.points = point;
	}
	public int getPoints(){
		return this.points;
	}

	public void setIdMatch(String id){
		this.idMatch = id;
	}
	public String getIdMatch(){
		return this.idMatch;
	}

}