public class DetallePedido {

    private Producto producto;
    private int cantidad;
    private double subtotal;

    public DetallePedido(Producto producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        this.producto = producto;
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        calcularSubtotal();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /** Precio de venta del producto por la cantidad pedida. */
    public double calcularSubtotal() {
        subtotal = producto.calcularPrecioVenta() * cantidad;
        return subtotal;
    }
}
