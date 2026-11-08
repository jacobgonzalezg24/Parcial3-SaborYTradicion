public class Postre extends Producto {

    private double pesoGramos;
    private double temperaturaAlmacenamiento;
    private boolean requiereRefrigeracion;

    public Postre(String codigo, String nombre, String descripcion, double precioBase,
                  int tiempoPreparacionMin, double pesoGramos, double temperaturaAlmacenamiento,
                  boolean requiereRefrigeracion) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMin, CategoriaProducto.POSTRE);
        if (pesoGramos <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0");
        }
        this.pesoGramos = pesoGramos;
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public double getTemperaturaAlmacenamiento() {
        return temperaturaAlmacenamiento;
    }

    public void setTemperaturaAlmacenamiento(double temperaturaAlmacenamiento) {
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
    }

    public boolean isRequiereRefrigeracion() {
        return requiereRefrigeracion;
    }

    public void setRequiereRefrigeracion(boolean requiereRefrigeracion) {
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    /** Incremento del 15 % sobre el precio base, más 5 % del precio base si requiere refrigeración. */
    @Override
    public double calcularPrecioVenta() {
        double precio = getPrecioBase() * 1.15;
        if (requiereRefrigeracion) {
            precio += getPrecioBase() * 0.05;
        }
        return precio;
    }

    /**
     * Peso entre 80 y 500 g. Con refrigeración: entre 0 y 6 °C. Sin refrigeración: entre 15 y 30 °C.
     */
    @Override
    public boolean cumpleCondicionesCalidad() {
        boolean cumple = false;
        if (pesoGramos >= 80 && pesoGramos <= 500) {
            if (requiereRefrigeracion) {
                cumple = temperaturaAlmacenamiento >= 0 && temperaturaAlmacenamiento <= 6;
            } else {
                cumple = temperaturaAlmacenamiento >= 15 && temperaturaAlmacenamiento <= 30;
            }
        }
        return cumple;
    }
}
