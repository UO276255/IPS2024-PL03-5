package uo276255.modelo.horario;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase que representa un horario para un empleado no deportivo.
 */
public class HorarioDTO {

    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean esSemanal;          // True si el horario se repite semanalmente, false si es para un día específico.
    private DayOfWeek diaSemana;        // Día de la semana para horarios semanales.
    private LocalDate fechaEspecifica;  // Fecha específica para un horario, si no es semanal.

    /**
     * Constructor para horarios semanales.
     *
     * @param diaSemana  Día de la semana en que se aplica el horario.
     * @param horaInicio Hora de inicio del horario.
     * @param horaFin    Hora de fin del horario.
     */
    public HorarioDTO(DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        validarHoras(horaInicio, horaFin);
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.diaSemana = diaSemana;
        this.esSemanal = true;
    }

    /**
     * Constructor para horarios de un día específico.
     *
     * @param fechaEspecifica Fecha específica en que se aplica el horario.
     * @param horaInicio      Hora de inicio del horario.
     * @param horaFin         Hora de fin del horario.
     */
    public HorarioDTO(LocalDate fechaEspecifica, LocalTime horaInicio, LocalTime horaFin) {
        validarHoras(horaInicio, horaFin);
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaEspecifica = fechaEspecifica;
        this.esSemanal = false;
    }

    /**
     * Valida que las horas sean correctas y que la duración no supere las 8 horas diarias.
     *
     * @param horaInicio Hora de inicio.
     * @param horaFin    Hora de fin.
     */
    private void validarHoras(LocalTime horaInicio, LocalTime horaFin) {
        if (horaInicio == null || horaFin == null) {
            throw new IllegalArgumentException("Las horas no pueden ser nulas.");
        }
        if (!horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin.");
        }
        long minutos = Duration.between(horaInicio, horaFin).toMinutes();
        if (minutos > 480) {
            throw new IllegalArgumentException("El horario no puede exceder 8 horas diarias.");
        }
    }

    public boolean esSemanal() {
        return esSemanal;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public LocalDate getFechaEspecifica() {
        return fechaEspecifica;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    /**
     * Calcula la duración del horario en minutos.
     *
     * @return Duración en minutos.
     */
    public long getDuracionEnMinutos() {
        return Duration.between(horaInicio, horaFin).toMinutes();
    }

    /**
     * Verifica si este horario corresponde a la fecha dada.
     *
     * @param fecha Fecha a verificar.
     * @return True si el horario aplica para la fecha, false en caso contrario.
     */
    public boolean aplicaParaFecha(LocalDate fecha) {
        if (esSemanal) {
            return fecha.getDayOfWeek().equals(diaSemana);
        } else {
            return fechaEspecifica != null && fechaEspecifica.equals(fecha);
        }
    }

    @Override
    public String toString() {
        if (esSemanal) {
            return "Horario Semanal (" + diaSemana + "): de " + horaInicio + " a " + horaFin;
        } else {
            return "Horario Específico (" + fechaEspecifica + "): de " + horaInicio + " a " + horaFin;
        }
    }
}
