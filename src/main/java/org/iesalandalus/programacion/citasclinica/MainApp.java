package org.iesalandalus.programacion.citasclinica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.citasclinica.modelo.Cita;
import org.iesalandalus.programacion.citasclinica.modelo.Citas;
import org.iesalandalus.programacion.citasclinica.modelo.Paciente;
import org.iesalandalus.programacion.citasclinica.vista.Consola;
import org.iesalandalus.programacion.citasclinica.vista.Opciones;

public class MainApp {
//	Creamos los atributos de la clase y el objeto Citas que contendrá el array de Cita´s donde se almacerán las citas.
	private static final int NUM_MAX_CITAS = 20;
	private static Citas citas = new Citas(NUM_MAX_CITAS);

//	Creamos el método Main que nos mostrará el Menú y ejecutará la opción que elijamos hasta que no elijamos salir.
	public static void main(String[] args) {
		boolean salir=false;
		do {
			Consola.mostrarMenu();
			Opciones opcionElegida=Consola.elegirOpcion();
			ejecutarOpcion(opcionElegida);
			if (opcionElegida==Opciones.SALIR) {
				salir=true;
			}
		} while (salir==false);	
	}

//	Creamos el método insertarCita, que mediante leerCita nos pedirá los datos de una nueva cita e intentará insetarla mediante el método insertar. 
	private static void insertarCita() {
		try {
			Cita nuevaCita = new Cita(Consola.leerCita());
			citas.insertar(nuevaCita);
			System.out.println("Cita creada correctamente.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
	}

//	Creamos el método buscarCita, al que le pasaremos una fecha mediante leerFechaHora. Después creará una cita con los datos obtenidos y el pacientePrueba, para después comprobar
//	si la cita existe mediante el método buscar, haciendo uso de la fecha para ello (por eso los datos del paciente son irrelevantes). Nos devuelve el string de haber coincidencias.
	public static void buscarCita() {
		LocalDateTime fechaHora = null;
		Paciente pacientePrueba = new Paciente("Manuel", "93627158T", "600000000");
		Cita citaEncontrada = null;
		Cita nuevaCita = null;
		try {
			fechaHora = Consola.leerFechaHora();
			nuevaCita = new Cita(pacientePrueba, fechaHora);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		citaEncontrada = citas.buscar(nuevaCita);
		if (citaEncontrada == null) {
			System.out.println("No existe ninguna cita para esa fecha y hora");
		} else {
			System.out.println(citaEncontrada.toString());
		}
	}

//	Creamos el método borrarCita, que nos pedirá una fecha y creará una cita con ella y el pacientePrueba. Después intentará borrar la cita mediante el método borrar.
	public static void borrarCita() {
		LocalDateTime fechaHora = null;
		Paciente pacientePrueba = new Paciente("Manuel", "93627158T", "600000000");
		Cita nuevaCita = null;
		try {
			fechaHora = Consola.leerFechaHora();
			nuevaCita = new Cita(pacientePrueba, fechaHora);
			citas.borrar(nuevaCita);
			System.out.println("Cita borrada correctamente");
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	
//	Creamos el método mostrarCitas, que nos retorna el string con el array de citas existentes.
	public static void mostrarCitas() {
		Cita [] totalCitas=citas.getCitas();
		if (citas.getTamano()==0) {
			System.out.println("No hay ninguna cita que mostrar");
		}
		else {
			for (int i=0;i<citas.getTamano();i++) {
				System.out.println(totalCitas[i].toString());
			}
		}
	}
	
//	Creamos el método mostrarCitasDia, que nos devolverá el string con un array de las citas del día que introduzcamos mediante leerFecha
	public static void mostrarCitasDia() {
		LocalDate fecha=Consola.leerFecha();
		Cita [] citasFecha=citas.getCitas(fecha);
		for (int i=0;i<citasFecha.length;i++) {
			System.out.println(citasFecha[i].toString());
		}
	}
	
//	Creamos el método ejecutarOpcion, que ante una opción elegida correrá el método correspondiente a ésta o terminará la ejecución.
	public static void ejecutarOpcion(Opciones opcionElegida) {
		switch(opcionElegida) {
		case INSERTAR_CITA:
			insertarCita();
			break;
		case BUSCAR_CITA:
			buscarCita();
			break;
		case BORRAR_CITA:
			borrarCita();
			break;
		case MOSTRAR_CITAS:
			mostrarCitas();
			break;
		case MOSTRAR_CITAS_DIA:
			mostrarCitasDia();
			break;
		case SALIR:
			System.out.print("¡Hasta pronto!");
			break;
		}
	}
	
	

}
