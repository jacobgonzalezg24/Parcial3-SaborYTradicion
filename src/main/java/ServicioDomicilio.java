public abstract class ServicioDomicilio implements IServicioDomicilio {

    private String nombre;
    private double distanciaMaximaKm;
    private boolean aceptado;

    public ServicioDomicilio(String nombre, double distanciaMaximaKm) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del servicio es obligatorio");
        }
        if (distanciaMaximaKm <= 0) {
            throw new IllegalArgumentException("La distancia máxima debe ser mayor a 0");
        }
        this.nombre = nombre;
        this.distanciaMaximaKm = distanciaMaximaKm;
        this.aceptado = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDistanciaMaximaKm() {
        return distanciaMaximaKm;
    }

    public void setDistanciaMaximaKm(double distanciaMaximaKm) {
        this.distanciaMaximaKm = distanciaMaximaKm;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    // La empresa acepta recorridos mayores que 0 y hasta su distancia máxima
    @Override
    public boolean aceptaDistancia(double distanciaKm) {
        return distanciaKm > 0 && distanciaKm <= distanciaMaximaKm;
    }

    // Procedimiento común para solicitar el servicio a cualquier empresa. Marca el servicio como aceptado solo si la distancia está permitida.

    @Override
    public boolean solicitarServicio(double distanciaKm) {
        aceptado = aceptaDistancia(distanciaKm);
        return aceptado;
    }

    // Cada empresa calcula su costo con su propia tarifa
    @Override
    public abstract double calcularCosto(double distanciaKm);
}