package TiposYInterfaces;

/**
 * Enumera los tipos de ejército disponibles en el juego para clasificar a los
 * reclutas/personajes: héroes y bestias.
 */
public enum tipoEjercito {

	/** Ejército de héroes. */
	HEROES("HÉROES"),

	/** Ejército de bestias. */
	BESTIAS("BESTIAS");

	/**
	 * Nombre legible del tipo de ejército.
	 */
	private final String nombre;

	/**
	 * Construye un tipo de ejército con el nombre legible indicado.
	 *
	 * @param nombre nombre legible del tipo de ejército.
	 */
	tipoEjercito(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve el nombre legible del tipo de ejército.
	 *
	 * @return nombre legible del tipo de ejército.
	 */
	public String getNombre() {
		return nombre;
	}
}