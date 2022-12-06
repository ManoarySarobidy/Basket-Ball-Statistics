package frame;
import listener.*;
import javax.swing.*;
import java.awt.*;
import object.*;
import java.util.*;
public class PanelFrame extends JPanel{
	
	// anaty data-match
	DataMatch data;
	Vector<JButton> buttons1; // equipe 1 controller
	Vector<JButton> buttons2; // equipe 2 controller
	JPanel jPanel2;
    JPanel jPanel3;
    JButton pause;

    PlayListener listener;
   
    // mila manana baolina

    public PanelFrame( Match match ) throws Exception{
    	this.setDataMatch( match );
    	this.setListener();
    	this.initComponents();
    }

    public JButton createButton( String text , String name ){
    	JButton button = new JButton(text);
    	button.setName(name);
        button.setFocusable(false);
    	return button;
    }

    public void setDataMatch( Match match) throws Exception {
    	this.data = new DataMatch(match);
    }
    public DataMatch getDataMatch(){
    	return this.data;
    }

    public void setListener(){
    	this.listener = new PlayListener( this.getDataMatch());
    }
    public PlayListener getListener(){
    	return this.listener;
    }

    public void setButtons1(){
    	this.buttons1 = new Vector<JButton>();
    }
    public void setButtons2(){
    	this.buttons2 = new Vector<JButton>();
    }
    public Vector<JButton> getButtons1(){
    	return this.buttons1;
    }
    public Vector<JButton> getButtons2(){
    	return this.buttons2;
    }

    public void initButtons( Vector<Joueur> players , Vector<JButton> buttons ) throws Exception{
            
            if( players.size() == 0 ){
                throw new Exception("Default : " + this.getDataMatch().getMatch().toString());
            }

        	for( int i = 0 ; i < players.size() ; i++ ){
        		JButton button = createButton( players.get(i).getNomJoueur() , players.get(i).getIdJoueur() );
        		buttons.add(button);
        		button.addMouseListener( this.getListener() );
        	}

        
    }

	public void setJPanel2(){
		this.jPanel2 = new JPanel();
	}
	public void setJPanel3(){
		this.jPanel3 = new JPanel();
	}
	
	public JPanel getJPanel2(){
		return this.jPanel2;
	}
	public JPanel getJPanel3(){
		return this.jPanel3;
	}
	

	// layout manager for the basket

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    void initComponents() throws Exception{

        this.setJPanel2();
        this.setJPanel3();

        this.setButtons1();
        this.setButtons2();
        this.setBackground(Color.white);
        this.getDataMatch().getEquipe1();
        try{
            this.initButtons( this.getDataMatch().getEquipe1() , this.getButtons1() );
        }catch( Exception exception ){
        throw new Exception(exception);
    }

        this.initButtons( this.getDataMatch().getEquipe2() , this.getButtons2() );
        this.setLayout();
        this.setLayoutPanel2();
        this.setLayoutPanel3();
    }

    public void setLayout(){
    	GroupLayout jPanel1Layout = new GroupLayout(this);
        this.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this.getJPanel2(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(this.getJPanel3(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(this.getJPanel3(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(this.getJPanel2(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }
    // layout for the player two
    public void setLayoutPanel3(){
    	// for the player manager 2
	    this.getJPanel3().setBackground(new Color(213, 255, 255));
        GroupLayout jPanel3Layout = new GroupLayout(this.getJPanel3());
        this.getJPanel3().setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(this.getButtons2().get(0))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(this.getButtons2().get(1)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(this.getButtons2().get(2))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(this.getButtons2().get(3))))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(this.getButtons2().get(4))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(this.getButtons2().get(0))
                    .addComponent(this.getButtons2().get(1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(this.getButtons2().get(2))
                    .addComponent(this.getButtons2().get(3)))
                .addGap(18, 18, 18)
                .addComponent(this.getButtons2().get(4))
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }
 // layout for the player one
    public void setLayoutPanel2(){
    	this.getJPanel2().setBackground(new Color(181, 255, 255));
        GroupLayout jPanel2Layout = new GroupLayout(this.getJPanel2());
        this.getJPanel2().setLayout(jPanel2Layout);
        System.out.println(this.getButtons1().size());
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(this.getButtons1().get(0))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(this.getButtons1().get(1)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(this.getButtons1().get(2))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(this.getButtons1().get(3))))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(this.getButtons1().get(4))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(this.getButtons1().get(0))
                    .addComponent(this.getButtons1().get(1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(this.getButtons1().get(2))
                    .addComponent(this.getButtons1().get(3)))
                .addGap(18, 18, 18)
                .addComponent(this.getButtons1().get(4))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );   	
    }                        

}