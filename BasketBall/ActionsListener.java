package listener;
import java.awt.event.*;
import java.awt.*;
import object.*;
import java.sql.Connection;
import sql.Connect;

public class ActionsListener implements KeyListener{
	
	DataMatch data;

	public void setData( DataMatch datas ){
		this.data = datas;
	}
	public DataMatch getData(){
		return this.data;
	}

	public ActionsListener(){

	}

	public ActionsListener(DataMatch datas){
		this.setData( datas );
	}

	// for the keyboard
	public void keyPressed(KeyEvent event){
		Joueur currentJoueur = this.getData().getCurrent();
		Match currMatch = this.getData().getMatch();
		if( currentJoueur == null ) return;
		Action action = new Action( currentJoueur.getIdJoueur() , "ACT0004" , 0 , currMatch.getIdMatch() );
		int p = 0;
		try{
			if( event.getKeyCode() == KeyEvent.VK_T ) {
				// tokony tsy hoe mi-failed shoot fa fail = true
				this.getData().save(action);
				this.getData().setFail(true);
				this.getData().setRebond(false);
			}
			else if( event.getKeyCode() == KeyEvent.VK_M ) {
				action.setPoints(2);
				this.getData().save(action);
				this.remiseEnJeu();
				this.succesShoot();
				this.getData().setRebond(false);
			}


		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void remiseEnJeu() throws Exception{
		
		// will check if the current player is in team one first 
		if( this.getData().getCurrent().getIdEquipe().equals( this.getData().getMatch().getIdEquipe1() ) ){
			Joueur meneur = this.getData().getEquipe2().get(0);
			this.getData().setCurrent(meneur);
		}
		else if(  this.getData().getCurrent().getIdEquipe().equals( this.getData().getMatch().getIdEquipe2() ) ){
			Joueur meneur = this.getData().getEquipe1().get(0);
			this.getData().setCurrent(meneur);
		}
		
	}

	public void succesShoot(){

		Joueur lastPasseur = this.getData().getPasseur().get( this.getData().getPasseur().size() - 1 );
		Action passeDecisive = new Action( lastPasseur.getIdJoueur() , "ACT0001", 1 ,  this.getData().getMatch().getIdMatch()); //ito ny joueur nanao passe décissive
		if (!this.getData().lastActionWasRebond()) this.getData().save( passeDecisive );
	}

	public void keyTyped(KeyEvent event){

	}

	public void keyReleased(KeyEvent event){
		 
	}


}