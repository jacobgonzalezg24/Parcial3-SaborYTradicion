import java.util.List;

public interface ICliente {

    boolean agregarPedidoAlHistorial(Pedido pedido);

    List<Pedido> consultarPedidos();
}
