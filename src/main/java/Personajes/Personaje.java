package Personajes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import TiposYInterfaces.tipoEjercito;

/**
 * Clase abstracta que representa a un personaje/recluta dentro del juego.
 * Proporciona la estructura base y el patrón Builder para la creación de
 * personajes.
 */
public abstract class Personaje {

	/**
	 * Nombre del recluta.
	 */
	protected final String nombre;

	/**
	 * Patrón del Nombre
	 */
	private static Pattern patron = Pattern.compile("^(?=.{2,30}$)[\\p{L}\\p{M}]+(?:[ '’-][\\p{L}\\p{M}]+)*$");

	/**
	 * Puntos de vida actuales del recluta.
	 */
	protected int puntosDeVida;

	/**
	 * Nivel de resistencia o armadura del recluta.
	 */
	protected final int nivelResistencia;

	/**
	 * Tipo de ejército al que pertenece el recluta.
	 */
	protected final tipoEjercito tipoRecluta;

	/**
	 * Constructor protegido que inicializa un personaje utilizando su Builder.
	 * 
	 * @param builder Instancia del Builder con los datos del personaje a construir.
	 */
	public Personaje(Builder builder) {
		this.nombre = builder.nombre;
		this.puntosDeVida = builder.puntosDeVida;
		this.nivelResistencia = builder.nivelResistencia;
		this.tipoRecluta = builder.tipoRecluta;
	}

	/**
	 * Obtiene el nombre del recluta.
	 * 
	 * @return Nombre del recluta.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene los puntos de vida actuales del recluta.
	 * 
	 * @return Puntos de vida actuales.
	 */
	public int getPuntosDeVida() {
		return puntosDeVida;
	}

	/**
	 * Actualiza los puntos de vida del recluta.
	 * 
	 * @param puntosDeVida Nuevo valor de puntos de vida.
	 */
	public void setPuntosDeVida(int puntosDeVida) {
		this.puntosDeVida = puntosDeVida;
	}

	/**
	 * Obtiene el nivel de resistencia o armadura del recluta.
	 * 
	 * @return Nivel de resistencia.
	 */
	public int getNivelResistencia() {
		return nivelResistencia;
	}

	/**
	 * Obtiene el tipo de ejército al que pertenece el recluta.
	 * 
	 * @return Tipo de ejército del recluta.
	 */
	public tipoEjercito getTipoRecluta() {
		return this.tipoRecluta;
	}

	/**
	 * Método responsable de interpretar el daño recibido por el oponente.
	 * 
	 * @param danoOponente - Va recibido tras la tirada del oponente.
	 */
	public void interpretarDano(int danoOponente) {
		int danoRealRecibido = danoOponente - getNivelResistencia();

		if (danoRealRecibido > 0) {
			setPuntosDeVida(getPuntosDeVida() - danoRealRecibido);
		}
	}

	/**
	 * Realiza un ataque contra el personaje enemigo.
	 *
	 * @param enemigo personaje que recibe el ataque.
	 * @return cantidad de daño que se realiza al enemigo.
	 */
	public abstract int atacar(Personaje enemigo);

	/**
	 * Obtiene el tipo de ejército al que pertenece el personaje.
	 *
	 * @return tipo de ejército del personaje.
	 */
	public tipoEjercito getTipoEjercito() {
		return tipoRecluta;
	};

	/**
	 * Clase estática interna para la construcción paso a paso de objetos
	 * {@link Personaje}.
	 */
	public static class Builder {

		/** Nombre a asignar al personaje. */
		String nombre;

		/** Puntos de vida iniciales a asignar al personaje. */
		int puntosDeVida;

		/** Nivel de resistencia a asignar al personaje. */
		int nivelResistencia;

		/** Tipo de ejército a asignar al personaje. */
		tipoEjercito tipoRecluta;

		/** Tipo o clase específica de personaje a instanciar. */
		String tipoPersonaje;

		/**
		 * Define el nombre del personaje, validándolo previamente con la expresión
		 * regular de nombres.
		 *
		 * @param nombre Nombre del personaje. Debe tener entre 2 y 30 caracteres y
		 *               contener solo letras, con espacios, guiones o apóstrofos entre
		 *               palabras.
		 * @return La propia instancia del Builder para encadenamiento.
		 * @throws IllegalArgumentException si el nombre no cumple el formato permitido.
		 */
		public Builder nombre(String nombre) throws IllegalArgumentException {

			Matcher matcherNombre = patron.matcher(nombre);

			if (nombre != null && matcherNombre.matches()) {
				this.nombre = nombre;
				return this;
			} else {
				throw new IllegalArgumentException(
						"Nombre no válido: usa solo letras (2-30 caracteres), con espacios, guiones o apóstrofos entre palabras.");
			}
		}

		/**
		 * Define los puntos de vida del personaje.
		 * 
		 * @param puntosDeVida Puntos de vida del personaje.
		 * @return La propia instancia del Builder para encadenamiento.
		 */
		public Builder puntosDeVida(int puntosDeVida) {
			this.puntosDeVida = puntosDeVida;
			return this;
		}

		/**
		 * Define el nivel de resistencia del personaje.
		 * 
		 * @param nivelResistencia Nivel de resistencia.
		 * @return La propia instancia del Builder para encadenamiento.
		 */
		public Builder nivelResistencia(int nivelResistencia) {
			this.nivelResistencia = nivelResistencia;
			return this;
		}

		/**
		 * Define el tipo de ejército al que pertenecerá el personaje.
		 * 
		 * @param tipoRecluta Tipo de ejército.
		 * @return La propia instancia del Builder para encadenamiento.
		 */
		public Builder tipoRecluta(tipoEjercito tipoRecluta) {
			this.tipoRecluta = tipoRecluta;
			return this;
		}

		/**
		 * Define la clase o subtipo específico del personaje.
		 * 
		 * @param tipoPersonaje Nombre del tipo de personaje (ej. "Elfo", "Orco").
		 * @return La propia instancia del Builder para encadenamiento.
		 */
		public Builder tipoPersonaje(String tipoPersonaje) {
			this.tipoPersonaje = tipoPersonaje;
			return this;
		}

		/**
		 * Construye y devuelve una instancia concreta de {@link Personaje} a través de
		 * la factoría.
		 * 
		 * @return Nueva instancia de {@link Personaje}.
		 */
		public Personaje build() {
			return PersonajeFactory.crear(this);
		}
	}
}