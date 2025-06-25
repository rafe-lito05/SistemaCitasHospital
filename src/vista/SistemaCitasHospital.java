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

    private JPanel crearPanelVerCitas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(colorFondo);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelSuperior.setBackground(colorFondo);

        JLabel titulo = new JLabel("Listado de Citas Programadas");
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(colorTexto);
        panelSuperior.add(titulo);

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelFiltros.setBackground(colorFondo);

        panelFiltros.add(crearEtiqueta("Filtrar por ID Paciente:"));
        JTextField txtIdPaciente = crearCampoTexto();
        txtIdPaciente.setPreferredSize(new Dimension(150, 25));
        panelFiltros.add(txtIdPaciente);

        JButton btnBuscar = crearBoton("Filtrar", Color.WHITE);
        panelFiltros.add(btnBuscar);

        JButton btnMostrarTodas = crearBoton("Mostrar Todas", Color.WHITE);
        panelFiltros.add(btnMostrarTodas);

        panelSuperior.add(panelFiltros);
        panel.add(panelSuperior, BorderLayout.NORTH);

        JTextArea areaCitas = new JTextArea();
        areaCitas.setFont(fuenteNormal);
        areaCitas.setEditable(false);
        areaCitas.setLineWrap(true);
        areaCitas.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(areaCitas);
        scrollPane.setPreferredSize(new Dimension(900, 500));
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Citas Programadas"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        panel.add(scrollPane, BorderLayout.CENTER);

        mostrarTodasLasCitas(areaCitas);

        btnBuscar.addActionListener(e -> {
            String idPaciente = txtIdPaciente.getText().trim();
            if (!idPaciente.isEmpty()) {
                List<Cita> citasPaciente = gestorCitas.obtenerCitasPorPaciente(idPaciente);
                mostrarCitasEnArea(areaCitas, citasPaciente, "Citas del paciente ID: " + idPaciente);
            }
        });

        btnMostrarTodas.addActionListener(e -> {
            mostrarTodasLasCitas(areaCitas);
            txtIdPaciente.setText("");
        });

        return panel;
    }

    private JPanel crearPanelGestionarCitas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(colorFondo);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 5, 5));
        panelSuperior.setBackground(colorFondo);

        JLabel titulo = new JLabel("Gestión de Citas");
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(colorTexto);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelSuperior.add(titulo);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBusqueda.setBackground(colorFondo);

        panelBusqueda.add(crearEtiqueta("ID de Cita:"));
        JTextField txtIdCita = crearCampoTexto();
        txtIdCita.setPreferredSize(new Dimension(150, 25));
        panelBusqueda.add(txtIdCita);

        JButton btnBuscar = crearBoton("Buscar Cita", Color.WHITE);
        panelBusqueda.add(btnBuscar);

        panelSuperior.add(panelBusqueda);
        panel.add(panelSuperior, BorderLayout.NORTH);

        JPanel panelDetalles = new JPanel(new GridLayout(0, 2, 15, 10));
        panelDetalles.setBackground(colorFondo);
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles de la Cita"));

        panelDetalles.add(crearEtiqueta("Especialidad:"));
        JComboBox<String> cmbEspecialidad = new JComboBox<>(new String[]{
                "Cardiología", "Dermatología", "Pediatría", "Neurología",
                "Oftalmología", "Traumatología", "Ginecología"
        });
        estilizarCombo(cmbEspecialidad);
        panelDetalles.add(cmbEspecialidad);

        panelDetalles.add(crearEtiqueta("Fecha (dd/MM/yyyy):"));
        JTextField txtFecha = crearCampoTexto();
        panelDetalles.add(txtFecha);

        panelDetalles.add(crearEtiqueta("Hora (HH:mm):"));
        JTextField txtHora = crearCampoTexto();
        panelDetalles.add(txtHora);

        JButton btnActualizar = crearBoton("Actualizar Cita", Color.WHITE);
        panelDetalles.add(btnActualizar);

        JButton btnCancelar = crearBoton("Cancelar Cita", new Color(220, 80, 80));
        btnCancelar.setForeground(Color.WHITE);
        panelDetalles.add(btnCancelar);

        panelDetalles.setEnabled(false);
        for (Component c : panelDetalles.getComponents()) {
            c.setEnabled(false);
        }

        panel.add(panelDetalles, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            String idCita = txtIdCita.getText().trim();
            if (idCita.isEmpty()) {
                mostrarError(panel, "Por favor ingrese un ID de cita");
                return;
            }

            Cita cita = gestorCitas.buscarCita(idCita);

            if (cita == null) {
                mostrarError(panel, "No se encontró una cita activa con ID: " + idCita);
            } else {
                panelDetalles.setEnabled(true);
                for (Component c : panelDetalles.getComponents()) {
                    c.setEnabled(true);
                }

                cmbEspecialidad.setSelectedItem(cita.getEspecialidad());
                txtFecha.setText(cita.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                txtHora.setText(cita.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                LocalDate fecha = LocalDate.parse(txtFecha.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalTime hora = LocalTime.parse(txtHora.getText(),
                        DateTimeFormatter.ofPattern("HH:mm"));

                boolean actualizado = gestorCitas.actualizarCita(
                        txtIdCita.getText(),
                        cmbEspecialidad.getSelectedItem().toString(),
                        fecha,
                        hora
                );

                if (actualizado) {
                    JOptionPane.showMessageDialog(panel, "Cita actualizada exitosamente!",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    mostrarError(panel, "No se pudo actualizar la cita (puede estar cancelada)");
                }
            } catch (DateTimeParseException ex) {
                mostrarError(panel, "Formato de fecha u hora incorrecto. Use dd/MM/yyyy para fecha y HH:mm para hora.");
            } catch (Exception ex) {
                mostrarError(panel, "Error inesperado: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                    panel,
                    "¿Está seguro que desea cancelar esta cita?",
                    "Confirmar Cancelación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean cancelada = gestorCitas.cancelarCita(txtIdCita.getText());
                if (cancelada) {
                    JOptionPane.showMessageDialog(panel, "Cita cancelada exitosamente!",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    panelDetalles.setEnabled(false);
                    for (Component c : panelDetalles.getComponents()) {
                        c.setEnabled(false);
                    }
                    txtIdCita.setText("");
                } else {
                    mostrarError(panel, "No se pudo cancelar la cita (puede que ya esté cancelada)");
                }
            }
        });

        return panel;
    }
}
