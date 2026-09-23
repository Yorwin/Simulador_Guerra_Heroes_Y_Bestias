package Tipos;

public enum tipoEjercito {
	HEROES("HÉROES"), BESTIAS("BESTIAS");

	private final String nombre;

	tipoEjercito(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
