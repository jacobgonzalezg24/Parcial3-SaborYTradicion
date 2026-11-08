import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntregaSeguraTest {

    EntregaSegura entregaSegura = new EntregaSegura();

    //1. El servicio debe tener el nombre y la distancia máxima correctos
    @Test
    void servicioDebeTenerLosDatosCorrectos() {
        assertEquals("EntregaSegura", entregaSegura.getNombre());
        assertEquals(20, entregaSegura.getDistanciaMaximaKm());
        assertFalse(entregaSegura.isAceptado());
    }

    //2. Hasta 5 kilómetros no tiene recargo: $7.000 más $1.000 por kilómetro
    @Test
    void hastaCincoKilometrosNoDebeTenerRecargo() {
        assertEquals(12000, entregaSegura.calcularCosto(5), 0.01);
    }

    //3. Si supera 5 kilómetros se suma un recargo de $3.000
    @Test
    void masDeCincoKilometrosDebeSumarRecargo() {
        assertEquals(16000, entregaSegura.calcularCosto(6), 0.01);
        assertEquals(20000, entregaSegura.calcularCosto(10), 0.01);
    }

    //4. Debe aceptar recorridos de hasta 20 kilómetros
    @Test
    void debeAceptarHasta20Kilometros() {
        assertTrue(entregaSegura.aceptaDistancia(20));
        assertTrue(entregaSegura.aceptaDistancia(13));
    }

    //5. No debe aceptar recorridos de más de 20 kilómetros
    @Test
    void noDebeAceptarMasDe20Kilometros() {
        assertFalse(entregaSegura.aceptaDistancia(21));
    }

    //6. Al solicitar el servicio con una distancia válida queda aceptado
    @Test
    void solicitarServicioConDistanciaValidaDebeAceptarlo() {
        assertTrue(entregaSegura.solicitarServicio(18));
        assertTrue(entregaSegura.isAceptado());
    }

    //7. Al solicitar el servicio con una distancia no permitida no queda aceptado
    @Test
    void solicitarServicioConDistanciaNoPermitidaNoDebeAceptarlo() {
        assertFalse(entregaSegura.solicitarServicio(25));
        assertFalse(entregaSegura.isAceptado());
    }

    //8. No debe calcular el costo de una distancia cero
    @Test
    void noDebeCalcularCostoConDistanciaCero() {
        assertThrows(IllegalArgumentException.class, () -> {entregaSegura.calcularCosto(0);});
    }

    //9. No debe calcular el costo de una distancia negativa
    @Test
    void noDebeCalcularCostoConDistanciaNegativa() {
        assertThrows(IllegalArgumentException.class, () -> {entregaSegura.calcularCosto(-2);});
    }
}
