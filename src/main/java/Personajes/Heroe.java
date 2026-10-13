package Personajes;

import TiposYInterfaces.Heroes;
import TiposYInterfaces.tipoPersonajes;

/**
 * Representa a un héroe del juego.
 *
 * Un héroe tiene un tipo de personaje concreto y puede atacar a otros
 * personajes aplicando los efectos correspondientes a su especie.
 */
public class Heroe extends Personaje {

	private tipoPersonajes tipoPersonaje;

	/**
	 * Construye un héroe a partir del Builder general de Personaje.
	 *
	 * @param builder Builder general con los datos del héroe.
	 */
	public Heroe(Personaje.Builder builder) {
		super(builder);
		this.tipoPersonaje = Heroes.seleccionarTipoHeroe(builder.tipoPersonaje);
	}

	/**
	 * Construye un héroe a partir de su Builder específico.
	 *
	 * @param builder Builder con los datos del héroe.
	 */
	public Heroe(Builder builder) {
		super(builder);
		this.tipoPersonaje = Heroes.seleccionarTipoHeroe(builder.tipoPersonaje);
	}

	/**
	 * Devuelve el tipo de personaje del héroe.
	 *
	 * @return Especie del héroe.
	 */
	public tipoPersonajes getTipoPersonaje() {
		return tipoPersonaje;
	}

	/**
	 * Calcula el daño del ataque del héroe contra otro personaje.
	 *
	 * Se realizan dos tiradas aleatorias y se utiliza la mayor. El daño obtenido se
	 * modifica según los efectos de la especie.
	 *
	 * @param enemigo Personaje que recibe el ataque.
	 * @return Daño final del ataque.
	 */
	@Override
	public int atacar(Personaje enemigo) {
		int tiradaUno = (int) (Math.random() * 101);
		int tiradaDos = (int) (Math.random() * 101);

		int danoBase = Math.max(tiradaUno, tiradaDos);

		return tipoPersonaje.aplicarAfectacion(enemigo, danoBase);
	}

	/**
	 * Builder específico para construir objetos de tipo Heroe.
	 *
	 * Valida los datos antes de asignarlos y antes de construir el objeto.
	 */
	public static class Builder {

		private String nombre;
		private int puntosDeVida;
		private int nivelDeResistencia;
		private String tipoPersonaje;

		/**
		 * Comprueba que el nombre cumple la expresión regular definida en Personaje.
		 *
		 * @param nombre Nombre que se desea validar.
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
		 * @param puntosDeVida Puntos de vida que se desean validar.
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
		 * @param nivelDeResistencia Nivel de resistencia que se desea validar.
		 * @throws IllegalArgumentException si la resistencia es negativa.
		 */
		private void validarNivelDeResistencia(int nivelDeResistencia) {
			if (nivelDeResistencia < 0) {
				throw new IllegalArgumentException("El nivel de resistencia no puede ser negativo.");
			}
		}

		/**
		 * Comprueba que la especie indicada sea válida para un héroe.
		 *
		 * @param tipoPersonaje Especie que se desea validar.
		 * @throws IllegalArgumentException si la especie no es válida.
		 */
		private void validarTipoPersonaje(String tipoPersonaje) {
			if (tipoPersonaje == null || tipoPersonaje.isBlank()) {
				throw new IllegalArgumentException("El tipo de personaje no puede ser nulo ni estar vacío.");
			}

			Heroes.seleccionarTipoHeroe(tipoPersonaje);
		}

		/**
		 * Establece el nombre del héroe.
		 *
		 * @param nombre Nombre del héroe.
		 * @return Este Builder para encadenar llamadas.
		 */
		public Builder nombre(String nombre) {
			validarNombre(nombre);
			this.nombre = nombre;
			return this;
		}

		/**
		 * Establece los puntos de vida del héroe.
		 *
		 * @param puntosDeVida Puntos de vida del héroe.
		 * @return Este Builder para encadenar llamadas.
		 */
		public Builder puntosDeVida(int puntosDeVida) {
			validarPuntosDeVida(puntosDeVida);
			this.puntosDeVida = puntosDeVida;
			return this;
		}

		/**
		 * Establece el nivel de resistencia del héroe.
		 *
		 * @param nivelDeResistencia Nivel de resistencia del héroe.
		 * @return Este Builder para encadenar llamadas.
		 */
		public Builder nivelDeResistencia(int nivelDeResistencia) {
			validarNivelDeResistencia(nivelDeResistencia);
			this.nivelDeResistencia = nivelDeResistencia;
			return this;
		}

		/**
		 * Establece la especie del héroe.
		 *
		 * @param tipoPersonaje Especie del héroe.
		 * @return Este Builder para encadenar llamadas.
		 */
		public Builder tipoPersonaje(String tipoPersonaje) {
			validarTipoPersonaje(tipoPersonaje);
			this.tipoPersonaje = tipoPersonaje;
			return this;
		}

		/**
		 * Devuelve el nombre configurado.
		 *
		 * @return Nombre del héroe.
		 */
		public String getNombre() {
			return nombre;
		}

		/**
		 * Establece el nombre mediante un setter.
		 *
		 * @param nombre Nombre del héroe.
		 */
		public void setNombre(String nombre) {
			validarNombre(nombre);
			this.nombre = nombre;
		}

		/**
		 * Devuelve los puntos de vida configurados.
		 *
		 * @return Puntos de vida.
		 */
		public int getPuntosDeVida() {
			return puntosDeVida;
		}

		/**
		 * Establece los puntos de vida mediante un setter.
		 *
		 * @param puntosDeVida Puntos de vida del héroe.
		 */
		public void setPuntosDeVida(int puntosDeVida) {
			validarPuntosDeVida(puntosDeVida);
			this.puntosDeVida = puntosDeVida;
		}

		/**
		 * Devuelve el nivel de resistencia configurado.
		 *
		 * @return Nivel de resistencia.
		 */
		public int getNivelDeResistencia() {
			return nivelDeResistencia;
		}

		/**
		 * Establece el nivel de resistencia mediante un setter.
		 *
		 * @param nivelDeResistencia Nivel de resistencia del héroe.
		 */
		public void setNivelDeResistencia(int nivelDeResistencia) {
			validarNivelDeResistencia(nivelDeResistencia);
			this.nivelDeResistencia = nivelDeResistencia;
		}

		/**
		 * Devuelve la especie configurada.
		 *
		 * @return Especie del héroe.
		 */
		public String getTipoPersonaje() {
			return tipoPersonaje;
		}

		/**
		 * Establece la especie mediante un setter.
		 *
		 * @param tipoPersonaje Especie del héroe.
		 */
		public void setTipoPersonaje(String tipoPersonaje) {
			validarTipoPersonaje(tipoPersonaje);
			this.tipoPersonaje = tipoPersonaje;
		}

		/**
		 * Comprueba los datos y construye el héroe.
		 *
		 * @return Nuevo objeto Heroe.
		 * @throws IllegalArgumentException si alguno de los datos no es válido.
		 */
		public Heroe build() {
			validarNombre(nombre);
			validarPuntosDeVida(puntosDeVida);
			validarNivelDeResistencia(nivelDeResistencia);
			validarTipoPersonaje(tipoPersonaje);

			return new Heroe(this);
		}
	}
}