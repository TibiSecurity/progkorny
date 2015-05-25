package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class LoginDialog extends JDialog {
	
	private JButton okButton;
	private JButton cancelButton;
	private JTextField userField;
	private JPasswordField passField;
	private LoginListener loginListener;
	
	public LoginDialog(JFrame parent){
		super(parent, "Kapcsolódás az adatbázishoz", true);
		 
		okButton = new JButton("OK");
		cancelButton = new JButton("Mégse");
		
		userField = new JTextField(10);
		passField = new JPasswordField(10);
		
		passField.setEchoChar('*');
		
		layoutLoginFields();
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = userField.getText();
				char[] password = passField.getPassword();

				if( loginListener != null ){
					loginListener.setLoginDetails(userName, new String(password));
				}
				
				setVisible(false);
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				System.gc();
				System.exit(0);
			}
		});
		
		setSize(450, 230);
		setLocationRelativeTo(parent);
	}
	
	private void layoutLoginFields(){
		
		JPanel controlsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		int space = 5;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Kapcsolódási paraméterek");

		controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
		
		controlsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		Insets rightPadding = new Insets( 0, 0, 0, 20 );
		Insets noPadding = new Insets( 0, 0, 0, 0 );
		
		// user and pass fields
		
		gc.gridy = 0;
		gc.gridx = 0;
		
		gc.weighty = 1;
		gc.weightx = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Felhasználónév: "), gc);
		
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(userField, gc);

		gc.gridy++;
		gc.gridx = 0;
		
		gc.weighty = 1;
		gc.weightx = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Jelszó: "), gc);
		
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(passField, gc);
		
		// buttons
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		gc.gridy++;
		gc.gridx = 0;
		buttonsPanel.add(okButton);

		gc.gridx++;
		buttonsPanel.add(cancelButton);
		
		Dimension buttonSize = cancelButton.getPreferredSize();
		okButton.setPreferredSize(buttonSize);
		
		setLayout(new BorderLayout());
		add(controlsPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}

	public void setLoginListener(LoginListener loginListener) {
		this.loginListener = loginListener;	
	}
}
