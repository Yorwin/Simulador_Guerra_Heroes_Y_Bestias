package Personajes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import TiposYInterfaces.tipoEjercito;
import TiposYInterfaces.tipoPersonajes;

@DisplayName("Comprobación de la clase Heroe")
public class HeroeTest {

	static Heroe heroe;
	static Bestia bestia;

	@BeforeEach
	void configuracionObjetos() {

		heroe = (Heroe) new Personaje.Builder().nombre("Boromir").puntosDeVida(130).nivelResistencia(55)
				.tipoPersonaje("Humano").tipoRecluta(tipoEjercito.HEROES).build();

		bestia = (Bestia) new Personaje.Builder().nombre("Gorbag").puntosDeVida(220).nivelResistencia(45)
				.tipoPersonaje("Trasgo").tipoRecluta(tipoEjercito.BESTIAS).build();
	}

	@Tag("validacion-personajes")
	@DisplayName("Validar Nombre Héroe")
	@Test
	void validarNombreHeroeTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Personaje.Builder().nombre("")
				.puntosDeVida(100).nivelResistencia(35).tipoPersonaje("Elfo").tipoRecluta(tipoEjercito.HEROES).build());
	}

	@Tag("validacion-personajes")
	@DisplayName("Validar Puntos de Vida Héroe")
	@Test
	void validarPuntosDeVidaHeroeTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Personaje.Builder().nombre("Legolas")
				.puntosDeVida(0).nivelResistencia(35).tipoPersonaje("Elfo").tipoRecluta(tipoEjercito.HEROES).build());
	}

	@Tag("validacion-personajes")
	@DisplayName("Validar Nivel de Resistencia Héroe")
	@Test
	void validarNivelResistenciaHeroeTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Personaje.Builder().nombre("Legolas")
				.puntosDeVida(100).nivelResistencia(0).tipoPersonaje("Elfo").tipoRecluta(tipoEjercito.HEROES).build());
	}

	@Tag("validacion-personajes")
	@DisplayName("Validar Tipo de Personaje Héroe")
	@Test
	void validarTipoPersonajeHeroeTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Personaje.Builder().nombre("Legolas")
				.puntosDeVida(100).nivelResistencia(35).tipoPersonaje("Orco").tipoRecluta(tipoEjercito.HEROES).build());
	}

	@Tag("validacion-personajes")
	@DisplayName("Validar Tipo de Personaje Héroe Vacío")
	@Test
	void validarTipoPersonajeHeroeVacioTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Personaje.Builder().nombre("Legolas")
				.puntosDeVida(100).nivelResistencia(35).tipoPersonaje("").tipoRecluta(tipoEjercito.HEROES).build());
	}

	@Tag("validacion-personajes")
	@DisplayName("Validar Tipo de Ejército Héroe")
	@Test
	void validarTipoEjercitoHeroeTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Personaje.Builder().nombre("Legolas")
				.puntosDeVida(100).nivelResistencia(35).tipoPersonaje("Elfo").tipoRecluta(null).build());
	}

	@Test
	@Tag("creacion-personajes")
	@DisplayName("Creación correcta de Héroe")
	void creacionHeroeTest() {
		assertAll(() -> assertEquals("Boromir", heroe.getNombre()), () -> assertEquals(130, heroe.getPuntosDeVida()),
				() -> assertEquals(55, heroe.getNivelResistencia()),
				() -> assertEquals(tipoPersonajes.HUMANOS, heroe.getTipoPersonaje()),
				() -> assertEquals(tipoEjercito.HEROES, heroe.getTipoRecluta()));
	}

	@Test
	@Tag("interaccion-combate")
	@DisplayName("Ataque de Héroe")
	void realizarAtaqueTest() {
		assertNotNull(heroe.atacar(bestia));
	}

	@Test
	@Tag("seleccion-personaje")
	@DisplayName("Selección de tipo de héroe")
	void seleccionarHeroeTest() {
		assertThrows(IllegalArgumentException.class, () -> heroe.seleccionarTipoHeroe("mago"));
		assertEquals(tipoPersonajes.ELFOS, heroe.seleccionarTipoHeroe("elfo"));
	}
}
