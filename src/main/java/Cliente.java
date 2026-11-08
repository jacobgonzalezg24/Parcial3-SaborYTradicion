import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona implements ICliente {

    private String direccion;
    private List<Pedido> historialPedidos;

    public Cliente(String documento, String nombreCompleto, String telefono, String correo, String direccion) {
        super(documento, nombreCompleto, telefono, correo);
        if (direccion == null || direccion.isBlank()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }
        this.direccion = direccion;
        this.historialPedidos = new ArrayList<>();
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }

    public void setHistorialPedidos(List<Pedido> historialPedidos) {
        this.historialPedidos = historialPedidos;
    }

    /**
     * Agrega un pedido al historial del cliente.
     * Devuelve false en caso de que el pedido es nulo o si ya existe un pedido con el mismo código.
     */
    @Override
    public boolean agregarPedidoAlHistorial(Pedido pedido) {
        boolean agregado = false;
        if (pedido != null) {
            Pedido existente = buscarPedido(pedido.getCodigo());
            if (existente == null) {
                historialPedidos.add(pedido);
                agregado = true;
            }
        }
        return agregado;
    }

    @Override
    public List<Pedido> consultarPedidos() {
        return new ArrayList<>(historialPedidos);
    }

    public Pedido buscarPedido(String codigo) {
        for (Pedido pedido : historialPedidos) {
            if (pedido.getCodigo().equals(codigo)) {
                return pedido;
            }
        }
        return null;
    }
}
