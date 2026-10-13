package Personajes;

import TiposYInterfaces.Bestias;
import TiposYInterfaces.tipoPersonajes;

/**
 * Representa una bestia concreta del juego, con un tipo de personaje asignado
 * (Orco o Trasgo).
 * <p>
 * Extiende {@link Personaje} y define el comportamiento de ataque propio de las
 * bestias, así como la identificación de su tipo de personaje mediante el enum
 * {@link tipoPersonajes}.
 * <p>
 * Dispone de un patrón Builder que permite configurar progresivamente los
 * atributos de la bestia antes de construir el objeto.
 */
public class Bestia extends Personaje {

	/**
	 * Tipo de personaje concreto de esta bestia (Orco o Trasgo), seleccionado
	 * durante la construcción del objeto.
	 */
	private tipoPersonajes tipoPersonaje;

	/**
	 * Construye una bestia a partir del builder de la clase {@link Personaje},
	 * seleccionando su tipo de personaje según el nombre indicado en
	 * {@code builder.tipoPersonaje}.
	 *
	 * @param builder builder de {@link Personaje} con los datos necesarios para
	 *                construir la bestia.
	 * @throws IllegalArgumentException si el tipo de bestia no es válido.
	 */
	public Bestia(Personaje.Builder builder) {
		super(builder);
		this.tipoPersonaje = Bestias.seleccionarTipoBestia(builder.tipoPersonaje);
	}

	/**
	 * Construye una bestia a partir de su builder específico, inicializando los
	 * atributos heredados y seleccionando su tipo de personaje.
	 *
	 * @param builder builder específico de {@link Bestia} con los datos necesarios
	 *                para construir la bestia.
	 * @throws IllegalArgumentException si el tipo de bestia no es válido.
	 */
	public Bestia(Builder builder) {
		super(builder);
		this.tipoPersonaje = Bestias.seleccionarTipoBestia(builder.tipoPersonaje);
	}

	/**
	 * Devuelve el tipo de personaje asignado a esta bestia.
	 *
	 * @return el {@link tipoPersonajes} correspondiente al tipo de bestia.
	 */
	public tipoPersonajes getTipoPersonaje() {
		return tipoPersonaje;
	}

	/**
	 * Ejecuta el ataque de la bestia contra el enemigo indicado.
	 * <p>
	 * Se realiza una única tirada aleatoria entre 0 y 90, ambos incluidos, sin el
	 * mecanismo de ventaja utilizado por los héroes.
	 * <p>
	 * El valor resultante se pasa, junto con el enemigo, al método
	 * {@link tipoPersonajes#aplicarAfectacion}, que aplica el efecto
	 * correspondiente al tipo de bestia y devuelve el daño infligido.
	 *
	 * @param enemigo personaje enemigo objetivo del ataque.
	 * @return el daño infligido al enemigo.
	 */
	@Override
	public int atacar(Personaje enemigo) {
		int tirada = (int) (Math.random() * 91);

		return tipoPersonaje.aplicarAfectacion(enemigo, tirada);
	}

	/**
	 * Permite construir objetos {@link Bestia} mediante la configuración progresiva
	 * de sus atributos.
	 * <p>
	 * Valida los datos antes de asignarlos y antes de construir el objeto. Los
	 * atributos comunes se transfieren a través del constructor de
	 * {@link Personaje}.
	 */
	public static class Builder {

		/** Nombre de la bestia. */
		private String nombre;

		/** Puntos de vida iniciales de la bestia. */
		private int puntosDeVida;

		/** Nivel de resistencia inicial de la bestia. */
		private int nivelDeResistencia;

		/**
		 * Nombre del tipo de personaje que se asignará a la bestia.
		 */
		private String tipoPersonaje;

		/**
		 * Comprueba que el nombre cumple la expresión regular definida en
		 * {@link Personaje}.
		 *
		 * @param nombre nombre que se desea validar.
		 * @throws IllegalArgumentException si el nombre no es válido.
		 */
		private void validarNombre(String nombre) {
			if (nombre == null || !patron.matcher(nombre).matches()) {
				throw new IllegalArgumentException("Nombre no válido: usa solo letras (2-30 caracteres), "
						+ "con espacios, guiones o apóstrofos entre palabras.");
			}
		}

		/**
		 * Comprueba que los puntos de vida sean positivos.
		 *
		 * @param puntosDeVida puntos de vida que se desean validar.
		 * @throws IllegalArgumentException si los puntos de vida no son válidos.
		 */
		private void validarPuntosDeVida(int puntosDeVida) {
			if (puntosDeVida <= 0) {
				throw new IllegalArgumentException("Los puntos de vida deben ser mayores que cero.");
			}
		}

		/**
		 * Comprueba que el nivel de resistencia no sea negativo.
		 *
		 * @param nivelDeResistencia nivel de resistencia que se desea validar.
		 * @throws IllegalArgumentException si la resistencia es negativa.
		 */
		private void validarNivelDeResistencia(int nivelDeResistencia) {
			if (nivelDeResistencia < 0) {
				throw new IllegalArgumentException("El nivel de resistencia no puede ser negativo.");
			}
		}

		/**
		 * Comprueba que la especie indicada sea válida para una bestia.
		 *
		 * @param tipoPersonaje especie que se desea validar.
		 * @throws IllegalArgumentException si la especie no es válida.
		 */
		private void validarTipoPersonaje(String tipoPersonaje) {
			if (tipoPersonaje == null || tipoPersonaje.isBlank()) {
				throw new IllegalArgumentException("El tipo de personaje no puede ser nulo ni estar vacío.");
			}

			Bestias.seleccionarTipoBestia(tipoPersonaje);
		}

		/**
		 * Establece el nombre de la bestia y devuelve el builder.
		 *
		 * @param nombre nombre que se asignará a la bestia.
		 * @return esta instancia del builder.
		 */
		public Builder nombre(String nombre) {
			validarNombre(nombre);
			this.nombre = nombre;
			return this;
		}

		/**
		 * Establece los puntos de vida de la bestia y devuelve el builder.
		 *
		 * @param puntosDeVida puntos de vida que se asignarán a la bestia.
		 * @return esta instancia del builder.
		 */
		public Builder puntosDeVida(int puntosDeVida) {
			validarPuntosDeVida(puntosDeVida);
			this.puntosDeVida = puntosDeVida;
			return this;
		}

		/**
		 * Establece el nivel de resistencia de la bestia y devuelve el builder.
		 *
		 * @param nivelDeResistencia nivel de resistencia que se asignará.
		 * @return esta instancia del builder.
		 */
		public Builder nivelDeResistencia(int nivelDeResistencia) {
			validarNivelDeResistencia(nivelDeResistencia);
			this.nivelDeResistencia = nivelDeResistencia;
			return this;
		}

		/**
		 * Establece el nombre del tipo de bestia y devuelve el builder.
		 *
		 * @param tipoPersonaje nombre del tipo de bestia que se asignará.
		 * @return esta instancia del builder.
		 */
		public Builder tipoPersonaje(String tipoPersonaje) {
			validarTipoPersonaje(tipoPersonaje);
			this.tipoPersonaje = tipoPersonaje;
			return this;
		}

		/**
		 * Devuelve el nombre configurado para la bestia.
		 *
		 * @return nombre de la bestia.
		 */
		public String getNombre() {
			return nombre;
		}

		/**
		 * Establece el nombre de la bestia.
		 *
		 * @param nombre nombre que se asignará a la bestia.
		 */
		public void setNombre(String nombre) {
			validarNombre(nombre);
			this.nombre = nombre;
		}

		/**
		 * Devuelve los puntos de vida configurados para la bestia.
		 *
		 * @return puntos de vida iniciales.
		 */
		public int getPuntosDeVida() {
			return puntosDeVida;
		}

		/**
		 * Establece los puntos de vida de la bestia.
		 *
		 * @param puntosDeVida puntos de vida que se asignarán a la bestia.
		 */
		public void setPuntosDeVida(int puntosDeVida) {
			validarPuntosDeVida(puntosDeVida);
			this.puntosDeVida = puntosDeVida;
		}

		/**
		 * Devuelve el nivel de resistencia configurado para la bestia.
		 *
		 * @return nivel de resistencia inicial.
		 */
		public int getNivelDeResistencia() {
			return nivelDeResistencia;
		}

		/**
		 * Establece el nivel de resistencia de la bestia.
		 *
		 * @param nivelDeResistencia nivel de resistencia que se asignará.
		 */
		public void setNivelDeResistencia(int nivelDeResistencia) {
			validarNivelDeResistencia(nivelDeResistencia);
			this.nivelDeResistencia = nivelDeResistencia;
		}

		/**
		 * Devuelve el nombre del tipo de personaje configurado.
		 *
		 * @return nombre del tipo de bestia.
		 */
		public String getTipoPersonaje() {
			return tipoPersonaje;
		}

		/**
		 * Establece el nombre del tipo de personaje de la bestia.
		 *
		 * @param tipoPersonaje nombre del tipo de bestia que se asignará.
		 */
		public void setTipoPersonaje(String tipoPersonaje) {
			validarTipoPersonaje(tipoPersonaje);
			this.tipoPersonaje = tipoPersonaje;
		}

		/**
		 * Comprueba los datos configurados y construye la bestia.
		 *
		 * @return nueva bestia con los atributos establecidos.
		 * @throws IllegalArgumentException si alguno de los datos no es válido.
		 */
		public Bestia build() {
			validarNombre(nombre);
			validarPuntosDeVida(puntosDeVida);
			validarNivelDeResistencia(nivelDeResistencia);
			validarTipoPersonaje(tipoPersonaje);

			return new Bestia(this);
		}
	}
}