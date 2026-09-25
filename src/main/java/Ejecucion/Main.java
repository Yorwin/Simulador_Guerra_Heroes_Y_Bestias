package Ejecucion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import Ejercito.Ejercito;
import Guerra.Guerra;
import Guerra.RegistroCombates;
import Guerra.Guerra.Combate;
import Guerra.InterpretadorGuerra;
import Personajes.Bestia;
import Personajes.Heroe;
import Personajes.Personaje;
import TiposYInterfaces.tipoEjercito;

/**
 * Clase de arranque de la aplicación.
 * <p>
 * Contiene el método {@code main}, que sirve como banco de pruebas para
 * comprobar el funcionamiento conjunto de los ejércitos de héroes y bestias, la
 * simulación de una guerra entre ambos y el registro/narración de los combates
 * generados.
 */
public class Main {

	/**
	 * Punto de entrada de la aplicación.
	 * <p>
	 * Crea un ejército de héroes y un ejército de bestias, los rellena con
	 * personajes de ejemplo, lanza una simulación de {@link Guerra} entre ambos y
	 * muestra por consola la narración generada a partir del registro de combates.
	 *
	 * @param args argumentos de línea de comandos (no se utilizan actualmente)
	 */
	public static void main(String[] args) {
		// ----------------------------------------------------
		// Simulación Ejercito con 3 Heroes y 3 Bestias
		// ----------------------------------------------------

		Ejercito<Heroe> ejercitoHeroes = new Ejercito<Heroe>();
		Ejercito<Bestia> ejercitoBestias = new Ejercito<Bestia>();

		// ---------------- HEROES ----------------
		// Se crean 3 héroes de ejemplo mediante el patrón Builder y se añaden
		// al ejército de héroes. Cualquier error de construcción o de tipo
		// incompatible con el ejército se captura y se muestra por consola.

		Heroe heroe1;
		try {
			heroe1 = (Heroe) new Personaje.Builder().nombre("Legolas").puntosDeVida(100).nivelResistencia(35)
					.tipoPersonaje("Elfo").tipoRecluta(tipoEjercito.HEROES).build();
			ejercitoHeroes.anadirRecluta(heroe1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Heroe heroe2;
		try {
			heroe2 = (Heroe) new Personaje.Builder().nombre("Aragorn").puntosDeVida(120).nivelResistencia(40)
					.tipoPersonaje("Humano").tipoRecluta(tipoEjercito.HEROES).build();
			ejercitoHeroes.anadirRecluta(heroe2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Heroe heroe3;
		try {
			heroe3 = (Heroe) new Personaje.Builder().nombre("Bilbo").puntosDeVida(80).nivelResistencia(25)
					.tipoPersonaje("Hobbit").tipoRecluta(tipoEjercito.HEROES).build();
			ejercitoHeroes.anadirRecluta(heroe3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ---------------- BESTIAS ----------------
		// Se repite el mismo proceso para crear 3 bestias de ejemplo y
		// añadirlas al ejército de bestias.

		Bestia bestia1;
		try {
			bestia1 = (Bestia) new Personaje.Builder().nombre("Mer").puntosDeVida(150).nivelResistencia(35)
					.tipoPersonaje("Orco").tipoRecluta(tipoEjercito.BESTIAS).build();
			ejercitoBestias.anadirRecluta(bestia1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Bestia bestia2;
		try {
			bestia2 = (Bestia) new Personaje.Builder().nombre("Grishnak").puntosDeVida(110).nivelResistencia(38)
					.tipoPersonaje("Orco").tipoRecluta(tipoEjercito.BESTIAS).build();
			ejercitoBestias.anadirRecluta(bestia2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Bestia bestia3;
		try {
			bestia3 = (Bestia) new Personaje.Builder().nombre("Snaga").puntosDeVida(70).nivelResistencia(20)
					.tipoPersonaje("Trasgo").tipoRecluta(tipoEjercito.BESTIAS).build();
			ejercitoBestias.anadirRecluta(bestia3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ---------------- COMPROBACION DE EJERCITOS ----------------
		// Se muestran por consola los nombres de los reclutas de cada
		// ejército, junto con el total de efectivos, para verificar que se
		// han añadido correctamente.

		System.out.println("--- Ejercito de Heroes ---");
		for (int i = 0; i < ejercitoHeroes.length(); i++) {
			System.out.println("- " + ejercitoHeroes.getReclutas().get(i).getNombre());
		}
		System.out.println("Total heroes: " + ejercitoHeroes.length());

		System.out.println("--- Ejercito de Bestias ---");
		for (int i = 0; i < ejercitoBestias.length(); i++) {
			System.out.println("- " + ejercitoBestias.getReclutas().get(i).getNombre());
		}
		System.out.println("Total bestias: " + ejercitoBestias.length());

		// ----------------------------------------------------
		// Prueba Guerra
		// ----------------------------------------------------
		// Se crea un registro de combates y se lanza, en un hilo separado,
		// un intérprete que irá generando la narración de la guerra a medida
		// que se registran combates. En el hilo principal se ejecuta la
		// simulación de la guerra en sí, que va rellenando el registro.

		System.out.println("\n" + "¡¡Empieza la guerra!!" + "\n");

		RegistroCombates registro = new RegistroCombates();

		ExecutorService gestorProcesos = Executors.newSingleThreadExecutor();
		Future<ArrayList<String>> resultado = gestorProcesos.submit(new InterpretadorGuerra(registro));

		Guerra guerraPrueba = new Guerra(ejercitoHeroes, ejercitoBestias, registro);
		guerraPrueba.start();

		ArrayList<String> narracion;

		// Se espera el resultado del hilo intérprete, que devuelve la
		// narración completa de la guerra una vez procesados los combates.
		try {
			narracion = resultado.get();
			narracion.forEach(turno -> System.out.println(turno));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		gestorProcesos.shutdown();
	}
}