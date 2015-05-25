package model;

/**
 * Model class.
 */
public class Model {
	/**
	 * The id of the model.
	 */
	private int modelId;
	/**
	 * The id of the model's manufacturer.
	 */
	private int manId;
	/**
	 * The name of the model.
	 */
	private String modelName;
	
	/**
	 * Constructs a {@code Model} object.
	 * 
	 * @param modelId The id of the model
	 * @param manId The id of the model's manufacturer
	 * @param modelName The name of the model
	 */
	public Model(int modelId, int manId, String modelName) {
		this.modelId = modelId;
		this.manId = manId;
		this.modelName = modelName;
	}
	
	/**
	 * Gets the id of the model.
	 *
	 * @return the id of the model
	 */
	public int getModelId() {
		return modelId;
	}

	/**
	 * Sets the id of the model.
	 *
	 * @param modelId - the id of the model
	 */
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	/**
	 * Gets the id of the model's manufacturer.
	 *
	 * @return the id of the model's manufacturer
	 */
	public int getManId() {
		return manId;
	}

	/**
	 * Sets the id of the model's manufacturer.
	 *
	 * @param manId - the id of the model's manufacturer
	 */
	public void setManId(int manId) {
		this.manId = manId;
	}

	/**
	 * Gets the name of the model.
	 *
	 * @return the name of the model
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * Sets the name of the model.
	 *
	 * @param modelName - the name of the model
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * Creates a {@code toString} method for the class.
	 * 
	 * @return the string representation of a model
	 */
	public String toString() {
		return "Model id: " + modelId + ", manId: " + manId + ", model name: " + modelName;
	}
	
}
