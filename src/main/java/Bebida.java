public class Bebida extends Producto {

    private double volumenMl;
    private double temperaturaServicio;
    private TipoBebida tipoBebida;
    private boolean preparacionEspecial;

    public Bebida(String codigo, String nombre, String descripcion, double precioBase,
                  int tiempoPreparacionMin, double volumenMl, double temperaturaServicio,
                  TipoBebida tipoBebida, boolean preparacionEspecial) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMin, CategoriaProducto.BEBIDA);
        if (volumenMl <= 0) {
            throw new IllegalArgumentException("El volumen debe ser mayor a 0");
        }
        if (tipoBebida == null) {
            throw new IllegalArgumentException("El tipo de bebida es obligatorio");
        }
        this.volumenMl = volumenMl;
        this.temperaturaServicio = temperaturaServicio;
        this.tipoBebida = tipoBebida;
        this.preparacionEspecial = preparacionEspecial;
    }

    public double getVolumenMl() {
        return volumenMl;
    }

    public void setVolumenMl(double volumenMl) {
        this.volumenMl = volumenMl;
    }

    public double getTemperaturaServicio() {
        return temperaturaServicio;
    }

    public void setTemperaturaServicio(double temperaturaServicio) {
        this.temperaturaServicio = temperaturaServicio;
    }

    public TipoBebida getTipoBebida() {
        return tipoBebida;
    }

    public void setTipoBebida(TipoBebida tipoBebida) {
        this.tipoBebida = tipoBebida;
    }

    public boolean isPreparacionEspecial() {
        return preparacionEspecial;
    }

    public void setPreparacionEspecial(boolean preparacionEspecial) {
        this.preparacionEspecial = preparacionEspecial;
    }

    /** Incremento del 10 % sobre el precio base, más $3.000 si requiere preparación especial. */
    @Override
    public double calcularPrecioVenta() {
        double precio = getPrecioBase() * 1.10;
        if (preparacionEspecial) {
            precio += 3000;
        }
        return precio;
    }

    /**
     * Volumen entre 150 y 1.500 ml. Fría: entre 0 y 10 °C. Caliente: entre 55 y 85 °C.
     */
    @Override
    public boolean cumpleCondicionesCalidad() {
        boolean cumple = false;
        if (volumenMl >= 150 && volumenMl <= 1500) {
            if (tipoBebida == TipoBebida.FRIA) {
                cumple = temperaturaServicio >= 0 && temperaturaServicio <= 10;
            } else if (tipoBebida == TipoBebida.CALIENTE) {
                cumple = temperaturaServicio >= 55 && temperaturaServicio <= 85;
            }
        }
        return cumple;
    }
}
