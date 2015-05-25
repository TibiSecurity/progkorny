package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import model.Car;
import model.CarDatabase;
import model.Manufacturer;
import model.Model;
import controller.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainFrame extends JFrame {
	Logger logger = LoggerFactory.getLogger(MainFrame.class);
	private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private Controller controller;
	private TablePanel tablePanel;
	private JTabbedPane tabPane;
	private JPanel clientOuterPane;
	private LoginDialog loginDialog;
	private Preferences loginDetails;
	private List<Manufacturer> manufacturers;
	private List<Model> models;
	private List<Car> cars;

	public MainFrame() {
		super("Ügyfélnyilvántartó");

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			logger.error("Egyéni stílus betöltése sikertelen!");
		}

		setLayout(new BorderLayout());

		toolbar = new Toolbar();
		textPanel = new TextPanel();
		tablePanel = new TablePanel();
		tabPane = new JTabbedPane();
		clientOuterPane = new JPanel();
		loginDialog = new LoginDialog(this);
		loginDetails = Preferences.userRoot().node("login");
		controller = new Controller();
		manufacturers = new LinkedList<Manufacturer>();
		models = new LinkedList<Model>();

		loginDialog.setLoginListener(new LoginListener() {
			public void setLoginDetails(String userName, String password) {
				loginDetails.put("userName", userName);
				loginDetails.put("password", password);
			}
		});
		
		loginDialog.setVisible(true);
		
		String userName = loginDetails.get("userName", "");
		String password = loginDetails.get("password", "");
		
		try {
			controller.configure(userName, password);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(MainFrame.this,
					"Nem sikerült adatbáziskapcsolatot létesíteni!",
					"Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
			System.gc();
			System.exit(0);
		}

		manufacturers = controller.loadManufacturers();

		loginDialog.setLoginListener(new LoginListener() {
			public void setLoginDetails(String userName, String password) {
				loginDetails.put("user", userName);
				loginDetails.put("password", password);
			}
		});

		setJMenuBar(createMenuBar());

		toolbar.setToolbarListener(new ToolbarListener() {
			public void saveEventOccured() {
				connect();

				controller.save();
				controller.remove();
			}

			public void refreshEventOccured() {
				refresh();
			}
		});

		clientOuterPane.setLayout(new BorderLayout());
		formPanel = new FormPanel(manufacturers);

		clientOuterPane.add(formPanel, BorderLayout.WEST);
		clientOuterPane.add(tablePanel, BorderLayout.CENTER);

		tabPane.addTab("Ügyfelek", clientOuterPane);
		tabPane.addTab("Üzenetek", textPanel);

		tablePanel.setData(controller.getClients());
		tablePanel.setCarData(controller.getCars());

		tablePanel.setClientTableListener(new ClientTableListener() {
			public void rowDeleted(int row) {
				controller.removeClient(row);
			}
		});

		formPanel.setFormListener(new FormListener() {
			public void formEventOccured(FormEvent e) {
				controller.addClient(e);
				controller.addCar(e);
				tablePanel.refresh();
				cars = new LinkedList<Car>();
				cars = controller.getCars();
			}
		});

		formPanel.setCarManChooserListener(new CarManChooserListener() {
			public void carManChooserEventOccured(CarManChooserEvent eCM) {
				String carMan = eCM.getManufacturerName();

				models = controller.loadModels(carMan);

				formPanel.setCarModelComboBox(models);
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				controller.disconnect();
				dispose();
				System.gc();
			}
		});

		add(toolbar, BorderLayout.NORTH);
		add(tabPane, BorderLayout.CENTER);

		refresh();

		setMinimumSize(new Dimension(600, 500));
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	public void loadModels(String manufacturerName) throws SQLException {
		controller.loadModels(manufacturerName);
	}

	public void connect() {
		try {
			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this,
					"Nem sikerült adatbáziskapcsolatot létesíteni!",
					"Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
			logger.error("Nem sikerült adatbáziskapcsolatot létesíteni!");
			System.gc();
			System.exit(0);
		}
	}

	private void refresh() {
		connect();

		controller.load();

		tablePanel.refresh();
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("Fájl");

		JMenuItem exitItem = new JMenuItem("Kilépés");

		fileMenu.add(exitItem);

		menuBar.add(fileMenu);

		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_K);

		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,
				ActionEvent.CTRL_MASK));

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int exitConfirm = JOptionPane.showConfirmDialog(MainFrame.this,
						"Biztos kilépsz?", "Kilépés megerősítése",
						JOptionPane.CANCEL_OPTION);
				if (exitConfirm == JOptionPane.OK_OPTION) {
					WindowListener[] wListeners = getWindowListeners();

					for (WindowListener listener : wListeners) {
						listener.windowClosing(new WindowEvent(MainFrame.this,
								0));
					}
				}
			}
		});

		return menuBar;
	}
}
