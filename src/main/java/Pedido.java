import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private String codigo;
    private LocalDate fecha;
    private Cliente cliente;
    private ModalidadEntrega modalidad;
    private EstadoPedido estado;
    private List<DetallePedido> detalles;
    private ServicioDomicilio servicioDomicilio;
    private double distanciaKm;
    private double valorTotal;

    public Pedido(String codigo, LocalDate fecha, Cliente cliente, ModalidadEntrega modalidad) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del pedido es obligatorio");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (modalidad == null) {
            throw new IllegalArgumentException("La modalidad de entrega es obligatoria");
        }
        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.modalidad = modalidad;
        this.estado = EstadoPedido.PENDIENTE;
        this.detalles = new ArrayList<>();
        this.servicioDomicilio = null;
        this.distanciaKm = 0;
        this.valorTotal = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ModalidadEntrega getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadEntrega modalidad) {
        this.modalidad = modalidad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public ServicioDomicilio getServicioDomicilio() {
        return servicioDomicilio;
    }

    public void setServicioDomicilio(ServicioDomicilio servicioDomicilio) {
        this.servicioDomicilio = servicioDomicilio;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * Agrega  producto al pedido. Solo se permite mientras el pedido esté PENDIENTE
     * y con cantidad mayor que cero. Si el producto ya estaba, suma la cantidad.
     */
    public boolean agregarDetalle(Producto producto, int cantidad) {
        boolean agregado = false;
        if (producto != null && cantidad > 0 && estado == EstadoPedido.PENDIENTE) {
            DetallePedido existente = null;
            for (DetallePedido detalle : detalles) {
                if (detalle.getProducto().getCodigo().equals(producto.getCodigo())) {
                    existente = detalle;
                }
            }
            if (existente != null) {
                existente.setCantidad(existente.getCantidad() + cantidad);
            } else {
                detalles.add(new DetallePedido(producto, cantidad));
            }
            agregado = true;
        }
        return agregado;
    }

    /**
     * Solicita el servicio de domicilio a la empresa indicada (RapidoExpress o EntregaSegura).
     * Solo aplica a pedidos PENDIENTES con modalidad DOMICILIO. El servicio queda asignado
     * únicamente si la empresa acepta la distancia.
     */
    public boolean asignarDomicilio(ServicioDomicilio servicio, double distanciaKm) {
        boolean asignado = false;
        if (servicio != null && modalidad == ModalidadEntrega.DOMICILIO && estado == EstadoPedido.PENDIENTE) {
            if (servicio.solicitarServicio(distanciaKm)) {
                this.servicioDomicilio = servicio;
                this.distanciaKm = distanciaKm;
                asignado = true;
            }
        }
        return asignado;
    }

    public double calcularTotalProductos() {
        double total = 0;
        for (DetallePedido detalle : detalles) {
            total += detalle.calcularSubtotal();
        }
        return total;
    }

    /** Es 0 si el pedido es para consumo en el restaurante o no tiene servicio asignado. */
    public double calcularCostoDomicilio() {
        double costo = 0;
        if (modalidad == ModalidadEntrega.DOMICILIO && servicioDomicilio != null) {
            costo = servicioDomicilio.calcularCosto(distanciaKm);
        }
        return costo;
    }

    /** Productos más domicilio (cuando corresponde). También actualiza valorTotal. */
    public double calcularTotal() {
        valorTotal = calcularTotalProductos() + calcularCostoDomicilio();
        return valorTotal;
    }

    /** Solo se puede confirmar un pedido PENDIENTE (evita confirmaciones repetidas). */
    public boolean marcarConfirmado() {
        boolean cambiado = false;
        if (estado == EstadoPedido.PENDIENTE) {
            estado = EstadoPedido.CONFIRMADO;
            cambiado = true;
        }
        return cambiado;
    }

    /** Solo se puede entregar un pedido CONFIRMADO. */
    public boolean marcarEntregado() {
        boolean cambiado = false;
        if (estado == EstadoPedido.CONFIRMADO) {
            estado = EstadoPedido.ENTREGADO;
            cambiado = true;
        }
        return cambiado;
    }

    /** No se puede cancelar un pedido ya entregado ni uno ya cancelado. */
    public boolean cancelar() {
        boolean cambiado = false;
        if (estado == EstadoPedido.PENDIENTE || estado == EstadoPedido.CONFIRMADO) {
            estado = EstadoPedido.CANCELADO;
            cambiado = true;
        }
        return cambiado;
    }
}
