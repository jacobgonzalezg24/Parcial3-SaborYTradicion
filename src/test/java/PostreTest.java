import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostreTest {

    Postre brownie = new Postre("D01", "Brownie", "Brownie con helado", 10000, 10, 200, 22, false);

    //1. El postre debe tener los datos correctos
    @Test
    void postreDebeTenerLosDatosCorrectos() {
        assertEquals("D01", brownie.getCodigo());
        assertEquals("Brownie", brownie.getNombre());
        assertEquals(10000, brownie.getPrecioBase());
        assertEquals(200, brownie.getPesoGramos());
        assertEquals(22, brownie.getTemperaturaAlmacenamiento());
        assertFalse(brownie.isRequiereRefrigeracion());
    }

    //2. La categoría debe ser POSTRE
    @Test
    void categoriaDebeSerPostre() {
        assertEquals(CategoriaProducto.POSTRE, brownie.getCategoria());
    }

    //3. El precio de venta sin refrigeración tiene un incremento del 15 %
    @Test
    void precioVentaSinRefrigeracionDebeSer15PorcientoMas() {
        assertEquals(11500, brownie.calcularPrecioVenta(), 0.01);
    }

    //4. El precio de venta con refrigeración suma 5 % adicional del precio base
    @Test
    void precioVentaConRefrigeracionDebeSumar5PorcientoAdicional() {
        brownie.setRequiereRefrigeracion(true);
        assertEquals(12000, brownie.calcularPrecioVenta(), 0.01);
    }

    //5. Un postre refrigerado debe estar entre 0 °C y 6 °C
    @Test
    void postreRefrigeradoDebeEstarEntre0y6Grados() {
        brownie.setRequiereRefrigeracion(true);
        brownie.setTemperaturaAlmacenamiento(4);
        assertTrue(brownie.cumpleCondicionesCalidad());
        brownie.setTemperaturaAlmacenamiento(7);
        assertFalse(brownie.cumpleCondicionesCalidad());
    }

    //6. Un postre sin refrigeración debe estar entre 15 °C y 30 °C
    @Test
    void postreSinRefrigeracionDebeEstarEntre15y30Grados() {
        assertTrue(brownie.cumpleCondicionesCalidad());
        brownie.setTemperaturaAlmacenamiento(10);
        assertFalse(brownie.cumpleCondicionesCalidad());
        brownie.setTemperaturaAlmacenamiento(31);
        assertFalse(brownie.cumpleCondicionesCalidad());
    }

    //7. El peso debe estar entre 80 y 500 gramos
    @Test
    void pesoDebeEstarEntre80y500Gramos() {
        brownie.setPesoGramos(80);
        assertTrue(brownie.cumpleCondicionesCalidad());
        brownie.setPesoGramos(500);
        assertTrue(brownie.cumpleCondicionesCalidad());
        brownie.setPesoGramos(79);
        assertFalse(brownie.cumpleCondicionesCalidad());
        brownie.setPesoGramos(501);
        assertFalse(brownie.cumpleCondicionesCalidad());
    }

    //8. No debe permitir precio base negativo
    @Test
    void noDebePermitirPrecioBaseNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {new Postre("D01", "Brownie", "Brownie con helado", -10000, 10, 200, 22, false);});
    }

    //9. No debe permitir peso negativo
    @Test
    void noDebePermitirPesoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {new Postre("D01", "Brownie", "Brownie con helado", 10000, 10, -200, 22, false);});
    }

    //10. No debe permitir un postre sin código
    @Test
    void noDebePermitirPostreSinCodigo() {
        assertThrows(IllegalArgumentException.class, () -> {new Postre("", "Brownie", "Brownie con helado", 10000, 10, 200, 22, false);});
    }
}
