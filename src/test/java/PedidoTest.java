import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    Cliente cliente = new Cliente("1001", "Ana Gómez", "3001112233", "ana@correo.com", "Calle 10 # 5-20");
    PlatoFuerte plato = new PlatoFuerte("P01", "Bandeja paisa", "Plato típico", 10000, 30, 500,
            TipoPreparacion.ASADO, true);
    Bebida bebida = new Bebida("B01", "Limonada natural", "Limonada fría", 5000, 5, 400, 5,
            TipoBebida.FRIA, false);
    Pedido pedidoLocal = new Pedido("PED-1", LocalDate.now(), cliente, ModalidadEntrega.CONSUMO_RESTAURANTE);
    Pedido pedidoDomicilio = new Pedido("PED-2", LocalDate.now(), cliente, ModalidadEntrega.DOMICILIO);

    //1. El pedido debe tener los datos correctos y empezar PENDIENTE
    @Test
    void pedidoDebeTenerLosDatosCorrectos() {
        assertEquals("PED-1", pedidoLocal.getCodigo());
        assertEquals("Ana Gómez", pedidoLocal.getCliente().getNombreCompleto());
        assertEquals(ModalidadEntrega.CONSUMO_RESTAURANTE, pedidoLocal.getModalidad());
        assertEquals(EstadoPedido.PENDIENTE, pedidoLocal.getEstado());
    }

    //2. Un pedido nuevo no tiene productos y su total es cero
    @Test
    void pedidoNuevoDebeEstarVacio() {
        assertTrue(pedidoLocal.getDetalles().isEmpty());
        assertEquals(0, pedidoLocal.calcularTotal(), 0.01);
    }

    //3. Al agregar un producto se suma al total de productos
    @Test
    void agregarProductoDebeSumarAlTotal() {
        assertTrue(pedidoLocal.agregarDetalle(plato, 2));
        assertEquals(1, pedidoLocal.getDetalles().size());
        assertEquals(24000, pedidoLocal.calcularTotalProductos(), 0.01);
    }

    //4. Si se agrega el mismo producto se adiciona la cantidad
    @Test
    void agregarElMismoProductoDebeSumarLaCantidad() {
        pedidoLocal.agregarDetalle(plato, 2);
        pedidoLocal.agregarDetalle(plato, 3);
        assertEquals(1, pedidoLocal.getDetalles().size());
        assertEquals(5, pedidoLocal.getDetalles().get(0).getCantidad());
    }

    //5. No debe permitir cantidades cero ni negativas
    @Test
    void noDebePermitirCantidadesCeroONegativas() {
        assertFalse(pedidoLocal.agregarDetalle(plato, 0));
        assertFalse(pedidoLocal.agregarDetalle(plato, -2));
        assertTrue(pedidoLocal.getDetalles().isEmpty());
    }

    //6. Un pedido para consumo en el restaurante no cobra domicilio
    @Test
    void pedidoParaConsumoEnRestauranteNoDebeCobrarDomicilio() {
        pedidoLocal.agregarDetalle(plato, 1);
        pedidoLocal.agregarDetalle(bebida, 1);
        assertEquals(0, pedidoLocal.calcularCostoDomicilio(), 0.01);
        assertEquals(17500, pedidoLocal.calcularTotal(), 0.01);
    }

    //7. El total de un pedido a domicilio incluye el costo del servicio
    @Test
    void totalDelPedidoADomicilioDebeIncluirElCostoDelServicio() {
        pedidoDomicilio.agregarDetalle(plato, 1);
        assertTrue(pedidoDomicilio.asignarDomicilio(new RapidoExpress(), 10));
        assertEquals(20000, pedidoDomicilio.calcularCostoDomicilio(), 0.01);
        assertEquals(32000, pedidoDomicilio.calcularTotal(), 0.01);
        assertEquals(32000, pedidoDomicilio.getValorTotal(), 0.01);
    }

    //8. No debe asignar un domicilio si la empresa rechaza la distancia
    @Test
    void noDebeAsignarDomicilioConDistanciaNoPermitida() {
        assertFalse(pedidoDomicilio.asignarDomicilio(new RapidoExpress(), 13));
        assertNull(pedidoDomicilio.getServicioDomicilio());
        assertEquals(0, pedidoDomicilio.calcularCostoDomicilio(), 0.01);
    }

    //9. Un pedido para consumo en el restaurante no admite domicilio
    @Test
    void pedidoParaConsumoEnRestauranteNoDebeAdmitirDomicilio() {
        assertFalse(pedidoLocal.asignarDomicilio(new EntregaSegura(), 5));
        assertNull(pedidoLocal.getServicioDomicilio());
    }

    //10. Los estados deben cambiar en el orden permitido
    @Test
    void estadosDebenCambiarEnElOrdenPermitido() {
        assertFalse(pedidoLocal.marcarEntregado());
        assertTrue(pedidoLocal.marcarConfirmado());
        assertFalse(pedidoLocal.marcarConfirmado());
        assertTrue(pedidoLocal.marcarEntregado());
        assertEquals(EstadoPedido.ENTREGADO, pedidoLocal.getEstado());
    }

    //11. Un pedido cancelado no se puede confirmar ni entregar
    @Test
    void pedidoCanceladoNoDebeConfirmarseNiEntregarse() {
        assertTrue(pedidoLocal.cancelar());
        assertFalse(pedidoLocal.marcarConfirmado());
        assertFalse(pedidoLocal.marcarEntregado());
        assertEquals(EstadoPedido.CANCELADO, pedidoLocal.getEstado());
    }

    //12. Un pedido entregado no se puede cancelar
    @Test
    void pedidoEntregadoNoDebeCancelarse() {
        pedidoLocal.marcarConfirmado();
        pedidoLocal.marcarEntregado();
        assertFalse(pedidoLocal.cancelar());
    }

    //13. No debe agregar productos a un pedido ya confirmado
    @Test
    void noDebeAgregarProductosAUnPedidoConfirmado() {
        pedidoLocal.marcarConfirmado();
        assertFalse(pedidoLocal.agregarDetalle(plato, 1));
    }

    //14. No debe permitir un pedido sin cliente
    @Test
    void noDebePermitirPedidoSinCliente() {
        assertThrows(IllegalArgumentException.class, () -> {new Pedido("PED-3", LocalDate.now(), null, ModalidadEntrega.DOMICILIO);});
    }

    //15. No debe permitir un pedido sin código
    @Test
    void noDebePermitirPedidoSinCodigo() {
        assertThrows(IllegalArgumentException.class, () -> {new Pedido("", LocalDate.now(), cliente, ModalidadEntrega.DOMICILIO);});
    }
}
