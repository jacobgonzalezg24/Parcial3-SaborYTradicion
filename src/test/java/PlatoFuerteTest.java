import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatoFuerteTest {

    PlatoFuerte plato = new PlatoFuerte("P01", "Bandeja paisa", "Plato típico", 10000, 30, 500,
            TipoPreparacion.ASADO, true);

    //1. El plato debe tener los datos correctos
    @Test
    void platoDebeTenerLosDatosCorrectos() {
        assertEquals("P01", plato.getCodigo());
        assertEquals("Bandeja paisa", plato.getNombre());
        assertEquals(10000, plato.getPrecioBase());
        assertEquals(30, plato.getTiempoPreparacionMin());
        assertEquals(500, plato.getPesoGramos());
        assertEquals(TipoPreparacion.ASADO, plato.getTipoPreparacion());
        assertTrue(plato.isCoccionAprobada());
    }

    //2. La categoría debe ser PLATO_FUERTE
    @Test
    void categoriaDebeSerPlatoFuerte() {
        assertEquals(CategoriaProducto.PLATO_FUERTE, plato.getCategoria());
    }

    //3. El precio de venta tiene un incremento del 20 % sobre el precio base
    @Test
    void precioVentaDebeTenerIncrementoDel20Porciento() {
        assertEquals(12000, plato.calcularPrecioVenta(), 0.01);
    }

    //4. Un plato con datos correctos cumple las condiciones de calidad
    @Test
    void platoCorrectoDebeCumplirCalidad() {
        assertTrue(plato.cumpleCondicionesCalidad());
    }

    //5. El peso debe estar entre 200 y 900 gramos
    @Test
    void pesoDebeEstarEntre200y900Gramos() {
        plato.setPesoGramos(200);
        assertTrue(plato.cumpleCondicionesCalidad());
        plato.setPesoGramos(900);
        assertTrue(plato.cumpleCondicionesCalidad());
        plato.setPesoGramos(199);
        assertFalse(plato.cumpleCondicionesCalidad());
        plato.setPesoGramos(901);
        assertFalse(plato.cumpleCondicionesCalidad());
    }

    //6. El tiempo de preparación debe estar entre 10 y 60 minutos
    @Test
    void tiempoDebeEstarEntre10y60Minutos() {
        plato.setTiempoPreparacionMin(10);
        assertTrue(plato.cumpleCondicionesCalidad());
        plato.setTiempoPreparacionMin(60);
        assertTrue(plato.cumpleCondicionesCalidad());
        plato.setTiempoPreparacionMin(9);
        assertFalse(plato.cumpleCondicionesCalidad());
        plato.setTiempoPreparacionMin(61);
        assertFalse(plato.cumpleCondicionesCalidad());
    }

    //7. La cocción debe estar aprobada para cumplir la calidad
    @Test
    void coccionNoAprobadaNoDebeCumplirCalidad() {
        plato.setCoccionAprobada(false);
        assertFalse(plato.cumpleCondicionesCalidad());
    }

    //8. No debe permitir precio base negativo
    @Test
    void noDebePermitirPrecioBaseNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {new PlatoFuerte("P01", "Bandeja paisa", "Plato típico", -10000, 30, 500, TipoPreparacion.ASADO, true);});
    }

    //9. No debe permitir peso negativo
    @Test
    void noDebePermitirPesoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {new PlatoFuerte("P01", "Bandeja paisa", "Plato típico", 10000, 30, -500, TipoPreparacion.ASADO, true);});
    }

    //10. No debe permitir un plato sin tipo de preparación
    @Test
    void noDebePermitirPlatoSinTipoDePreparacion() {
        assertThrows(IllegalArgumentException.class, () -> {new PlatoFuerte("P01", "Bandeja paisa", "Plato típico", 10000, 30, 500, null, true);});
    }
}
