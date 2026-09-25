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

	@Test
	@Tag("creacion-personajes")
	@DisplayName("Creación correcta de Bestia")
	void creacionBestiaTest() {
		assertAll(() -> assertEquals("Gorbag", bestia.getNombre()), () -> assertEquals(220, bestia.getPuntosDeVida()),
				() -> assertEquals(45, bestia.getNivelResistencia()),
				() -> assertEquals(tipoPersonajes.TRASGOS, bestia.getTipoPersonaje()),
				() -> assertEquals(tipoEjercito.BESTIAS, bestia.getTipoRecluta()));
	}

	@Test
	@Tag("interaccion-combate")
	@DisplayName("Ataque de Bestia")
	void realizarAtaqueTest() {
		assertNotNull(bestia.atacar(heroe));
	}

	@Test
    @Tag("seleccion-personaje")
    @DisplayName("Selección de tipo de bestia")
    void seleccionarBestiaTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> bestia.seleccionarTipoBestia("gigante")
        );

        assertEquals(
                tipoPersonajes.ORCOS,
                bestia.seleccionarTipoBestia("orco")
        );
    }
}
