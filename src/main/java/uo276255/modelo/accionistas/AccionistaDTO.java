package uo276255.modelo.accionistas;

/**
 * Data Transfer Object (DTO) para representar un Accionista.
 */
public class AccionistaDTO {
    private int idAccionista;
    private String nombre;
    private String dni;
    private String telefono;
    private String email;

    /**
     * Constructor vacío.
     */
    public AccionistaDTO() {}

    /**
     * Constructor con parámetros.
     *
     * @param idAccionista Identificador del accionista.
     * @param nombre Nombre del accionista.
     * @param dni DNI del accionista.
     * @param telefono Teléfono del accionista.
     * @param email Correo electrónico del accionista.
     */
    public AccionistaDTO(int idAccionista, String nombre, String dni, String telefono, String email) {
        this.idAccionista = idAccionista;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    /**
     * Obtiene el identificador del accionista.
     *
     * @return Identificador del accionista.
     */
    public int getIdAccionista() {
        return idAccionista;
    }

    /**
     * Establece el identificador del accionista.
     *
     * @param idAccionista Identificador del accionista.
     */
    public void setIdAccionista(int idAccionista) {
        this.idAccionista = idAccionista;
    }

    /**
     * Obtiene el nombre del accionista.
     *
     * @return Nombre del accionista.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del accionista.
     *
     * @param nombre Nombre del accionista.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el DNI del accionista.
     *
     * @return DNI del accionista.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del accionista.
     *
     * @param dni DNI del accionista.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el teléfono del accionista.
     *
     * @return Teléfono del accionista.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del accionista.
     *
     * @param telefono Teléfono del accionista.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del accionista.
     *
     * @return Correo electrónico del accionista.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del accionista.
     *
     * @param email Correo electrónico del accionista.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve una representación en cadena del objeto AccionistaDTO.
     *
     * @return Representación en cadena del accionista.
     */
    @Override
    public String toString() {
        return "AccionistaDTO{" +
                "idAccionista=" + idAccionista +
                ", nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
