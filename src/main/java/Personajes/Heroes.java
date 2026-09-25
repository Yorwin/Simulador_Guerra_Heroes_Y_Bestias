package Personajes;

import TiposYInterfaces.tipoPersonajes;

/**
 * Clase abstracta que representa a los héroes del juego.
 * <p>
 * Extiende {@link Personaje} y sirve de base para las subclases concretas de
 * héroes (por ejemplo, Humano, Elfo, Hobbit) definidas dentro del paquete
 * {@code Personajes}, que se encargan de completar el comportamiento de ataque
 * propio de cada tipo de héroe.
 */
public abstract class Heroes extends Personaje {

	/**
	 * Tipos de personaje válidos que pueden clasificarse como héroes: humanos,
	 * elfos y hobbits.
	 * <p>
	 * Se declara como {@code static} porque su contenido es el mismo para todas las
	 * instancias de {@code Heroes} y no depende del estado de ningún objeto en
	 * concreto.
	 */
	private static final tipoPersonajes[] TIPOS_DE_HEROES = { tipoPersonajes.HUMANOS, tipoPersonajes.ELFOS,
			tipoPersonajes.HOBBITS };

	/**
	 * Construye un héroe a partir del builder proporcionado.
	 * <p>
	 * Constructor de visibilidad de paquete, pensado para ser invocado únicamente
	 * desde las subclases concretas de {@code Heroes} (por ejemplo, Humano, Elfo,
	 * Hobbit) situadas en el paquete {@code Personajes}.
	 *
	 * @param builder builder de {@link Personaje} con los datos necesarios para
	 *                construir el héroe.
	 */
	Heroes(Personaje.Builder builder) {
		super(builder);
	}

	/**
	 * Devuelve los tipos de personaje válidos para los héroes.
	 *
	 * @return array con los tipos de héroe disponibles: humanos, elfos y hobbits.
	 */
	public static tipoPersonajes[] getTIPOS_DE_HEROES() {
		return TIPOS_DE_HEROES;
	}

	/**
	 * Selecciona el tipo de héroe correspondiente al nombre indicado.
	 *
	 * @param heroeSeleccionado nombre del héroe a seleccionar (no distingue
	 *                          mayúsculas de minúsculas). Valores admitidos:
	 *                          "Humano", "Elfo" y "Hobbit".
	 * @return el {@link tipoPersonajes} correspondiente al nombre indicado.
	 * @throws IllegalArgumentException si {@code heroeSeleccionado} es {@code null}
	 *                                  o no corresponde a ningún tipo de héroe
	 *                                  válido.
	 */
	public tipoPersonajes seleccionarTipoHeroe(String heroeSeleccionado) throws IllegalArgumentException {

		if (heroeSeleccionado == null) {
			throw new IllegalArgumentException("El nombre del héroe no puede ser nulo.");
		}

		if (heroeSeleccionado.equalsIgnoreCase("Humano")) {
			return getTIPOS_DE_HEROES()[0];
		} else if (heroeSeleccionado.equalsIgnoreCase("Elfo")) {
			return getTIPOS_DE_HEROES()[1];
		} else if (heroeSeleccionado.equalsIgnoreCase("Hobbit")) {
			return getTIPOS_DE_HEROES()[2];
		} else {
			throw new IllegalArgumentException("Tipo de héroe no válido: " + heroeSeleccionado + "\n"
					+ "Recuerda los tipos de heroes a elegir son: " + tipoPersonajes.especiesValidas(0));
		}
	}

	/**
	 * Ejecuta el ataque del héroe contra el enemigo indicado.
	 * <p>
	 * Es abstracto porque cada tipo de héroe aplica sus propias modificaciones o
	 * buffs al ataque; son las subclases concretas las que terminan de implementar
	 * este método.
	 *
	 * @param enemigo personaje enemigo objetivo del ataque.
	 * @return el daño infligido al enemigo.
	 */
	@Override
	public abstract int atacar(Personaje enemigo);
}