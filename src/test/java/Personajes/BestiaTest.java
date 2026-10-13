package Personajes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import TiposYInterfaces.tipoEjercito;
import TiposYInterfaces.tipoPersonajes;

@DisplayName("Comprobación de la clase Bestia")
public class BestiaTest {

	static Bestia bestia;
	static Heroe heroe;

	@BeforeEach
	void configuracionObjetos() {

		bestia = (Bestia) new Personaje.Builder().nombre("Gorbag").puntosDeVida(220).nivelResistencia(45)
				.tipoPersonaje("Trasgo").tipoRecluta(tipoEjercito.BESTIAS).build();

		heroe = (Heroe) new Personaje.Builder().nombre("Boromir").puntosDeVida(130).nivelResistencia(55)
				.tipoPersonaje("Humano").tipoRecluta(tipoEjercito.HEROES).build();
	}

	// --------------------------------------------------
	// TESTS DEL CONSTRUCTOR GENERAL: Personaje.Builder
	// --------------------------------------------------

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar nombre vacío")
	void validarNombreVacioBestiaTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("").puntosDeVida(200).nivelResistencia(60).tipoPersonaje("Orco")
					.tipoRecluta(tipoEjercito.BESTIAS).build();
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar puntos de vida iguales a cero")
	void validarPuntosDeVidaCeroBestiaTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("Lurtz").puntosDeVida(0).nivelResistencia(60).tipoPersonaje("Orco")
					.tipoRecluta(tipoEjercito.BESTIAS).build();
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar resistencia negativa")
	void validarResistenciaNegativaBestiaTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("Lurtz").puntosDeVida(200).nivelResistencia(-1).tipoPersonaje("Orco")
					.tipoRecluta(tipoEjercito.BESTIAS).build();
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Permitir resistencia igual a cero")
	void permitirResistenciaCeroPersonajeBuilderBestiaTest() {

		Bestia bestiaCeroResistencia = (Bestia) new Personaje.Builder().nombre("Lurtz").puntosDeVida(200)
				.nivelResistencia(0).tipoPersonaje("Orco").tipoRecluta(tipoEjercito.BESTIAS).build();

		assertEquals(0, bestiaCeroResistencia.getNivelResistencia());
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar especie de héroe en una bestia")
	void validarTipoPersonajeInvalidoBestiaTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("Lurtz").puntosDeVida(200).nivelResistencia(60).tipoPersonaje("Humano")
					.tipoRecluta(tipoEjercito.BESTIAS).build();
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar especie vacía")
	void validarTipoPersonajeVacioBestiaTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("Lurtz").puntosDeVida(200).nivelResistencia(60).tipoPersonaje("")
					.tipoRecluta(tipoEjercito.BESTIAS).build();
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar ejército nulo")
	void validarTipoEjercitoNuloBestiaTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Personaje.Builder().nombre("Lurtz").puntosDeVida(200).nivelResistencia(60).tipoPersonaje("Orco")
					.tipoRecluta(null).build();
		});
	}

	// --------------------------------------------------
	// TESTS DEL NOMBRE: Bestia.Builder
	// --------------------------------------------------

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar nombre vacío en Bestia.Builder")
	void validarNombreVacioBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().nombre("");
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar nombre nulo en Bestia.Builder")
	void validarNombreNuloBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().nombre(null);
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar nombre de un carácter")
	void validarNombreUnCaracterBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().nombre("A");
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar nombre con números")
	void validarNombreConNumerosBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().nombre("Lurtz123");
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar espacios al principio y al final")
	void validarEspaciosExternosNombreBestiaBuilderTest() {

		assertAll(() -> assertThrows(IllegalArgumentException.class, () -> new Bestia.Builder().nombre(" Lurtz")),
				() -> assertThrows(IllegalArgumentException.class, () -> new Bestia.Builder().nombre("Lurtz ")));
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar separadores consecutivos")
	void validarSeparadoresConsecutivosBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().nombre("Lurtz--Gorbag");
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar nombre superior a treinta caracteres")
	void validarNombreDemasiadoLargoBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().nombre("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDE");
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Aceptar nombre con guion")
	void validarNombreConGuionBestiaBuilderTest() {

		Bestia bestiaConGuion = new Bestia.Builder().nombre("Lurtz-Gor").puntosDeVida(200).nivelDeResistencia(35)
				.tipoPersonaje("Orco").build();

		assertEquals("Lurtz-Gor", bestiaConGuion.getNombre());
	}

	// --------------------------------------------------
	// TESTS DE PUNTOS DE VIDA: Bestia.Builder
	// --------------------------------------------------

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar puntos de vida negativos")
	void validarPuntosDeVidaNegativosBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().puntosDeVida(-1);
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar puntos de vida iguales a cero")
	void validarPuntosDeVidaCeroBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().puntosDeVida(0);
		});
	}

	// --------------------------------------------------
	// TESTS DE RESISTENCIA: Bestia.Builder
	// --------------------------------------------------

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar resistencia negativa en Bestia.Builder")
	void validarResistenciaNegativaBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().nivelDeResistencia(-1);
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Permitir resistencia igual a cero en Bestia.Builder")
	void permitirResistenciaCeroBestiaBuilderTest() {

		Bestia bestiaCeroResistencia = new Bestia.Builder().nombre("Gorbag").puntosDeVida(220).nivelDeResistencia(0)
				.tipoPersonaje("Trasgo").build();

		assertEquals(0, bestiaCeroResistencia.getNivelResistencia());
	}

	// --------------------------------------------------
	// TESTS DE ESPECIE: Bestia.Builder
	// --------------------------------------------------

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar especie Humano en Bestia.Builder")
	void validarEspecieHumanoBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().tipoPersonaje("Humano");
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar especie Elfo en Bestia.Builder")
	void validarEspecieElfoBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().tipoPersonaje("Elfo");
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar especie vacía en Bestia.Builder")
	void validarEspecieVaciaBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().tipoPersonaje("");
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar especie nula en Bestia.Builder")
	void validarEspecieNulaBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().tipoPersonaje(null);
		});
	}

	// --------------------------------------------------
	// TESTS DE BUILD: Bestia.Builder
	// --------------------------------------------------

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar construcción sin datos")
	void validarBuildSinDatosBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().build();
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar construcción sin puntos de vida")
	void validarBuildSinPuntosDeVidaBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().nombre("Gorbag").nivelDeResistencia(35).tipoPersonaje("Trasgo").build();
		});
	}

	@Tag("validacion-personajes")
	@Test
	@DisplayName("Rechazar construcción sin especie")
	void validarBuildSinEspecieBestiaBuilderTest() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Bestia.Builder().nombre("Gorbag").puntosDeVida(220).nivelDeResistencia(35).build();
		});
	}

	// --------------------------------------------------
	// TESTS DE GETTERS Y SETTERS: Bestia.Builder
	// --------------------------------------------------

	@Test
	@Tag("validacion-personajes")
	@DisplayName("Comprobar getters y setters de Bestia.Builder")
	void gettersSettersBestiaBuilderTest() {

		Bestia.Builder builder = new Bestia.Builder();

		builder.setNombre("Gorbag");
		builder.setPuntosDeVida(220);
		builder.setNivelDeResistencia(45);
		builder.setTipoPersonaje("Trasgo");

		assertAll(() -> assertEquals("Gorbag", builder.getNombre()), () -> assertEquals(220, builder.getPuntosDeVida()),
				() -> assertEquals(45, builder.getNivelDeResistencia()),
				() -> assertEquals("Trasgo", builder.getTipoPersonaje()));
	}

	@Test
	@Tag("validacion-personajes")
	@DisplayName("Rechazar valores inválidos mediante los setters")
	void validarSettersBestiaBuilderTest() {

		Bestia.Builder builder = new Bestia.Builder();

		assertAll(() -> assertThrows(IllegalArgumentException.class, () -> builder.setNombre(null)),
				() -> assertThrows(IllegalArgumentException.class, () -> builder.setPuntosDeVida(0)),
				() -> assertThrows(IllegalArgumentException.class, () -> builder.setNivelDeResistencia(-1)),
				() -> assertThrows(IllegalArgumentException.class, () -> builder.setTipoPersonaje("Humano")));
	}

	// --------------------------------------------------
	// TESTS DE ENCADENAMIENTO: Bestia.Builder
	// --------------------------------------------------

	@Test
	@Tag("validacion-personajes")
	@DisplayName("Permitir encadenar métodos de Bestia.Builder")
	void encadenamientoBestiaBuilderTest() {

		Bestia.Builder builder = new Bestia.Builder();

		assertAll(() -> assertSame(builder, builder.nombre("Gorbag")),
				() -> assertSame(builder, builder.puntosDeVida(220)),
				() -> assertSame(builder, builder.nivelDeResistencia(45)),
				() -> assertSame(builder, builder.tipoPersonaje("Trasgo")));
	}

	// --------------------------------------------------
	// TESTS DE CREACIÓN: Bestia
	// --------------------------------------------------

	@Test
	@Tag("creacion-personajes")
	@DisplayName("Crear bestia correctamente mediante Personaje.Builder")
	void creacionBestiaPersonajeBuilderTest() {

		Bestia bestiaCreada = (Bestia) new Personaje.Builder().nombre("Gorbag").puntosDeVida(220).nivelResistencia(45)
				.tipoPersonaje("Trasgo").tipoRecluta(tipoEjercito.BESTIAS).build();

		assertAll(() -> assertEquals("Gorbag", bestiaCreada.getNombre()),
				() -> assertEquals(220, bestiaCreada.getPuntosDeVida()),
				() -> assertEquals(45, bestiaCreada.getNivelResistencia()),
				() -> assertEquals(tipoPersonajes.TRASGOS, bestiaCreada.getTipoPersonaje()),
				() -> assertEquals(tipoEjercito.BESTIAS, bestiaCreada.getTipoRecluta()));
	}

	@Test
	@Tag("creacion-personajes")
	@DisplayName("Crear bestia correctamente mediante Bestia.Builder")
	void creacionBestiaBuilderTest() {

		Bestia bestiaCreada = new Bestia.Builder().nombre("Lurtz").puntosDeVida(200).nivelDeResistencia(60)
				.tipoPersonaje("Orco").build();

		assertAll(() -> assertEquals("Lurtz", bestiaCreada.getNombre()),
				() -> assertEquals(200, bestiaCreada.getPuntosDeVida()),
				() -> assertEquals(60, bestiaCreada.getNivelResistencia()),
				() -> assertEquals(tipoPersonajes.ORCOS, bestiaCreada.getTipoPersonaje()),
				() -> assertEquals(tipoEjercito.BESTIAS, bestiaCreada.getTipoRecluta()));
	}

	// --------------------------------------------------
	// TEST DE ATAQUE
	// --------------------------------------------------

	@Test
	@Tag("interaccion-combate")
	@DisplayName("Comprobar que una bestia puede atacar")
	void realizarAtaqueTest() {

		assertNotNull(bestia.atacar(heroe));
	}
}