package Ejercito;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Representa un ejercito compuesto por un conjunto de reclutas de tipo genérico
 * {@code T}.
 * <p>
 * Cada ejercito tiene un {@link TiposYInterfaces.tipoEjercito} asociado, y solo
 * se permite añadir reclutas cuyo tipo de ejercito coincida con el del ejercito
 * actual.
 *
 * @param <T> tipo de los reclutas que forman parte del ejercito (normalmente
 *            una subclase o implementación de {@link Personajes.Personaje})
 */
public class Ejercito<T> {

	/**
	 * Logger para registrar eventos y advertencias de la clase Ejercito.
	 */
	private static final Logger LOGGER = Logger.getLogger(Ejercito.class.getName());

	/** Lista de reclutas que forman parte del ejercito. */
	private ArrayList<T> reclutas;

	/**
	 * Crea un nuevo ejercito con la lista de reclutas y el tipo indicados.
	 */
	public Ejercito() {
		this.reclutas = new ArrayList<T>();
	}

	/**
	 * Devuelve la lista de reclutas del ejercito.
	 *
	 * @return lista de reclutas
	 */
	public ArrayList<T> getReclutas() {
		return reclutas;
	}

	/**
	 * Establece una nueva lista de reclutas para el ejercito.
	 *
	 * @param reclutas nueva lista de reclutas
	 */
	public void setReclutas(ArrayList<T> reclutas) {
		this.reclutas = reclutas;
	}

	/**
	 * Añade un nuevo recluta al ejercito, siempre que su tipo de ejercito coincida
	 * con el tipo de este ejercito.
	 *
	 * @param recluta recluta que se desea añadir
	 * @throws IllegalArgumentException si el recluta es nulo
	 */
	public void anadirRecluta(T recluta) {
		if (recluta == null) {
			LOGGER.warning("Intento de añadir un recluta nulo al ejército.");
			throw new IllegalArgumentException("El recluta no puede ser nulo.");
		}

		reclutas.add(recluta);
	}

	/**
	 * Retira (elimina) un recluta del ejercito.
	 *
	 * @param recluta recluta que se desea retirar
	 */
	public boolean retirarRecluta(T recluta) {
		if (recluta == null) {
			LOGGER.warning("Intento de remover un recluta nulo del ejército.");
			throw new IllegalArgumentException("El recluta no puede ser nulo.");
		}

		return reclutas.remove(recluta);
	}

	/**
	 * Cambia la posición de un recluta dentro de la lista, moviéndolo desde su
	 * índice actual a una nueva posición.
	 *
	 * @param indiceActual  índice actual del recluta dentro de la lista
	 * @param nuevaPosicion nueva posición que ocupará el recluta
	 * @throws IndexOutOfBoundsException si alguno de los índices está fuera del
	 *                                   rango válido de la lista de reclutas
	 */
	public void cambiarOrden(int indiceActual, int nuevaPosicion) {
		if (indiceActual < 0 || indiceActual >= reclutas.size() || nuevaPosicion < 0
				|| nuevaPosicion >= reclutas.size()) {

			throw new IndexOutOfBoundsException("Se ha intentado acceder a un valor de la lista que no existe");
		}

		T personaje = reclutas.remove(indiceActual);
		reclutas.add(nuevaPosicion, personaje);
	}

	/**
	 * Devuelve el número de reclutas que forman parte del ejercito.
	 *
	 * @return cantidad de reclutas
	 */
	public int length() {
		return reclutas.size();
	}

	/**
	 * Crea y devuelve una copia superficial (shallow copy) de este ejército.
	 * <p>
	 * El nuevo ejército contendrá las mismas referencias a las unidades o reclutas
	 * que el ejército original, conservando su orden.
	 * </p>
	 *
	 * @return una nueva instancia de {@code Ejercito<T>} con los mismos reclutas.
	 */
	public Ejercito<T> copiar() {
		Ejercito<T> copiaEjercito = new Ejercito<T>();
		ArrayList<T> reclutasEjercitoActual = getReclutas();

		for (T recluta : reclutasEjercitoActual) {
			copiaEjercito.anadirRecluta(recluta);
		}

		return copiaEjercito;
	}
}
