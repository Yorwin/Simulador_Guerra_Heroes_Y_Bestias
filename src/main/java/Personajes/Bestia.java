package Personajes;

import TiposYInterfaces.tipoPersonajes;

/**
 * Representa una bestia concreta del juego, con un tipo de personaje asignado
 * (Orco o Trasgo).
 */
public class Bestia extends Bestias {

	/**
	 * Tipo de personaje concreto de esta bestia (Orco o Trasgo), seleccionado
	 * durante la construcción del objeto.
	 */
	private tipoPersonajes tipoPersonaje;

	/**
	 * Construye una bestia a partir del builder proporcionado, seleccionando su
	 * tipo de personaje según el nombre indicado en {@code builder.tipoPersonaje}.
	 *
	 * @param builder builder de {@link Personaje} con los datos necesarios para
	 *                construir la bestia, incluyendo el nombre del tipo de bestia a
	 *                asignar.
	 * @throws IllegalArgumentException si el tipo de bestia indicado en el builder
	 *                                  no es válido (ver
	 *                                  {@link Bestias#seleccionarTipoBestia(String)}).
	 */
	public Bestia(Personaje.Builder builder) {
		super(builder);
		this.tipoPersonaje = seleccionarTipoBestia(builder.tipoPersonaje);
	}

	/**
	 * Devuelve el tipo de personaje de esta bestia.
	 *
	 * @return el {@link tipoPersonajes} asignado a esta bestia.
	 */
	public tipoPersonajes getTipoPersonaje() {
		return tipoPersonaje;
	}

	/**
	 * Ejecuta el ataque de la bestia contra el enemigo indicado.
	 * <p>
	 * Se realiza una única tirada aleatoria entre 0 y 90 (ambos incluidos), sin
	 * mecanismo de "ventaja" como en el caso de los héroes. El valor resultante se
	 * pasa junto con el enemigo a {@link tipoPersonajes#aplicacionAfectacion}, que
	 * aplica el efecto de ataque propio del tipo de bestia sobre el enemigo y
	 * devuelve el daño infligido.
	 *
	 * @param enemigo personaje enemigo objetivo del ataque.
	 * @return el daño infligido al enemigo.
	 */
	@Override
	public int atacar(Personaje enemigo) {
		int valorFinal;

		int tirada = (int) (Math.random() * 91);
		valorFinal = tipoPersonaje.aplicacionAfectacion(enemigo, tirada);

		return valorFinal;
	}

}