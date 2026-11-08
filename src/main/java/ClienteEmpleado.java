/**
 * Persona que es cliente y empleado a la vez.
 * Java no permite extender dos clases, por eso extiende Cliente
 * e implementa la interfaz IEmpleado.
 */
public class ClienteEmpleado extends Cliente implements IEmpleado {

    private String codigoEmpleado;
    private Cargo cargo;

    public ClienteEmpleado(String documento, String nombreCompleto, String telefono, String correo,
                           String direccion, String codigoEmpleado, Cargo cargo) {
        super(documento, nombreCompleto, telefono, correo, direccion);
        if (codigoEmpleado == null || codigoEmpleado.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de empleado es obligatorio");
        }
        if (cargo == null) {
            throw new IllegalArgumentException("El cargo es obligatorio");
        }
        this.codigoEmpleado = codigoEmpleado;
        this.cargo = cargo;
    }

    @Override
    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    @Override
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
