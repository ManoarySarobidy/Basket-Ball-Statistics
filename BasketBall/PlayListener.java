package listener;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JButton;
import object.DataMatch;
import object.Joueur;

public class PlayListener implements MouseListener{

	DataMatch currData;

	public PlayListener( DataMatch data ){
		this.setData(data);
	}
	public void setData( DataMatch data ){
		this.currData = data;
	}
	public DataMatch getData(){
		return this.currData;
	}

	public void mouseClicked(MouseEvent event){		

		String id = null; // to get the player ID through the button Click

		if( event.getSource() instanceof JButton ){
			try{
				id = ((JButton)event.getSource()).getName();
				Joueur temp = (Joueur)(new Joueur()).select(id).get(0);
				 
				if( this.getData().getFail() ){ 
					this.getData().setCurrent(temp);
					this.getData().rebonds();
					this.getData().setFail( false );
				}else{
					this.getData().addPasseur();
					this.getData().setCurrent(temp);
				}
				// satria efa noteneniko izy hoe rebonds no action farany de raha miverina mikitika aho de tokony hoe lasa miverina tsy rebonds ny action farany
			}catch(Exception e){
				e.printStackTrace();
			}

		}
	}
	public void mouseEntered(MouseEvent event){

	}
	public void mouseExited(MouseEvent event){

	}
	public void mousePressed(MouseEvent event){

	}
	public void mouseReleased(MouseEvent event){

	}
}