public interface IServicioDomicilio {


    boolean aceptaDistancia(double distanciaKm);

    double calcularCosto(double distanciaKm);

    boolean solicitarServicio(double distanciaKm);
}
