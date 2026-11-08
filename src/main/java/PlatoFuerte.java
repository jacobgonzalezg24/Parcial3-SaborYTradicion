public class PlatoFuerte extends Producto {

    private double pesoGramos;
    private TipoPreparacion tipoPreparacion;
    private boolean coccionAprobada;

    public PlatoFuerte(String codigo, String nombre, String descripcion, double precioBase,
                       int tiempoPreparacionMin, double pesoGramos, TipoPreparacion tipoPreparacion,
                       boolean coccionAprobada) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMin, CategoriaProducto.PLATO_FUERTE);
        if (pesoGramos <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0");
        }
        if (tipoPreparacion == null) {
            throw new IllegalArgumentException("El tipo de preparación es obligatorio");
        }
        this.pesoGramos = pesoGramos;
        this.tipoPreparacion = tipoPreparacion;
        this.coccionAprobada = coccionAprobada;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public TipoPreparacion getTipoPreparacion() {
        return tipoPreparacion;
    }

    public void setTipoPreparacion(TipoPreparacion tipoPreparacion) {
        this.tipoPreparacion = tipoPreparacion;
    }

    public boolean isCoccionAprobada() {
        return coccionAprobada;
    }

    public void setCoccionAprobada(boolean coccionAprobada) {
        this.coccionAprobada = coccionAprobada;
    }

    /** Incremento del 20 % sobre el precio base. */
    @Override
    public double calcularPrecioVenta() {
        return getPrecioBase() * 1.20;
    }

    /** Peso entre 200 - 900 g, preparación entre 10 - 60 min y cocción aprobada. */
    @Override
    public boolean cumpleCondicionesCalidad() {
        boolean pesoValido = pesoGramos >= 200 && pesoGramos <= 900;
        boolean tiempoValido = getTiempoPreparacionMin() >= 10 && getTiempoPreparacionMin() <= 60;
        return pesoValido && tiempoValido && coccionAprobada;
    }
}
