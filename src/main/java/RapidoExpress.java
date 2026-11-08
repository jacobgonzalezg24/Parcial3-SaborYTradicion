public class RapidoExpress extends ServicioDomicilio {

    private static final double TARIFA_BASE = 5000;
    private static final double COSTO_POR_KM = 1500;
    private static final double MAX_KM = 12;

    public RapidoExpress() {
        super("RapidoExpress", MAX_KM);
    }

    /** $5.000 de tarifa base más $1.500 por kilómetro. */
    @Override
    public double calcularCosto(double distanciaKm) {
        if (distanciaKm <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor a 0");
        }
        return TARIFA_BASE + COSTO_POR_KM * distanciaKm;
    }
}
