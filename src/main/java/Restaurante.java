import java.util.ArrayList;
import java.util.List;

public class Restaurante {

    private String nombreComercial;
    private String nit;
    private String direccion;
    private List<Producto> listaProductos;
    private List<Cliente> listaClientes;
    private List<IEmpleado> listaEmpleados;
    private List<Pedido> listaPedidos;
    private List<ServicioDomicilio> listaServiciosDomicilio;

    public Restaurante(String nombreComercial, String nit, String direccion) {
        if (nombreComercial == null || nombreComercial.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre comercial es obligatorio");
        }
        if (nit == null || nit.trim().isEmpty()) {
            throw new IllegalArgumentException("El NIT es obligatorio");
        }
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.listaProductos = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.listaEmpleados = new ArrayList<>();
        this.listaPedidos = new ArrayList<>();
        this.listaServiciosDomicilio = new ArrayList<>();
        this.listaServiciosDomicilio.add(new RapidoExpress());
        this.listaServiciosDomicilio.add(new EntregaSegura());
    }

    // ------------------------------------------------------------------
    // Getters y setters
    // ------------------------------------------------------------------

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<IEmpleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<IEmpleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public List<ServicioDomicilio> getListaServiciosDomicilio() {
        return listaServiciosDomicilio;
    }

    public void setListaServiciosDomicilio(List<ServicioDomicilio> listaServiciosDomicilio) {
        this.listaServiciosDomicilio = listaServiciosDomicilio;
    }


    // Productos (funcionalidades 1, 3, 4 y 5)


    public boolean verificarProducto(String codigo) {
        boolean existe = false;
        for (Producto producto : listaProductos) {
            if (producto.getCodigo().equals(codigo)) {
                existe = true;
            }
        }
        return existe;
    }

    public boolean registrarProducto(Producto producto) {
        boolean registrado = false;
        if (producto != null) {
            boolean existe = verificarProducto(producto.getCodigo());
            if (!existe) {
                listaProductos.add(producto);
                registrado = true;
            }
        }
        return registrado;
    }

    public Producto buscarProducto(String codigo) {
        for (Producto producto : listaProductos) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        return null;
    }

    public List<Producto> consultarProductos() {
        return new ArrayList<>(listaProductos);
    }

    public List<Producto> consultarProductosCumplenCalidad() {
        List<Producto> cumplen = new ArrayList<>();
        for (Producto producto : listaProductos) {
            if (producto.cumpleCondicionesCalidad()) {
                cumplen.add(producto);
            }
        }
        return cumplen;
    }

    public List<Producto> consultarProductosNoCumplenCalidad() {
        List<Producto> noCumplen = new ArrayList<>();
        for (Producto producto : listaProductos) {
            if (!producto.cumpleCondicionesCalidad()) {
                noCumplen.add(producto);
            }
        }
        return noCumplen;
    }


    // Clientes [funcionalidades 2 y 10]


    public boolean verificarCliente(String documento) {
        boolean existe = false;
        for (Cliente cliente : listaClientes) {
            if (cliente.getDocumento().equals(documento)) {
                existe = true;
            }
        }
        return existe;
    }

    public boolean registrarCliente(Cliente cliente) {
        boolean registrado = false;
        if (cliente != null) {
            boolean existe = verificarCliente(cliente.getDocumento());
            if (!existe) {
                listaClientes.add(cliente);
                registrado = true;
            }
        }
        return registrado;
    }

    public Cliente buscarCliente(String documento) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null;
    }


     //Todos los pedidos realizados por un cliente (en cualquier estado). El historial confirmado del cliente se consulta con Cliente.consultarPedidos().
    public List<Pedido> consultarPedidosCliente(String documento) {
        List<Pedido> pedidosCliente = new ArrayList<>();
        for (Pedido pedido : listaPedidos) {
            if (pedido.getCliente().getDocumento().equals(documento)) {
                pedidosCliente.add(pedido);
            }
        }
        return pedidosCliente;
    }

    // Empleados


    public boolean verificarEmpleado(String codigoEmpleado) {
        boolean existe = false;
        for (IEmpleado empleado : listaEmpleados) {
            if (empleado.getCodigoEmpleado().equals(codigoEmpleado)) {
                existe = true;
            }
        }
        return existe;
    }

    public boolean registrarEmpleado(IEmpleado empleado) {
        boolean registrado = false;
        if (empleado != null) {
            boolean existe = verificarEmpleado(empleado.getCodigoEmpleado());
            if (!existe) {
                listaEmpleados.add(empleado);
                registrado = true;
            }
        }
        return registrado;
    }

    public IEmpleado buscarEmpleado(String codigoEmpleado) {
        for (IEmpleado empleado : listaEmpleados) {
            if (empleado.getCodigoEmpleado().equals(codigoEmpleado)) {
                return empleado;
            }
        }
        return null;
    }

    public List<IEmpleado> consultarEmpleados() {
        return new ArrayList<>(listaEmpleados);
    }


    // Pedidos (funcionalidades 6, 8, 9 y 11)


    public boolean verificarPedido(String codigo) {
        boolean existe = false;
        for (Pedido pedido : listaPedidos) {
            if (pedido.getCodigo().equals(codigo)) {
                existe = true;
            }
        }
        return existe;
    }

    /** Registra el pedido (queda PENDIENTE, sin confirmar). Rechaza códigos duplicados. */
    public boolean registrarPedido(Pedido pedido) {
        boolean registrado = false;
        if (pedido != null) {
            boolean existe = verificarPedido(pedido.getCodigo());
            if (!existe) {
                listaPedidos.add(pedido);
                registrado = true;
            }
        }
        return registrado;
    }

    public Pedido buscarPedido(String codigo) {
        for (Pedido pedido : listaPedidos) {
            if (pedido.getCodigo().equals(codigo)) {
                return pedido;
            }
        }
        return null;
    }

    public boolean agregarProductoAPedido(String codigoPedido, String codigoProducto, int cantidad) {
        boolean agregado = false;
        Pedido pedido = buscarPedido(codigoPedido);
        Producto producto = buscarProducto(codigoProducto);
        if (pedido != null && producto != null) {
            agregado = pedido.agregarDetalle(producto, cantidad);
        }
        return agregado;
    }

    /**
     * Confirma un pedido solo si se cumplen TODAS estas condiciones:
     * 1. El pedido existe y está PENDIENTE (evita confirmaciones repetidas).
     * 2. El cliente está registrado en el restaurante.
     * 3. Tiene al menos un producto, y cada uno pertenece al menú, con cantidad mayor
     *    que cero y cumpliendo las condiciones de calidad.
     * 4. Si es a domicilio, la empresa externa acepta el servicio.
     * Al confirmar, el pedido queda en el historial del restaurante (listaPedidos)
     * y en el historial del cliente.
     */
    public boolean confirmarPedido(String codigoPedido) {
        boolean confirmado = false;
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido != null && pedido.getEstado() == EstadoPedido.PENDIENTE) {
            boolean valido = pedido.getCliente() != null && verificarCliente(pedido.getCliente().getDocumento());

            if (pedido.getDetalles().isEmpty()) {
                valido = false;
            }
            for (DetallePedido detalle : pedido.getDetalles()) {
                Producto producto = detalle.getProducto();
                if (!verificarProducto(producto.getCodigo())
                        || detalle.getCantidad() <= 0
                        || !producto.cumpleCondicionesCalidad()) {
                    valido = false;
                }
            }

            if (pedido.getModalidad() == ModalidadEntrega.DOMICILIO) {
                ServicioDomicilio servicio = pedido.getServicioDomicilio();
                if (servicio == null || !servicio.solicitarServicio(pedido.getDistanciaKm())) {
                    valido = false;
                }
            }

            if (valido) {
                Cliente cliente = buscarCliente(pedido.getCliente().getDocumento());
                pedido.calcularTotal();
                pedido.marcarConfirmado();
                cliente.agregarPedidoAlHistorial(pedido);
                confirmado = true;
            }
        }
        return confirmado;
    }

    /** Devuelve 0 si el pedido no existe. */
    public double calcularValorTotalPedido(String codigoPedido) {
        double total = 0;
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido != null) {
            total = pedido.calcularTotal();
        }
        return total;
    }

    public boolean entregarPedido(String codigoPedido) {
        boolean entregado = false;
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido != null) {
            entregado = pedido.marcarEntregado();
        }
        return entregado;
    }

    public boolean cancelarPedido(String codigoPedido) {
        boolean cancelado = false;
        Pedido pedido = buscarPedido(codigoPedido);
        if (pedido != null) {
            cancelado = pedido.cancelar();
        }
        return cancelado;
    }

    public List<Pedido> consultarPedidosPorEstado(EstadoPedido estado) {
        List<Pedido> pedidosEstado = new ArrayList<>();
        for (Pedido pedido : listaPedidos) {
            if (pedido.getEstado() == estado) {
                pedidosEstado.add(pedido);
            }
        }
        return pedidosEstado;
    }

    // Domicilios (funcionalidad 7)

    public ServicioDomicilio buscarServicioDomicilio(String nombre) {
        for (ServicioDomicilio servicio : listaServiciosDomicilio) {
            if (servicio.getNombre().equalsIgnoreCase(nombre)) {
                return servicio;
            }
        }
        return null;
    }


     // Solicita el domicilio de un pedido a la empresa indicada. Funciona igual para cualquier empresa porque usa el procedimiento común de ServicioDomicilio.

    public boolean solicitarDomicilio(String codigoPedido, String nombreServicio, double distanciaKm) {
        boolean solicitado = false;
        Pedido pedido = buscarPedido(codigoPedido);
        ServicioDomicilio servicio = buscarServicioDomicilio(nombreServicio);
        if (pedido != null && servicio != null) {
            solicitado = pedido.asignarDomicilio(servicio, distanciaKm);
        }
        return solicitado;
    }

    // Reportes de ventas (funcionalidad 12) Solo cuentan los pedidos CONFIRMADOS o ENTREGADOS.


    // Ingresos propios del restaurante: solo productos, sin el valor de los domicilios
    public double calcularIngresosRestaurante() {
        double ingresos = 0;
        for (Pedido pedido : listaPedidos) {
            if (esPedidoContable(pedido)) {
                ingresos += pedido.calcularTotalProductos();
            }
        }
        return ingresos;
    }

    // Valores cobrados por domicilios, que se muestran aparte de los ingresos del restaurante.
    public double calcularIngresosDomicilios() {
        double ingresos = 0;
        for (Pedido pedido : listaPedidos) {
            if (esPedidoContable(pedido)) {
                ingresos += pedido.calcularCostoDomicilio();
            }
        }
        return ingresos;
    }

    // Producto con más unidades vendidas, o null si todavía no hay ventas.
    public Producto obtenerProductoMasVendido() {
        Producto masVendido = null;
        int maxUnidades = 0;
        for (Producto producto : listaProductos) {
            int unidades = calcularUnidadesVendidas(producto);
            if (unidades > maxUnidades) {
                maxUnidades = unidades;
                masVendido = producto;
            }
        }
        return masVendido;
    }

    /** Categoría con mayores ingresos, o null si todavía no hay ventas. */
    public CategoriaProducto obtenerCategoriaMayoresIngresos() {
        CategoriaProducto mayor = null;
        double maxIngresos = 0;
        for (CategoriaProducto categoria : CategoriaProducto.values()) {
            double ingresos = calcularIngresosPorCategoria(categoria);
            if (ingresos > maxIngresos) {
                maxIngresos = ingresos;
                mayor = categoria;
            }
        }
        return mayor;
    }


    // Métodos auxiliares privados


    private boolean esPedidoContable(Pedido pedido) {
        return pedido.getEstado() == EstadoPedido.CONFIRMADO || pedido.getEstado() == EstadoPedido.ENTREGADO;
    }

    private int calcularUnidadesVendidas(Producto producto) {
        int unidades = 0;
        for (Pedido pedido : listaPedidos) {
            if (esPedidoContable(pedido)) {
                for (DetallePedido detalle : pedido.getDetalles()) {
                    if (detalle.getProducto().getCodigo().equals(producto.getCodigo())) {
                        unidades += detalle.getCantidad();
                    }
                }
            }
        }
        return unidades;
    }

    private double calcularIngresosPorCategoria(CategoriaProducto categoria) {
        double ingresos = 0;
        for (Pedido pedido : listaPedidos) {
            if (esPedidoContable(pedido)) {
                for (DetallePedido detalle : pedido.getDetalles()) {
                    if (detalle.getProducto().getCategoria() == categoria) {
                        ingresos += detalle.calcularSubtotal();
                    }
                }
            }
        }
        return ingresos;
    }
}
