package Personajes;

import Tipos.tipoPersonajes;

public class Heroe extends Heroes {

	tipoPersonajes tipoPersonaje;

	public Heroe(Personaje.Builder builder) {
		super(builder);
		this.tipoPersonaje = seleccionarTipoHeroe(builder.tipoPersonaje);
	}

	public tipoPersonajes getTipoPersonaje() {
		return tipoPersonaje;
	}

	@Override
	public int atacar(Personaje enemigo) {		
		int valorFinal;

		int primTirada = (int) (Math.random() * 101);
		int secTirada = (int) (Math.random() * 101);

		int mayorTirada = Math.max(primTirada, secTirada);

		valorFinal = tipoPersonaje.aplicacionAfectacion(enemigo, mayorTirada);

		return valorFinal;
	}
	
}
