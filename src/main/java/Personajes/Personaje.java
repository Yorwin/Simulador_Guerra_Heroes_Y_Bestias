package Personajes;

import Tipos.tipoEjercito;

public abstract class Personaje {

	/**
	 * Nombre del recluta.
	 */
	protected final String nombre;

	/**
	 * Puntos de vida actuales del recluta.
	 */
	protected int puntosDeVida;

	/**
	 * Nivel de resistencia o armadura del recluta.
	 */
	protected final int nivelResistencia;

	/**
	 * Tipos de ejército válidos para clasificar a los reclutas.
	 */
	protected final tipoEjercito[] ejercitos = { tipoEjercito.HEROES, tipoEjercito.BESTIAS };

	/**
	 * Tipo de ejército al que pertenece el recluta.
	 */
	protected final tipoEjercito tipoRecluta;

	public Personaje(Builder builder) {
		this.nombre = builder.nombre;
		this.puntosDeVida = builder.puntosDeVida;
		this.nivelResistencia = builder.nivelResistencia;
		this.tipoRecluta = builder.tipoRecluta;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPuntosDeVida() {
		return puntosDeVida;
	}

	public void setPuntosDeVida(int puntosDeVida) {
		this.puntosDeVida = puntosDeVida;
	}

	public int getNivelResistencia() {
		return nivelResistencia;
	}

	public tipoEjercito[] getEjercitos() {
		return ejercitos;
	}

	public tipoEjercito getTipoRecluta() {
		return this.tipoRecluta;
	}

	/**
	 * Método responsable del interpretar el daño recibido por el oponente.
	 * 
	 * @param danoOponente - Va recibido tras la tirada del oponente.
	 * @return No devuelve un valor en sí, solamente modifica los puntos de vida del
	 *         personaje si corresponde.
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

	public static class Builder {
		String nombre;
		int puntosDeVida;
		int nivelResistencia;
		tipoEjercito tipoRecluta;
		String tipoPersonaje;

		public Builder nombre(String nombre) {
			this.nombre = nombre;
			return this;
		}

		public Builder puntosDeVida(int puntosDeVida) {
			this.puntosDeVida = puntosDeVida;
			return this;
		}

		public Builder nivelResistencia(int nivelResistencia) {
			this.nivelResistencia = nivelResistencia;
			return this;
		}

		public Builder tipoRecluta(tipoEjercito tipoRecluta) {
			this.tipoRecluta = tipoRecluta;
			return this;
		}

		public Builder tipoPersonaje(String tipoPersonaje) {
			this.tipoPersonaje = tipoPersonaje;
			return this;
		}

		public Personaje build() {
			return PersonajeFactory.crear(this);
		}
	}
}
