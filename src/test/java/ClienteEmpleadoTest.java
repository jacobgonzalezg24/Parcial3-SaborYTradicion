import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteEmpleadoTest {

    ClienteEmpleado clienteEmpleado = new ClienteEmpleado("1003", "Juan Torres", "3101234567", "juan@correo.com",
            "Avenida Bolívar # 3-12", "E002", Cargo.DOMICILIARIO);

    //1. El cliente-empleado tiene los datos de cliente y de empleado
    @Test
    void debeTenerLosDatosCorrectos() {
        assertEquals("1003", clienteEmpleado.getDocumento());
        assertEquals("Juan Torres", clienteEmpleado.getNombreCompleto());
        assertEquals("Avenida Bolívar # 3-12", clienteEmpleado.getDireccion());
        assertEquals("E002", clienteEmpleado.getCodigoEmpleado());
        assertEquals(Cargo.DOMICILIARIO, clienteEmpleado.getCargo());
    }

    //2. Debe ser Cliente, Persona y cumplir las interfaces ICliente e IEmpleado
    @Test
    void debeSerClienteYEmpleadoAlMismoTiempo() {
        assertTrue(clienteEmpleado instanceof Persona);
        assertTrue(clienteEmpleado instanceof Cliente);
        assertTrue(clienteEmpleado instanceof ICliente);
        assertTrue(clienteEmpleado instanceof IEmpleado);
    }

    //3. Debe poder agregar pedidos a su historial como cliente
    @Test
    void debeAgregarPedidosAlHistorial() {
        Pedido pedido = new Pedido("PED-7", LocalDate.now(), clienteEmpleado, ModalidadEntrega.CONSUMO_RESTAURANTE);
        assertTrue(clienteEmpleado.agregarPedidoAlHistorial(pedido));
        assertEquals(1, clienteEmpleado.consultarPedidos().size());
    }

    //4. No debe permitir pedidos con código repetido en su historial
    @Test
    void noDebePermitirPedidosConCodigoRepetido() {
        Pedido pedido = new Pedido("PED-7", LocalDate.now(), clienteEmpleado, ModalidadEntrega.CONSUMO_RESTAURANTE);
        Pedido repetido = new Pedido("PED-7", LocalDate.now(), clienteEmpleado, ModalidadEntrega.DOMICILIO);
        clienteEmpleado.agregarPedidoAlHistorial(pedido);
        assertFalse(clienteEmpleado.agregarPedidoAlHistorial(repetido));
    }

    //5. Debe poder cambiar el cargo
    @Test
    void debePermitirCambiarElCargo() {
        clienteEmpleado.setCargo(Cargo.MESERO);
        assertEquals(Cargo.MESERO, clienteEmpleado.getCargo());
    }

    //6. Debe poder usarse como IEmpleado
    @Test
    void debePoderUsarseComoIEmpleado() {
        IEmpleado empleado = clienteEmpleado;
        assertEquals("E002", empleado.getCodigoEmpleado());
        assertEquals(Cargo.DOMICILIARIO, empleado.getCargo());
    }

    //7. No debe permitir un cliente-empleado sin código de empleado
    @Test
    void noDebePermitirSinCodigoDeEmpleado() {
        assertThrows(IllegalArgumentException.class, () -> {new ClienteEmpleado("1003", "Juan Torres", "3101234567", "juan@correo.com", "Avenida Bolívar # 3-12", "", Cargo.DOMICILIARIO);});
    }

    //8. No debe permitir un cliente-empleado sin cargo
    @Test
    void noDebePermitirSinCargo() {
        assertThrows(IllegalArgumentException.class, () -> {new ClienteEmpleado("1003", "Juan Torres", "3101234567", "juan@correo.com", "Avenida Bolívar # 3-12", "E002", null);});
    }

    //9. El correo debe contener una @
    @Test
    void noDebePermitirCorreosSinArroba() {
        assertThrows(IllegalArgumentException.class, () -> {new ClienteEmpleado("1003", "Juan Torres", "3101234567", "juancorreo.com", "Avenida Bolívar # 3-12", "E002", Cargo.DOMICILIARIO);});
    }
}
