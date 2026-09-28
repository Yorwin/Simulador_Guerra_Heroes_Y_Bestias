package Excepciones;

/**
 * Excepción comprobada que se lanza cuando un recluta no cumple las reglas de
 * validez necesarias para ser añadido al ejército (por ejemplo, nombre nulo,
 * puntos de vida o resistencia no positivos, o tipo de ejército sin indicar).
 * <p>
 * Al ser una excepción comprobada, quien invoque un método que la declare
 * estará obligado a capturarla o a propagarla con {@code throws}.
 */
public class ReclutaNoValidoException extends Exception {

	/**
	 * Identificador de versión para la serialización de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva excepción con un mensaje que describe el motivo por el que el
	 * recluta no es válido.
	 *
	 * @param mensaje descripción del error de validación, indicando el atributo
	 *                incorrecto.
	 */
	public ReclutaNoValidoException(String mensaje) {
		super(mensaje);
	}
}