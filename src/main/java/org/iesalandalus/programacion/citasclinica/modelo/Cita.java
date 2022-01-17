package org.iesalandalus.programacion.citasclinica.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Cita {
	public static final String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm";
	private LocalDateTime fechaHora;
	public Paciente paciente;

//	getters y setters
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public Paciente getPaciente() {
		return new Paciente(paciente);
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		if (fechaHora == null)
			throw new NullPointerException("ERROR: La fecha y hora de una cita no puede ser nula.");
		this.fechaHora = fechaHora;
	}

	private void setPaciente(Paciente paciente) {
		if (paciente == null) {
			throw new NullPointerException("ERROR: El paciente de una cita no puede ser nulo.");
		}
		this.paciente = new Paciente(paciente);
	}

//	Creamos el constructor con parámetros
	public Cita(Paciente paciente, LocalDateTime fechaHora) {
		setPaciente(paciente);
		setFechaHora(fechaHora);
	}

//	Creamos el constructor copia lanzando excepción con null
	public Cita(Cita c) {
		if (c == null) {
			throw new NullPointerException("ERROR: No se puede copiar una cita nula.");
		}
		setPaciente(c.getPaciente());
		setFechaHora(c.getFechaHora());
	}

//	Creamos hashCode y equals
	@Override
	public int hashCode() {
		return Objects.hash(fechaHora);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cita other = (Cita) obj;
		return Objects.equals(fechaHora, other.fechaHora);
	}

//	Creamos toString
	@Override
	public String toString() {
		return paciente.toString() + ", fechaHora=" + fechaHora.format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA));
	}

}
