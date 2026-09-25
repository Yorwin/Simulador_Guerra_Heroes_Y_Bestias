
package Personajes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import TiposYInterfaces.tipoPersonajes;

class HeroesTest {

	static tipoPersonajes[] tiposHeroes = Heroes.getTIPOS_DE_HEROES();
	
	@Test
	@Tag("tipos-personajes")
	@DisplayName("Tipos de héroes válidos")
	void tiposHeroeTest() {
		Assertions.assertArrayEquals(tiposHeroes,
				new tipoPersonajes[] { tipoPersonajes.HUMANOS, tipoPersonajes.ELFOS, tipoPersonajes.HOBBITS });
	}

	@Test
	@Tag("tipos-personajes")
	@DisplayName("Especies de héroes")
	void especiesHeroeTest() {
		assertAll(() -> assertEquals("Humano", tiposHeroes[0].getESPECIE()),
				() -> assertEquals("Elfo", tiposHeroes[1].getESPECIE()),
				() -> assertEquals("Hobbit", tiposHeroes[2].getESPECIE()));
	}
}
