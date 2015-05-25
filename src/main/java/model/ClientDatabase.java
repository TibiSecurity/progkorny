package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for database and client related tasks.
 */
public class ClientDatabase {
	Logger logger = LoggerFactory.getLogger(ClientDatabase.class);
	/**
	 * List of clients that are in the database.
	 */
	private List<Client> clients;

	/**
	 * Constructs a {@code ClientDatabase} object.
	 */
	public ClientDatabase() {
		clients = new LinkedList<Client>();
	}

	/**
	 * Saves clients to the database if they are not present in it, otherwise updates them.
	 * 
	 * @param con that will be used for the queries
	 * @throws SQLException
	 *             in case saving queries fail
	 */
	public void saveClient(Connection con) throws SQLException {
		String checkSQL = "select count(*) as count from h_b2wkl0.client where client_id = ?";
		PreparedStatement checkStatement = con.prepareStatement(checkSQL);

		String insertSQL = "insert into h_b2wkl0.client (client_name, phone_number, creation_date) values (?, ?, ?)";
		PreparedStatement insertStatement = con.prepareStatement(insertSQL);

		String updateCheckSQL = "select client_name, phone_number from h_b2wkl0.client where client_id = ?";
		PreparedStatement updateCheckStatement = con
				.prepareStatement(updateCheckSQL);

		String updateSQL = "update h_b2wkl0.client set client_name = ?,  phone_number = ?, modification_date = ? where client_id = ?";
		PreparedStatement updateStatement = con.prepareStatement(updateSQL);

		for (Client client : clients) {
			int col;
			int id = client.getId();
			String name = client.getName();
			String phoneNum = client.getPhoneNum();
			Date currentDate = client.getCreationDate();

			checkStatement.setInt(1, id);

			ResultSet checkRes = checkStatement.executeQuery();

			checkRes.next();
			int count = checkRes.getInt(1);

			if (count == 0) {
				logger.info(name
						+ " nevű ügyfél hozzáadása az adatbázishoz..");

				col = 1;
				insertStatement.setString(col++, name);
				insertStatement.setString(col++, phoneNum);
				insertStatement.setTimestamp(col++,
						new Timestamp(currentDate.getTime()));

				insertStatement.executeUpdate();
			} else {
				col = 1;
				updateCheckStatement.setInt(col++, id);

				ResultSet updateCheck = updateCheckStatement.executeQuery();
				if (updateCheck.next()) {
					String nameDB = updateCheck.getString("client_name");
					String phoneNumDB = updateCheck.getString("phone_number");

					if (!nameDB.equals(name) || !phoneNumDB.equals(phoneNum)) {
						logger.info(name
								+ " nevű ügyfél módosítása az adatbázisban..");

						Date modificationDate = new Date();

						col = 1;
						updateStatement.setString(col++, name);
						updateStatement.setString(col++, phoneNum);
						updateStatement.setTimestamp(col++, new Timestamp(
								modificationDate.getTime()));
						updateStatement.setInt(col++, id);
						updateStatement.executeUpdate();
					} else {
						logger.info(name
										+ " értékei változatlanok, nincs szükség frissítésre!");
					}

				}
			}
		}

		insertStatement.close();
		updateCheckStatement.close();
		updateStatement.close();
		checkStatement.close();
	}

	/**
	 * Loads clients from the database.
	 * 
	 * @param con that will be used for the queries
	 * @throws SQLException
	 *             in case loading queries fail
	 */
	public void loadClients(Connection con) throws SQLException {
		clients.clear();

		String loadSQL = "select client_id, client_name, phone_number, creation_date, modification_date "
				+ "from h_b2wkl0.client " + "order by client_name";
		Statement loadStatement = con.createStatement();

		ResultSet results = loadStatement.executeQuery(loadSQL);
		while (results.next()) {
			int id = results.getInt("client_id");
			String name = results.getString("client_name");
			String phoneNum = results.getString("phone_number");
			Date creationDate = results.getTimestamp("creation_date");
			Date modificationDate = results.getTimestamp("modification_date");

			Client client = new Client(id, name, phoneNum, creationDate,
					modificationDate);
			clients.add(client);
		}

		results.close();
		loadStatement.close();
	}

	/**
	 * Removes clients from the database that were deleted on the GUI by the
	 * user.
	 * 
	 * @param con that will be used for the queries
	 * @throws SQLException
	 *             in case the query fails
	 */
	public void removeMarkedClients(Connection con) throws SQLException {
		String removeSQL = "delete from h_b2wkl0.client where client_id = ?";
		PreparedStatement removeStatement = con.prepareStatement(removeSQL);

		for (Client client : clients) {
			if (client.isMarkedForDeletion()) {
				logger.info(client.getName() + " nevű ügyfél (id: "
						+ client.getId() + ") törlése az adatbázisból..");

				removeStatement.setInt(1, client.getId());
				removeStatement.executeQuery();
			}
		}

		removeStatement.close();
	}

	/**
	 * Adds a client to the list of clients.
	 * 
	 * @param client - The client that will be added to the list of
	 *        clients
	 */
	public void addClient(Client client) {
		clients.add(client);
	}

	/**
	 * Removes a client from the list of clients.
	 * 
	 * @param index - The client that has the position of {@code index}
	 *        in the list, will be deleted
	 *        
	 * @return clientNameToRemove - helper for the {@code removeCar} method
	 */
	public String removeClient(int index) {
		String clientNameToRemove = clients.get(index).getName();
		clients.get(index).setMarkedForDeletion(true);

		return clientNameToRemove;
	}

	/**
	 * Returns the list of clients.
	 * 
	 * @return the list of clients
	 */
	public List<Client> getClients() {
		return Collections.unmodifiableList(clients);
	}
}
