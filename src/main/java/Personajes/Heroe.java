package Personajes;

import TiposYInterfaces.tipoPersonajes;

/**
 * Representa un héroe concreto del juego, con un tipo de personaje asignado
 * (Humano, Elfo o Hobbit).
 */
public class Heroe extends Heroes {

	/**
	 * Tipo de personaje concreto de este héroe (Humano, Elfo o Hobbit),
	 * seleccionado durante la construcción del objeto.
	 */
	private tipoPersonajes tipoPersonaje;

	/**
	 * Construye un héroe a partir del builder proporcionado, seleccionando su tipo
	 * de personaje según el nombre indicado en {@code builder.tipoPersonaje}.
	 *
	 * @param builder builder de {@link Personaje} con los datos necesarios para
	 *                construir el héroe, incluyendo el nombre del tipo de héroe a
	 *                asignar.
	 * @throws IllegalArgumentException si el tipo de héroe indicado en el builder
	 *                                  no es válido (ver
	 *                                  {@link Heroes#seleccionarTipoHeroe(String)}).
	 */
	public Heroe(Personaje.Builder builder) {
		super(builder);
		this.tipoPersonaje = seleccionarTipoHeroe(builder.tipoPersonaje);
	}

	/**
	 * Devuelve el tipo de personaje de este héroe.
	 *
	 * @return el {@link tipoPersonajes} asignado a este héroe.
	 */
	public tipoPersonajes getTipoPersonaje() {
		return tipoPersonaje;
	}

	/**
	 * Ejecuta el ataque del héroe contra el enemigo indicado.
	 * <p>
	 * Se realizan dos tiradas aleatorias entre 0 y 100 (ambos incluidos) y se
	 * utiliza la mayor de las dos, a modo de "ventaja", para favorecer ligeramente
	 * el resultado del ataque. El valor resultante se pasa junto con el enemigo a
	 * {@link tipoPersonajes#aplicacionAfectacion}, que aplica el efecto de ataque
	 * propio del tipo de héroe sobre el enemigo y devuelve el daño infligido.
	 *
	 * @param enemigo personaje enemigo objetivo del ataque.
	 * @return el daño infligido al enemigo.
	 */
	@Override
	public int atacar(Personaje enemigo) {
		int valorFinal;

		int primTirada = (int) (Math.random() * 101);
		int secTirada = (int) (Math.random() * 101);

		int mayorTirada = Math.max(primTirada, secTirada);

		valorFinal = tipoPersonaje.aplicacionAfectacion(enemigo, mayorTirada);

		return valorFinal;
	}

}