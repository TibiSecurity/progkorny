package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import model.Car;
import model.CarDatabase;
import model.Client;
import model.ClientDatabase;
import model.Database;
import model.Manufacturer;
import model.ManufacturerDatabase;
import model.Model;
import model.ModelDatabase;
import view.FormEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class.
 */
public class Controller {
	Logger logger = LoggerFactory.getLogger(Controller.class);
	/**
	 * {@code Database} object to be able to reach main database related methods.
	 */
	private Database db = new Database();
	/**
	 * {@code ClientDatabase} object to be able to reach client related database
	 * methods.
	 */
	private ClientDatabase clientDb = new ClientDatabase();
	/**
	 * {@code ManufacturerDatabase} object to be able to reach manufacturer
	 * related database methods.
	 */
	private ManufacturerDatabase manDb = new ManufacturerDatabase();
	/**
	 * {@code ModelDatabase} object to be able to reach model related database
	 * methods.
	 */
	private ModelDatabase modelDb = new ModelDatabase();
	/**
	 * {@code CarDatabase} object to be able to reach car related database
	 * methods.
	 */
	private CarDatabase carDb = new CarDatabase();
	/**
	 * {@code List<Manufacturer>} - the list of manufacturers will be pulled
	 * from the database into this list.
	 */
	private List<Manufacturer> manufacturers;
	/**
	 * {@code List<Model>} - the list of models will be pulled from the database
	 * into this list.
	 */
	private List<Model> models;

	/**
	 * Gets the connection through the {@code Database} model's {@code getConnection} method.
	 *
	 * @return the connection itself
	 */
	public Connection getConnection() {
		return db.getConnection();
	}

	/**
	 * Gets the list of clients through the {@code ClientDatabase} model's {@code getClients} method.
	 *
	 * @return the list of clients
	 */
	public List<Client> getClients() {
		return clientDb.getClients();
	}
	
	/**
	 * Gets the list of cars through the {@code CarDatabase} model's {@code getCars} method.
	 *
	 * @return the list of cars
	 */
	public List<Car> getCars() {
		return carDb.getCars();
	}
	
	/**
	 * Removes a client from the client list, then removes the car that belonged
	 * to the client.
	 *
	 * @param index of the client, to be removed
	 */
	public void removeClient(int index) {
		String clientNameRemoveHelper = clientDb.removeClient(index);
		carDb.removeCar(clientNameRemoveHelper);
	}

	/**
	 * Saves clients and cars into database.
	 * 
	 */
	public void save() {
		try {
			clientDb.saveClient(db.getConnection());
		} catch (SQLException e) {
			logger.error("Nem sikerült ügyfeleket menteni az adatbázisba!");
		}
		
		try {
			carDb.saveCar(db.getConnection());
		} catch (SQLException e) {
			logger.error("Nem sikerült autókat menteni az adatbázisba!");
		}
	}

	/**
	 * Loads clients and cars from database.
	 * 
	 */
	public void load() {
		try {
			clientDb.loadClients(db.getConnection());
		} catch (SQLException e) {
			logger.error("Nem sikerült ügyfeleket betölteni az adatbázisból!");
		}
		try {
			carDb.loadCars(db.getConnection());
		} catch (SQLException e) {
			logger.error("Nem sikerült autókat betölteni az adatbázisból!");
		}
	}

	/**
	 * Removes previously marked clients and cars from database.
	 * 
	 */
	public void remove() {
		try {
			carDb.removeMarkedCars(db.getConnection());
		} catch (SQLException e) {
			logger.error("Nem sikerült autókat törölni az adatbázisból!");
		}
		try {
			clientDb.removeMarkedClients(db.getConnection());
		} catch (SQLException e) {
			logger.error("Nem sikerült ügyfeleket törölni az adatbázisból!");
		}
	}

	/**
	 * Gets the list of manufacturers from the database by calling the
	 * {@code ManufacturerDatabase} model's {@code loadManufacturers} method.
	 *
	 * @return the list of manufacturers
	 * 
	 */
	public List<Manufacturer> loadManufacturers() {
		manufacturers = new LinkedList<Manufacturer>();
		try {
			manufacturers = manDb.loadManufacturers(db.getConnection());
		} catch (SQLException e) {
			logger.error("Nem sikerült autógyártókat betölteni az adatbázisból!");
		}

		return manufacturers;
	}

	/**
	 * Gets the list of models by calling the {@code ModelDatabase} model's
	 * {@code loadModels} method.
	 *
	 * @param manufacturerName - every model that belongs to this
	 *            manufacturer will be pulled from database
	 * @return the list of models
	 * 
	 */
	public List<Model> loadModels(String manufacturerName) {
		models = new LinkedList<Model>();
		try {
			models = modelDb.loadModels(db.getConnection(), manufacturerName);
		} catch (SQLException e) {
			logger.error("Nem sikerült autótípusokat betölteni az adatbázisból!");
		}

		return models;
	}

	/**
	 * Connects to the database through the {@code Database} model's
	 * {@code connect} method.
	 *
	 */
	public void connect() {
		try {
			db.connect();
		} catch (Exception e) {
			logger.error("Nem sikerült kapcsolódni az adatbázishoz!");
		}
	}

	/**
	 * Disconnects from the database through the {@code Database} model's
	 * {@code disconnect} method.
	 *
	 */
	public void disconnect() {
		db.disconnect();
	}

	/**
	 * Configures database connection parameters through the {@code Database} model's
	 * {@code configure} method.
	 *
	 * @param username - the username that will be used for the database connection
	 * @param password - the password that will be used for the database connection
	 * 
	 */
	public void configure(String username, String password) {
		try {
			db.configure(username, password);
		} catch (Exception e) {
			logger.error("Nem sikerült kapcsolódni az adatbázishoz a megadott paraméterekkel!");
		}
	}

	/**
	 * Adds a {@code Client} to the client list.
	 *
	 * @param ev - {@code FormEvent} that contains every client related information entered by the user
	 */
	public void addClient(FormEvent ev) {
		String name = ev.getName();
		String phoneNum = ev.getPhoneNum();
		Date todaysDate = ev.getCreationDate();

		Client client = new Client(name, phoneNum, todaysDate);

		clientDb.addClient(client);
	}

	/**
	 * Adds a {@code Car} to the car list.
	 *
	 * @param ev - {@code FormEvent} that contains every car related information entered by the user
	 */
	public void addCar(FormEvent ev) {
		String licensePlateNo = ev.getLicensePlateNo();
		String manName = ev.getCarManufacturer();
		String modelName = ev.getCarModel();
		String clientName = ev.getName();
		String year = ev.getYear();
		String color = ev.getColor();
		Integer odometer = ev.getOdometer();

		Car car = new Car(licensePlateNo, manName, modelName, clientName, year,
				color, odometer);

		carDb.addCar(car);
	}
}
