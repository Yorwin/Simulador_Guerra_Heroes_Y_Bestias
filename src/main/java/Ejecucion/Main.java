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
import Tipos.tipoEjercito;

public class Main {

	public static void main(String[] args) {
		System.out.println("Se ejecuta bien");

		// ----------------------------------------------------
		// Prueba Ejercito con 3 Heroes y 3 Bestias
		// ----------------------------------------------------

		Ejercito<Heroe> ejercitoHeroes = new Ejercito<Heroe>();
		Ejercito<Bestia> ejercitoBestias = new Ejercito<Bestia>();

		// ---------------- HEROES ----------------

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

		Bestia bestia1;
		try {
			bestia1 = (Bestia) new Personaje.Builder().nombre("Mer").puntosDeVida(100).nivelResistencia(35)
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

		System.out.println("--- Ejercito de Heroes ---");
		for (int i = 0; i < ejercitoHeroes.length(); i++) {
			System.out.println(ejercitoHeroes.getReclutas().get(i).getNombre());
		}
		System.out.println("Total heroes: " + ejercitoHeroes.length());

		System.out.println("--- Ejercito de Bestias ---");
		for (int i = 0; i < ejercitoBestias.length(); i++) {
			System.out.println(ejercitoBestias.getReclutas().get(i).getNombre());
		}
		System.out.println("Total bestias: " + ejercitoBestias.length());

		// ----------------------------------------------------
		// Prueba Guerra
		// ----------------------------------------------------
		
		RegistroCombates registro = new RegistroCombates();
		
		ExecutorService gestorProcesos = Executors.newSingleThreadExecutor();
		Future<ArrayList<String>> resultado = gestorProcesos.submit(new InterpretadorGuerra(registro)); 
				
		Guerra guerraPrueba = new Guerra(ejercitoHeroes, ejercitoBestias, registro);
		guerraPrueba.start();

		Map<Integer, List<Combate>> registroCombates = registro.getCombates();

		System.out.println("Rondas registradas: ");

		/* registroCombates.forEach((ronda, combates) -> {
			System.out.println("=== Ronda " + ronda + " ===");
			combates.forEach(c -> System.out.println(c.getHeroe() + " (vida despues: " + c.getVidaHeroeDespues()
					+ ") vs " + c.getBestia() + " (vida despues: " + c.getVidaBestiaDespues() + ")"));
		}); */
		
		String narracion = "";
		
		try {
			narracion = resultado.get().toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}	
		
		System.out.println(narracion);
		
		gestorProcesos.shutdown();
	}
}
