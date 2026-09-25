package Guerra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.text.StringSubstitutor;

import Guerra.Guerra.Combate;
import TiposYInterfaces.tipoEjercito;
import TiposYInterfaces.tipoPersonajes;

/**
 * Traduce el registro "en bruto" de combates de una {@link Guerra} en mensajes
 * de texto legibles para el usuario.
 * <p>
 * Esta clase actúa como consumidor dentro de un patrón productor-consumidor: el
 * hilo de {@link Guerra} va registrando combates turno a turno en un
 * {@link RegistroCombates} compartido y notificando mediante {@code notify()},
 * mientras que un {@link InterpretadorGuerra} (ejecutado en su propio hilo a
 * través de {@link Callable}) espera esas notificaciones con {@code wait()}
 * para ir interpretando cada turno a medida que se completa, en lugar de
 * esperar a que la guerra entera haya terminado.
 * </p>
 * <p>
 * El resultado de la interpretación es una lista de {@code String}, con un
 * elemento por cada turno interpretado más un último elemento con el anuncio
 * del resultado final de la guerra (victoria de un bando o empate).
 * </p>
 */
public class InterpretadorGuerra implements Callable<ArrayList<String>> {

	/**
	 * Registro compartido con el hilo de {@link Guerra}, desde el que se leen los
	 * combates de cada turno y el estado de finalización de la guerra. También
	 * actúa como monitor de sincronización para el mecanismo wait/notify.
	 */
	RegistroCombates acontecimientos;

	/**
	 * Lista con los mensajes ya interpretados: uno por turno, en orden, más el
	 * anuncio final del resultado de la guerra como último elemento.
	 */
	ArrayList<String> listaCombates;

	/**
	 * Crea un intérprete asociado a un registro de combates concreto.
	 *
	 * @param acontecimientos registro de combates que se irá interpretando a medida
	 *                        que la guerra avance.
	 */
	public InterpretadorGuerra(RegistroCombates acontecimientos) {
		super();
		this.acontecimientos = acontecimientos;
		this.listaCombates = new ArrayList<String>();
	}

	/**
	 * Ejecuta el bucle principal de interpretación de la guerra.
	 * <p>
	 * Permanece a la espera (mediante {@code wait()}) de que el hilo de
	 * {@link Guerra} notifique la finalización de un turno. Cada vez que recibe una
	 * notificación, interpreta el turno correspondiente y avanza al siguiente. El
	 * bucle termina cuando la guerra ha finalizado y ya no quedan turnos pendientes
	 * por interpretar, momento en el que se añade el anuncio del resultado final.
	 * </p>
	 *
	 * @return lista de mensajes interpretados, uno por turno más el anuncio del
	 *         resultado final de la guerra.
	 * @throws Exception si el hilo es interrumpido mientras espera una notificación
	 *                   (aunque actualmente dicha excepción se captura internamente
	 *                   y no se relanza).
	 */
	@Override
	public ArrayList<String> call() throws Exception {
		int turno = 1;

		synchronized (acontecimientos) {
			while (true) {
				// Espera solo mientras el turno actual todavía no existe en el mapa
				// y la guerra no ha terminado (si el turno ya existe, hay que
				// procesarlo, aunque la guerra ya haya finalizado).
				while (turno > acontecimientos.getCombates().size() && !acontecimientos.isGuerraFinalizada()) {
					try {
						acontecimientos.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// Solo se sale cuando el turno actual NO existe (ya se procesaron
				// todos los turnos registrados) Y la guerra ha terminado.
				if (turno > acontecimientos.getCombates().size() && acontecimientos.isGuerraFinalizada()) {
					break;
				}

				interpretarCombates(turno);
				turno++;
			}
		}

		anunciarResultadoFinal();

		return listaCombates;
	}

	/**
	 * Devuelve la lista de combates ya interpretados hasta el momento.
	 * <p>
	 * A diferencia del valor devuelto por {@link #call()}, este método puede
	 * invocarse en cualquier momento (incluso con la guerra todavía en curso) para
	 * consultar el progreso de la interpretación.
	 * </p>
	 *
	 * @return lista de mensajes de texto interpretados hasta ahora.
	 */
	public ArrayList<String> getListaCombatesInterpretados() {
		return listaCombates;
	}

	/**
	 * Interpreta todos los combates de un turno concreto y añade el mensaje de
	 * texto resultante a {@link #listaCombates}.
	 * <p>
	 * Para cada combate del turno se genera un bloque de texto con: la presentación
	 * de los dos combatientes (vida y armadura antes del combate), el daño
	 * infligido por cada uno y la vida restada al contrario, y un anuncio adicional
	 * por cada combatiente que haya muerto durante ese combate concreto.
	 * </p>
	 * <p>
	 * Un combatiente se considera muerto en este combate cuando la vida que tenía
	 * antes de empezar es menor que la vida que ha perdido durante él (equivalente
	 * a que su vida después del combate sea negativa o cero).
	 * </p>
	 *
	 * @param turno número de turno cuyos combates se van a interpretar. Debe
	 *              existir una entrada para este turno en el registro de combates.
	 */
	public void interpretarCombates(int turno) {
		Map<Integer, List<Combate>> combates = acontecimientos.getCombates();
		List<Combate> combatesTurno = combates.get(turno);

		StringBuilder mensajeFinal = new StringBuilder();

		String titulo = "Turno " + turno + ":";
		mensajeFinal.append(titulo + "\n");

		for (int i = 0; i < combatesTurno.size(); i++) {
			Combate combate = combatesTurno.get(i);

			// Datos Heroe.
			String nombreHeroe = combate.getHeroe();
			int vidaHeroe = combate.getVidaHeroeAntes();
			int armaduraHeroe = combate.getArmaduraHeroe();
			int danoHeroe = combate.getDanoHeroe();
			int vidaPerdidaHeroe = combate.getVidaHeroeAntes() - combate.getVidaHeroeDespues();
			tipoPersonajes especieHeroe = combate.getEspecieHeroe();

			// Datos Bestia.
			String nombreBestia = combate.getBestia();
			int vidaBestia = combate.getVidaBestiaAntes();
			int armaduraBestia = combate.getArmaduraBestia();
			int danoBestia = combate.getDanoBestia();
			int vidaPerdidaBestia = combate.getVidaBestiaAntes() - combate.getVidaBestiaDespues();
			tipoPersonajes especieBestia = combate.getEspecieBestia();

			// Se vuelcan todos los datos del combate en un mapa para poder sustituirlos
			// dentro de las plantillas de texto mediante StringSubstitutor.
			Map<String, String> valores = Map.of("nombreHeroe", nombreHeroe, "vidaHeroe", String.valueOf(vidaHeroe),
					"armaduraHeroe", String.valueOf(armaduraHeroe), "danoHeroe", String.valueOf(danoHeroe),
					"vidaPerdidaHeroe", String.valueOf(vidaPerdidaHeroe), "nombreBestia", nombreBestia, "vidaBestia",
					String.valueOf(vidaBestia), "armaduraBestia", String.valueOf(armaduraBestia), "danoBestia",
					String.valueOf(danoBestia), "vidaPerdidaBestia", String.valueOf(vidaPerdidaBestia));

			// Plantillas de texto para presentar el combate y su resultado.
			String[] lineasPlantilla = {
					"- Lucha entre ${nombreHeroe} (Vida=${vidaHeroe} Armadura ${armaduraHeroe}) y ${nombreBestia} (Vida=${vidaBestia} Armadura ${armaduraBestia}). \n",
					"${nombreHeroe} saca ${danoHeroe} y le quita ${vidaPerdidaBestia} de vida a ${nombreBestia}. \n",
					"${nombreBestia} saca ${danoBestia} y le quita ${vidaPerdidaHeroe} de vida a ${nombreHeroe}. \n" };

			StringSubstitutor sustitutor = new StringSubstitutor(valores);

			for (String linea : lineasPlantilla) {
				String resultado = sustitutor.replace(linea);
				mensajeFinal.append(resultado);
			}

			// Anuncio de bajas ocurridas durante el combate.

			// Se agrupan por posición (0 = héroe, 1 = bestia) para recorrer ambos
			// combatientes con el mismo bucle en vez de duplicar la comprobación.
			int[] vidaHeroeYBestia = { vidaHeroe, vidaBestia };
			int[] vidaPerdidaHeroeYBestia = { vidaPerdidaHeroe, vidaPerdidaBestia };

			for (int j = 0; j < vidaHeroeYBestia.length; j++) {
				tipoPersonajes especie = j == 0 ? especieHeroe : especieBestia;
				String nombre = j == 0 ? nombreHeroe : nombreBestia;

				// Si la vida perdida supera la vida que tenía antes del combate,
				// el combatiente ha muerto durante este enfrentamiento.
				if (vidaHeroeYBestia[j] < vidaPerdidaHeroeYBestia[j]) {
					String anuncioMuerte = String.format("Muere %s %s! \n", especie.getESPECIE(), nombre);
					mensajeFinal.append(anuncioMuerte);
				}
			}
		}

		String textoTerminado = mensajeFinal.toString();
		listaCombates.add(textoTerminado);
	}

	/**
	 * Calcula y añade a {@link #listaCombates} el anuncio del resultado final de la
	 * guerra.
	 * <p>
	 * El resultado se determina a partir del último combate del último turno
	 * registrado: si ambos combatientes de ese combate terminan con la vida a cero
	 * o por debajo, se declara empate; en caso contrario, gana el bando cuyo
	 * representante siga con vida (el bando del combatiente que no llegó a cero).
	 * </p>
	 */
	private void anunciarResultadoFinal() {
		Map<Integer, List<Combate>> combates = acontecimientos.getCombates();
		int ultimoTurno = combates.size();
		List<Combate> combatesUltimoTurno = combates.get(ultimoTurno);
		Combate ultimoCombate = combatesUltimoTurno.get(combatesUltimoTurno.size() - 1);

		String anuncioResultado;

		if (ultimoCombate.getVidaHeroeDespues() <= 0 && ultimoCombate.getVidaBestiaDespues() <= 0) {
			anuncioResultado = "¡¡EMPATE!!";
		} else {
			tipoEjercito ejercitoGanador = ultimoCombate.getVidaBestiaDespues() <= 0 ? tipoEjercito.HEROES
					: tipoEjercito.BESTIAS;
			anuncioResultado = ejercitoGanador == tipoEjercito.HEROES ? "¡¡VICTORIA DE LOS HÉROES!!"
					: "¡¡VICTORIA DE LAS BESTIAS!!";
		}

		listaCombates.add(anuncioResultado);
	}
}