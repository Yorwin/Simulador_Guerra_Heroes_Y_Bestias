package Guerra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Guerra.Guerra.Combate;

/**
 * Almacena y organiza los combates realizados durante una guerra.
 * <p>
 * Los combates se agrupan según el turno en el que se producen. Cada turno
 * puede contener una lista con los diferentes combates realizados durante dicho
 * turno.
 * </p>
 */
public class RegistroCombates {
	/**
	 * Mapa que relaciona cada número de turno con los combates realizados durante
	 * dicho turno.
	 */
	private Map<Integer, List<Combate>> combates;

	/**
	 * Estado de la guerra
	 */
	private boolean guerraFinalizada = false;

	/**
	 * Crea un registro de combates vacío.
	 */
	public RegistroCombates() {
		this.combates = new HashMap<Integer, List<Combate>>();
	}

	/**
	 * Registra un combate dentro del turno correspondiente.
	 * <p>
	 * Si el turno todavía no tiene ningún combate registrado, se crea una nueva
	 * lista para almacenar los combates de ese turno.
	 * </p>
	 *
	 * @param turno   número del turno en el que se produce el combate.
	 * @param combate combate que se desea registrar.
	 */
	public void registrarCombate(int turno, Combate combate) {
		combates.computeIfAbsent(turno, t -> new ArrayList<>()).add(combate);
	}

	/**
	 * Devuelve el registro de combates obtenido.
	 *
	 * @return Devuelve los combates realizados durante la guerra.
	 */
	public Map<Integer, List<Combate>> getCombates() {
		return combates;
	}

	/**
	 * Indica si la guerra ha llegado a su fin.
	 *
	 * @return {@code true} si la guerra está finalizada; {@code false} en caso
	 *         contrario.
	 */
	public boolean isGuerraFinalizada() {
		return guerraFinalizada;
	}

	/**
	 * Establece el estado de finalización de la guerra.
	 *
	 * @param guerraFinalizada {@code true} para marcar la guerra como concluida,
	 *                         {@code false} para mantenerla activa.
	 */
	public void setGuerraFinalizada(boolean guerraFinalizada) {
		this.guerraFinalizada = guerraFinalizada;
	}
}
