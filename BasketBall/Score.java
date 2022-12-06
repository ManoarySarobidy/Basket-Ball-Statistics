package object;

public class Score extends BddObject{
	String nomEquipe;
	int points;
	String idMatch;

	public void init(){
		super.setTable("default_score");
	}
	public Score(){
		this.init();
	}
	public Score( int points , String idMatch ){
		this.setPoints(points);
		this.setIdMatch(idMatch);
		this.init();
	}
	public void setNomEquipe(String id){
		this.nomEquipe = id;
	}
	public void setPoints(int points){
		this.points = points;
	}
	public void setIdMatch( String id ){
		this.idMatch = id;
	}
	public String getNomEquipe(){
		return this.nomEquipe;
	}
	public String getIdMatch(){
		return this.idMatch;
	}
	public int getPoints(){
		return this.points;
	}

	public Score getPointsPerEquipe(String idE , String idM) throws Exception{
		String query = "Select * from " + this.getTable() + " where idEquipe = " + this.surround(idE) + " and idMatch = " + this.surround(idM);
		Score score = (Score)this.selectOperation( query ).get(0);
		return score;
	}

}