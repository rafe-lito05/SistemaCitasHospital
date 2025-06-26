package persistencia;

import modelo.Cita;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class GestorCitas {

   private static final String ARCHIVO_CITAS = "citas.dat";
    private List<Cita> citas;

    public GestorCitas() {
        citas = new ArrayList<>();
        cargarCitas();
    }

    public String generarNuevoIdCita() {
        int ultimoNumero = 0;
        for (Cita c : citas) {
            if (c.getIdCita().startsWith("CITA-")) {
                try {
                    int numero = Integer.parseInt(c.getIdCita().substring(5));
                    if (numero > ultimoNumero) {
                        ultimoNumero = numero;
                    }
                } catch (NumberFormatException e) {
                    // Ignorar IDs con formato incorrecto
                }
            }
        }
        return "CITA-" + (ultimoNumero + 1);
    }

    public void agregarCita(Cita cita) {
        citas.add(cita);
        guardarCitas();
    }

   public List<Cita> obtenerCitasPorPaciente(String idPaciente) {
        List<Cita> citasPaciente = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getIdPaciente().equals(idPaciente) && cita.isActiva()) {
                citasPaciente.add(cita);
            }
        }
        return citasPaciente;
    }

    public List<Cita> obtenerTodasLasCitas() {
        List<Cita> citasActivas = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.isActiva()) {
                citasActivas.add(cita);
            }
        }
        return citasActivas;
    }

   public boolean cancelarCita(String idCita) {
        for (Cita cita : citas) {
            if (cita.getIdCita().equals(idCita) && cita.isActiva()) {
                cita.setActiva(false);
                guardarCitas();
                return true;
            }
        }
        return false;
    }
  
}
