package Guerra;

import java.util.List;
import java.util.Map;

import Ejercito.Ejercito;
import Personajes.Bestia;
import Personajes.Heroe;
import Tipos.tipoPersonajes;

/**
 * Gestiona una guerra entre un ejército de héroes y un ejército de bestias.
 * <p>
 * La clase se encarga de controlar el desarrollo de los turnos, realizar los
 * combates entre los integrantes de ambos ejércitos, comprobar las bajas y
 * registrar los resultados de cada combate.
 * </p>
 */
public class Guerra {

	/**
	 * Ejército formado por los héroes que participan en la guerra.
	 */
	private Ejercito<Heroe> ejercitoHeroes;

	/**
	 * Ejército formado por las bestias que participan en la guerra.
	 */
	private Ejercito<Bestia> ejercitoBestias;

	/**
	 * Número del turno actual de la guerra.
	 */
	private int turno = 1;

	/**
	 * Registro que almacena los combates realizados durante la guerra, agrupados
	 * por turno.
	 */
	private RegistroCombates registroCombates;

	/**
	 * Crea una nueva guerra entre un ejército de héroes y un ejército de bestias.
	 *
	 * @param ejercitoHeroes  ejército de héroes que participará en la guerra.
	 * @param ejercitoBestias ejército de bestias que participará en la guerra.
	 */
	public Guerra(Ejercito<Heroe> ejercitoHeroes, Ejercito<Bestia> ejercitoBestias, RegistroCombates registroCombates) {
		this.ejercitoHeroes = ejercitoHeroes.copiar();
		this.ejercitoBestias = ejercitoBestias.copiar();
		this.registroCombates = registroCombates;
	}

	/**
	 * Devuelve el registro de combates de la guerra.
	 *
	 * @return registro que contiene los combates realizados durante la guerra.
	 */
	public Map<Integer, List<Combate>> getRegistroCombates() {
		return registroCombates.getCombates();
	}

	/**
	 * Inicia y controla la simulación de la guerra.
	 * <p>
	 * En cada turno se determinan los enfrentamientos que pueden realizarse, se
	 * ejecutan los combates correspondientes, se registran sus resultados y se
	 * comprueban las bajas producidas. La guerra continúa mientras ambos ejércitos
	 * tengan integrantes con vida.
	 * </p>
	 */
	public void start() {
		do {
			int totalInteracciones = manejarInteracciones();
			int indiceCombate = 0;

			while (indiceCombate < totalInteracciones) {
				Heroe peleador1 = ejercitoHeroes.getReclutas().get(indiceCombate);
				Bestia peleador2 = ejercitoBestias.getReclutas().get(indiceCombate);

				// Estadisticas antes del combate.

				int nivelVidaAntesHeroe = peleador1.getPuntosDeVida();
				int nivelVidaAntesBestia = peleador2.getPuntosDeVida();

				// Turno Heroe.
				int turnoHeroe = peleador1.atacar(peleador2);
				peleador2.interpretarDano(turnoHeroe);

				// Turno Bestia.
				int turnoBestia = peleador2.atacar(peleador1);
				peleador1.interpretarDano(turnoBestia);

				// Registrar Combate
				Combate combate = new Combate(peleador1.getNombre(), peleador1.getTipoPersonaje(),
						peleador2.getNombre(), peleador2.getTipoPersonaje(), nivelVidaAntesHeroe,
						peleador1.getPuntosDeVida(), peleador1.getNivelResistencia(), nivelVidaAntesBestia,
						peleador2.getPuntosDeVida(), peleador2.getNivelResistencia(), turnoHeroe, turnoBestia,
						ejercitoHeroes.length(), ejercitoBestias.length());

				registroCombates.registrarCombate(turno, combate);
				indiceCombate++;
			}

			synchronized (registroCombates) {
				registroCombates.notify();
			}

			comprobarBajas();
			this.turno++;
		} while (ejercitoHeroes.length() > 0 && ejercitoBestias.length() > 0);

		synchronized (registroCombates) {
			registroCombates.setGuerraFinalizada(true);
			registroCombates.notify();
		}
	}

	/**
	 * Determina el número de enfrentamientos que pueden realizarse durante un
	 * turno.
	 * <p>
	 * El número de interacciones queda limitado por el ejército que tenga menos
	 * integrantes con vida, ya que cada interacción enfrenta a un héroe con una
	 * bestia.
	 * </p>
	 *
	 * @return número de enfrentamientos que se pueden realizar en el turno.
	 */
	private int manejarInteracciones() {
		int interaccionesTurno = ejercitoHeroes.length() > ejercitoBestias.length() ? ejercitoBestias.length()
				: ejercitoHeroes.length();
		return interaccionesTurno;
	}

	/**
	 * Comprueba los resultados de los combates realizados y retira de sus
	 * respectivos ejércitos a los combatientes que hayan perdido todos sus puntos
	 * de vida.
	 */
	private void comprobarBajas() {
		ejercitoHeroes.getReclutas().removeIf(h -> h.getPuntosDeVida() <= 0);
		ejercitoBestias.getReclutas().removeIf(h -> h.getPuntosDeVida() <= 0);
	}

	/**
	 * Representa la información de un combate entre un héroe y una bestia.
	 * <p>
	 * Almacena los datos necesarios para consultar posteriormente el resultado del
	 * combate, incluyendo los puntos de vida de ambos participantes antes y después
	 * del enfrentamiento y el daño infligido por cada uno.
	 * </p>
	 */
	public static class Combate {

		/** Nombre del héroe que participa en el combate. */
		private String heroe;

		/** Especie del héroe que participa en el combate */
		private tipoPersonajes especieHeroe;

		/** Nombre de la bestia que participa en el combate. */
		private String bestia;

		/** Especie de la bestia que participa en el combate */
		private tipoPersonajes especieBestia;

		/** Puntos de vida del héroe antes del combate. */
		private int vidaHeroeAntes;

		/** Puntos de vida del héroe después del combate. */
		private int vidaHeroeDespues;

		/** Puntos de armadura Heroe */
		private int armaduraHeroe;

		/** Puntos de vida de la bestia antes del combate. */
		private int vidaBestiaAntes;

		/** Puntos de vida de la bestia después del combate. */
		private int vidaBestiaDespues;

		/** Puntos de armadura Bestia */
		private int armaduraBestia;

		/** Daño infligido por el héroe durante el combate. */
		private int danoHeroe;

		/** Daño infligido por la bestia durante el combate. */
		private int danoBestia;

		/** Daño infligido por la bestia durante el combate. */
		private int tamannoEjercitoHeroes;

		/** Daño infligido por la bestia durante el combate. */
		private int tamannoEjercitoBestias;

		/**
		 * Crea un registro con la información de un combate.
		 *
		 * @param heroe             nombre del héroe participante.
		 * @param bestia            nombre de la bestia participante.
		 * @param vidaHeroeAntes    puntos de vida del héroe antes del combate.
		 * @param vidaHeroeDespues  puntos de vida del héroe después del combate.
		 * @param vidaBestiaAntes   puntos de vida de la bestia antes del combate.
		 * @param vidaBestiaDespues puntos de vida de la bestia después del combate.
		 * @param danoHeroe         daño infligido por el héroe.
		 * @param danoBestia        daño infligido por la bestia.
		 */
		public Combate(String heroe, tipoPersonajes especieHeroe, String bestia, tipoPersonajes especieBestia,
				int vidaHeroeAntes, int vidaHeroeDespues, int armaduraHeroe, int vidaBestiaAntes, int vidaBestiaDespues,
				int armaduraBestia, int danoHeroe, int danoBestia, int tamannoEjercitoHeroes,
				int tamannoEjercitoBestias) {

			super();

			this.heroe = heroe;
			this.especieHeroe = especieHeroe;
			this.bestia = bestia;
			this.especieBestia = especieBestia;
			this.vidaHeroeAntes = vidaHeroeAntes;
			this.vidaHeroeDespues = vidaHeroeDespues;
			this.armaduraHeroe = armaduraHeroe;
			this.vidaBestiaAntes = vidaBestiaAntes;
			this.vidaBestiaDespues = vidaBestiaDespues;
			this.armaduraBestia = armaduraBestia;
			this.danoHeroe = danoHeroe;
			this.danoBestia = danoBestia;
			this.tamannoEjercitoHeroes = tamannoEjercitoHeroes;
			this.tamannoEjercitoBestias = tamannoEjercitoBestias;
		}

		/**
		 * Obtiene el nombre del héroe participante.
		 *
		 * @return nombre del héroe.
		 */
		public String getHeroe() {
			return heroe;
		}

		/**
		 * Obtiene la especie del héroe participante.
		 *
		 * @return e del héroe.
		 */
		public tipoPersonajes getEspecieHeroe() {
			return especieHeroe;
		}

		/**
		 * Obtiene el nombre de la bestia participante.
		 *
		 * @return nombre de la bestia.
		 */
		public String getBestia() {
			return bestia;
		}

		/**
		 * Obtiene la especie de la bestia participante.
		 *
		 * @return nombre de la bestia.
		 */
		public tipoPersonajes getEspecieBestia() {
			return especieBestia;
		}

		/**
		 * Obtiene los puntos de vida del héroe antes del combate.
		 *
		 * @return puntos de vida iniciales del héroe.
		 */
		public int getVidaHeroeAntes() {
			return vidaHeroeAntes;
		}

		/**
		 * Obtiene los puntos de vida del héroe después del combate.
		 *
		 * @return puntos de vida finales del héroe.
		 */
		public int getVidaHeroeDespues() {
			return vidaHeroeDespues;
		}

		/**
		 * Obtiene los puntos de armadura del héroe.
		 *
		 * @return puntos de armadura del héroe.
		 */
		public int getArmaduraHeroe() {
			return armaduraHeroe;
		}

		/**
		 * Obtiene los puntos de vida de la bestia antes del combate.
		 *
		 * @return puntos de vida iniciales de la bestia.
		 */
		public int getVidaBestiaAntes() {
			return vidaBestiaAntes;
		}

		/**
		 * Obtiene los puntos de armadura de la bestia.
		 *
		 * @return puntos de armadura de la bestia.
		 */
		public int getArmaduraBestia() {
			return armaduraBestia;
		}

		/**
		 * Obtiene los puntos de vida de la bestia después del combate.
		 *
		 * @return puntos de vida finales de la bestia.
		 */
		public int getVidaBestiaDespues() {
			return vidaBestiaDespues;
		}

		/**
		 * Obtiene el daño infligido por el héroe.
		 *
		 * @return daño causado por el héroe.
		 */
		public int getDanoHeroe() {
			return danoHeroe;
		}

		/**
		 * Obtiene el daño infligido por la bestia.
		 *
		 * @return daño causado por la bestia.
		 */
		public int getDanoBestia() {
			return danoBestia;
		}

		/**
		 * Devuelve el tamaño actual del ejército de héroes.
		 *
		 * @return número de héroes que forman el ejército.
		 */
		public int getTamannoEjercitoHeroes() {
			return tamannoEjercitoHeroes;
		}

		/**
		 * Devuelve el tamaño actual del ejército de bestias.
		 *
		 * @return número de bestias que forman el ejército.
		 */
		public int getTamannoEjercitoBestias() {
			return tamannoEjercitoBestias;
		}

	}
}
