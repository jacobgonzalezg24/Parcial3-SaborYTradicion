public abstract class Persona {

    private String documento;
    private String nombreCompleto;
    private String telefono;
    private String correo;

    public Persona(String documento, String nombreCompleto, String telefono, String correo) {
        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("El documento es obligatorio");
        }
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        }
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("El correo debe contener @");
        }
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
