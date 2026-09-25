package TiposYInterfaces;

import Personajes.Personaje;

/**
 * Define el comportamiento de los tipos de personaje capaces de aplicar un
 * efecto de ataque sobre un enemigo.
 * <p>
 * Implementada, entre otros, por los distintos valores del enum de tipos de
 * personaje (héroes y bestias), permitiendo que cada tipo aplique sus propias
 * modificaciones o buffs sobre el daño base antes de infligirlo al enemigo.
 */
public interface Afectable {

	/**
	 * Aplica el efecto de ataque propio del tipo de personaje sobre el enemigo
	 * indicado, a partir de un daño base.
	 *
	 * @param enemigo  personaje enemigo sobre el que se aplica el efecto.
	 * @param danoBase valor de daño base, previo a la aplicación de las
	 *                 modificaciones propias del tipo de personaje.
	 * @return el daño final infligido al enemigo, una vez aplicado el efecto
	 *         correspondiente.
	 */
	int aplicacionAfectacion(Personaje enemigo, int danoBase);
}