import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RapidoExpressTest {

    RapidoExpress rapidoExpress = new RapidoExpress();

    //1. El servicio debe tener el nombre y la distancia máxima correctos
    @Test
    void servicioDebeTenerLosDatosCorrectos() {
        assertEquals("RapidoExpress", rapidoExpress.getNombre());
        assertEquals(12, rapidoExpress.getDistanciaMaximaKm());
        assertFalse(rapidoExpress.isAceptado());
    }

    //2. El costo es $5.000 de tarifa base más $1.500 por kilómetro
    @Test
    void costoDebeSerTarifaBaseMasValorPorKilometro() {
        assertEquals(20000, rapidoExpress.calcularCosto(10), 0.01);
        assertEquals(6500, rapidoExpress.calcularCosto(1), 0.01);
    }

    //3. Debe aceptar recorridos de hasta 12 kilómetros
    @Test
    void debeAceptarHasta12Kilometros() {
        assertTrue(rapidoExpress.aceptaDistancia(12));
        assertTrue(rapidoExpress.aceptaDistancia(5));
    }

    //4. No debe aceptar recorridos de más de 12 kilómetros
    @Test
    void noDebeAceptarMasDe12Kilometros() {
        assertFalse(rapidoExpress.aceptaDistancia(13));
    }

    //5. No debe aceptar distancias cero ni negativas
    @Test
    void noDebeAceptarDistanciasCeroONegativas() {
        assertFalse(rapidoExpress.aceptaDistancia(0));
        assertFalse(rapidoExpress.aceptaDistancia(-5));
    }

    //6. Al solicitar el servicio con una distancia válida queda aceptado
    @Test
    void solicitarServicioConDistanciaValidaDebeAceptarlo() {
        assertTrue(rapidoExpress.solicitarServicio(8));
        assertTrue(rapidoExpress.isAceptado());
    }

    //7. Al solicitar el servicio con una distancia no permitida no queda aceptado
    @Test
    void solicitarServicioConDistanciaNoPermitidaNoDebeAceptarlo() {
        assertFalse(rapidoExpress.solicitarServicio(15));
        assertFalse(rapidoExpress.isAceptado());
    }

    //8. No debe calcular el costo de una distancia cero
    @Test
    void noDebeCalcularCostoConDistanciaCero() {
        assertThrows(IllegalArgumentException.class, () -> {rapidoExpress.calcularCosto(0);});
    }

    //9. No debe calcular el costo de una distancia negativa
    @Test
    void noDebeCalcularCostoConDistanciaNegativa() {
        assertThrows(IllegalArgumentException.class, () -> {rapidoExpress.calcularCosto(-3);});
    }
}
