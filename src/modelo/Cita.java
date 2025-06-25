package modelo;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Cita implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idCita;
    private String idPaciente;
    private String nombrePaciente;
    private String especialidad;
    private transient LocalDate fecha;
    private transient LocalTime hora;
    private boolean activa;

    public Cita(String idCita, String idPaciente, String nombrePaciente,
                String especialidad, LocalDate fecha, LocalTime hora) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.nombrePaciente = nombrePaciente;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.hora = hora;
        this.activa = true;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(fecha.toString());
        out.writeObject(hora.toString());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        fecha = LocalDate.parse((String) in.readObject());
        hora = LocalTime.parse((String) in.readObject());
    }

    // Getters y Setters
    public String getIdCita() { return idCita; }
    public String getIdPaciente() { return idPaciente; }
    public String getNombrePaciente() { return nombrePaciente; }
    public String getEspecialidad() { return especialidad; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public boolean isActiva() { return activa; }
    public String getEstado() { return activa ? "ACTIVA" : "CANCELADA"; }

    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public void setActiva(boolean activa) { this.activa = activa; }

    @Override
    public String toString() {
        return "Cita ID: " + idCita +
                "\nPaciente: " + nombrePaciente + " (ID: " + idPaciente + ")" +
                "\nEspecialidad: " + especialidad +
                "\nFecha: " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " Hora: " + hora.format(DateTimeFormatter.ofPattern("HH:mm")) +
                "\nEstado: " + getEstado();
    }
}