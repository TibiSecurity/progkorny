package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for database and car related tasks.
 */
public class CarDatabase {
	/**
	 * Logger object for the CarDatabase class.
	 */
	Logger logger = LoggerFactory.getLogger(CarDatabase.class);
	/**
	 * List of cars that are in the database.
	 */
	private List<Car> cars;

	/**
	 * Constructs a {@code CarDatabase} object.
	 */
	public CarDatabase() {
		cars = new LinkedList<Car>();
	}

	/**
	 * Saves cars to the database.
	 * 
	 * @param con that will be used for the queries
	 * @throws SQLException
	 *             in case saving queries fail
	 */
	public void saveCar(Connection con) throws SQLException {
		String checkSQL = "select count(*) as count from h_b2wkl0.car where license_plate_no = ?";
		PreparedStatement checkStatement = con.prepareStatement(checkSQL);

		for (Car car : cars) {
			String insertSQL = "insert into h_b2wkl0.car "
					+ "(license_plate_no, manufacturer_id, model_id, client_id, year, color, odometer) "
					+ "values (?, ?, ?, ?, TO_DATE( '" + car.getYear()
					+ "/01/01', 'YYYY/MM/DD' ), ?, ?)";
			PreparedStatement insertStatement = con.prepareStatement(insertSQL);

			int col;
			String licensePlateNo = car.getLicensePlateNo();

			checkStatement.setString(1, licensePlateNo);
			ResultSet checkRes = checkStatement.executeQuery();

			checkRes.next();
			int count = checkRes.getInt(1);

			if (count == 0) {
				logger.info("A(z) " + licensePlateNo + " rendszámú autó hozzáadása az adatbázisboz..");
				String getManIdSQL = "select manufacturer_id from h_b2wkl0.manufacturer where manufacturer_name = ?";

				PreparedStatement getManDataStatement = con
						.prepareStatement(getManIdSQL);
				getManDataStatement.setString(1, car.getManufacturerName());

				ResultSet getManRes = getManDataStatement.executeQuery();
				getManRes.next();
				int manufacturerId = getManRes.getInt("manufacturer_id");

				String getModNameSQL = "select model_id from h_b2wkl0.model where "
						+ "manufacturer_id = ? and model_name = ?";

				PreparedStatement getModDataStatement = con
						.prepareStatement(getModNameSQL);
				getModDataStatement.setInt(1, manufacturerId);
				getModDataStatement.setString(2, car.getModelName());

				ResultSet getModRes = getModDataStatement.executeQuery();
				getModRes.next();
				int modelId = getModRes.getInt("model_id");

				String getCliNameSQL = "select client_id from h_b2wkl0.client where client_name = ?";

				PreparedStatement getCliDataStatement = con
						.prepareStatement(getCliNameSQL);
				getCliDataStatement.setString(1, car.getClientName());

				ResultSet getCliRes = getCliDataStatement.executeQuery();
				getCliRes.next();
				int clientId = getCliRes.getInt("client_id");

				col = 1;
				insertStatement.setString(col++, licensePlateNo);
				insertStatement.setInt(col++, manufacturerId);
				insertStatement.setInt(col++, modelId);
				insertStatement.setInt(col++, clientId);
				insertStatement.setString(col++, car.getColor());
				insertStatement.setInt(col++, car.getOdometer());

				insertStatement.executeUpdate();
			} else {
				logger.warn("A(z) " + licensePlateNo
						+ " rendszámú autó már szerepel az adatbázisban!");
			}

			insertStatement.close();
		}

		checkStatement.close();
	}
	
	/**
	 * Loads cars from the database.
	 * 
	 * @param con that will be used for the queries
	 * @throws SQLException
	 *             in case loading queries fail
	 */
	public void loadCars(Connection con) throws SQLException {
		cars.clear();

		String loadSQL = "select license_plate_no, manufacturer_id, model_id, client_id, year, color, odometer "
				+ "from h_b2wkl0.car order by license_plate_no";
		Statement loadStatement = con.createStatement();

		ResultSet results = loadStatement.executeQuery(loadSQL);
		while (results.next()) {
			String licensePlateNo = results.getString("license_plate_no");

			int manufacturerId = results.getInt("manufacturer_id");
			String getManNameSQL = "select manufacturer_name from h_b2wkl0.manufacturer where manufacturer_id = ?";

			PreparedStatement getManDataStatement = con
					.prepareStatement(getManNameSQL);
			getManDataStatement.setInt(1, manufacturerId);

			ResultSet getManRes = getManDataStatement.executeQuery();
			getManRes.next();
			String manufacturerName = getManRes.getString("manufacturer_name");

			int modelId = results.getInt("model_id");
			String getModNameSQL = "select model_name from h_b2wkl0.model where model_id = ?";

			PreparedStatement getModDataStatement = con
					.prepareStatement(getModNameSQL);
			getModDataStatement.setInt(1, modelId);

			ResultSet getModRes = getModDataStatement.executeQuery();
			getModRes.next();
			String modelName = getModRes.getString("model_name");

			int clientId = results.getInt("client_id");
			String getCliNameSQL = "select client_name from h_b2wkl0.client where client_id = ?";

			PreparedStatement getCliDataStatement = con
					.prepareStatement(getCliNameSQL);
			getCliDataStatement.setInt(1, clientId);

			ResultSet getCliRes = getCliDataStatement.executeQuery();
			getCliRes.next();
			String clientName = getCliRes.getString("client_name");

			String year = results.getString("year").substring(0, 4);
			String color = results.getString("color");
			Integer odometer = results.getInt("odometer");

			Car car = new Car(licensePlateNo, manufacturerName, modelName,
					clientName, year, color, odometer);

			cars.add(car);
		}

		results.close();
		loadStatement.close();
	}

	/**
	 * Removes cars from the database that were deleted on the GUI by the user.
	 * 
	 * @param con that will be used for the queries
	 * @throws SQLException
	 *             in case the query fails
	 */
	public void removeMarkedCars(Connection con) throws SQLException {
		String removeSQL = "delete from h_b2wkl0.car where license_plate_no = ?";
		PreparedStatement removeStatement = con.prepareStatement(removeSQL);

		for (Car car : cars) {
			if (car.isMarkedForDeletion()) {
				logger.info(car.getLicensePlateNo()
						+ " rendszámú autó (" + car.getManufacturerName() + "-"
						+ car.getModelName()
						+ ") törlése az adatbázisból.. (ügyfél: "
						+ car.getClientName() + ")");

				removeStatement.setString(1, car.getLicensePlateNo());
				removeStatement.executeQuery();
			}
		}

		removeStatement.close();
	}

	/**
	 * Adds a car to the list of cars.
	 * 
	 * @param car - The car that will be added to the list of cars
	 */
	public void addCar(Car car) {
		cars.add(car);
	}

	/**
	 * Removes a car from the list of cars.
	 * 
	 * @param clientName - The car that belongs to this client, will be
	 *        removed from the list of cars
	 */
	public void removeCar(String clientName) {
		for (Car car : cars)
			if (car.getClientName().equals(clientName))
				car.setMarkedForDeletion(true);
	}
	
	/**
	 * Returns the list of cars.
	 * 
	 * @return the list of cars
	 */
	public List<Car> getCars() {
		return Collections.unmodifiableList(cars);
	}
}
