package vista;

import modelo.Cita;
import persistencia.GestorCitas;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SistemaCitasHospital {
    private GestorCitas gestorCitas;
    private JFrame frame;
    private Color colorFondo = new Color(240, 248, 255);
    private Color colorBoton = new Color(70, 130, 180);
    private Color colorTexto = new Color(25, 25, 112);
    private Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 16);
    private Font fuenteNormal = new Font("Segoe UI", Font.PLAIN, 14);

    public SistemaCitasHospital() {
        gestorCitas = new GestorCitas();
        inicializarGUI();
    }

    private void inicializarGUI() {
        frame = new JFrame("Sistema de Citas Hospitalarias");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(fuenteTitulo);

        tabbedPane.addTab("Programar Cita", crearPanelProgramarCita());
        tabbedPane.addTab("Ver Citas", crearPanelVerCitas());
        tabbedPane.addTab("Gestionar Citas", crearPanelGestionarCitas());

        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);
        frame.add(panelPrincipal);
        frame.setVisible(true);
    }
}
