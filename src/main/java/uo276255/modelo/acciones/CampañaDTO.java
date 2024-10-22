package uo276255.modelo.acciones;

public class CampañaDTO {
    private int idCampaña;
    private String nombre;
    private int numeroAcciones;
    private String fechaInicio;
    private String fechaFin;
    private int fase;


	/**
     * Constructor vacío.
     */
    public CampañaDTO() {}

    /**
     * Constructor con parámetros.
     *
     * @param idCampaña Identificador de la campaña.
     * @param nombre Nombre de la campaña.
     * @param numeroAcciones Número de acciones en la campaña.
     * @param fechaInicio Fecha de inicio de la campaña.
     * @param fechaFin Fecha de finalización de la campaña.
     */
    public CampañaDTO(int idCampaña, String nombre,int fase, int numeroAcciones, String fechaInicio, String fechaFin) {
        this.idCampaña = idCampaña;
        this.nombre = nombre;
        this.fase = fase;
        this.numeroAcciones = numeroAcciones;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene el identificador de la campaña.
     *
     * @return Identificador de la campaña.
     */
    public int getIdCampaña() {
        return idCampaña;
    }

    /**
     * Establece el identificador de la campaña.
     *
     * @param idCampaña Identificador de la campaña.
     */
    public void setIdCampaña(int idCampaña) {
        this.idCampaña = idCampaña;
    }

    /**
     * Obtiene el nombre de la campaña.
     *
     * @return Nombre de la campaña.
     */
    public String getNombre() {
        return nombre;
    }
   

    /**
     * Establece el nombre de la campaña.
     *
     * @param nombre Nombre de la campaña.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    /**
     * Obtiene la fase de la campaña.
     *
     * @return fase de la campaña.
     */
    public int getFase() {
		return fase;
	}

    /**
     * Establece la fase de la campaña.
     *
     * @param fase fase de la campaña.
     */
	public void setFase(int fase) {
		this.fase = fase;
	}


    /**
     * Obtiene el número de acciones en la campaña.
     *
     * @return Número de acciones en la campaña.
     */
    public int getNumeroAcciones() {
        return numeroAcciones;
    }

    /**
     * Establece el número de acciones en la campaña.
     *
     * @param numeroAcciones Número de acciones en la campaña.
     */
    public void setNumeroAcciones(int numeroAcciones) {
        this.numeroAcciones = numeroAcciones;
    }

    /**
     * Obtiene la fecha de inicio de la campaña.
     *
     * @return Fecha de inicio de la campaña.
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio de la campaña.
     *
     * @param fechaInicio Fecha de inicio de la campaña.
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de finalización de la campaña.
     *
     * @return Fecha de finalización de la campaña.
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de finalización de la campaña.
     *
     * @param fechaFin Fecha de finalización de la campaña.
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Devuelve una representación en cadena del objeto CampañaDTO.
     *
     * @return Representación en cadena de la campaña.
     */
    @Override
    public String toString() {
        return "CampañaDTO{" +
                "idCampaña=" + idCampaña +
                ", nombre='" + nombre + '\'' +
                ", numeroAcciones=" + numeroAcciones +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                '}';
    }
}