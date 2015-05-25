package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for database and manufacturer related tasks.
 */
public class ManufacturerDatabase {
	/**
	 * List of manufacturers that are in the database.
	 */
	private List<Manufacturer> manufacturers;

	/**
	 * Constructs a {@code ManufacturerDatabase} object.
	 */
	public ManufacturerDatabase() {
		manufacturers = new LinkedList<Manufacturer>();
	}

	/**
	 * Loads manufacturers from the database.
	 * 
	 * @param con that will be used for the queries
	 * @return the list of manufacturers, loaded from the database
	 * @throws SQLException
	 *             in case loading queries fail
	 */
	public List<Manufacturer> loadManufacturers(Connection con)
			throws SQLException {
		manufacturers.clear();

		String loadSQL = "select manufacturer_id, manufacturer_name "
				+ "from h_b2wkl0.manufacturer " + "order by manufacturer_name";
		Statement loadStatement = con.createStatement();

		ResultSet results = loadStatement.executeQuery(loadSQL);
		while (results.next()) {
			int manId = results.getInt("manufacturer_id");
			String manName = results.getString("manufacturer_name");

			Manufacturer manufacturer = new Manufacturer(manId, manName);
			manufacturers.add(manufacturer);
		}

		results.close();
		loadStatement.close();

		return manufacturers;
	}

	/**
	 * Returns the list of manufacturers.
	 * 
	 * @return the list of manufacturers
	 */
	public List<Manufacturer> getManufacturers() {
		return Collections.unmodifiableList(manufacturers);
	}
}
