package Guerra;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import Ejercito.Ejercito;
import Guerra.Guerra.Combate;
import Personajes.Bestia;
import Personajes.Bestias;
import Personajes.Heroe;
import Personajes.Heroes;
import Personajes.Personaje;
import TiposYInterfaces.tipoEjercito;
import TiposYInterfaces.tipoPersonajes;

@Tag("guerra")
@DisplayName("Guerra: registro de combates")
class GuerraTest {

	Guerra guerra;
	Ejercito<Heroe> ejercitoHeroes;
	Ejercito<Bestia> ejercitoBestias;
	RegistroCombates registroCombates;
	Random random;

	@BeforeEach
	void resetearEjercitos() {
		ejercitoHeroes = new Ejercito<Heroe>();
		ejercitoBestias = new Ejercito<Bestia>();
		registroCombates = new RegistroCombates();

		long semilla = System.nanoTime();
		random = new Random(semilla);
	}

	// ---------- Tests con datos aleatorios ----------

	@Tag("aleatorio")
	@DisplayName("El registro devuelto no es nulo ni está vacío")
	@ParameterizedTest(name = "Caso {index}: {0} héroes vs {1} bestias")
	@MethodSource("combinacionesDeTamanos")
	void guerraDevuelveRegistroCorrecto(int numHeroes, int numBestias) {
		rellenarEjercitos(numHeroes, numBestias);

		guerra = new Guerra(ejercitoHeroes, ejercitoBestias, registroCombates);
		guerra.start();

		Map<Integer, List<Combate>> registro = registroCombates.getCombates();

		assertNotNull(registro);
		assertFalse(registro.isEmpty());
	}

	@Tag("aleatorio")
	@DisplayName("Todos los turnos registrados tienen al menos un combate")
	@ParameterizedTest(name = "Caso {index}: {0} héroes vs {1} bestias")
	@MethodSource("combinacionesDeTamanos")
	void todosLosTurnosTienenCombates(int numHeroes, int numBestias) {
		rellenarEjercitos(numHeroes, numBestias);
		guerra = new Guerra(ejercitoHeroes, ejercitoBestias, registroCombates);
		guerra.start();

		for (List<Combate> combates : registroCombates.getCombates().values()) {
			assertNotNull(combates);
			assertFalse(combates.isEmpty());
		}
	}

	@Tag("aleatorio")
	@DisplayName("Los turnos son consecutivos, sin huecos")
	@ParameterizedTest(name = "Caso {index}: {0} héroes vs {1} bestias")
	@MethodSource("combinacionesDeTamanos")
	void losTurnosSonConsecutivos(int numHeroes, int numBestias) {
		rellenarEjercitos(numHeroes, numBestias);
		guerra = new Guerra(ejercitoHeroes, ejercitoBestias, registroCombates);
		guerra.start();

		Set<Integer> turnos = registroCombates.getCombates().keySet();
		int primero = Collections.min(turnos);
		int ultimo = Collections.max(turnos);

		assertEquals(ultimo - primero + 1, turnos.size());
	}

	@Tag("aleatorio")
	@DisplayName("Ningún combate tiene héroe o bestia nulos")
	@ParameterizedTest(name = "Caso {index}: {0} héroes vs {1} bestias")
	@MethodSource("combinacionesDeTamanos")
	void ningunCombateTieneParticipantesNulos(int numHeroes, int numBestias) {
		rellenarEjercitos(numHeroes, numBestias);
		guerra = new Guerra(ejercitoHeroes, ejercitoBestias, registroCombates);
		guerra.start();

		registroCombates.getCombates().values().stream().flatMap(List::stream).forEach(c -> {
			assertNotNull(c.getHeroe());
			assertNotNull(c.getBestia());
		});
	}

	// ---------- Tests con valores fijos ----------

	@Tag("valores-fijos")
	@DisplayName("El registro está vacío antes de iniciar la guerra")
	@Test
	void registroVacioAntesDeIniciarLaGuerra() {
		ejercitoHeroes.anadirRecluta(crearHeroe("Heroe", 100, 50));
		ejercitoBestias.anadirRecluta(crearBestia("Bestia", 100, 50));
		guerra = new Guerra(ejercitoHeroes, ejercitoBestias, registroCombates);

		assertTrue(guerra.getRegistroCombates().isEmpty());
	}

	@Tag("valores-fijos")
	@DisplayName("Un héroe muy fuerte contra una bestia débil termina en un turno")
	@Test
	void heroeMuyFuerteContraBestiaDebilTerminaEnUnTurno() {
		ejercitoHeroes.anadirRecluta(crearHeroe("Heroe fuerte", 1000, 100));
		ejercitoBestias.anadirRecluta(crearBestia("Bestia debil", 1, 1));
		guerra = new Guerra(ejercitoHeroes, ejercitoBestias, registroCombates);
		guerra.start();

		Map<Integer, List<Combate>> registro = guerra.getRegistroCombates();

		assertEquals(1, registro.size());
		assertEquals(1, registro.values().iterator().next().size());
	}

	@Tag("valores-fijos")
	@DisplayName("El registro contiene los nombres de los participantes")
	@Test
	void elRegistroContieneLosNombresDeLosParticipantes() {
		ejercitoHeroes.anadirRecluta(crearHeroe("Aragorn", 1000, 100));
		ejercitoBestias.anadirRecluta(crearBestia("Shrek", 1, 1));
		guerra = new Guerra(ejercitoHeroes, ejercitoBestias, registroCombates);
		guerra.start();

		Combate combate = guerra.getRegistroCombates().get(1).get(0);

		assertEquals("Aragorn", combate.getHeroe());
		assertEquals("Shrek", combate.getBestia());
	}

	// ---------- Auxiliares ----------

	private void rellenarEjercitos(int numHeroes, int numBestias) {
		for (int i = 0; i < numHeroes; i++) {
			ejercitoHeroes.anadirRecluta(crearHeroeAleatorio(i));
		}

		for (int i = 0; i < numBestias; i++) {
			ejercitoBestias.anadirRecluta(crearBestiasAleatorio(i));
		}
	}

	static Stream<Arguments> combinacionesDeTamanos() {
		return Stream.of(Arguments.of(1, 1), Arguments.of(2, 2), Arguments.of(3, 3), Arguments.of(3, 1),
				Arguments.of(2, 5));
	}

	private String elegirAleatorio(tipoPersonajes[] opciones) {
		return opciones[random.nextInt(opciones.length)].getESPECIE();
	}

	private String elegirPrimero(tipoPersonajes[] opciones) {
		return opciones[0].getESPECIE();
	}

	private int entre(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("min no puede ser mayor que max");
		}

		return min + random.nextInt(max - min + 1);
	}

	// Factorías aleatorias
	private Heroe crearHeroeAleatorio(int i) {
		return (Heroe) new Personaje.Builder().nombre("Heroe " + (char) ('A' + i)).puntosDeVida(entre(50, 200))
				.nivelResistencia(entre(30, 100)).tipoPersonaje(elegirAleatorio(Heroes.getTIPOS_DE_HEROES()))
				.tipoRecluta(tipoEjercito.HEROES).build();
	}

	private Bestia crearBestiasAleatorio(int i) {
		return (Bestia) new Personaje.Builder().nombre("Bestia " + (char) ('A' + i)).puntosDeVida(entre(50, 200))
				.nivelResistencia(entre(30, 100)).tipoPersonaje(elegirAleatorio(Bestias.getTIPOS_DE_BESTIAS()))
				.tipoRecluta(tipoEjercito.BESTIAS).build();
	}

	// Factorías con valores fijos (tipo siempre el primero, para que sea
	// reproducible)
	private Heroe crearHeroe(String nombre, int vida, int resistencia) {
		return (Heroe) new Personaje.Builder().nombre(nombre).puntosDeVida(vida).nivelResistencia(resistencia)
				.tipoPersonaje(elegirPrimero(Heroes.getTIPOS_DE_HEROES())).tipoRecluta(tipoEjercito.HEROES).build();
	}

	private Bestia crearBestia(String nombre, int vida, int resistencia) {
		return (Bestia) new Personaje.Builder().nombre(nombre).puntosDeVida(vida).nivelResistencia(resistencia)
				.tipoPersonaje(elegirPrimero(Bestias.getTIPOS_DE_BESTIAS())).tipoRecluta(tipoEjercito.BESTIAS).build();
	}
}