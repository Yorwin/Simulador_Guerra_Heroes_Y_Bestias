package TiposYInterfaces;

import Personajes.Bestia;
import Personajes.Personaje;

/**
 * Representa las diferentes especies de personajes y el efecto que cada una
 * aplica sobre sus características de combate.
 */
public enum tipoPersonajes implements Afectable {

	HUMANOS("Humano", 0), ELFOS("Elfo", 10), HOBBITS("Hobbit", -5), ORCOS("Orco", 10), TRASGOS("Trasgo", 0);

	/**
	 * Array con las especies que pueden pertenecer al ejército de héroes.
	 */
	private static final tipoPersonajes[] HEROES = { HUMANOS, ELFOS, HOBBITS };

	/**
	 * Array con las especies que pueden pertenecer al ejército de bestias.
	 */
	private static final tipoPersonajes[] BESTIAS = { ORCOS, TRASGOS };

	/**
	 * Nombre de la especie del personaje.
	 */
	private final String ESPECIE;

	/**
	 * Modificador que se aplica al daño o a la resistencia durante el combate,
	 * según las reglas de afectación de cada especie.
	 */
	private final int EFECTO;

	/**
	 * Constructor de las especies disponibles para los personajes.
	 *
	 * @param especie nombre de la especie.
	 * @param efecto  modificador que aplica la especie durante el combate.
	 */
	tipoPersonajes(String especie, int efecto) {
		this.ESPECIE = especie;
		this.EFECTO = efecto;
	}

	/**
	 * Obtiene el nombre de la especie del personaje.
	 *
	 * @return el nombre de la especie.
	 */
	public String getESPECIE() {
		return ESPECIE;
	}

	/**
	 * Calcula el daño final que este tipo de personaje inflige a un enemigo,
	 * aplicando bonificaciones o penalizaciones según la interacción entre
	 * especies.
	 * <p>
	 * Las reglas aplicadas son las siguientes:
	 * <ul>
	 * <li>Si este tipo es {@code ELFOS} y el enemigo es de tipo {@code ORCOS}, se
	 * suma el efecto de la especie ({@code EFECTO}) al daño base.</li>
	 * <li>Si este tipo es {@code HOBBITS} y el enemigo es de tipo {@code TRASGOS},
	 * se suma el efecto de la especie ({@code EFECTO}) al daño base.</li>
	 * <li>Si este tipo es {@code ORCOS}, se reduce la resistencia del enemigo en un
	 * porcentaje igual a {@code EFECTO}, y la reducción de resistencia resultante
	 * se suma al daño base.</li>
	 * <li>En cualquier otro caso, el daño final es igual al daño base, sin
	 * modificaciones.</li>
	 * </ul>
	 *
	 * @param enemigo  personaje enemigo sobre el que se calcula la afectación; se
	 *                 espera que sea una instancia de {@link Bestia}.
	 * @param danoBase daño base antes de aplicar cualquier afectación.
	 *
	 * @return el daño final resultante tras aplicar las reglas de afectación.
	 */
	@Override
	public int aplicarAfectacion(Personaje enemigo, int danoBase) {
		int danoFinal = danoBase;

		if (this == ELFOS && ((Bestia) enemigo).getTipoPersonaje() == ORCOS) {
			danoFinal = danoBase + EFECTO;
		} else if (this == HOBBITS && ((Bestia) enemigo).getTipoPersonaje() == TRASGOS) {
			danoFinal = danoBase + EFECTO;
		} else if (this == ORCOS) {
			int nivelDeResistencia = enemigo.getNivelResistencia();
			int resistenciaRestante = (int) (nivelDeResistencia * (1 - EFECTO / 100.0));
			int armaduraReducida = nivelDeResistencia - resistenciaRestante;
			danoFinal = danoBase + armaduraReducida;
		}

		return danoFinal;
	}

	/**
	 * Indica las especies válidas en función del tipo de ejército seleccionado.
	 * <p>
	 * Si se seleccionan héroes ({@code 0}), devuelve los nombres de las especies
	 * {@code HUMANOS}, {@code ELFOS} y {@code HOBBITS}. Si se seleccionan bestias
	 * ({@code 1}), devuelve los nombres de las especies {@code ORCOS} y
	 * {@code TRASGOS}.
	 *
	 * @param especieDeseada valor que identifica el tipo de ejército: {@code 0}
	 *                       para héroes y {@code 1} para bestias.
	 *
	 * @return los nombres de las especies válidas separados por comas.
	 *
	 * @throws IllegalArgumentException si {@code especieDeseada} no es {@code 0} ni
	 *                                  {@code 1}.
	 */
	public static String indicarEspeciesValidas(int especieDeseada) {
		if (especieDeseada == 0) {
			return String.join(", ", HUMANOS.ESPECIE, ELFOS.ESPECIE, HOBBITS.ESPECIE);
		} else if (especieDeseada == 1) {
			return String.join(", ", ORCOS.ESPECIE, TRASGOS.ESPECIE);
		} else {
			throw new IllegalArgumentException(
					"No has seleccionado la especie correctamente: 0 para Heroes, 1 para Bestias");
		}
	}

	/**
	 * Obtiene las especies que pueden pertenecer al ejército de bestias.
	 *
	 * @return array con las especies {@code ORCOS} y {@code TRASGOS}.
	 */
	public static tipoPersonajes[] getTipoBestias() {
		return BESTIAS;
	}

	/**
	 * Obtiene las especies que pueden pertenecer al ejército de héroes.
	 *
	 * @return array con las especies {@code HUMANOS}, {@code ELFOS} y
	 *         {@code HOBBITS}.
	 */
	public static tipoPersonajes[] getTipoHeroes() {
		return HEROES;
	}
}