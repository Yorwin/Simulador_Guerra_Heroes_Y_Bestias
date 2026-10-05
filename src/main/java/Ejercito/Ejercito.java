package Ejercito;

import java.util.ArrayList;
import java.util.logging.Logger;

import Excepciones.ReclutaNoValidoException;
import Personajes.Personaje;

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

		try {
			validarRecluta(recluta);
		} catch (ReclutaNoValidoException e) {
			LOGGER.warning(e.getMessage());
		}
		reclutas.add(recluta);
	}

	/**
	 * Valida que un recluta tenga todos sus atributos correctamente informados
	 * antes de ser añadido al ejército.
	 * <p>
	 * Comprueba, por este orden, que:
	 * <ul>
	 * <li>el nombre no sea nulo,</li>
	 * <li>los puntos de vida sean mayores que 0,</li>
	 * <li>el nivel de resistencia sea mayor que 0,</li>
	 * <li>la especie (tipo de recluta) esté indicada,</li>
	 * <li>el tipo de ejército esté indicado.</li>
	 * </ul>
	 * La validación se detiene en el primer atributo incorrecto.
	 * 
	 * @param recluta recluta que se desea validar.
	 * @throws IllegalArgumentException si alguno de los atributos del recluta es
	 *                                  nulo o no cumple las reglas de validez, con
	 *                                  un mensaje que indica el atributo
	 *                                  incorrecto.
	 */
	private void validarRecluta(T recluta) throws ReclutaNoValidoException {

		Personaje personaje = (Personaje) recluta;

		if (personaje.getNombre() == null || personaje.getNombre().length() < 1) {
			throw new ReclutaNoValidoException("Es necesario que indiques el nombre de tu personaje");
		}

		if (personaje.getPuntosDeVida() <= 0) {
			throw new ReclutaNoValidoException("Los puntos de vida deben ser mayores que 0");
		}

		if (personaje.getNivelResistencia() <= 0) {
			throw new ReclutaNoValidoException("La resistencia del personaje debe ser mayor que 0");
		}

		if (personaje.getTipoRecluta() == null) {
			throw new ReclutaNoValidoException("La especie de tu recluta debe estar indicada");
		}

		if (personaje.getTipoEjercito() == null) {
			throw new ReclutaNoValidoException(
					"El tipo de ejercito debe estar indicado y debe corresponder a los tipos disponibles");
		}
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
