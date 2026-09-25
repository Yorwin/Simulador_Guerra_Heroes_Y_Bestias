package Personajes;

/**
 * Factoría encargada de crear instancias concretas de {@link Personaje} a
 * partir de un {@link Personaje.Builder}.
 * <p>
 * Se encarga de decidir, según el tipo de ejército indicado en el builder
 * ({@code tipoRecluta}), qué subclase concreta de {@link Personaje} debe
 * instanciarse (por ejemplo, {@link Heroe} o {@link Bestia}).
 * <p>
 * Es una clase de utilidad ({@code final}, con constructor privado) que no está
 * pensada para ser instanciada.
 */
public final class PersonajeFactory {

	/**
	 * Constructor privado para impedir la instanciación de esta clase de utilidad.
	 */
	private PersonajeFactory() {
	}

	/**
	 * Crea una instancia concreta de {@link Personaje} según el tipo de ejército
	 * indicado en el builder.
	 *
	 * @param builder builder de {@link Personaje} con los datos necesarios para
	 *                construir el personaje, incluyendo el tipo de ejército
	 *                ({@code tipoRecluta}) que determina la subclase a instanciar.
	 * @return una nueva instancia de {@link Heroe} si {@code tipoRecluta} es
	 *         {@code HEROES}, o de {@link Bestia} si es {@code BESTIAS}.
	 * @throws IllegalArgumentException si {@code builder.tipoRecluta} no
	 *                                  corresponde a ningún tipo de ejército
	 *                                  soportado.
	 */
	public static Personaje crear(Personaje.Builder builder) {
		return switch (builder.tipoRecluta) {
		case HEROES -> new Heroe(builder);
		case BESTIAS -> new Bestia(builder);
		default -> throw new IllegalArgumentException("Tipo de ejercito no soportado: " + builder.tipoRecluta);
		};
	}
}