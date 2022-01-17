package org.iesalandalus.programacion.citasclinica.modelo;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Paciente {

	private static final String ER_DNI = "([0-9]{8})([A-Za-z])";
	private static final String ER_TELEFONO = "([0-9]{9})";
	private String nombre;
	private String dni;
	private String telefono;

//	Creamos el método formateaNombre. Primero quitamos los espacios extras y ponemos todo el String en minúscula. Después creamos un array tipo char en el 
//	que copiaremos el String recorriéndolo con un for. Después recorremos el array de char con otro for 
//	y, cada vez que encuentre un espacio, ponemos en mayúscula el siguiente char. Por último, ponemos el primer char del array en mayúscula, 
//	pasamos todo el array char a un String y lo devolvemos.
	private String formateaNombre(String nombreSinFormato) {
		String nombre = nombreSinFormato.trim().replaceAll("\\s{2,}", " ").toLowerCase();
		char cadenaChar[] = new char[nombre.length()];
		for (int i = 0; i < nombre.length(); ++i) {
			cadenaChar[i] = nombre.charAt(i);
		}
		for (int i = 0; i < cadenaChar.length; ++i) {
			if (cadenaChar[i] == ' ') {
				cadenaChar[i + 1] = Character.toUpperCase(cadenaChar[i + 1]);
			}
		}
		cadenaChar[0] = Character.toUpperCase(cadenaChar[0]);
		nombre = String.valueOf(cadenaChar);
		return nombre;
	}

//	Creamos el método comprobarLetraDni. Establecemos una bandera false por efecto. Después generamos los grupos de expresiones con Pattern y Matcher.
//	Si el formato es correcto, pasamos el primer grupo a numero y obtenemos su resto al dividir entre 23. Después creamos un array tipo char con todas
//	las posibles letras del DNI y pasamos a mayúscula el char de la letra del DNI para compararlas. Si coinciden, devolvemos la bandera en true.
	private boolean comprobarLetraDni(String dni) {
		boolean letraCorrecta = false;
		Pattern pat = Pattern.compile(ER_DNI);
		Matcher mat = pat.matcher(dni);
		if (mat.matches()) {
			int parteNumerica = Integer.parseInt(mat.group(1)) % 23;

			char letra = Character.toUpperCase(dni.charAt(8));
			char tablaLetra[] = new char[] { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z',
					'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E' };
			if (letra == tablaLetra[parteNumerica]) {
				letraCorrecta = true;
			}
		}
		return letraCorrecta;
	}

//	Creamos el método getIniciales. Obtenemos el nombre mediante getNombre(), creamos un array tipo char para copiar el String del nombre y otro
//	array tipo char para guardar las iniciales. Copiamos el String nombre en el primer array, y después lo recorremos guardando los char tras los
//	espacios en el array para las iniciales. Una vez termine, colocamos el primer char incial de íncide 0 en el primer lugar del array de las 
//	iniciales y pasamos su valor a un String. Devolvemos dicho String pasándolo todo a mayúsculas y eliminando todos los espacios finales.
	private String getIniciales() {
		String nombre = getNombre();
		char cadenaChar[] = new char[nombre.length()];
		char iniciales[] = new char[nombre.length()];
		int indice = 1;
		for (int i = 0; i < nombre.length(); ++i) {
			cadenaChar[i] = nombre.charAt(i);
		}
		for (int i = 1; i < cadenaChar.length; ++i) {
			if (cadenaChar[i] == ' ') {
				iniciales[indice] = cadenaChar[i + 1];
				indice++;
			}
		}
		iniciales[0] = cadenaChar[0];
		String inicialesFinales = String.valueOf(iniciales);
		return inicialesFinales.toUpperCase().trim();
	}

//	Getters y Setters validando nulls, formatos y demás.
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			throw new NullPointerException("ERROR: El nombre de un paciente no puede ser nulo o vacío.");
		} else {
			this.nombre = formateaNombre(nombre);
		}
	}

	public String getDni() {
		return dni;
	}

	private void setDni(String dni) {
		if (dni == null || dni.trim().isEmpty()) {
			throw new NullPointerException("ERROR: El DNI de un paciente no puede ser nulo o vacío.");
		}
		Pattern pat = Pattern.compile(ER_DNI);
		Matcher mat = pat.matcher(dni);

		if (mat.matches() == false) {
			throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
		} else if (comprobarLetraDni(dni) == false) {
			throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
		} else {
			this.dni = dni;
		}
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		if (telefono == null || telefono.isBlank()) {
			throw new NullPointerException("ERROR: El teléfono de un paciente no puede ser nulo o vacío.");
		}
		Pattern pat = Pattern.compile(ER_TELEFONO);
		Matcher mat = pat.matcher(telefono);
		if (mat.matches() == false) {
			throw new IllegalArgumentException("ERROR: El teléfono no tiene un formato válido.");
		} else {
			this.telefono = telefono;
		}
	}

//	Creamos el constructor con parámetros
	public Paciente(String nombre, String dni, String telefono) {
		setNombre(nombre);
		setDni(dni);
		setTelefono(telefono);
	}

//	Creamos el constructor copia lanzando excepción con null
	public Paciente(Paciente p) {
		if (p == null) {
			throw new NullPointerException("ERROR: No es posible copiar un paciente nulo.");
		}
		setNombre(p.getNombre());
		setDni(p.getDni());
		setTelefono(p.getTelefono());
	}

//	Hash y equals
	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		return Objects.equals(dni, other.dni);
	}

//	toString con las iniciales incluídas
	@Override
	public String toString() {
		return "nombre=" + getNombre() + " (" + getIniciales() + "), DNI=" + getDni() + ", teléfono=" + getTelefono();
	}

}
