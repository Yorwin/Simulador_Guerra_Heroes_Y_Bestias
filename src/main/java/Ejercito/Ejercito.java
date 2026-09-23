package Ejercito;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Logger;

import Tipos.tipoEjercito;
import Personajes.Personaje;

/**
 * Representa un ejercito compuesto por un conjunto de reclutas de tipo genérico
 * {@code T}.
 * <p>
 * Cada ejercito tiene un {@link Tipos.tipoEjercito} asociado, y solo se permite
 * añadir reclutas cuyo tipo de ejercito coincida con el del ejercito actual.
 *
 * @param <T> tipo de los reclutas que forman parte del ejercito (normalmente
 *            una subclase o implementación de {@link Personajes.Personaje})
 */

public class Ejercito<T> {

	private static final Logger LOGGER = Logger.getLogger(Ejercito.class.getName());

	/** Lista de reclutas que forman parte del ejercito. */
	private ArrayList<T> reclutas;

	/**
	 * Crea un nuevo ejercito con la lista de reclutas y el tipo indicados.
	 *
	 * @param reclutas     lista inicial de reclutas del ejercito
	 * @param tipoEjercito tipo de ejercito que se va a crear
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
	 * @throws Exception si el recluta no pertenece al mismo tipo de ejercito
	 */
	public void anadirRecluta(T recluta) {
		reclutas.add(recluta);
	}

	/**
	 * Retira (elimina) un recluta del ejercito.
	 *
	 * @param recluta recluta que se desea retirar
	 */
	public void retirarRecluta(T recluta) {
		reclutas.remove(recluta);
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
			LOGGER.warning("Índice fuera de rango al intentar cambiar el orden de un recluta");
			return;
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

	public Ejercito<T> copiar() {
		Ejercito<T> copiaEjercito = new Ejercito<T>();
		ArrayList<T> reclutasEjercitoActual = getReclutas();

		for (T recluta : reclutasEjercitoActual) {
			copiaEjercito.anadirRecluta(recluta);
		}

		return copiaEjercito;
	}
}
