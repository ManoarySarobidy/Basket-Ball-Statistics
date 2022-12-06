package frame;

import javax.swing.*;

import java.awt.*;

import listener.*;

import object.Match;

public class FramesPanel extends JFrame implements Runnable{
	
	PanelFrame frame;
    JPanel jPanel4;
    JButton pause;
    Match match;
    ActionsListener shoot;
	public FramesPanel( Match match ) throws Exception{
        this.setTitle("Instant Match");
        this.setMatch(match);
        initComponents();
        this.setShoot();
        this.addKeyListener(this.getShoot());
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void run(){
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setVisible(true);
    }

    public void setMatch( Match match ){
        this.match = match;
    }
    public Match getMatch(){
        return this.match;
    }

    public void setShoot(){
        this.shoot = new ActionsListener(this.getFrame().getDataMatch());
    }
    public ActionsListener getShoot(){
        return this.shoot;
    }

    public void setFrame( ) throws Exception{
        if( this.getMatch() == null ){
            throw new Exception("Desole erreur de match");
        }
    	this.frame = new PanelFrame(this.getMatch());
    }
    public PanelFrame getFrame(){
    	return this.frame;
    }
    public void setJPanel4( ){
    	this.jPanel4 = new JPanel();
    }
    public JPanel getJPanel4(){
    	return this.jPanel4;
    }

	public void setPause(String text){
		this.pause = new JButton(text);
	}
	public JButton getPause(){
		return this.pause;
	}

    public void setLayoutPanel4(){
    	this.getJPanel4().setBackground(Color.white);

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        this.getJPanel4().setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.getPause(), GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.getPause(), GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
        );
    }

    @SuppressWarnings("unchecked")
    void initComponents() throws Exception{
    	this.setFrame();
        this.setJPanel4();
        this.setPause("Pause");
        setBackground(Color.white);
        this.setLayout();
        this.setLayoutPanel4();
    }
    public void setLayout(){
    	GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this.getFrame(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(this.getJPanel4(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 91, Short.MAX_VALUE)
                .addComponent(this.getJPanel4(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(this.getFrame(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
    }

}

