package org.iesalandalus.programacion.citasclinica.modelo;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

public class Citas {
	private int capacidad;
	private int tamano;

//	Declaramos el array totalCitas, donde iremos guardando, borrando y consultando las citas de la clase Cita
	Cita[] totalCitas;

//	Creamos getters y setters sin parámetros
	public int getCapacidad() {
		return capacidad;
	}

	public int getTamano() {
		return tamano;
	}

	public Cita[] getCitas() {
		return totalCitas;
	}

//	Creamos constructor sin parámetros, que a su vez inicializa totalCitas con la capacidad del array.
	public Citas(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		totalCitas = new Cita[capacidad];
		this.capacidad = capacidad;
		this.tamano = 0;
	}

//	Creamos los métodos tamanoSuperado y capacidadSuperada. Tamaño nos indica el número de elementos en el array, así como la posición en la que insertar el siguiente.
//	Capacidad hace referencia a los espacios totales del array. Si tamaño iguala o supera a  capacidad, el array estará lleno y no se podrán insertar más elementos
	private boolean tamanoSuperado(int indice) {
		if (indice >= tamano) {
			return true;
		}
		return false;
	}

	private boolean capacidadSuperada(int tamano) {
		if (tamano >= capacidad) {
			return true;
		}
		return false;
	}

//	Creamos buscarIndice, que recorrerá el array comprobando si hay alguna cita igual a la del parámetro. 
//	Si es así, nos devuelve su índice, y si no, develve tamano+1
	private int buscarIndice(Cita cita) {
		boolean encontrado = false;
		int resultado = 0;
		for (int i = 0; i <= tamano-1; i++) {
			if (cita.equals(totalCitas[i])) {
				encontrado = true;
				resultado = i;
			}
		}
		if (encontrado) {
			return resultado;
		} else {
			return tamano + 1;
		}
	}

//	Creamos insertar, que primero busca con buscarIndice, que buscará si la cita existe y nos dará su índice. Si no, dos dará tamano+1, así que pasamos el índice
//	obtenido por tamanoSuperado. Si nos da true, significa que el resultado de buscarIndice ha sido tamano+1, ergo no existe esa cita y tenemos que insertarla en el
//	siguiente índice libre, que sería tamano
	public void insertar(Cita cita) throws OperationNotSupportedException {
		int posibleHueco = buscarIndice(cita);
		if (cita == null) {
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		} else if (capacidadSuperada(tamano)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más citas.");
		} else if (tamanoSuperado(posibleHueco)) {
			totalCitas[tamano] = new Cita(cita);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una cita para esa fecha y hora.");
		}
	}

//	Creamos buscar, que recorre el array en busca de una cita igual a la cita del parámetro o hasta que se acaba. Si encuentra una cita igual, pone
//	encontrado en true y sale del for. Si encontrado está en true, devuelve la cita y si no, devuelve nulo
	public Cita buscar(Cita cita) {
		boolean encontrado = false;
		int indice = 0;
		for (int i = 0; i <= tamano - 1; i++) {
			if (cita.equals(totalCitas[i])) {
				encontrado = true;
				indice = i;
			}
		}
		if (encontrado == true) {
			return new Cita(totalCitas[indice]);
		} else {
			return null;
		}
	}

//	Creamos este método, que recorre el array desde el índice que le damos y desde ahí pisa esa cita con su cita de la derecha durante todo el tamaño
	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		for (int i = indice; i < tamano-1; ++i) {
			totalCitas[i] = new Cita(totalCitas[i + 1]);
		}
	}

//	Creamos borrar, que primero comprueba si la cita existe con buscar y luego nos da su índice con buscarIndice. Después lo desplaza todo con el método
//	correspondiente y el índice obtenido, después pone a null el último elemento porque se quedaría copiado y podría dar lugar a error y reduce el tamaño.
	public void borrar(Cita cita) throws OperationNotSupportedException {
		int indice = 0;
		if (cita == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una cita nula.");
		} else if (cita.equals(buscar(cita))) {
			indice = buscarIndice(cita);
			desplazarUnaPosicionHaciaIzquierda(indice);
			totalCitas[tamano - 1] = null;
			tamano--;
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna cita para esa fecha y hora.");
		}

	}

//	Creamos getCitas, que crea un array de capacidad igual al tamaño y que contendrá todas las coincidencias. Después recorremos el array
//	totalCitas, obteniendo las LocalDateTime de éstas y transformándolas a LocalDate para compararlas con la fecha del parámetro. Si coinciden, crea una
//	copia de dicha cita en el primer índice vacío del array recién creado y luego incrementa dicho índice para seguir insertando.
	public Cita[] getCitas(LocalDate fecha) {
		if (fecha==null) {
			throw new NullPointerException("ERROR: No se pueden devolver las citas para un día nulo.");
		}
		Cita[] citasFechaBuscada = new Cita[tamano];
		int indiceCopia = 0;
		for (int i = 0; i < tamano ; ++i) {
			if (fecha.equals(totalCitas[i].getFechaHora().toLocalDate())) {
				citasFechaBuscada[indiceCopia] = new Cita(totalCitas[i]);
				indiceCopia++;
			}
		}
		return citasFechaBuscada;
	}

}
