public class EntregaSegura extends ServicioDomicilio {

    private static final double TARIFA_BASE = 7000;
    private static final double COSTO_POR_KM = 1000;
    private static final double RECARGO = 3000;
    private static final double UMBRAL_RECARGO_KM = 5;
    private static final double MAX_KM = 20;

    public EntregaSegura() {
        super("EntregaSegura", MAX_KM);
    }

    /** $7.000 de tarifa base más $1.000 por kilómetro, más $3.000 si supera 5 km. */
    @Override
    public double calcularCosto(double distanciaKm) {
        if (distanciaKm <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor a 0");
        }
        double costo = TARIFA_BASE + COSTO_POR_KM * distanciaKm;
        if (distanciaKm > UMBRAL_RECARGO_KM) {
            costo += RECARGO;
        }
        return costo;
    }
}
