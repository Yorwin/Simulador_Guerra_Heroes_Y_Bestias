package Personajes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import TiposYInterfaces.tipoPersonajes;

class BestiasTest {
	
	static tipoPersonajes[] tiposBestias = Bestias.getTIPOS_DE_BESTIAS();
	
	@Test
	@Tag("tipos-personajes")
	@DisplayName("Tipos de bestias válidos")
	void tiposBestiaTest() {
		Assertions.assertArrayEquals(Bestias.getTIPOS_DE_BESTIAS(),
				new tipoPersonajes[] { tipoPersonajes.ORCOS, tipoPersonajes.TRASGOS });
	}

	@Test
	@Tag("tipos-personajes")
	@DisplayName("Especies de bestias")
	void especiesBestiaTest() {

		tipoPersonajes[] tiposBestias = Bestias.getTIPOS_DE_BESTIAS();

		assertAll(() -> assertEquals("Orco", tiposBestias[0].getESPECIE()),
				() -> assertEquals("Trasgo", tiposBestias[1].getESPECIE()));
	}

}
