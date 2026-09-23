package Personajes;

import Tipos.tipoPersonajes;

public abstract class Bestias extends Personaje {

	private final tipoPersonajes[] TIPOS_DE_BESTIAS = { tipoPersonajes.ORCOS, tipoPersonajes.TRASGOS };

	Bestias(Personaje.Builder builder) {
		super(builder);
	}

	public tipoPersonajes[] getTIPOS_DE_BESTIAS() {
		return TIPOS_DE_BESTIAS;
	}

	public tipoPersonajes seleccionarTipoBestia(String bestiaSeleccionada) throws IllegalArgumentException {
		if (bestiaSeleccionada == null) {
			throw new IllegalArgumentException("El nombre de la bestia no puede ser nulo.");
		}

		if (bestiaSeleccionada.equalsIgnoreCase("Orco")) {
			return getTIPOS_DE_BESTIAS()[0];
		} else if (bestiaSeleccionada.equalsIgnoreCase("Trasgo")) {
			return getTIPOS_DE_BESTIAS()[1];
		}  else {
			throw new IllegalArgumentException("Tipo de bestia no válido: " + bestiaSeleccionada + "\n"
					+ "Recuerda los tipos de bestia a elegir son: " + tipoPersonajes.especiesValidasBestias(1));
		}
	}

	@Override
	public abstract int atacar(Personaje enemigo);
}
