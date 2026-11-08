public abstract class Producto {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int tiempoPreparacionMin;
    private CategoriaProducto categoria;

    public Producto(String codigo, String nombre, String descripcion, double precioBase,
                    int tiempoPreparacionMin, CategoriaProducto categoria) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del producto es obligatorio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (precioBase <= 0) {
            throw new IllegalArgumentException("El precio base debe ser mayor a 0");
        }
        if (tiempoPreparacionMin < 0) {
            throw new IllegalArgumentException("El tiempo de preparación no puede ser negativo");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiempoPreparacionMin = tiempoPreparacionMin;
        this.categoria = categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public int getTiempoPreparacionMin() {
        return tiempoPreparacionMin;
    }

    public void setTiempoPreparacionMin(int tiempoPreparacionMin) {
        this.tiempoPreparacionMin = tiempoPreparacionMin;
    }

    public CategoriaProducto getCategoria() {
        return categoria;
    }

    public abstract double calcularPrecioVenta();

    public abstract boolean cumpleCondicionesCalidad();
}
