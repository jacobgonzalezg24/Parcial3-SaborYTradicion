import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BebidaTest {

    Bebida limonada = new Bebida("B01", "Limonada natural", "Limonada fría", 5000, 5, 400, 5,
            TipoBebida.FRIA, false);

    //1. La bebida debe tener los datos correctos
    @Test
    void bebidaDebeTenerLosDatosCorrectos() {
        assertEquals("B01", limonada.getCodigo());
        assertEquals("Limonada natural", limonada.getNombre());
        assertEquals(5000, limonada.getPrecioBase());
        assertEquals(400, limonada.getVolumenMl());
        assertEquals(5, limonada.getTemperaturaServicio());
        assertEquals(TipoBebida.FRIA, limonada.getTipoBebida());
        assertFalse(limonada.isPreparacionEspecial());
    }

    //2. La categoría debe ser BEBIDA
    @Test
    void categoriaDebeSerBebida() {
        assertEquals(CategoriaProducto.BEBIDA, limonada.getCategoria());
    }

    //3. El precio de venta sin preparación especial tiene un incremento del 10 %
    @Test
    void precioVentaSinPreparacionEspecialDebeSer10PorcientoMas() {
        assertEquals(5500, limonada.calcularPrecioVenta(), 0.01);
    }

    //4. El precio de venta con preparación especial suma un recargo fijo de $3.000
    @Test
    void precioVentaConPreparacionEspecialDebeSumar3000() {
        limonada.setPreparacionEspecial(true);
        assertEquals(8500, limonada.calcularPrecioVenta(), 0.01);
    }

    //5. Una bebida fría debe estar entre 0 °C y 10 °C
    @Test
    void bebidaFriaDebeEstarEntre0y10Grados() {
        assertTrue(limonada.cumpleCondicionesCalidad());
        limonada.setTemperaturaServicio(10);
        assertTrue(limonada.cumpleCondicionesCalidad());
        limonada.setTemperaturaServicio(11);
        assertFalse(limonada.cumpleCondicionesCalidad());
        limonada.setTemperaturaServicio(-1);
        assertFalse(limonada.cumpleCondicionesCalidad());
    }

    //6. Una bebida caliente debe estar entre 55 °C y 85 °C
    @Test
    void bebidaCalienteDebeEstarEntre55y85Grados() {
        Bebida cafe = new Bebida("B02", "Café", "Café caliente", 4000, 8, 250, 70, TipoBebida.CALIENTE, false);
        assertTrue(cafe.cumpleCondicionesCalidad());
        cafe.setTemperaturaServicio(54);
        assertFalse(cafe.cumpleCondicionesCalidad());
        cafe.setTemperaturaServicio(86);
        assertFalse(cafe.cumpleCondicionesCalidad());
    }

    //7. El volumen debe estar entre 150 y 1.500 mililitros
    @Test
    void volumenDebeEstarEntre150y1500Mililitros() {
        limonada.setVolumenMl(150);
        assertTrue(limonada.cumpleCondicionesCalidad());
        limonada.setVolumenMl(1500);
        assertTrue(limonada.cumpleCondicionesCalidad());
        limonada.setVolumenMl(149);
        assertFalse(limonada.cumpleCondicionesCalidad());
        limonada.setVolumenMl(1501);
        assertFalse(limonada.cumpleCondicionesCalidad());
    }

    //8. No debe permitir precio base negativo
    @Test
    void noDebePermitirPrecioBaseNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {new Bebida("B01", "Limonada natural", "Limonada fría", -5000, 5, 400, 5, TipoBebida.FRIA, false);});
    }

    //9. No debe permitir volumen negativo
    @Test
    void noDebePermitirVolumenNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {new Bebida("B01", "Limonada natural", "Limonada fría", 5000, 5, -400, 5, TipoBebida.FRIA, false);});
    }

    //10. No debe permitir una bebida sin tipo
    @Test
    void noDebePermitirBebidaSinTipo() {
        assertThrows(IllegalArgumentException.class, () -> {new Bebida("B01", "Limonada natural", "Limonada fría", 5000, 5, 400, 5, null, false);});
    }
}
