package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener{

	private JButton saveButton;
	private JButton refreshButton;
	
	private ToolbarListener textListener;
	
	public Toolbar(){
		setBorder( BorderFactory.createEtchedBorder() );
		
		saveButton = new JButton();
		refreshButton = new JButton();
		try {
			saveButton.setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/save16x16.gif"))));
			refreshButton.setIcon(new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/refresh16x16.gif"))));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		saveButton.setToolTipText("Mentés");
		refreshButton.setToolTipText("Frissítés");

		saveButton.addActionListener(this);
		refreshButton.addActionListener(this);

		addSeparator();
		add( saveButton );
		addSeparator();
		add( refreshButton );
	}
	
	public void setToolbarListener(ToolbarListener listener){
		this.textListener = listener;
	}

	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		
		if( clicked == saveButton ){
			if( textListener != null ){
				textListener.saveEventOccured();
			}
		}
		else if( clicked == refreshButton ){
			if( textListener != null ){
				textListener.refreshEventOccured();
			}
		}
	}
}
