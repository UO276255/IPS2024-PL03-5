package uo276255.modelo.empleado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uo276255.modelo.horario.HorarioDTO;

/**
 * Clase que representa un empleado con sus datos principales.
 */
public class EmpleadoDTO {

    private String id;
    private String nombre;
    private String apellido;
    private String dni;
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTipoEmpleado() {
		return tipoEmpleado;
	}

	public void setTipoEmpleado(String tipoEmpleado) {
		this.tipoEmpleado = tipoEmpleado;
	}

	public String getTipoDetalle() {
		return tipoDetalle;
	}

	public void setTipoDetalle(String tipoDetalle) {
		this.tipoDetalle = tipoDetalle;
	}

	public double getSalarioAnualBruto() {
		return salarioAnualBruto;
	}

	public void setSalarioAnualBruto(double salarioAnualBruto) {
		this.salarioAnualBruto = salarioAnualBruto;
	}

	public void setHorarios(List<HorarioDTO> horarios) {
		this.horarios = horarios;
	}

	private String telefono;
    private LocalDate fechaNacimiento;
    private String tipoEmpleado;
    private String tipoDetalle;
    private double salarioAnualBruto;
    private List<HorarioDTO> horarios;

    /**
     * Constructor vacío de EmpleadoDTO.
     */
    public EmpleadoDTO() {
        this.horarios = new ArrayList<>();
    }

    /**
     * Constructor de EmpleadoDTO con todos los campos.
     *
     * @param id                Identificador del empleado.
     * @param nombre            Nombre del empleado.
     * @param apellido          Apellido del empleado.
     * @param dni               DNI del empleado.
     * @param telefono          Teléfono del empleado.
     * @param fechaNacimiento   Fecha de nacimiento del empleado.
     * @param tipoEmpleado      Tipo de empleado.
     * @param tipoDetalle       Detalles adicionales del tipo de empleado.
     * @param salarioAnualBruto Salario anual bruto del empleado.
     */
    public EmpleadoDTO(String id, String nombre, String apellido, String dni, String telefono, LocalDate fechaNacimiento,
                       String tipoEmpleado, String tipoDetalle, double salarioAnualBruto) {
        this(nombre, apellido, dni, telefono, fechaNacimiento, tipoEmpleado, tipoDetalle, salarioAnualBruto);
        this.id = id;
    }

    /**
     * Constructor de EmpleadoDTO sin el campo id.
     *
     * @param nombre            Nombre del empleado.
     * @param apellido          Apellido del empleado.
     * @param dni               DNI del empleado.
     * @param telefono          Teléfono del empleado.
     * @param fechaNacimiento   Fecha de nacimiento del empleado.
     * @param tipoEmpleado      Tipo de empleado.
     * @param tipoDetalle       Detalles adicionales del tipo de empleado.
     * @param salarioAnualBruto Salario anual bruto del empleado.
     */
    public EmpleadoDTO(String nombre, String apellido, String dni, String telefono, LocalDate fechaNacimiento,
                       String tipoEmpleado, String tipoDetalle, double salarioAnualBruto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoEmpleado = tipoEmpleado;
        this.tipoDetalle = tipoDetalle;
        this.salarioAnualBruto = salarioAnualBruto;
        this.horarios = new ArrayList<>();
    }

    // Getters y Setters omitidos por brevedad (los mismos que antes)

    /**
     * Agrega un horario semanal al empleado.
     *
     * @param horario Horario a agregar.
     */
    public void agregarHorarioSemanal(HorarioDTO horario) {
        if (!tipoEmpleado.equals("No Deportivo")) {
            throw new IllegalArgumentException("Solo los empleados no deportivos pueden tener horarios.");
        }

        // Verificar que no hay horario semanal en el mismo día
        for (HorarioDTO h : horarios) {
            if (h.esSemanal() && h.getDiaSemana().equals(horario.getDiaSemana())) {
                throw new IllegalArgumentException("Ya existe un horario semanal para este día.");
            }
        }

        // Verificar que no excede las 8 horas diarias
        if (horario.getDuracionEnMinutos() > 480) {
            throw new IllegalArgumentException("El horario no puede exceder 8 horas diarias.");
        }

        // Verificar que no excede las 40 horas semanales
        long totalMinutosSemanales = calcularHorasSemanalesTotalEnMinutos();
        if ((totalMinutosSemanales + horario.getDuracionEnMinutos()) > 2400) { // 40 horas * 60 minutos
            throw new IllegalArgumentException("El total de horas semanales no puede exceder las 40 horas.");
        }

        horarios.add(horario);
    }

    /**
     * Agrega un horario específico al empleado.
     *
     * @param horario Horario a agregar.
     */
    public void agregarHorarioEspecifico(HorarioDTO horario) {
        if (!tipoEmpleado.equals("No Deportivo")) {
            throw new IllegalArgumentException("Solo los empleados no deportivos pueden tener horarios.");
        }

        // Verificar que no hay horario específico para esa fecha
        for (HorarioDTO h : horarios) {
            if (!h.esSemanal() && h.getFechaEspecifica().equals(horario.getFechaEspecifica())) {
                throw new IllegalArgumentException("Ya existe un horario específico para este día.");
            }
        }

        // Verificar que no excede las 8 horas diarias
        if (horario.getDuracionEnMinutos() > 480) {
            throw new IllegalArgumentException("El horario no puede exceder 8 horas diarias.");
        }

        // Verificar que no excede las 40 horas semanales
        LocalDate semanaInicio = horario.getFechaEspecifica().with(java.time.DayOfWeek.MONDAY);
        long minutosSemanales = calcularHorasSemanales(semanaInicio);

        // Restamos las horas del día en cuestión si hay horario semanal
        long minutosDia = calcularHorasEnFecha(horario.getFechaEspecifica());
        minutosSemanales -= minutosDia;

        if ((minutosSemanales + horario.getDuracionEnMinutos()) > 2400) { // 40 horas * 60 minutos
            throw new IllegalArgumentException("El total de horas semanales no puede exceder las 40 horas.");
        }

        horarios.add(horario);
    }

    /**
     * Calcula el total de minutos semanales asignados al empleado en una semana específica.
     *
     * @param semanaInicio Fecha de inicio de la semana.
     * @return Total de minutos semanales.
     */
    public long calcularHorasSemanales(LocalDate semanaInicio) {
        LocalDate semanaFin = semanaInicio.plusDays(6);
        long totalMinutos = 0;

        for (LocalDate fecha = semanaInicio; !fecha.isAfter(semanaFin); fecha = fecha.plusDays(1)) {
            totalMinutos += calcularHorasEnFecha(fecha);
        }

        return totalMinutos;
    }

    /**
     * Calcula el total de minutos asignados al empleado en una fecha específica.
     *
     * @param fecha Fecha a calcular.
     * @return Total de minutos en la fecha.
     */
    public long calcularHorasEnFecha(LocalDate fecha) {
        long minutos = 0;

        // Primero, buscamos si hay un horario específico para esa fecha
        for (HorarioDTO horario : horarios) {
            if (!horario.esSemanal() && horario.aplicaParaFecha(fecha)) {
                minutos += horario.getDuracionEnMinutos();
                return minutos; // Si hay horario específico, ignoramos los semanales
            }
        }

        // Si no hay horario específico, buscamos horarios semanales
        for (HorarioDTO horario : horarios) {
            if (horario.esSemanal() && horario.aplicaParaFecha(fecha)) {
                minutos += horario.getDuracionEnMinutos();
            }
        }

        return minutos;
    }

    /**
     * Calcula el total de minutos semanales asignados en todos los horarios semanales.
     *
     * @return Total de minutos semanales.
     */
    private long calcularHorasSemanalesTotalEnMinutos() {
        long totalMinutos = 0;
        for (HorarioDTO horario : horarios) {
            if (horario.esSemanal()) {
                totalMinutos += horario.getDuracionEnMinutos();
            }
        }
        return totalMinutos;
    }

    /**
     * Obtiene la lista de horarios del empleado.
     *
     * @return Lista inmutable de horarios.
     */
    public List<HorarioDTO> getHorarios() {
        return Collections.unmodifiableList(horarios);
    }

    /**
     * Método que devuelve una representación en cadena de los datos del empleado.
     *
     * @return Cadena con los datos del empleado.
     */
    @Override
    public String toString() {
        return "EmpleadoDTO{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", tipoEmpleado='" + tipoEmpleado + '\'' +
                ", tipoDetalle='" + tipoDetalle + '\'' +
                ", salarioAnualBruto=" + salarioAnualBruto +
                '}';
    }
}
