import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoTest {

    Empleado empleado = new Empleado("2001", "Laura Pérez", "3007778899", "laura@correo.com", "E001", Cargo.CAJERO);

    //1. El empleado debe tener los datos correctos
    @Test
    void empleadoDebeTenerLosDatosCorrectos() {
        assertEquals("2001", empleado.getDocumento());
        assertEquals("Laura Pérez", empleado.getNombreCompleto());
        assertEquals("3007778899", empleado.getTelefono());
        assertEquals("laura@correo.com", empleado.getCorreo());
        assertEquals("E001", empleado.getCodigoEmpleado());
        assertEquals(Cargo.CAJERO, empleado.getCargo());
    }

    //2. Debe poder cambiar el cargo
    @Test
    void debePermitirCambiarElCargo() {
        empleado.setCargo(Cargo.GERENTE);
        assertEquals(Cargo.GERENTE, empleado.getCargo());
    }

    //3. Debe poder cambiar el código de empleado
    @Test
    void debePermitirCambiarElCodigo() {
        empleado.setCodigoEmpleado("E099");
        assertEquals("E099", empleado.getCodigoEmpleado());
    }

    //4. El empleado es una Persona y cumple el contrato de IEmpleado
    @Test
    void empleadoDebeSerPersonaEIEmpleado() {
        assertTrue(empleado instanceof Persona);
        assertTrue(empleado instanceof IEmpleado);
    }

    //5. El correo debe contener una @
    @Test
    void noDebePermitirCorreosSinArroba() {
        assertThrows(IllegalArgumentException.class, () -> {new Empleado("2001", "Laura Pérez", "3007778899", "lauracorreo.com", "E001", Cargo.CAJERO);});
    }

    //6. No debe permitir un empleado sin código
    @Test
    void noDebePermitirEmpleadoSinCodigo() {
        assertThrows(IllegalArgumentException.class, () -> {new Empleado("2001", "Laura Pérez", "3007778899", "laura@correo.com", "", Cargo.CAJERO);});
    }

    //7. No debe permitir un empleado sin cargo
    @Test
    void noDebePermitirEmpleadoSinCargo() {
        assertThrows(IllegalArgumentException.class, () -> {new Empleado("2001", "Laura Pérez", "3007778899", "laura@correo.com", "E001", null);});
    }

    //8. No debe permitir un empleado sin documento
    @Test
    void noDebePermitirEmpleadoSinDocumento() {
        assertThrows(IllegalArgumentException.class, () -> {new Empleado("", "Laura Pérez", "3007778899", "laura@correo.com", "E001", Cargo.CAJERO);});
    }
}
