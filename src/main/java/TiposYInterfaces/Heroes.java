package TiposYInterfaces;

/**
 * Interfaz que define operaciones relacionadas con los héroes del juego.
 * <p>
 * Proporciona un método estático para seleccionar el tipo de héroe
 * correspondiente a un nombre, utilizando los valores definidos en el enum
 * {@link tipoPersonajes}.
 */
public interface Heroes {

	/**
	 * Selecciona el tipo de héroe correspondiente al nombre indicado.
	 *
	 * @param heroeSeleccionado nombre del héroe a seleccionar (no distingue
	 *                          mayúsculas de minúsculas). Valores admitidos:
	 *                          "Humano", "Elfo" y "Hobbit".
	 * @return el valor de {@link tipoPersonajes} correspondiente al nombre
	 *         indicado.
	 * @throws IllegalArgumentException si {@code heroeSeleccionado} es {@code null}
	 *                                  o no corresponde a ningún tipo de héroe
	 *                                  válido.
	 */
	public static tipoPersonajes seleccionarTipoHeroe(String heroeSeleccionado) throws IllegalArgumentException {

		if (heroeSeleccionado == null) {
			throw new IllegalArgumentException("El nombre del héroe no puede ser nulo.");
		}

		if (heroeSeleccionado.equalsIgnoreCase("Humano")) {
			return tipoPersonajes.getTipoHeroes()[0];
		} else if (heroeSeleccionado.equalsIgnoreCase("Elfo")) {
			return tipoPersonajes.getTipoHeroes()[1];
		} else if (heroeSeleccionado.equalsIgnoreCase("Hobbit")) {
			return tipoPersonajes.getTipoHeroes()[2];
		} else {
			throw new IllegalArgumentException("Tipo de héroe no válido: " + heroeSeleccionado + "\n"
					+ "Recuerda que los tipos de héroes disponibles son: " + tipoPersonajes.indicarEspeciesValidas(0));
		}
	}
}