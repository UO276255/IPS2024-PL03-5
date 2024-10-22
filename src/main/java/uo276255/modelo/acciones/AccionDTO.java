package uo276255.modelo.acciones;

/**
 * Data Transfer Object (DTO) para representar una Acción.
 */
public class AccionDTO {
    private String idAccion;
    private int idEmpleado;
    private int idCampaña;
    private int idAccionista;
    private boolean enVenta;

    /**
     * Constructor vacío.
     */
    public AccionDTO() {}

    /**
     * Constructor con parámetros.
     *
     * @param idAccion Identificador de la acción.
     * @param idEmpleado Identificador del empleado asociado.
     * @param idCampaña Identificador de la campaña asociada.
     * @param idAccionista Identificador del accionista asociado.
     * @param enVenta Indicador de si la acción está en venta.
     */
    public AccionDTO(String idAccion, int idEmpleado, int idCampaña, int idAccionista, boolean enVenta) {
        this.idAccion = idAccion;
        this.idEmpleado = idEmpleado;
        this.idCampaña = idCampaña;
        this.idAccionista = idAccionista;
        this.enVenta = enVenta;
    }

    /**
     * Obtiene el identificador de la acción.
     *
     * @return Identificador de la acción.
     */
    public String getIdAccion() {
        return idAccion;
    }

    /**
     * Establece el identificador de la acción.
     *
     * @param idAccion Identificador de la acción.
     */
    public void setIdAccion(String idAccion) {
        this.idAccion = idAccion;
    }
    /**
     * Obtiene el identificador del empleado asociado.
     *
     * @return Identificador del empleado.
     */
    public int getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * Establece el identificador del empleado asociado.
     *
     * @param idEmpleado Identificador del empleado.
     */
    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    /**
     * Obtiene el identificador de la campaña asociada.
     *
     * @return Identificador de la campaña.
     */
    public int getIdCampaña() {
        return idCampaña;
    }

    /**
     * Establece el identificador de la campaña asociada.
     *
     * @param idCampaña Identificador de la campaña.
     */
    public void setIdCampaña(int idCampaña) {
        this.idCampaña = idCampaña;
    }

    /**
     * Obtiene el identificador del accionista asociado.
     *
     * @return Identificador del accionista.
     */
    public int getIdAccionista() {
        return idAccionista;
    }

    /**
     * Establece el identificador del accionista asociado.
     *
     * @param idAccionista Identificador del accionista.
     */
    public void setIdAccionista(int idAccionista) {
        this.idAccionista = idAccionista;
    }

    /**
     * Verifica si la acción está en venta.
     *
     * @return {@code true} si la acción está en venta, {@code false} en caso contrario.
     */
    public boolean isEnVenta() {
        return enVenta;
    }

    /**
     * Establece si la acción está en venta.
     *
     * @param enVenta Indicador de si la acción está en venta.
     */
    public void setEnVenta(boolean enVenta) {
        this.enVenta = enVenta;
    }

    /**
     * Devuelve una representación en cadena del objeto AccionDTO.
     *
     * @return Representación en cadena de la acción.
     */
    @Override
    public String toString() {
        return "AccionDTO{" +
                "idAccion='" + idAccion + '\'' +
                ", idEmpleado=" + idEmpleado +
                ", idCampaña=" + idCampaña +
                ", idAccionista=" + idAccionista +
                ", enVenta=" + enVenta +
                '}';
    }
}
