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

    private JPanel crearPanelProgramarCita() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(colorFondo);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Programar Nueva Cita");
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(colorTexto);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(crearEtiqueta("ID de Cita:"), gbc);
        JTextField txtIdCita = crearCampoTexto();
        txtIdCita.setEditable(false);
        txtIdCita.setText(gestorCitas.generarNuevoIdCita());
        gbc.gridx = 1;
        panel.add(txtIdCita, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(crearEtiqueta("ID de Paciente:"), gbc);
        JTextField txtIdPaciente = crearCampoTexto();
        gbc.gridx = 1;
        panel.add(txtIdPaciente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(crearEtiqueta("Nombre de Paciente:"), gbc);
        JTextField txtNombrePaciente = crearCampoTexto();
        gbc.gridx = 1;
        panel.add(txtNombrePaciente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(crearEtiqueta("Especialidad:"), gbc);
        JComboBox<String> cmbEspecialidad = new JComboBox<>(new String[]{
                "Cardiología", "Dermatología", "Pediatría", "Neurología",
                "Oftalmología", "Traumatología", "Ginecología"
        });
        estilizarCombo(cmbEspecialidad);
        gbc.gridx = 1;
        panel.add(cmbEspecialidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(crearEtiqueta("Fecha (dd/MM/yyyy):"), gbc);
        JTextField txtFecha = crearCampoTexto();
        gbc.gridx = 1;
        panel.add(txtFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(crearEtiqueta("Hora (HH:mm):"), gbc);
        JTextField txtHora = crearCampoTexto();
        gbc.gridx = 1;
        panel.add(txtHora, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        JButton btnProgramar = crearBoton("Programar Cita", Color.WHITE);
        panel.add(btnProgramar, gbc);

        btnProgramar.addActionListener(e -> {
            try {
                LocalDate fecha = LocalDate.parse(txtFecha.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalTime hora = LocalTime.parse(txtHora.getText(),
                        DateTimeFormatter.ofPattern("HH:mm"));

                if (txtIdPaciente.getText().isEmpty() || txtNombrePaciente.getText().isEmpty()) {
                    throw new IllegalArgumentException("Todos los campos deben estar completos");
                }

                Cita nuevaCita = new Cita(
                        txtIdCita.getText(),
                        txtIdPaciente.getText(),
                        txtNombrePaciente.getText(),
                        cmbEspecialidad.getSelectedItem().toString(),
                        fecha,
                        hora
                );

                gestorCitas.agregarCita(nuevaCita);
                JOptionPane.showMessageDialog(panel, "Cita programada exitosamente!\nID: " + txtIdCita.getText(),
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                txtIdCita.setText(gestorCitas.generarNuevoIdCita());
                limpiarCampos(txtIdPaciente, txtNombrePaciente, txtFecha, txtHora);
            } catch (DateTimeParseException ex) {
                mostrarError(panel, "Formato de fecha u hora incorrecto. Use dd/MM/yyyy para fecha y HH:mm para hora.");
            } catch (IllegalArgumentException ex) {
                mostrarError(panel, ex.getMessage());
            } catch (Exception ex) {
                mostrarError(panel, "Error inesperado: " + ex.getMessage());
            }
        });

        return panel;
    }
}
