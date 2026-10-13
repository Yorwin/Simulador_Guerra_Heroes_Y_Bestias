package TiposYInterfaces;

/**
 * Interfaz que define operaciones relacionadas con las bestias del juego.
 * <p>
 * Proporciona un método estático para seleccionar el tipo de bestia
 * correspondiente a un nombre, utilizando los valores definidos en el enum
 * {@link tipoPersonajes}.
 */
public interface Bestias {

	/**
	 * Selecciona el tipo de bestia correspondiente al nombre indicado.
	 *
	 * @param bestiaSeleccionada nombre de la bestia a seleccionar (no distingue
	 *                           mayúsculas de minúsculas). Valores admitidos:
	 *                           "Orco" y "Trasgo".
	 * @return el valor de {@link tipoPersonajes} correspondiente al nombre
	 *         indicado.
	 * @throws IllegalArgumentException si {@code bestiaSeleccionada} es
	 *                                  {@code null} o no corresponde a ningún tipo
	 *                                  de bestia válido.
	 */
	public static tipoPersonajes seleccionarTipoBestia(String bestiaSeleccionada) throws IllegalArgumentException {

		if (bestiaSeleccionada == null) {
			throw new IllegalArgumentException("El nombre de la bestia no puede ser nulo.");
		}

		if (bestiaSeleccionada.equalsIgnoreCase("Orco")) {
			return tipoPersonajes.getTipoBestias()[0];
		} else if (bestiaSeleccionada.equalsIgnoreCase("Trasgo")) {
			return tipoPersonajes.getTipoBestias()[1];
		} else {
			throw new IllegalArgumentException("Tipo de bestia no válido: " + bestiaSeleccionada + "\n"
					+ "Recuerda que los tipos de bestia disponibles son: " + tipoPersonajes.indicarEspeciesValidas(1));
		}
	}
}
