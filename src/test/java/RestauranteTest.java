import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {

    Restaurante restaurante;
    PlatoFuerte plato;      // precio de venta: 36.000
    PlatoFuerte platoMalo;  // pesa 150 g, no cumple la calidad
    Bebida bebida;          // precio de venta: 6.600
    Cliente cliente;

    // Se arma un restaurante nuevo antes de cada prueba para que sean independientes
    @BeforeEach
    void prepararRestaurante() {
        restaurante = new Restaurante("Sabor & Tradición", "900123456-7", "Armenia, Quindío");
        plato = new PlatoFuerte("P01", "Bandeja paisa", "Plato típico", 30000, 35, 600, TipoPreparacion.ASADO, true);
        platoMalo = new PlatoFuerte("P02", "Trucha frita", "Plato liviano", 28000, 25, 150, TipoPreparacion.FRITO, true);
        bebida = new Bebida("B01", "Limonada natural", "Limonada fría", 6000, 5, 400, 5, TipoBebida.FRIA, false);
        cliente = new Cliente("1001", "Ana Gómez", "3001112233", "ana@correo.com", "Calle 10 # 5-20");

        restaurante.registrarProducto(plato);
        restaurante.registrarProducto(platoMalo);
        restaurante.registrarProducto(bebida);
        restaurante.registrarCliente(cliente);
    }

    Pedido crearPedido(String codigo, ModalidadEntrega modalidad) {
        Pedido pedido = new Pedido(codigo, LocalDate.now(), cliente, modalidad);
        restaurante.registrarPedido(pedido);
        return pedido;
    }

    //1. El restaurante debe tener los datos correctos y los dos servicios de domicilio
    @Test
    void restauranteDebeTenerLosDatosCorrectos() {
        assertEquals("Sabor & Tradición", restaurante.getNombreComercial());
        assertEquals("900123456-7", restaurante.getNit());
        assertEquals("Armenia, Quindío", restaurante.getDireccion());
        assertEquals(2, restaurante.getListaServiciosDomicilio().size());
        assertEquals("RapidoExpress", restaurante.buscarServicioDomicilio("RapidoExpress").getNombre());
        assertEquals("EntregaSegura", restaurante.buscarServicioDomicilio("EntregaSegura").getNombre());
    }

    //2. No debe registrar productos con código duplicado
    @Test
    void noDebeRegistrarProductosConCodigoDuplicado() {
        Bebida repetida = new Bebida("P01", "Otra bebida", "Otra", 1000, 5, 300, 5, TipoBebida.FRIA, false);
        assertFalse(restaurante.registrarProducto(repetida));
        assertEquals(3, restaurante.consultarProductos().size());
    }

    //3. No debe registrar clientes con documento repetido
    @Test
    void noDebeRegistrarClientesConDocumentoRepetido() {
        Cliente repetido = new Cliente("1001", "Otra Persona", "300", "otra@correo.com", "Otra calle");
        assertFalse(restaurante.registrarCliente(repetido));
        assertEquals(1, restaurante.getListaClientes().size());
    }

    //4. Debe buscar productos y clientes, y devolver null si no existen
    @Test
    void debeBuscarProductosYClientes() {
        assertEquals(plato, restaurante.buscarProducto("P01"));
        assertNull(restaurante.buscarProducto("XXX"));
        assertEquals(cliente, restaurante.buscarCliente("1001"));
        assertNull(restaurante.buscarCliente("9999"));
    }

    //5. Debe registrar empleados y clientes-empleados sin repetir el código
    @Test
    void debeRegistrarEmpleadosSinCodigosRepetidos() {
        Empleado empleado = new Empleado("2001", "Laura Pérez", "300", "laura@correo.com", "E001", Cargo.CAJERO);
        ClienteEmpleado clienteEmpleado = new ClienteEmpleado("1003", "Juan Torres", "310", "juan@correo.com",
                "Calle 3", "E002", Cargo.DOMICILIARIO);
        ClienteEmpleado repetido = new ClienteEmpleado("1004", "Pedro Díaz", "311", "pedro@correo.com",
                "Calle 4", "E001", Cargo.CHEF);

        assertTrue(restaurante.registrarEmpleado(empleado));
        assertTrue(restaurante.registrarEmpleado(clienteEmpleado));
        assertFalse(restaurante.registrarEmpleado(repetido));
        assertEquals(2, restaurante.consultarEmpleados().size());
        assertEquals("E002", restaurante.buscarEmpleado("E002").getCodigoEmpleado());
    }

    //6. Debe separar los productos que cumplen y no cumplen la calidad
    @Test
    void debeSepararProductosPorCalidad() {
        assertEquals(2, restaurante.consultarProductosCumplenCalidad().size());
        assertEquals(1, restaurante.consultarProductosNoCumplenCalidad().size());
        assertEquals("P02", restaurante.consultarProductosNoCumplenCalidad().get(0).getCodigo());
    }

    //7. No debe registrar pedidos con código duplicado
    @Test
    void noDebeRegistrarPedidosConCodigoDuplicado() {
        crearPedido("PED-1", ModalidadEntrega.CONSUMO_RESTAURANTE);
        Pedido repetido = new Pedido("PED-1", LocalDate.now(), cliente, ModalidadEntrega.DOMICILIO);
        assertFalse(restaurante.registrarPedido(repetido));
        assertEquals(1, restaurante.getListaPedidos().size());
    }

    //8. Debe validar el pedido, el producto y la cantidad al agregar un producto
    @Test
    void agregarProductoAPedidoDebeValidarPedidoProductoYCantidad() {
        crearPedido("PED-1", ModalidadEntrega.CONSUMO_RESTAURANTE);
        assertTrue(restaurante.agregarProductoAPedido("PED-1", "P01", 2));
        assertFalse(restaurante.agregarProductoAPedido("PED-1", "XXX", 1));
        assertFalse(restaurante.agregarProductoAPedido("NO-EXISTE", "P01", 1));
        assertFalse(restaurante.agregarProductoAPedido("PED-1", "P01", 0));
    }

    //9. Debe confirmar un pedido válido y guardarlo en el historial del cliente
    @Test
    void debeConfirmarPedidoValidoYGuardarloEnElHistorial() {
        crearPedido("PED-1", ModalidadEntrega.CONSUMO_RESTAURANTE);
        restaurante.agregarProductoAPedido("PED-1", "P01", 2);

        assertTrue(restaurante.confirmarPedido("PED-1"));
        assertEquals(EstadoPedido.CONFIRMADO, restaurante.buscarPedido("PED-1").getEstado());
        assertEquals(1, cliente.consultarPedidos().size());
        assertEquals(72000, restaurante.calcularValorTotalPedido("PED-1"), 0.01);
    }

    //10. No debe confirmar un pedido con un producto que no cumple la calidad
    @Test
    void noDebeConfirmarPedidoConProductoQueNoCumpleCalidad() {
        crearPedido("PED-1", ModalidadEntrega.CONSUMO_RESTAURANTE);
        restaurante.agregarProductoAPedido("PED-1", "P01", 1);
        restaurante.agregarProductoAPedido("PED-1", "P02", 1);

        assertFalse(restaurante.confirmarPedido("PED-1"));
        assertEquals(EstadoPedido.PENDIENTE, restaurante.buscarPedido("PED-1").getEstado());
        assertTrue(cliente.consultarPedidos().isEmpty());
    }

    //11. No debe confirmar un pedido sin productos
    @Test
    void noDebeConfirmarPedidoSinProductos() {
        crearPedido("PED-1", ModalidadEntrega.CONSUMO_RESTAURANTE);
        assertFalse(restaurante.confirmarPedido("PED-1"));
    }

    //12. No debe confirmar el pedido de un cliente que no está registrado
    @Test
    void noDebeConfirmarPedidoDeClienteNoRegistrado() {
        Cliente intruso = new Cliente("9999", "Intruso", "300", "intruso@correo.com", "Calle X");
        Pedido pedido = new Pedido("PED-X", LocalDate.now(), intruso, ModalidadEntrega.CONSUMO_RESTAURANTE);
        restaurante.registrarPedido(pedido);
        restaurante.agregarProductoAPedido("PED-X", "P01", 1);

        assertFalse(restaurante.confirmarPedido("PED-X"));
    }

    //13. No debe confirmar dos veces el mismo pedido
    @Test
    void noDebeConfirmarDosVecesElMismoPedido() {
        crearPedido("PED-1", ModalidadEntrega.CONSUMO_RESTAURANTE);
        restaurante.agregarProductoAPedido("PED-1", "P01", 1);

        assertTrue(restaurante.confirmarPedido("PED-1"));
        assertFalse(restaurante.confirmarPedido("PED-1"));
        assertEquals(1, cliente.consultarPedidos().size());
    }

    //14. Un pedido a domicilio sin servicio aceptado no se puede confirmar
    @Test
    void pedidoADomicilioSinServicioAceptadoNoDebeConfirmarse() {
        crearPedido("PED-D", ModalidadEntrega.DOMICILIO);
        restaurante.agregarProductoAPedido("PED-D", "P01", 1);

        assertFalse(restaurante.confirmarPedido("PED-D"));
        assertFalse(restaurante.solicitarDomicilio("PED-D", "RapidoExpress", 13));
        assertFalse(restaurante.confirmarPedido("PED-D"));
    }

    //15. Un pedido a domicilio con servicio aceptado se confirma con el costo incluido
    @Test
    void pedidoADomicilioConServicioAceptadoDebeConfirmarseConCostoIncluido() {
        crearPedido("PED-D", ModalidadEntrega.DOMICILIO);
        restaurante.agregarProductoAPedido("PED-D", "P01", 1);

        assertTrue(restaurante.solicitarDomicilio("PED-D", "RapidoExpress", 10));
        assertTrue(restaurante.confirmarPedido("PED-D"));
        assertEquals(56000, restaurante.calcularValorTotalPedido("PED-D"), 0.01);
    }

    //16. El procedimiento de solicitar domicilio funciona igual con cualquier empresa
    @Test
    void solicitarDomicilioDebeFuncionarConCualquierEmpresa() {
        crearPedido("PED-D", ModalidadEntrega.DOMICILIO);
        assertTrue(restaurante.solicitarDomicilio("PED-D", "EntregaSegura", 18));
        assertEquals("EntregaSegura", restaurante.buscarPedido("PED-D").getServicioDomicilio().getNombre());
        assertFalse(restaurante.solicitarDomicilio("PED-D", "NoExiste", 5));
    }

    //17. Solo se pueden entregar pedidos confirmados
    @Test
    void soloDebeEntregarPedidosConfirmados() {
        crearPedido("PED-1", ModalidadEntrega.CONSUMO_RESTAURANTE);
        restaurante.agregarProductoAPedido("PED-1", "P01", 1);

        assertFalse(restaurante.entregarPedido("PED-1"));
        restaurante.confirmarPedido("PED-1");
        assertTrue(restaurante.entregarPedido("PED-1"));
        assertEquals(1, restaurante.consultarPedidosPorEstado(EstadoPedido.ENTREGADO).size());
    }

    //18. Un pedido cancelado no se entrega ni se contabiliza como ingreso
    @Test
    void pedidoCanceladoNoDebeEntregarseNiContabilizarse() {
        crearPedido("PED-1", ModalidadEntrega.CONSUMO_RESTAURANTE);
        restaurante.agregarProductoAPedido("PED-1", "P01", 2);
        restaurante.confirmarPedido("PED-1");

        assertTrue(restaurante.cancelarPedido("PED-1"));
        assertFalse(restaurante.entregarPedido("PED-1"));
        assertEquals(0, restaurante.calcularIngresosRestaurante(), 0.01);
        assertNull(restaurante.obtenerProductoMasVendido());
    }

    //19. Debe consultar los pedidos de un cliente con su estado
    @Test
    void debeConsultarLosPedidosDeUnCliente() {
        crearPedido("PED-1", ModalidadEntrega.CONSUMO_RESTAURANTE);
        crearPedido("PED-2", ModalidadEntrega.CONSUMO_RESTAURANTE);
        restaurante.cancelarPedido("PED-2");

        assertEquals(2, restaurante.consultarPedidosCliente("1001").size());
        assertEquals(0, restaurante.consultarPedidosCliente("9999").size());
        assertEquals(1, restaurante.consultarPedidosPorEstado(EstadoPedido.CANCELADO).size());
    }

    // Ventas de ejemplo para los reportes:
    // PED-1: 3 platos (108.000) + 1 bebida (6.600) = 114.600, consumo en el restaurante, confirmado
    // PED-2: 1 bebida (6.600) + domicilio RapidoExpress de 10 km (20.000), confirmado
    // PED-3: 5 platos, queda pendiente y no debe contarse
    void registrarVentasDeEjemplo() {
        crearPedido("PED-1", ModalidadEntrega.CONSUMO_RESTAURANTE);
        restaurante.agregarProductoAPedido("PED-1", "P01", 3);
        restaurante.agregarProductoAPedido("PED-1", "B01", 1);
        restaurante.confirmarPedido("PED-1");

        crearPedido("PED-2", ModalidadEntrega.DOMICILIO);
        restaurante.agregarProductoAPedido("PED-2", "B01", 1);
        restaurante.solicitarDomicilio("PED-2", "RapidoExpress", 10);
        restaurante.confirmarPedido("PED-2");

        crearPedido("PED-3", ModalidadEntrega.CONSUMO_RESTAURANTE);
        restaurante.agregarProductoAPedido("PED-3", "P01", 5);
    }

    //20. Los ingresos del restaurante no incluyen domicilios ni pedidos pendientes
    @Test
    void ingresosDelRestauranteNoDebenIncluirDomiciliosNiPendientes() {
        registrarVentasDeEjemplo();
        assertEquals(121200, restaurante.calcularIngresosRestaurante(), 0.01);
    }

    //21. Los valores cobrados por domicilios se calculan aparte
    @Test
    void ingresosPorDomiciliosDebenCalcularseAparte() {
        registrarVentasDeEjemplo();
        assertEquals(20000, restaurante.calcularIngresosDomicilios(), 0.01);
    }

    //22. Debe identificar el producto más vendido
    @Test
    void debeIdentificarElProductoMasVendido() {
        registrarVentasDeEjemplo();
        assertEquals("P01", restaurante.obtenerProductoMasVendido().getCodigo());
    }

    //23. Debe identificar la categoría con mayores ingresos
    @Test
    void debeIdentificarLaCategoriaConMayoresIngresos() {
        registrarVentasDeEjemplo();
        assertEquals(CategoriaProducto.PLATO_FUERTE, restaurante.obtenerCategoriaMayoresIngresos());
    }

    //24. Sin ventas los reportes no devuelven producto ni categoría
    @Test
    void sinVentasLosReportesDebenEstarVacios() {
        assertNull(restaurante.obtenerProductoMasVendido());
        assertNull(restaurante.obtenerCategoriaMayoresIngresos());
        assertEquals(0, restaurante.calcularIngresosRestaurante(), 0.01);
    }

    //25. No debe permitir un restaurante sin nombre comercial
    @Test
    void noDebePermitirRestauranteSinNombre() {
        assertThrows(IllegalArgumentException.class, () -> {new Restaurante("", "900123456-7", "Armenia, Quindío");});
    }

    //26. No debe permitir un restaurante sin NIT
    @Test
    void noDebePermitirRestauranteSinNit() {
        assertThrows(IllegalArgumentException.class, () -> {new Restaurante("Sabor & Tradición", "", "Armenia, Quindío");});
    }
}
