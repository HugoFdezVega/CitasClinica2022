package org.iesalandalus.programacion.citasclinica.vista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.citasclinica.modelo.Cita;
import org.iesalandalus.programacion.citasclinica.modelo.Paciente;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private Consola() {

	}

	public static void mostrarMenu() {
		System.out.println("Menú");
		System.out.println("1. Insertar cita");
		System.out.println("2. Buscar cita");
		System.out.println("3. Borrar cita");
		System.out.println("4. Mostrar todas las citas");
		System.out.println("5. Mostrar todas las citas de un día");
		System.out.println("6. Salir");
	}

	public static Opciones elegirOpcion() {
		int seleccion = 0;
		Opciones opcionElegida = null;
		do {
			System.out.println("Seleccione una opción del Menú");
			seleccion = Entrada.entero();
		} while (seleccion < 0 || seleccion > 6);
		switch (seleccion) {
		case 1:
			opcionElegida = Opciones.INSERTAR_CITA;
			break;
		case 2:
			opcionElegida = Opciones.BUSCAR_CITA;
			break;
		case 3:
			opcionElegida = Opciones.BORRAR_CITA;
			break;
		case 4:
			opcionElegida = Opciones.MOSTRAR_CITAS;
			break;
		case 5:
			opcionElegida = Opciones.MOSTRAR_CITAS_DIA;
			break;
		case 6:
			opcionElegida = Opciones.SALIR;
			break;
		}
		return opcionElegida;
	}

//	Creamos el método leerPaciente, que nos pide el nombre, el dni y el teléfono del paciente y luego crea un paciente nuevo con dichos datos, donde se validarán, y nos retorna una
//	copia del mismo
	public static Paciente leerPaciente() throws OperationNotSupportedException {
		Paciente nuevoPaciente;
		System.out.print("Introduzca el nombre del paciente: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduzca el DNI del paciente (formato 000000000A): ");
		String dni = Entrada.cadena();
		System.out.print("Introduzca el teléfono móvil del paciente (formato 000000000): ");
		String telefono = Entrada.cadena();
		nuevoPaciente = new Paciente(nombre, dni, telefono);
		return new Paciente(nuevoPaciente);
	}

//	Creamos el método leerFechaHora, que nos pedirá una fecha y una hora en cierto formato, que se validará y estará pidiendo los datos hasta que el formato no sea válido.
	public static LocalDateTime leerFechaHora() {
		String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm";
		LocalDateTime fechaHoraFinal = null;
		boolean bandera = true;
		DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA);
		do {
			try {
				System.out.println("Introduzca una fecha y una hora para su cita (formato dd/MM/yyyy HH:mm):");
				String fechaIntroducida = Entrada.cadena();
				fechaHoraFinal = LocalDateTime.parse(fechaIntroducida, formatoFechaHora);
				bandera = true;

			} catch (DateTimeParseException e) {
				System.out.println("Formato incorrecto");
				bandera = false;
			}
		} while (bandera == false);
		return fechaHoraFinal;
	}

//	Creamos el método leerCita, que nos pedirá primero los datos del paciente con el método leerPaciente y después la fecha y hora con el método leerFechaHora. Nos devolverá
//	una copia de la cita.
	public static Cita leerCita() throws OperationNotSupportedException {
		Cita nuevaCita = new Cita(leerPaciente(), leerFechaHora());
		return new Cita(nuevaCita);
	}

//	Creamos el método leerFecha, que nos pedirá una fecha en cierto formato, dicho formato se validará y estará pidiéndonos la fecha mientras no se valide el formato.
	public static LocalDate leerFecha() {
		String FORMATO_FECHA = "dd/MM/yyyy";
		LocalDate fechaFinal = null;
		boolean bandera = true;
		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA);
		do {
			try {
				System.out.println("Introduzca una fecha (formato dd/MM/yyyy):");
				String fechaIntroducida = Entrada.cadena();
				fechaFinal = LocalDate.parse(fechaIntroducida, formatoFecha);
				bandera = true;

			} catch (DateTimeParseException e) {
				System.out.println("Formato incorrecto");
				bandera = false;
			}
		} while (bandera == false);
		return fechaFinal;
	}

}
