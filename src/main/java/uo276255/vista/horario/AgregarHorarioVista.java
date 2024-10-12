package uo276255.vista.horario;

import uo276255.modelo.empleado.EmpleadoDTO;
import uo276255.modelo.horario.HorarioDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.List;

public class AgregarHorarioVista extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tablaEmpleados;
    private JButton btnAgregarHorario;
    private List<EmpleadoDTO> listaEmpleados;

    // Componentes del formulario de horario
    private JDialog dialogoHorario;
    private JComboBox<String> comboTipoHorario;
    private JComboBox<DayOfWeek> comboDiaSemana;
    private JSpinner spinnerFechaEspecifica;
    private JSpinner spinnerHoraInicio;
    private JSpinner spinnerHoraFin;
    private JButton btnGuardarHorario;

    // Listeners
    private ActionListener listenerAgregarHorario;
    private ActionListener listenerGuardarHorario;

    public AgregarHorarioVista() {
        setTitle("Agregar Horario a Empleado");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(panelPrincipal);

        JLabel lblTitulo = new JLabel("Selecciona un Empleado para Agregar Horario", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        tablaEmpleados = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tablaEmpleados);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        btnAgregarHorario = new JButton("Añadir Nuevo Horario");
        btnAgregarHorario.setEnabled(false); // Deshabilitado hasta que se seleccione un empleado
        panelPrincipal.add(btnAgregarHorario, BorderLayout.SOUTH);

        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {
            boolean seleccionado = tablaEmpleados.getSelectedRow() != -1;
            btnAgregarHorario.setEnabled(seleccionado);
        });

        // Inicializar el diálogo de horario
        inicializarDialogoHorario(); // Llamada añadida para inicializar el diálogo y sus componentes
    }

    private void inicializarDialogoHorario() {
        dialogoHorario = new JDialog(this, "Agregar Horario", true);
        dialogoHorario.setSize(400, 400);
        dialogoHorario.setLocationRelativeTo(this);
        dialogoHorario.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tipo de Horario
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogoHorario.add(new JLabel("Tipo de Horario:"), gbc);

        comboTipoHorario = new JComboBox<>(new String[]{"Semanal", "Específico"});
        gbc.gridx = 1;
        dialogoHorario.add(comboTipoHorario, gbc);

        // Día de la Semana
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialogoHorario.add(new JLabel("Día de la Semana:"), gbc);

        comboDiaSemana = new JComboBox<>(DayOfWeek.values());
        gbc.gridx = 1;
        dialogoHorario.add(comboDiaSemana, gbc);

        // Fecha Específica
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialogoHorario.add(new JLabel("Fecha Específica:"), gbc);

        spinnerFechaEspecifica = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFechaEspecifica, "yyyy-MM-dd");
        spinnerFechaEspecifica.setEditor(dateEditor);
        gbc.gridx = 1;
        dialogoHorario.add(spinnerFechaEspecifica, gbc);

        // Hora de Inicio
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialogoHorario.add(new JLabel("Hora de Inicio:"), gbc);

        spinnerHoraInicio = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorInicio = new JSpinner.DateEditor(spinnerHoraInicio, "HH:mm");
        spinnerHoraInicio.setEditor(timeEditorInicio);
        gbc.gridx = 1;
        dialogoHorario.add(spinnerHoraInicio, gbc);

        // Hora de Fin
        gbc.gridx = 0;
        gbc.gridy = 4;
        dialogoHorario.add(new JLabel("Hora de Fin:"), gbc);

        spinnerHoraFin = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorFin = new JSpinner.DateEditor(spinnerHoraFin, "HH:mm");
        spinnerHoraFin.setEditor(timeEditorFin);
        gbc.gridx = 1;
        dialogoHorario.add(spinnerHoraFin, gbc);

        // Botón Guardar
        btnGuardarHorario = new JButton("Guardar Horario");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        dialogoHorario.add(btnGuardarHorario, gbc);

        // Configurar visibilidad inicial
        comboTipoHorario.addActionListener(e -> actualizarVisibilidadCampos());
        actualizarVisibilidadCampos();
    }

    private void actualizarVisibilidadCampos() {
        String tipoHorario = (String) comboTipoHorario.getSelectedItem();
        if ("Semanal".equals(tipoHorario)) {
            comboDiaSemana.setEnabled(true);
            spinnerFechaEspecifica.setEnabled(false);
        } else {
            comboDiaSemana.setEnabled(false);
            spinnerFechaEspecifica.setEnabled(true);
        }
    }

    /**
     * Carga la lista de empleados en la tabla.
     *
     * @param empleados Lista de empleados.
     */
    public void cargarEmpleados(List<EmpleadoDTO> empleados) {
        this.listaEmpleados = empleados;
        String[] columnas = {"ID", "Nombre", "Apellido", "DNI", "Tipo Empleado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (EmpleadoDTO empleado : empleados) {
            Object[] fila = {
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getDni(),
                    empleado.getTipoEmpleado()
            };
            modeloTabla.addRow(fila);
        }

        tablaEmpleados.setModel(modeloTabla);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Obtiene el empleado seleccionado en la tabla.
     *
     * @return Empleado seleccionado o null si no hay selección.
     */
    public EmpleadoDTO getEmpleadoSeleccionado() {
        int indiceSeleccionado = tablaEmpleados.getSelectedRow();
        if (indiceSeleccionado != -1) {
            String idEmpleado = tablaEmpleados.getValueAt(indiceSeleccionado, 0).toString();
            for (EmpleadoDTO empleado : listaEmpleados) {
                if (empleado.getId().equals(idEmpleado)) {
                    return empleado;
                }
            }
        }
        return null;
    }

    /**
     * Agrega el listener para el botón "Añadir Nuevo Horario".
     *
     * @param listener Listener a agregar.
     */
    public void agregarListenerAgregarHorario(ActionListener listener) {
        this.listenerAgregarHorario = listener;
        btnAgregarHorario.addActionListener(listener);
    }

    /**
     * Agrega el listener para el botón "Guardar Horario" en el diálogo.
     *
     * @param listener Listener a agregar.
     */
    public void agregarListenerGuardarHorario(ActionListener listener) {
        this.listenerGuardarHorario = listener;
        btnGuardarHorario.addActionListener(listener);
    }

    /**
     * Muestra el diálogo para ingresar los datos del nuevo horario.
     */
    public void mostrarDialogoHorario() {
        // Resetear campos
        comboTipoHorario.setSelectedIndex(0);
        comboDiaSemana.setSelectedIndex(0);
        spinnerFechaEspecifica.setValue(new java.util.Date());
        spinnerHoraInicio.setValue(new java.util.Date());
        spinnerHoraFin.setValue(new java.util.Date());

        dialogoHorario.setVisible(true);
    }

    /**
     * Obtiene los datos ingresados en el formulario de horario.
     *
     * @return HorarioDTO con los datos ingresados.
     */
    public HorarioDTO obtenerDatosHorario() throws IllegalArgumentException {
        String tipoHorario = (String) comboTipoHorario.getSelectedItem();
        LocalTime horaInicio = convertirDateALocalTime((java.util.Date) spinnerHoraInicio.getValue());
        LocalTime horaFin = convertirDateALocalTime((java.util.Date) spinnerHoraFin.getValue());

        if (!horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin.");
        }

        if (Duration.between(horaInicio, horaFin).toHours() > 8) {
            throw new IllegalArgumentException("La duración no puede exceder las 8 horas.");
        }

        if ("Semanal".equals(tipoHorario)) {
            DayOfWeek diaSemana = (DayOfWeek) comboDiaSemana.getSelectedItem();
            return new HorarioDTO(diaSemana, horaInicio, horaFin);
        } else {
            LocalDate fechaEspecifica = convertirDateALocalDate((java.util.Date) spinnerFechaEspecifica.getValue());
            return new HorarioDTO(fechaEspecifica, horaInicio, horaFin);
        }
    }

    private LocalTime convertirDateALocalTime(java.util.Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    private LocalDate convertirDateALocalDate(java.util.Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param mensaje Mensaje a mostrar.
     */
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de éxito al usuario.
     *
     * @param mensaje Mensaje a mostrar.
     */
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Cierra el diálogo de horario.
     */
    public void cerrarDialogoHorario() {
        dialogoHorario.dispose();
    }
}
