package Personajes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

	// --------------------------------------------------
	// TESTS DEL CONSTRUCTOR GENERAL: Personaje.Builder
	// --------------------------------------------------

	@Test
	@DisplayName("Rechazar nombre vacío")
	void validarNombreVacioHeroeTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("").puntosDeVida(130).nivelResistencia(55).tipoPersonaje("Humano")
					.tipoRecluta(tipoEjercito.HEROES).build();
		});
	}

	@Test
	@DisplayName("Rechazar puntos de vida iguales a cero")
	void validarPuntosDeVidaCeroHeroeTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("Boromir").puntosDeVida(0).nivelResistencia(55).tipoPersonaje("Humano")
					.tipoRecluta(tipoEjercito.HEROES).build();
		});
	}

	@Test
	@DisplayName("Rechazar resistencia negativa")
	void validarNivelResistenciaNegativoHeroeTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("Boromir").puntosDeVida(130).nivelResistencia(-1).tipoPersonaje("Humano")
					.tipoRecluta(tipoEjercito.HEROES).build();
		});
	}

	@Test
	@DisplayName("Permitir resistencia igual a cero")
	void permitirNivelResistenciaCeroHeroeTest() {

		Heroe heroeCeroResistencia = (Heroe) new Personaje.Builder().nombre("Boromir").puntosDeVida(130)
				.nivelResistencia(0).tipoPersonaje("Humano").tipoRecluta(tipoEjercito.HEROES).build();

		assertEquals(0, heroeCeroResistencia.getNivelResistencia());
	}

	@Test
	@DisplayName("Rechazar especie de bestia en un héroe")
	void validarTipoPersonajeInvalidoHeroeTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("Boromir").puntosDeVida(130).nivelResistencia(55).tipoPersonaje("Orco")
					.tipoRecluta(tipoEjercito.HEROES).build();
		});
	}

	@Test
	@DisplayName("Rechazar especie vacía")
	void validarTipoPersonajeVacioHeroeTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("Boromir").puntosDeVida(130).nivelResistencia(55).tipoPersonaje("")
					.tipoRecluta(tipoEjercito.HEROES).build();
		});
	}

	@Test
	@DisplayName("Rechazar ejército nulo")
	void validarTipoEjercitoNuloHeroeTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("Boromir").puntosDeVida(130).nivelResistencia(55).tipoPersonaje("Humano")
					.tipoRecluta(null).build();
		});
	}

	// --------------------------------------------------
	// TESTS DEL NOMBRE: Heroe.Builder
	// --------------------------------------------------

	@Test
	@DisplayName("Rechazar nombre vacío en Heroe.Builder")
	void validarNombreVacioHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().nombre("");
		});
	}

	@Test
	@DisplayName("Rechazar nombre nulo en Heroe.Builder")
	void validarNombreNuloHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().nombre(null);
		});
	}

	@Test
	@DisplayName("Rechazar nombre de un solo carácter")
	void validarNombreUnCaracterHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().nombre("A");
		});
	}

	@Test
	@DisplayName("Rechazar nombre con números")
	void validarNombreConNumerosHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().nombre("Legolas123");
		});
	}

	@Test
	@DisplayName("Rechazar espacios al principio y al final")
	void validarEspaciosExternosNombreHeroeBuilderTest() {

		assertAll(() -> assertThrows(IllegalArgumentException.class, () -> new Heroe.Builder().nombre(" Legolas")),
				() -> assertThrows(IllegalArgumentException.class, () -> new Heroe.Builder().nombre("Legolas ")));
	}

	@Test
	@DisplayName("Rechazar separadores consecutivos")
	void validarSeparadoresConsecutivosHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().nombre("Jean--Luc");
		});
	}

	@Test
	@DisplayName("Rechazar nombre superior a treinta caracteres")
	void validarNombreDemasiadoLargoHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().nombre("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDE");
		});
	}

	@Test
	@DisplayName("Aceptar nombre con guion")
	void validarNombreConGuionHeroeBuilderTest() {

		Heroe heroeGuion = new Heroe.Builder().nombre("Jean-Luc").puntosDeVida(100).nivelDeResistencia(35)
				.tipoPersonaje("Humano").build();

		assertEquals("Jean-Luc", heroeGuion.getNombre());
	}

	// --------------------------------------------------
	// TESTS DE PUNTOS DE VIDA: Heroe.Builder
	// --------------------------------------------------

	@Test
	@DisplayName("Rechazar puntos de vida negativos")
	void validarPuntosDeVidaNegativosHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().puntosDeVida(-1);
		});
	}

	@Test
	@DisplayName("Rechazar puntos de vida iguales a cero")
	void validarPuntosDeVidaCeroHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().puntosDeVida(0);
		});
	}

	// --------------------------------------------------
	// TESTS DE RESISTENCIA: Heroe.Builder
	// --------------------------------------------------

	@Test
	@DisplayName("Rechazar resistencia negativa")
	void validarResistenciaNegativaHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().nivelDeResistencia(-1);
		});
	}

	@Test
	@DisplayName("Permitir resistencia igual a cero")
	void permitirResistenciaCeroHeroeBuilderTest() {

		Heroe heroeCeroResistencia = new Heroe.Builder().nombre("Legolas").puntosDeVida(100).nivelDeResistencia(0)
				.tipoPersonaje("Elfo").build();

		assertEquals(0, heroeCeroResistencia.getNivelResistencia());
	}

	// --------------------------------------------------
	// TESTS DE ESPECIE: Heroe.Builder
	// --------------------------------------------------

	@Test
	@DisplayName("Rechazar especie Orco en Heroe.Builder")
	void validarEspecieOrcoHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().tipoPersonaje("Orco");
		});
	}

	@Test
	@DisplayName("Rechazar especie Trasgo en Heroe.Builder")
	void validarEspecieTrasgoHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().tipoPersonaje("Trasgo");
		});
	}

	@Test
	@DisplayName("Rechazar especie vacía en Heroe.Builder")
	void validarEspecieVaciaHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().tipoPersonaje("");
		});
	}

	@Test
	@DisplayName("Rechazar especie nula en Heroe.Builder")
	void validarEspecieNulaHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().tipoPersonaje(null);
		});
	}

	// --------------------------------------------------
	// TESTS DE BUILD: Heroe.Builder
	// --------------------------------------------------

	@Test
	@DisplayName("Rechazar construcción sin datos")
	void validarBuildSinDatosHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().build();
		});
	}

	@Test
	@DisplayName("Rechazar construcción sin puntos de vida")
	void validarBuildSinPuntosDeVidaHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().nombre("Legolas").nivelDeResistencia(35).tipoPersonaje("Elfo").build();
		});
	}

	@Test
	@DisplayName("Rechazar construcción sin especie")
	void validarBuildSinEspecieHeroeBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Heroe.Builder().nombre("Legolas").puntosDeVida(100).nivelDeResistencia(35).build();
		});
	}

	// --------------------------------------------------
	// TESTS DE GETTERS Y SETTERS: Heroe.Builder
	// --------------------------------------------------

	@Test
	@DisplayName("Comprobar getters y setters de Heroe.Builder")
	void gettersSettersHeroeBuilderTest() {

		Heroe.Builder builder = new Heroe.Builder();

		builder.setNombre("Legolas");
		builder.setPuntosDeVida(100);
		builder.setNivelDeResistencia(35);
		builder.setTipoPersonaje("Elfo");

		assertAll(() -> assertEquals("Legolas", builder.getNombre()),
				() -> assertEquals(100, builder.getPuntosDeVida()),
				() -> assertEquals(35, builder.getNivelDeResistencia()),
				() -> assertEquals("Elfo", builder.getTipoPersonaje()));
	}

	@Test
	@DisplayName("Rechazar valores inválidos mediante los setters")
	void validarSettersHeroeBuilderTest() {

		Heroe.Builder builder = new Heroe.Builder();

		assertAll(() -> assertThrows(IllegalArgumentException.class, () -> builder.setNombre(null)),
				() -> assertThrows(IllegalArgumentException.class, () -> builder.setPuntosDeVida(0)),
				() -> assertThrows(IllegalArgumentException.class, () -> builder.setNivelDeResistencia(-1)),
				() -> assertThrows(IllegalArgumentException.class, () -> builder.setTipoPersonaje("Orco")));
	}

	// --------------------------------------------------
	// TESTS DE ENCADENAMIENTO: Heroe.Builder
	// --------------------------------------------------

	@Test
	@DisplayName("Permitir encadenar métodos de Heroe.Builder")
	void encadenamientoHeroeBuilderTest() {

		Heroe.Builder builder = new Heroe.Builder();

		assertAll(() -> assertSame(builder, builder.nombre("Legolas")),
				() -> assertSame(builder, builder.puntosDeVida(100)),
				() -> assertSame(builder, builder.nivelDeResistencia(35)),
				() -> assertSame(builder, builder.tipoPersonaje("Elfo")));
	}

	// --------------------------------------------------
	// TESTS DE CREACIÓN: Heroe
	// --------------------------------------------------

	@Test
	@DisplayName("Crear héroe correctamente mediante Personaje.Builder")
	void crearHeroeCorrectamentePersonajeBuilderTest() {

		Heroe heroeCreado = (Heroe) new Personaje.Builder().nombre("Boromir").puntosDeVida(130).nivelResistencia(55)
				.tipoPersonaje("Humano").tipoRecluta(tipoEjercito.HEROES).build();

		assertAll(() -> assertEquals("Boromir", heroeCreado.getNombre()),
				() -> assertEquals(130, heroeCreado.getPuntosDeVida()),
				() -> assertEquals(55, heroeCreado.getNivelResistencia()),
				() -> assertEquals(tipoPersonajes.HUMANOS, heroeCreado.getTipoPersonaje()),
				() -> assertEquals(tipoEjercito.HEROES, heroeCreado.getTipoRecluta()));
	}

	@Test
	@DisplayName("Crear héroe correctamente mediante Heroe.Builder")
	void crearHeroeCorrectamenteHeroeBuilderTest() {

		Heroe heroeCreado = new Heroe.Builder().nombre("Legolas").puntosDeVida(100).nivelDeResistencia(35)
				.tipoPersonaje("Elfo").build();

		assertAll(() -> assertEquals("Legolas", heroeCreado.getNombre()),
				() -> assertEquals(100, heroeCreado.getPuntosDeVida()),
				() -> assertEquals(35, heroeCreado.getNivelResistencia()),
				() -> assertEquals(tipoPersonajes.ELFOS, heroeCreado.getTipoPersonaje()),
				() -> assertEquals(tipoEjercito.HEROES, heroeCreado.getTipoRecluta()));
	}

	// --------------------------------------------------
	// TEST DE ATAQUE
	// --------------------------------------------------

	@Test
	@DisplayName("Comprobar que un héroe puede atacar")
	void atacarBestiaTest() {
		assertNotNull(heroe.atacar(bestia));
	}
}