package Personajes;

public final class PersonajeFactory {
	private PersonajeFactory() {} 
	
	public static Personaje crear(Personaje.Builder builder) {
		return switch (builder.tipoRecluta) {
			case HEROES -> new Heroe(builder);
			case BESTIAS -> new Bestia(builder);
			default -> throw new IllegalArgumentException("Tipo de ejercito no soportado: " + builder.tipoRecluta);
		};
	}
}
