package Personajes;

import Tipos.tipoPersonajes;

public abstract class Heroes extends Personaje {
	private final tipoPersonajes[] TIPOS_DE_HEROES = { tipoPersonajes.HUMANOS, tipoPersonajes.ELFOS,
			tipoPersonajes.HOBBITS };

	Heroes(Personaje.Builder builder) {
		super(builder);
	}

	public tipoPersonajes[] getTIPOS_DE_HEROES() {
		return TIPOS_DE_HEROES;
	}

	public tipoPersonajes seleccionarTipoHeroe(String heroeSeleccionado) throws IllegalArgumentException {

		if (heroeSeleccionado == null) {
			throw new IllegalArgumentException("El nombre del héroe no puede ser nulo.");
		}

		if (heroeSeleccionado.equalsIgnoreCase("Humano")) {
			return getTIPOS_DE_HEROES()[0];
		} else if (heroeSeleccionado.equalsIgnoreCase("Elfo")) {
			return getTIPOS_DE_HEROES()[1];
		} else if (heroeSeleccionado.equalsIgnoreCase("Hobbit")) {
			return getTIPOS_DE_HEROES()[2];
		} else {
			throw new IllegalArgumentException("Tipo de héroe no válido: " + heroeSeleccionado + "\n" + "Recuerda los tipos de heroes a elegir son: " + tipoPersonajes.especiesValidasBestias(0));
		}
	}

	@Override
	public abstract int atacar(Personaje enemigo);
}
