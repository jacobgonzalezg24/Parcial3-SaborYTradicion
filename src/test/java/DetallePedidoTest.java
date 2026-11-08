import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetallePedidoTest {

    PlatoFuerte plato = new PlatoFuerte("P01", "Bandeja paisa", "Plato típico", 10000, 30, 500,
            TipoPreparacion.ASADO, true);
    DetallePedido detalle = new DetallePedido(plato, 3);

    //1. El detalle debe guardar el producto y la cantidad
    @Test
    void detalleDebeTenerLosDatosCorrectos() {
        assertEquals("P01", detalle.getProducto().getCodigo());
        assertEquals(3, detalle.getCantidad());
    }

    //2. El subtotal es el precio de venta por la cantidad
    @Test
    void subtotalDebeSerPrecioDeVentaPorCantidad() {
        assertEquals(36000, detalle.getSubtotal(), 0.01);
        assertEquals(36000, detalle.calcularSubtotal(), 0.01);
    }

    //3. Al cambiar la cantidad se recalcula el subtotal
    @Test
    void cambiarLaCantidadDebeRecalcularElSubtotal() {
        detalle.setCantidad(5);
        assertEquals(60000, detalle.getSubtotal(), 0.01);
    }

    //4. El subtotal debe incluir los recargos del producto
    @Test
    void subtotalDebeIncluirLosRecargosDelProducto() {
        Bebida cafe = new Bebida("B02", "Café especial", "Con preparación especial", 5000, 8, 250, 70,
                TipoBebida.CALIENTE, true);
        DetallePedido detalleCafe = new DetallePedido(cafe, 2);
        assertEquals(17000, detalleCafe.calcularSubtotal(), 0.01);
    }

    //5. No debe permitir cantidad cero
    @Test
    void noDebePermitirCantidadCero() {
        assertThrows(IllegalArgumentException.class, () -> {new DetallePedido(plato, 0);});
    }

    //6. No debe permitir cantidad negativa
    @Test
    void noDebePermitirCantidadNegativa() {
        assertThrows(IllegalArgumentException.class, () -> {new DetallePedido(plato, -2);});
    }

    //7. No debe permitir un detalle sin producto
    @Test
    void noDebePermitirDetalleSinProducto() {
        assertThrows(IllegalArgumentException.class, () -> {new DetallePedido(null, 2);});
    }
}
