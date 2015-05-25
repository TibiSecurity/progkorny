package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for database and manufacturer related tasks.
 */
public class ModelDatabase {
	/**
	 * List of models that are in the database.
	 */
	private List<Model> models;

	/**
	 * Constructs a {@code ModelDatabase} object.
	 */
	public ModelDatabase() {
		models = new LinkedList<Model>();
	}

	/**
	 * Loads those models from the database that belong to the specified
	 * manufacturer.
	 * 
	 * @param con
	 *            - the database connection
	 * @param manufacturerName
	 *            - the name of the manufacturer for which models will be pullsed
	 *            from database
	 * @return the list of models, loaded from the database
	 * @throws SQLException
	 *             in case loading queries fail
	 */
	public List<Model> loadModels(Connection con, String manufacturerName)
			throws SQLException {
		models.clear();

		String loadSQL = "select model_id, manufacturer_id, model_name "
				+ "from h_b2wkl0.model "
				+ "where manufacturer_id = "
				+ "( select manufacturer_id from manufacturer where manufacturer_name = ? ) "
				+ "order by model_name";
		PreparedStatement loadStatement = con.prepareStatement(loadSQL);

		int col = 1;
		loadStatement.setString(col++, manufacturerName);

		ResultSet results = loadStatement.executeQuery();
		while (results.next()) {
			int modelId = results.getInt("model_id");
			int manId = results.getInt("manufacturer_id");
			String modelName = results.getString("model_name");

			Model model = new Model(modelId, manId, modelName);
			models.add(model);
		}

		results.close();
		loadStatement.close();

		return models;
	}

	/**
	 * Returns the list of models.
	 * 
	 * @return the list of models
	 */
	public List<Model> getModels() {
		return Collections.unmodifiableList(models);
	}
}
