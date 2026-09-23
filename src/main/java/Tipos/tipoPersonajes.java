package Tipos;

import Personajes.Bestia;
import Personajes.Personaje;

public enum tipoPersonajes implements Afectable {
	HUMANOS("Humano", 0), ELFOS("Elfo", 10), HOBBITS("Hobbit", -5), ORCOS("Orco", 10), TRASGOS("Trasgo", 0);

	private final String ESPECIE;
	private final int EFECTO;

	tipoPersonajes(String especie, int efecto) {
		this.ESPECIE = especie;
		this.EFECTO = efecto;
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
	 * @param Afectacion parámetro de afectación adicional (actualmente sin uso en
	 *                   el cálculo)
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

	public static String especiesValidasBestias(int especieDeseada) {
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
