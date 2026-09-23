package Guerra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.text.StringSubstitutor;

import Guerra.Guerra.Combate;

public class InterpretadorGuerra implements Callable<ArrayList<String>> {
	RegistroCombates acontecimientos;
	ArrayList<String> listaCombates;

	public InterpretadorGuerra(RegistroCombates acontecimientos) {
		super();
		this.acontecimientos = acontecimientos;
		this.listaCombates = new ArrayList<String>();
	}

	@Override
	public ArrayList<String> call() throws Exception {
		int turno = 1;

		synchronized (acontecimientos) {
			while (!acontecimientos.isGuerraFinalizada()) {
				try {
					acontecimientos.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (turno >= acontecimientos.getCombates().size() && acontecimientos.isGuerraFinalizada()) {
					break;
				}

				interpretarCombates(turno);
				turno++;
			}
		}

		return listaCombates;
	}

	public ArrayList<String> getListaCombatesInterpretados() {
		return listaCombates;
	}

	public void interpretarCombates(int turno) {
		List<Combate> combatesTurno = acontecimientos.getCombates().get(turno);

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

			// Datos Bestia.

			String nombreBestia = combate.getBestia();
			int vidaBestia = combate.getVidaBestiaAntes();
			int armaduraBestia = combate.getArmaduraBestia();
			int danoBestia = combate.getDanoBestia();

			int vidaPerdidaBestia = combate.getVidaBestiaAntes() - combate.getVidaBestiaDespues();

			Map<String, String> valores = Map.of("nombreHeroe", nombreHeroe, "vidaHeroe", String.valueOf(vidaHeroe),
					"armaduraHeroe", String.valueOf(armaduraHeroe), "danoHeroe", String.valueOf(danoHeroe),
					"vidaPerdidaHeroe", String.valueOf(vidaPerdidaHeroe), "nombreBestia", nombreBestia, "vidaBestia",
					String.valueOf(vidaBestia), "armaduraBestia", String.valueOf(armaduraBestia), "danoBestia",
					String.valueOf(danoBestia), "vidaPerdidaBestia", String.valueOf(vidaPerdidaBestia));

			String[] lineasPlantilla = {
					"- Lucha entre ${nombreHeroe} (Vida=${vidaHeroe} Armadura ${armaduraHeroe}) y ${nombreBestia} (Vida=${vidaBestia} Armadura ${armaduraBestia}). \n",
					"${nombreHeroe} saca ${danoHeroe} y le quita ${vidaPerdidaBestia} de vida a ${nombreBestia}. \n",
					"${nombreBestia} saca ${danoBestia} y le quita ${vidaPerdidaHeroe} de vida a ${nombreHeroe}. \n" };

			StringSubstitutor sustitutor = new StringSubstitutor(valores);

			for (String linea : lineasPlantilla) {
				String resultado = sustitutor.replace(linea);
				mensajeFinal.append(resultado);
			}
		}

		mensajeFinal.append("\n");

		String textoTerminado = mensajeFinal.toString();

		listaCombates.add(textoTerminado);

	}
}
