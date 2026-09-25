package Personajes;

import TiposYInterfaces.tipoPersonajes;

/**
 * Clase abstracta que representa a las bestias del juego.
 * <p>
 * Extiende {@link Personaje} y sirve de base para las subclases concretas de
 * bestias (por ejemplo, Orco, Trasgo) definidas dentro del paquete
 * {@code Personajes}, que se encargan de completar el comportamiento de ataque
 * propio de cada tipo de bestia.
 */
public abstract class Bestias extends Personaje {

	/**
	 * Tipos de personaje válidos que pueden clasificarse como bestias: orcos y
	 * trasgos.
	 * <p>
	 * Se declara como {@code static} porque su contenido es el mismo para todas las
	 * instancias de {@code Bestias} y no depende del estado de ningún objeto en
	 * concreto.
	 */
	private static final tipoPersonajes[] TIPOS_DE_BESTIAS = { tipoPersonajes.ORCOS, tipoPersonajes.TRASGOS };

	/**
	 * Construye una bestia a partir del builder proporcionado.
	 * <p>
	 * Constructor de visibilidad de paquete, pensado para ser invocado únicamente
	 * desde las subclases concretas de {@code Bestias} (por ejemplo, Orco, Trasgo)
	 * situadas en el paquete {@code Personajes}.
	 *
	 * @param builder builder de {@link Personaje} con los datos necesarios para
	 *                construir la bestia.
	 */
	Bestias(Personaje.Builder builder) {
		super(builder);
	}

	/**
	 * Devuelve los tipos de personaje válidos para las bestias.
	 *
	 * @return array con los tipos de bestia disponibles: orcos y trasgos.
	 */
	public static tipoPersonajes[] getTIPOS_DE_BESTIAS() {
		return TIPOS_DE_BESTIAS;
	}

	/**
	 * Selecciona el tipo de bestia correspondiente al nombre indicado.
	 *
	 * @param bestiaSeleccionada nombre de la bestia a seleccionar (no distingue
	 *                           mayúsculas de minúsculas). Valores admitidos:
	 *                           "Orco" y "Trasgo".
	 * @return el {@link tipoPersonajes} correspondiente al nombre indicado.
	 * @throws IllegalArgumentException si {@code bestiaSeleccionada} es
	 *                                  {@code null} o no corresponde a ningún tipo
	 *                                  de bestia válido.
	 */
	public tipoPersonajes seleccionarTipoBestia(String bestiaSeleccionada) throws IllegalArgumentException {
		if (bestiaSeleccionada == null) {
			throw new IllegalArgumentException("El nombre de la bestia no puede ser nulo.");
		}

		if (bestiaSeleccionada.equalsIgnoreCase("Orco")) {
			return getTIPOS_DE_BESTIAS()[0];
		} else if (bestiaSeleccionada.equalsIgnoreCase("Trasgo")) {
			return getTIPOS_DE_BESTIAS()[1];
		} else {
			throw new IllegalArgumentException("Tipo de bestia no válido: " + bestiaSeleccionada + "\n"
					+ "Recuerda los tipos de bestia a elegir son: " + tipoPersonajes.especiesValidas(1));
		}
	}

	/**
	 * Ejecuta el ataque de la bestia contra el enemigo indicado.
	 * <p>
	 * Es abstracto porque cada tipo de bestia aplica sus propias modificaciones o
	 * buffs al ataque; son las subclases concretas las que terminan de implementar
	 * este método.
	 *
	 * @param enemigo personaje enemigo objetivo del ataque.
	 * @return el daño infligido al enemigo.
	 */
	@Override
	public abstract int atacar(Personaje enemigo);
}