import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    Cliente cliente = new Cliente("1001", "Ana Gómez", "3001112233", "ana@correo.com", "Calle 10 # 5-20");
    Pedido pedido = new Pedido("PED-1", LocalDate.now(), cliente, ModalidadEntrega.CONSUMO_RESTAURANTE);

    //1. El cliente debe tener los datos correctos
    @Test
    void clienteDebeTenerLosDatosCorrectos() {
        assertEquals("1001", cliente.getDocumento());
        assertEquals("Ana Gómez", cliente.getNombreCompleto());
        assertEquals("3001112233", cliente.getTelefono());
        assertEquals("ana@correo.com", cliente.getCorreo());
        assertEquals("Calle 10 # 5-20", cliente.getDireccion());
    }

    //2. El historial de pedidos debe iniciar vacío
    @Test
    void historialDebeIniciarVacio() {
        assertTrue(cliente.getHistorialPedidos().isEmpty());
    }

    //3. Debe agregar un pedido al historial
    @Test
    void debeAgregarUnPedidoAlHistorial() {
        assertTrue(cliente.agregarPedidoAlHistorial(pedido));
        assertEquals(1, cliente.consultarPedidos().size());
    }

    //4. No se puede permitir dos pedidos con el mismo código en el historial
    @Test
    void noDebePermitirPedidosConCodigoRepetido() {
        cliente.agregarPedidoAlHistorial(pedido);
        Pedido repetido = new Pedido("PED-1", LocalDate.now(), cliente, ModalidadEntrega.DOMICILIO);
        assertFalse(cliente.agregarPedidoAlHistorial(repetido));
        assertEquals(1, cliente.consultarPedidos().size());
    }

    //5. Debe encontrar un pedido del historial por su código
    @Test
    void debeBuscarUnPedidoPorSuCodigo() {
        cliente.agregarPedidoAlHistorial(pedido);
        assertEquals("PED-1", cliente.buscarPedido("PED-1").getCodigo());
    }

    //6. Debe devolver null si el pedido no está en el historial
    @Test
    void buscarPedidoInexistenteDebeDevolverNull() {
        assertNull(cliente.buscarPedido("NO-EXISTE"));
    }

    //7. No debe agregar un pedido nulo
    @Test
    void noDebeAgregarPedidoNulo() {
        assertFalse(cliente.agregarPedidoAlHistorial(null));
    }

    //8. El correo debe contener una @
    @Test
    void noDebePermitirCorreosSinArroba() {
        assertThrows(IllegalArgumentException.class, () -> {new Cliente("1001", "Ana Gómez", "3001112233", "anacorreo.com", "Calle 10 # 5-20");});
    }

    //9. No debe permitir un cliente sin documento
    @Test
    void noDebePermitirClienteSinDocumento() {
        assertThrows(IllegalArgumentException.class, () -> {new Cliente("", "Ana Gómez", "3001112233", "ana@correo.com", "Calle 10 # 5-20");});
    }

    //10. No debe permitir un cliente sin dirección
    @Test
    void noDebePermitirClienteSinDireccion() {
        assertThrows(IllegalArgumentException.class, () -> {new Cliente("1001", "Ana Gómez", "3001112233", "ana@correo.com", "");});
    }
}
