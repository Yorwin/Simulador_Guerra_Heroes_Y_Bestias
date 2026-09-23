package Personajes;

import Tipos.tipoPersonajes;

public class Bestia extends Bestias {

	tipoPersonajes tipoPersonaje;
	
	public Bestia(Personaje.Builder builder) {
		super(builder);
		this.tipoPersonaje = seleccionarTipoBestia(builder.tipoPersonaje);
	}

	public tipoPersonajes getTipoPersonaje() {
		return tipoPersonaje;
	}

	@Override
	public int atacar(Personaje enemigo) {
		int valorFinal;

		int tirada = (int) (Math.random() * 91);
		valorFinal = tipoPersonaje.aplicacionAfectacion(enemigo, tirada);

		return valorFinal;
	}


}
