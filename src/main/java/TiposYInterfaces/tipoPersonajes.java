package TiposYInterfaces;

import Personajes.Bestia;
import Personajes.Personaje;

/**
 * Representa las diferentes especies de personajes y el efecto que cada una
 * aplica sobre sus características de combate.
 */
public enum tipoPersonajes implements Afectable {
	HUMANOS("Humano", 0), ELFOS("Elfo", 10), HOBBITS("Hobbit", -5), ORCOS("Orco", 10), TRASGOS("Trasgo", 0);

	private final String ESPECIE;
	private final int EFECTO;

	/**
	 * Constructor de las especies disponibles para los personajes.
	 *
	 * @param especie nombre de la especie.
	 * @param efecto  modificador de ataque que aplica la especie.
	 */
	tipoPersonajes(String especie, int efecto) {
		this.ESPECIE = especie;
		this.EFECTO = efecto;
	}

	/**
	 * Obtiene la especie del personaje.
	 * 
	 * @return la especie del personaje.
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
	 * @param enemigo    El personaje enemigo sobre el que se calcula la afectación;
	 *                   se espera que sea una instancia de {@link Bestia}
	 * @param danoBase   El daño base antes de aplicar cualquier afectación
	 * 
	 * @return el daño final resultante tras aplicar las reglas de afectación según
	 *         la especie
	 */
	@Override
	public int aplicacionAfectacion(Personaje enemigo, int danoBase) {
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
	 * Obtiene las especies válidas en función del tipo de ejército seleccionado.
	 * <p>
	 * Si se seleccionan héroes ({@code 0}), devuelve las especies {@code HUMANOS},
	 * {@code ELFOS} y {@code HOBBITS}. Si se seleccionan bestias ({@code 1}),
	 * devuelve las especies {@code ORCOS} y {@code TRASGOS}.
	 *
	 * @param especieDeseada valor que identifica el tipo de ejército: {@code 0}
	 *                       para héroes y {@code 1} para bestias.
	 * @return una cadena con las especies válidas separadas por comas.
	 * @throws IllegalArgumentException si {@code especieDeseada} no es {@code 0} ni
	 *                                  {@code 1}.
	 */
	public static String especiesValidas(int especieDeseada) {
		if (especieDeseada == 0) {
			return String.join(", ", HUMANOS.ESPECIE, ELFOS.ESPECIE, HOBBITS.ESPECIE);
		} else if (especieDeseada == 1) {
			return String.join(", ", ORCOS.ESPECIE, TRASGOS.ESPECIE);
		} else {
			throw new IllegalArgumentException(
					"No has seleccionado la especie correctamente: 0 para Heroes, 1 para Bestias");
		}
	}
}
