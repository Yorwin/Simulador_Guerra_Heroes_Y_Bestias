package Ejercito;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import Personajes.Heroe;
import Personajes.Personaje;
import TiposYInterfaces.tipoEjercito;

class EjercitoTest {

	static Ejercito<Heroe> ejercito;
	static Heroe heroe;

	@BeforeEach
	void creacionEjercito() {
		ejercito = new Ejercito<Heroe>();
		heroe = (Heroe) new Personaje.Builder().nombre("Legolas").puntosDeVida(100).nivelResistencia(35)
				.tipoPersonaje("Elfo").tipoRecluta(tipoEjercito.HEROES).build();
	}

	@Tag("modificacion-ejercito")
	@DisplayName("Añadir nuevos reclutas")
	@Test
	void añadirReclutasTest() {
		int lengthAntesDeAnadir = ejercito.length();

		Assertions.assertThrows(IllegalArgumentException.class, () -> ejercito.anadirRecluta(null));

		ejercito.anadirRecluta(heroe);
		Assertions.assertTrue(ejercito.getReclutas().contains(heroe));

		Assertions.assertTrue(ejercito.length() > lengthAntesDeAnadir);
	}

	@Tag("modificacion-ejercito")
	@DisplayName("Retirar reclutas existentes")
	@Test
	void retirarReclutasTest() {
		ejercito.anadirRecluta(heroe);
		int lengthAntesDeRetirar = ejercito.length();

		Assertions.assertThrows(IllegalArgumentException.class, () -> ejercito.retirarRecluta(null));

		ejercito.retirarRecluta(heroe);
		Assertions.assertTrue(lengthAntesDeRetirar > ejercito.length());

		Assertions.assertFalse(ejercito.retirarRecluta(heroe));
	}

	@Tag("modificacion-ejercito")
	@DisplayName("Cambiar orden reclutas")
	@Test
	void cambiarOrdenReclutasTest() {
		Heroe segundoHeroe = (Heroe) new Personaje.Builder().nombre("Aragorn").puntosDeVida(150).nivelResistencia(50)
				.tipoPersonaje("Humano").tipoRecluta(tipoEjercito.HEROES).build();

		ejercito.anadirRecluta(heroe);
		ejercito.anadirRecluta(segundoHeroe);

		Assertions.assertEquals(ejercito.getReclutas().get(0), heroe);

		ejercito.cambiarOrden(0, 1);
		Assertions.assertNotEquals(ejercito.getReclutas().get(0), heroe);
		Assertions.assertEquals(ejercito.getReclutas().get(1), heroe);

		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> ejercito.cambiarOrden(0, 4));
	}

	@Tag("comprobacion-estado-ejercito")
	@DisplayName("Comprobar longitud ejercito")
	@Test
	void comprobarLengthTest() {
		ejercito.anadirRecluta(heroe);
		Assertions.assertEquals(1, ejercito.length());

		ejercito.retirarRecluta(heroe);
		Assertions.assertEquals(0, ejercito.length());
	}

	@Tag("comprobacion-estado-ejercito")
	@DisplayName("Obtener reclutas")
	@Test
	void obtenerReclutasTest() {
		ArrayList<Heroe> reclutasEjercitoHeroeObtenido = ejercito.getReclutas();
		Assertions.assertEquals(0, reclutasEjercitoHeroeObtenido.size());

		ejercito.anadirRecluta(heroe);
		reclutasEjercitoHeroeObtenido = ejercito.getReclutas();
		Assertions.assertNotEquals(0, reclutasEjercitoHeroeObtenido.size());

		Assertions.assertEquals(heroe, reclutasEjercitoHeroeObtenido.get(0));
	}

	@Tag("copiar-ejercito")
	@DisplayName("Realizar copia ejercito")
	@Test
	void copiaEjercitoTest() {
		Ejercito<Heroe> copiaEjercitoVacio = ejercito.copiar();
		ejercito.anadirRecluta(heroe);
		Ejercito<Heroe> copiaTrasAnadirUnHeroe = ejercito.copiar();

		Assertions.assertEquals(0, copiaEjercitoVacio.length());
		Assertions.assertTrue(copiaTrasAnadirUnHeroe.length() > 0);
	}
}
