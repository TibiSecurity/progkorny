package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.Border;

import model.Manufacturer;
import model.Model;

public class FormPanel extends JPanel {

	private JLabel nameLabel;
	private JLabel phoneLabel;
	private JLabel creationDateLabel;
	private JLabel carInfoLabel;
	private JTextField nameField;
	private JTextField phoneField;
	private JFormattedTextField creationDateField;

	private static final String CMAN_NOT_SELECTABLE_OPTION = "-Válassz autógyártót-";
	private JComboBox<String> carManComboBox;
	private static final String CMOD_NOT_SELECTABLE_OPTION = "-Válassz autótípust-";
	private JComboBox<String> carModelComboBox;
	private JTextField licensePlateNoField;
	private JTextField yearField;
	private JTextField colorField;
	private JTextField odometerField;
	private JLabel licensePlateNoLabel;
	private JLabel yearLabel;
	private JLabel colorLabel;
	private JLabel odometerLabel;

	private JButton okBtn;
	private FormListener formListener;
	private CarManChooserListener carManChooserListener;

	public FormPanel(List<Manufacturer> manufacturers) {
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);

		nameLabel = new JLabel("*Név: ");
		phoneLabel = new JLabel("Telefonszám: ");
		creationDateLabel = new JLabel("Mai dátum: ");
		carInfoLabel = new JLabel("Gépjármű információk: ");
		nameField = new JTextField(10);
		phoneField = new JTextField(10);

		carManComboBox = new JComboBox<String>();
		carModelComboBox = new JComboBox<String>();
		licensePlateNoField = new JTextField(10);
		yearField = new JTextField(10);
		colorField = new JTextField(10);
		odometerField = new JTextField(10);
		licensePlateNoLabel = new JLabel("Rendszám: ");
		yearLabel = new JLabel("Gyártási év: ");
		colorLabel = new JLabel("Szín: ");
		odometerLabel = new JLabel("Km-óra állás: ");
		
		licensePlateNoField.setText("*Rendszám");
		licensePlateNoField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
		        if(licensePlateNoField.getText().trim().equals(""))
		        	licensePlateNoField.setText("*Rendszám");
		    }
			
			public void focusGained(FocusEvent e) {
		        if(licensePlateNoField.getText().trim().equals("*Rendszám"))
		        	licensePlateNoField.setText("");
		    }
		});
		
		yearField.setText("Gyártási év");
		yearField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
		        if(yearField.getText().trim().equals(""))
		        	yearField.setText("Gyártási év");
		    }
			
			public void focusGained(FocusEvent e) {
		        if(yearField.getText().trim().equals("Gyártási év"))
		        	yearField.setText("");
		    }
		});
		
		colorField.setText("Gépjármű színe");
		colorField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
		        if(colorField.getText().trim().equals(""))
		        	colorField.setText("Gépjármű színe");
		    }
			
			public void focusGained(FocusEvent e) {
		        if(colorField.getText().trim().equals("Gépjármű színe"))
		        	colorField.setText("");
		    }
		});
		
		odometerField.setText("Kilóméteróra állás");
		odometerField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
		        if(odometerField.getText().trim().equals(""))
		        	odometerField.setText("Kilóméteróra állás");
		    }
			
			public void focusGained(FocusEvent e) {
		        if(odometerField.getText().trim().equals("Kilóméteróra állás"))
		        	odometerField.setText("");
		    }
		});
		
		carModelComboBox.setVisible(false);

		carManComboBox.setModel(new DefaultComboBoxModel<String>() {
			boolean selectionAllowed = true;

			public void setSelectedItem(Object anObject) {
				if (!CMAN_NOT_SELECTABLE_OPTION.equals(anObject)) {
					super.setSelectedItem(anObject);
				} else if (selectionAllowed) {
					// Allow this just once
					selectionAllowed = false;
					super.setSelectedItem(anObject);
				}
			}
		});

		carModelComboBox.setModel(new DefaultComboBoxModel<String>() {
			boolean selectionAllowed = true;

			public void setSelectedItem(Object anObject) {
				if (!CMOD_NOT_SELECTABLE_OPTION.equals(anObject)) {
					super.setSelectedItem(anObject);
				} else if (selectionAllowed) {
					// Allow this just once
					selectionAllowed = false;
					super.setSelectedItem(anObject);
				}
			}
		});

		carManComboBox.addItem(CMAN_NOT_SELECTABLE_OPTION);
		for (Manufacturer man : manufacturers) {
			carManComboBox.addItem(man.getManufacturerName());
		}

		Date todaysDate = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		creationDateField = new JFormattedTextField(
				dateformat.format(todaysDate));
		creationDateField.setEditable(false);

		okBtn = new JButton("OK");

		okBtn.setMnemonic(KeyEvent.VK_O);
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);
		phoneLabel.setDisplayedMnemonic(KeyEvent.VK_T);
		phoneLabel.setLabelFor(phoneField);

		carManComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String carMan = (String) carManComboBox.getSelectedItem();

				CarManChooserEvent eCM = new CarManChooserEvent(this, carMan);

				if (carManChooserListener != null) {
					carManChooserListener.carManChooserEventOccured(eCM);
				}
			}
		});

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String phoneNum = phoneField.getText();
				Date creationDate = new Date();

				String carMan = (String) carManComboBox.getSelectedItem();
				String carModel = (String) carModelComboBox.getSelectedItem();
				String licensePlateNo = licensePlateNoField.getText();
				String year = yearField.getText();
				String color = colorField.getText();
				String odometer = odometerField.getText();
				
				if( year.equals("Gyártási év") ){
					year = "";
				}
				
				if( color.equals("Gépjármű színe") ){
					color = "";
				}
				
				if (name.equals("")) {
					JOptionPane.showMessageDialog(FormPanel.this,
							"A 'Név' mező nem maradhat üresen!",
							"Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
				} else if(carMan.equals(CMAN_NOT_SELECTABLE_OPTION)){
					JOptionPane.showMessageDialog(FormPanel.this,
							"Autógyártó választása kötelező!",
							"Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
				} else if(carModel.equals(CMOD_NOT_SELECTABLE_OPTION)){
					JOptionPane.showMessageDialog(FormPanel.this,
							"Autótípus választása kötelező!",
							"Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
				} else if(licensePlateNo.equals("*Rendszám")){
					JOptionPane.showMessageDialog(FormPanel.this,
							"Rendszám megadása kötelező!",
							"Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
				} else if(!odometer.equals("Kilóméteróra állás") && !isInteger(odometer) ){
					JOptionPane.showMessageDialog(FormPanel.this,
							"A kilóméteróra állás szám kell hogy legyen!",
							"Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
				} else {
					Integer odoMeter;
					if( odometer.equals("Kilóméteróra állás") ){
						odoMeter = 0;
					} else{
						odoMeter = Integer.parseInt(odometer);
					}
					
					nameField.setText("");
					phoneField.setText("");
					licensePlateNoField.setText("*Rendszám");
					yearField.setText("Gyártási év");
					colorField.setText("Gépjármű színe");
					odometerField.setText("Kilóméteróra állás");
					
					FormEvent ev = new FormEvent(this, name, phoneNum, creationDate,
												 carMan, carModel,
												 licensePlateNo, year, color, odoMeter);

					if (formListener != null) {
						formListener.formEventOccured(ev);
					}
				}
			}
		});

		Border innerBorder = BorderFactory.createTitledBorder("Új ügyfél");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		layoutComponents();
	}

	public void layoutComponents() {
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		// Name label and name field
		gc.gridy = 0;
		gc.gridx = 0;

		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(nameLabel, gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(nameField, gc);

		// Phone label and name field
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(phoneLabel, gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(phoneField, gc);

		// Date
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(creationDateLabel, gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(creationDateField, gc);

		////////////// Separator //////////////
		
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 1.0;
		gc.weighty = 0.3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		add(new JSeparator(JSeparator.HORIZONTAL), gc);

		// Car info label
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 0, 0, 0);
		add(carInfoLabel, gc);

		// Car manufacturer chooser
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(5, 0, 0, 0);
		add(carManComboBox, gc);

		// Car model chooser
		gc.gridy++;
		gc.gridx = 0;

		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(5, 0, 0, 0);
		add(carModelComboBox, gc);

		// Car license plate no.
		gc.gridy++;
		gc.gridx = 1;

		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(licensePlateNoField, gc);

		// Car year
		gc.gridy++;
		gc.gridx = 1;
		
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(yearField, gc);

		// Car color
		gc.gridy++;
		gc.gridx = 1;

		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(colorField, gc);

		// Car odometer
		gc.gridy++;
		gc.gridx = 1;

		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(odometerField, gc);

		// OK Button
		gc.gridy++;
		gc.gridx = 1;

		gc.weightx = 1;
		gc.weighty = 1.5;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 0, 0, 5);
		add(okBtn, gc);
	}

	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}

	public void setCarManChooserListener(CarManChooserListener listener) {
		this.carManChooserListener = listener;
	}

	public void setCarModelComboBox(List<Model> models) {
		this.carModelComboBox.removeAllItems();

		carModelComboBox.addItem(CMOD_NOT_SELECTABLE_OPTION);
		for (Model model : models) {
			this.carModelComboBox.addItem(model.getModelName());
		}

		carModelComboBox.setVisible(true);
	}
	
	public static boolean isInteger(String str) {
		int length = str.length();
		
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}
}
