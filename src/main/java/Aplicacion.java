import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Aplicacion {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Restaurante restaurante = new Restaurante("Sabor & Tradición", "900123456-7", "Armenia, Quindío");

        int opcion = 0;
        do {
            System.out.println();
            System.out.println("========== Sabor & Tradición - Restaurante ===========");
            System.out.println("1. Registrar plato fuerte en el menú");
            System.out.println("2. Registrar bebida en el menú");
            System.out.println("3. Registrar postre en el menú");
            System.out.println("4. Consultar el menú (información y precio de venta)");
            System.out.println("5. Consultar productos que cumplen y no cumplen las condiciones de calidad");
            System.out.println("6. Registrar cliente");
            System.out.println("7. Registrar empleado");
            System.out.println("8. Consultar clientes y empleados");
            System.out.println("9. Crear pedido");
            System.out.println("10. Agregar producto a un pedido");
            System.out.println("11. Solicitar domicilio para un pedido");
            System.out.println("12. Confirmar pedido");
            System.out.println("13. Calcular el valor total de un pedido");
            System.out.println("14. Consultar los pedidos de un cliente");
            System.out.println("15. Marcar pedido como entregado");
            System.out.println("16. Cancelar pedido");
            System.out.println("17. Consultar pedidos por estado");
            System.out.println("18. Reportes de ventas");
            System.out.println("0. Salir");

            System.out.print("Seleccione la opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarPlatoFuerte(restaurante, sc);
                    break;
                case 2:
                    registrarBebida(restaurante, sc);
                    break;
                case 3:
                    registrarPostre(restaurante, sc);
                    break;
                case 4:
                    consultarMenu(restaurante);
                    break;
                case 5:
                    consultarCalidadProductos(restaurante);
                    break;
                case 6:
                    registrarCliente(restaurante, sc);
                    break;
                case 7:
                    registrarEmpleado(restaurante, sc);
                    break;
                case 8:
                    consultarClientesYEmpleados(restaurante);
                    break;
                case 9:
                    crearPedido(restaurante, sc);
                    break;
                case 10:
                    agregarProductoAPedido(restaurante, sc);
                    break;
                case 11:
                    solicitarDomicilio(restaurante, sc);
                    break;
                case 12:
                    confirmarPedido(restaurante, sc);
                    break;
                case 13:
                    calcularValorTotalPedido(restaurante, sc);
                    break;
                case 14:
                    consultarPedidosCliente(restaurante, sc);
                    break;
                case 15:
                    entregarPedido(restaurante, sc);
                    break;
                case 16:
                    cancelarPedido(restaurante, sc);
                    break;
                case 17:
                    consultarPedidosPorEstado(restaurante, sc);
                    break;
                case 18:
                    mostrarReportes(restaurante);
                    break;
                case 0:
                    System.out.println("Gracias por usar el sistema de " + restaurante.getNombreComercial() + ".");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
                    break;
            }

        } while (opcion != 0);
        sc.close();
    }

    // ==================================================================
    // Productos (funcionalidades 1, 3, 4 y 5)
    // ==================================================================

    static void registrarPlatoFuerte(Restaurante restaurante, Scanner sc) {
        System.out.println("----Registrar plato fuerte----");

        System.out.println("Ingrese el código del plato");
        String codigo = sc.nextLine();

        System.out.println("Ingrese el nombre del plato");
        String nombre = sc.nextLine();

        System.out.println("Ingrese la descripción del plato");
        String descripcion = sc.nextLine();

        System.out.println("Ingrese el precio base del plato");
        double precioBase = sc.nextDouble();
        sc.nextLine();

        System.out.println("Ingrese el tiempo de preparación en minutos");
        int tiempoPreparacion = sc.nextInt();
        sc.nextLine();

        System.out.println("Ingrese el peso del plato en gramos");
        double peso = sc.nextDouble();
        sc.nextLine();

        System.out.println("Seleccione el tipo de preparación: (1. Asado, 2. Frito, 3. Horneado, 4. A la plancha)");
        int opcionPreparacion = sc.nextInt();
        sc.nextLine();

        TipoPreparacion tipoPreparacion = null;
        if (opcionPreparacion == 1) tipoPreparacion = TipoPreparacion.ASADO;
        else if (opcionPreparacion == 2) tipoPreparacion = TipoPreparacion.FRITO;
        else if (opcionPreparacion == 3) tipoPreparacion = TipoPreparacion.HORNEADO;
        else if (opcionPreparacion == 4) tipoPreparacion = TipoPreparacion.A_LA_PLANCHA;

        if (tipoPreparacion == null) {
            System.out.println("Error (el tipo de preparación no es válido).");
            return;
        }

        System.out.println("¿El plato superó el control de cocción? (1. Sí, 2. No)");
        int opcionCoccion = sc.nextInt();
        sc.nextLine();
        boolean coccionAprobada = (opcionCoccion == 1);

        try {
            PlatoFuerte plato = new PlatoFuerte(codigo, nombre, descripcion, precioBase, tiempoPreparacion, peso,
                    tipoPreparacion, coccionAprobada);
            mostrarResultadoRegistroProducto(restaurante.registrarProducto(plato), plato);
        } catch (IllegalArgumentException e) {
            System.out.println("Error (" + e.getMessage() + ").");
        }
    }

    static void registrarBebida(Restaurante restaurante, Scanner sc) {
        System.out.println("----Registrar bebida----");

        System.out.println("Ingrese el código de la bebida");
        String codigo = sc.nextLine();

        System.out.println("Ingrese el nombre de la bebida");
        String nombre = sc.nextLine();

        System.out.println("Ingrese la descripción de la bebida");
        String descripcion = sc.nextLine();

        System.out.println("Ingrese el precio base de la bebida");
        double precioBase = sc.nextDouble();
        sc.nextLine();

        System.out.println("Ingrese el tiempo de preparación en minutos");
        int tiempoPreparacion = sc.nextInt();
        sc.nextLine();

        System.out.println("Ingrese el volumen en mililitros");
        double volumen = sc.nextDouble();
        sc.nextLine();

        System.out.println("Ingrese la temperatura de servicio en °C");
        double temperatura = sc.nextDouble();
        sc.nextLine();

        System.out.println("Seleccione el tipo de bebida: (1. Fría, 2. Caliente)");
        int opcionTipoBebida = sc.nextInt();
        sc.nextLine();

        TipoBebida tipoBebida = null;
        if (opcionTipoBebida == 1) tipoBebida = TipoBebida.FRIA;
        else if (opcionTipoBebida == 2) tipoBebida = TipoBebida.CALIENTE;

        if (tipoBebida == null) {
            System.out.println("Error (el tipo de bebida no es válido).");
            return;
        }

        System.out.println("¿La bebida requiere preparación especial? (1. Sí, 2. No)");
        int opcionEspecial = sc.nextInt();
        sc.nextLine();
        boolean preparacionEspecial = (opcionEspecial == 1);

        try {
            Bebida bebida = new Bebida(codigo, nombre, descripcion, precioBase, tiempoPreparacion, volumen,
                    temperatura, tipoBebida, preparacionEspecial);
            mostrarResultadoRegistroProducto(restaurante.registrarProducto(bebida), bebida);
        } catch (IllegalArgumentException e) {
            System.out.println("Error (" + e.getMessage() + ").");
        }
    }

    static void registrarPostre(Restaurante restaurante, Scanner sc) {
        System.out.println("----Registrar postre----");

        System.out.println("Ingrese el código del postre");
        String codigo = sc.nextLine();

        System.out.println("Ingrese el nombre del postre");
        String nombre = sc.nextLine();

        System.out.println("Ingrese la descripción del postre");
        String descripcion = sc.nextLine();

        System.out.println("Ingrese el precio base del postre");
        double precioBase = sc.nextDouble();
        sc.nextLine();

        System.out.println("Ingrese el tiempo de preparación en minutos");
        int tiempoPreparacion = sc.nextInt();
        sc.nextLine();

        System.out.println("Ingrese el peso del postre en gramos");
        double peso = sc.nextDouble();
        sc.nextLine();

        System.out.println("Ingrese la temperatura de almacenamiento en °C");
        double temperatura = sc.nextDouble();
        sc.nextLine();

        System.out.println("¿El postre requiere refrigeración? (1. Sí, 2. No)");
        int opcionRefrigeracion = sc.nextInt();
        sc.nextLine();
        boolean requiereRefrigeracion = (opcionRefrigeracion == 1);

        try {
            Postre postre = new Postre(codigo, nombre, descripcion, precioBase, tiempoPreparacion, peso,
                    temperatura, requiereRefrigeracion);
            mostrarResultadoRegistroProducto(restaurante.registrarProducto(postre), postre);
        } catch (IllegalArgumentException e) {
            System.out.println("Error (" + e.getMessage() + ").");
        }
    }

    static void mostrarResultadoRegistroProducto(boolean registrado, Producto producto) {
        if (registrado) {
            System.out.println("El producto ha sido registrado con éxito. Precio de venta: "
                    + dinero(producto.calcularPrecioVenta()));
            if (!producto.cumpleCondicionesCalidad()) {
                System.out.println("Advertencia: el producto no cumple las condiciones de calidad "
                        + "y no podrá incluirse en pedidos confirmados.");
            }
        } else {
            System.out.println("Error (ya existe un producto con el código " + producto.getCodigo() + ").");
        }
    }

    static void consultarMenu(Restaurante restaurante) {
        System.out.println("----Menú del restaurante----");
        List<Producto> productos = restaurante.consultarProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        }
        for (Producto producto : productos) {
            mostrarProducto(producto);
        }
    }

    static void consultarCalidadProductos(Restaurante restaurante) {
        System.out.println("----Productos que CUMPLEN las condiciones de calidad----");
        List<Producto> cumplen = restaurante.consultarProductosCumplenCalidad();
        if (cumplen.isEmpty()) {
            System.out.println("Ninguno.");
        }
        for (Producto producto : cumplen) {
            mostrarProducto(producto);
        }

        System.out.println();
        System.out.println("----Productos RECHAZADOS (no cumplen las condiciones de calidad)----");
        List<Producto> noCumplen = restaurante.consultarProductosNoCumplenCalidad();
        if (noCumplen.isEmpty()) {
            System.out.println("Ninguno.");
        }
        for (Producto producto : noCumplen) {
            mostrarProducto(producto);
        }
    }

    static void mostrarProducto(Producto producto) {
        System.out.println("[" + producto.getCodigo() + "] " + producto.getNombre()
                + " (" + producto.getCategoria() + ")");
        System.out.println("     " + producto.getDescripcion());
        System.out.println("     Precio base: " + dinero(producto.getPrecioBase())
                + " | Precio de venta: " + dinero(producto.calcularPrecioVenta())
                + " | Preparación: " + producto.getTiempoPreparacionMin() + " min"
                + " | Calidad: " + (producto.cumpleCondicionesCalidad() ? "CUMPLE" : "NO CUMPLE"));
    }

    // ==================================================================
    // Personas (funcionalidad 2)
    // ==================================================================

    static void registrarCliente(Restaurante restaurante, Scanner sc) {
        System.out.println("----Registrar cliente----");

        System.out.println("Ingrese el documento de identidad");
        String documento = sc.nextLine();

        if (restaurante.verificarCliente(documento)) {
            System.out.println("Error (ya existe un cliente con ese documento).");
            return;
        }

        System.out.println("Ingrese el nombre completo");
        String nombre = sc.nextLine();

        System.out.println("Ingrese el teléfono");
        String telefono = sc.nextLine();

        System.out.println("Ingrese el correo electrónico");
        String correo = sc.nextLine();

        System.out.println("Ingrese la dirección de residencia");
        String direccion = sc.nextLine();

        try {
            Cliente cliente = new Cliente(documento, nombre, telefono, correo, direccion);
            if (restaurante.registrarCliente(cliente)) {
                System.out.println("El cliente ha sido registrado con éxito.");
            } else {
                System.out.println("Error (no se pudo registrar el cliente).");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error (" + e.getMessage() + ").");
        }
    }

    static void registrarEmpleado(Restaurante restaurante, Scanner sc) {
        System.out.println("----Registrar empleado----");

        System.out.println("Seleccione el tipo de empleado: (1. Empleado, 2. Cliente-empleado [es cliente y empleado a la vez])");
        int opcionTipo = sc.nextInt();
        sc.nextLine();

        if (opcionTipo != 1 && opcionTipo != 2) {
            System.out.println("Debe seleccionar una opción válida");
            return;
        }

        System.out.println("Ingrese el código del empleado");
        String codigoEmpleado = sc.nextLine();

        if (restaurante.verificarEmpleado(codigoEmpleado)) {
            System.out.println("Error (ya existe un empleado con ese código).");
            return;
        }

        System.out.println("Ingrese el documento de identidad");
        String documento = sc.nextLine();

        if (opcionTipo == 2 && restaurante.verificarCliente(documento)) {
            System.out.println("Error (ya existe un cliente con ese documento).");
            return;
        }

        System.out.println("Ingrese el nombre completo");
        String nombre = sc.nextLine();

        System.out.println("Ingrese el teléfono");
        String telefono = sc.nextLine();

        System.out.println("Ingrese el correo electrónico");
        String correo = sc.nextLine();

        System.out.println("Seleccione el cargo: (1. Mesero, 2. Cajero, 3. Chef, 4. Domiciliario, 5. Gerente)");
        int opcionCargo = sc.nextInt();
        sc.nextLine();

        Cargo cargo = null;
        if (opcionCargo == 1) cargo = Cargo.MESERO;
        else if (opcionCargo == 2) cargo = Cargo.CAJERO;
        else if (opcionCargo == 3) cargo = Cargo.CHEF;
        else if (opcionCargo == 4) cargo = Cargo.DOMICILIARIO;
        else if (opcionCargo == 5) cargo = Cargo.GERENTE;

        if (cargo == null) {
            System.out.println("Error (el cargo no es válido).");
            return;
        }

        if (opcionTipo == 1) {
            try {
                Empleado empleado = new Empleado(documento, nombre, telefono, correo, codigoEmpleado, cargo);
                if (restaurante.registrarEmpleado(empleado)) {
                    System.out.println("El empleado ha sido registrado con éxito.");
                } else {
                    System.out.println("Error (no se pudo registrar el empleado).");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error (" + e.getMessage() + ").");
            }
        } else {
            System.out.println("Ingrese la dirección de residencia");
            String direccion = sc.nextLine();

            try {
                ClienteEmpleado clienteEmpleado = new ClienteEmpleado(documento, nombre, telefono, correo,
                        direccion, codigoEmpleado, cargo);
                if (restaurante.registrarEmpleado(clienteEmpleado) && restaurante.registrarCliente(clienteEmpleado)) {
                    System.out.println("El cliente-empleado ha sido registrado como empleado y como cliente.");
                } else {
                    System.out.println("Error (no se pudo registrar el cliente-empleado).");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error (" + e.getMessage() + ").");
            }
        }
    }

    static void consultarClientesYEmpleados(Restaurante restaurante) {
        System.out.println("----Clientes----");
        if (restaurante.getListaClientes().isEmpty()) {
            System.out.println("No hay clientes registrados.");
        }
        for (Cliente cliente : restaurante.getListaClientes()) {
            System.out.println("- " + cliente.getDocumento() + " | " + cliente.getNombreCompleto()
                    + " | " + cliente.getTelefono() + " | " + cliente.getCorreo()
                    + " | " + cliente.getDireccion());
        }

        System.out.println();
        System.out.println("----Empleados----");
        if (restaurante.consultarEmpleados().isEmpty()) {
            System.out.println("No hay empleados registrados.");
        }
        for (IEmpleado empleado : restaurante.consultarEmpleados()) {
            String nombre = "";
            if (empleado instanceof Persona) {
                nombre = ((Persona) empleado).getNombreCompleto();
            }
            System.out.println("- " + empleado.getCodigoEmpleado() + " | " + nombre + " | " + empleado.getCargo());
        }
    }

    // ==================================================================
    // Pedidos (funcionalidades 6, 8, 9, 10 y 11)
    // ==================================================================

    static void crearPedido(Restaurante restaurante, Scanner sc) {
        System.out.println("----Crear pedido----");

        System.out.println("Ingrese el código del pedido");
        String codigo = sc.nextLine();

        if (restaurante.verificarPedido(codigo)) {
            System.out.println("Error (ya existe un pedido con ese código).");
            return;
        }

        System.out.println("Ingrese el documento del cliente");
        String documento = sc.nextLine();

        Cliente cliente = restaurante.buscarCliente(documento);
        if (cliente == null) {
            System.out.println("Error (el cliente no está registrado).");
            return;
        }

        System.out.println("Seleccione la modalidad de entrega: (1. Consumo en el restaurante, 2. Domicilio)");
        int opcionModalidad = sc.nextInt();
        sc.nextLine();

        ModalidadEntrega modalidad = null;
        if (opcionModalidad == 1) modalidad = ModalidadEntrega.CONSUMO_RESTAURANTE;
        else if (opcionModalidad == 2) modalidad = ModalidadEntrega.DOMICILIO;

        if (modalidad == null) {
            System.out.println("Error (la modalidad de entrega no es válida).");
            return;
        }

        try {
            Pedido pedido = new Pedido(codigo, LocalDate.now(), cliente, modalidad);
            if (restaurante.registrarPedido(pedido)) {
                System.out.println("El pedido ha sido creado en estado PENDIENTE. Agregue productos con la opción 10.");
                if (modalidad == ModalidadEntrega.DOMICILIO) {
                    System.out.println("Recuerde solicitar el domicilio con la opción 11 antes de confirmar.");
                }
            } else {
                System.out.println("Error (no se pudo crear el pedido).");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error (" + e.getMessage() + ").");
        }
    }

    static void agregarProductoAPedido(Restaurante restaurante, Scanner sc) {
        System.out.println("----Agregar producto a un pedido----");

        System.out.println("Ingrese el código del pedido");
        String codigoPedido = sc.nextLine();

        Pedido pedido = restaurante.buscarPedido(codigoPedido);
        if (pedido == null) {
            System.out.println("Error (el pedido no existe).");
            return;
        }

        System.out.println("Ingrese el código del producto");
        String codigoProducto = sc.nextLine();

        if (restaurante.buscarProducto(codigoProducto) == null) {
            System.out.println("Error (el producto no pertenece al menú).");
            return;
        }

        System.out.println("Ingrese la cantidad");
        int cantidad = sc.nextInt();
        sc.nextLine();

        if (restaurante.agregarProductoAPedido(codigoPedido, codigoProducto, cantidad)) {
            System.out.println("El producto ha sido agregado al pedido.");
        } else {
            System.out.println("Error (la cantidad debe ser mayor a 0 y el pedido debe estar PENDIENTE).");
        }
    }

    static void solicitarDomicilio(Restaurante restaurante, Scanner sc) {
        System.out.println("----Solicitar domicilio----");

        System.out.println("Ingrese el código del pedido");
        String codigoPedido = sc.nextLine();

        Pedido pedido = restaurante.buscarPedido(codigoPedido);
        if (pedido == null) {
            System.out.println("Error (el pedido no existe).");
            return;
        }
        if (pedido.getModalidad() != ModalidadEntrega.DOMICILIO) {
            System.out.println("Error (el pedido es para consumo en el restaurante, no admite domicilio).");
            return;
        }

        System.out.println("Empresas de domicilio disponibles:");
        int numero = 1;
        for (ServicioDomicilio servicioListado : restaurante.getListaServiciosDomicilio()) {
            System.out.println(numero + ". " + servicioListado.getNombre()
                    + " (hasta " + servicioListado.getDistanciaMaximaKm() + " km)");
            numero++;
        }

        System.out.println("Seleccione la empresa");
        int opcionEmpresa = sc.nextInt();
        sc.nextLine();

        if (opcionEmpresa < 1 || opcionEmpresa > restaurante.getListaServiciosDomicilio().size()) {
            System.out.println("Error (la empresa seleccionada no es válida).");
            return;
        }
        ServicioDomicilio servicio = restaurante.getListaServiciosDomicilio().get(opcionEmpresa - 1);

        System.out.println("Ingrese la distancia del recorrido en kilómetros");
        double distancia = sc.nextDouble();
        sc.nextLine();

        if (restaurante.solicitarDomicilio(codigoPedido, servicio.getNombre(), distancia)) {
            System.out.println(servicio.getNombre() + " aceptó el servicio. Costo del domicilio: "
                    + dinero(servicio.calcularCosto(distancia)));
        } else if (!servicio.aceptaDistancia(distancia)) {
            System.out.println("Error (" + servicio.getNombre() + " rechazó el servicio: la distancia no está permitida, máximo "
                    + servicio.getDistanciaMaximaKm() + " km).");
        } else {
            System.out.println("Error (el pedido debe estar PENDIENTE para solicitar el domicilio).");
        }
    }

    static void confirmarPedido(Restaurante restaurante, Scanner sc) {
        System.out.println("----Confirmar pedido----");

        System.out.println("Ingrese el código del pedido");
        String codigoPedido = sc.nextLine();

        Pedido pedido = restaurante.buscarPedido(codigoPedido);
        if (pedido == null) {
            System.out.println("Error (el pedido no existe).");
            return;
        }

        if (restaurante.confirmarPedido(codigoPedido)) {
            System.out.println("El pedido ha sido confirmado y registrado en el historial del cliente.");
            System.out.println("Valor total: " + dinero(pedido.getValorTotal()));
        } else {
            System.out.println("Error (no se pudo confirmar el pedido):");
            explicarFalloConfirmacion(restaurante, pedido);
        }
    }

    static void explicarFalloConfirmacion(Restaurante restaurante, Pedido pedido) {
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            System.out.println(" - El pedido está " + pedido.getEstado() + " (solo se confirman pedidos PENDIENTES).");
            return;
        }
        if (!restaurante.verificarCliente(pedido.getCliente().getDocumento())) {
            System.out.println(" - El cliente no está registrado.");
        }
        if (pedido.getDetalles().isEmpty()) {
            System.out.println(" - El pedido no tiene productos.");
        }
        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto producto = detalle.getProducto();
            if (!restaurante.verificarProducto(producto.getCodigo())) {
                System.out.println(" - El producto " + producto.getNombre() + " no pertenece al menú.");
            }
            if (detalle.getCantidad() <= 0) {
                System.out.println(" - El producto " + producto.getNombre() + " tiene una cantidad no válida.");
            }
            if (!producto.cumpleCondicionesCalidad()) {
                System.out.println(" - El producto " + producto.getNombre() + " no cumple las condiciones de calidad.");
            }
        }
        if (pedido.getModalidad() == ModalidadEntrega.DOMICILIO && pedido.getServicioDomicilio() == null) {
            System.out.println(" - Ninguna empresa de domicilio ha aceptado el servicio.");
        }
    }

    static void calcularValorTotalPedido(Restaurante restaurante, Scanner sc) {
        System.out.println("----Calcular el valor total de un pedido----");

        System.out.println("Ingrese el código del pedido");
        String codigoPedido = sc.nextLine();

        Pedido pedido = restaurante.buscarPedido(codigoPedido);
        if (pedido == null) {
            System.out.println("Error (el pedido no existe).");
            return;
        }

        restaurante.calcularValorTotalPedido(codigoPedido);
        mostrarPedido(pedido);
    }

    static void consultarPedidosCliente(Restaurante restaurante, Scanner sc) {
        System.out.println("----Consultar los pedidos de un cliente----");

        System.out.println("Ingrese el documento del cliente");
        String documento = sc.nextLine();

        Cliente cliente = restaurante.buscarCliente(documento);
        if (cliente == null) {
            System.out.println("Error (el cliente no está registrado).");
            return;
        }

        List<Pedido> pedidos = restaurante.consultarPedidosCliente(documento);
        System.out.println("Pedidos realizados por " + cliente.getNombreCompleto() + ":");
        if (pedidos.isEmpty()) {
            System.out.println("El cliente no ha realizado pedidos.");
        }
        for (Pedido pedido : pedidos) {
            mostrarPedido(pedido);
        }
    }

    static void entregarPedido(Restaurante restaurante, Scanner sc) {
        System.out.println("----Marcar pedido como entregado----");

        System.out.println("Ingrese el código del pedido");
        String codigoPedido = sc.nextLine();

        Pedido pedido = restaurante.buscarPedido(codigoPedido);
        if (pedido == null) {
            System.out.println("Error (el pedido no existe).");
        } else if (restaurante.entregarPedido(codigoPedido)) {
            System.out.println("El pedido ha sido marcado como ENTREGADO.");
        } else {
            System.out.println("Error (el pedido está " + pedido.getEstado()
                    + " y solo se entregan pedidos CONFIRMADOS).");
        }
    }

    static void cancelarPedido(Restaurante restaurante, Scanner sc) {
        System.out.println("----Cancelar pedido----");

        System.out.println("Ingrese el código del pedido");
        String codigoPedido = sc.nextLine();

        Pedido pedido = restaurante.buscarPedido(codigoPedido);
        if (pedido == null) {
            System.out.println("Error (el pedido no existe).");
        } else if (restaurante.cancelarPedido(codigoPedido)) {
            System.out.println("El pedido ha sido CANCELADO y no se contabilizará como ingreso.");
        } else {
            System.out.println("Error (el pedido está " + pedido.getEstado() + " y no se puede cancelar).");
        }
    }

    static void consultarPedidosPorEstado(Restaurante restaurante, Scanner sc) {
        System.out.println("----Consultar pedidos por estado----");

        System.out.println("Seleccione el estado: (1. Pendiente, 2. Confirmado, 3. Entregado, 4. Cancelado)");
        int opcionEstado = sc.nextInt();
        sc.nextLine();

        EstadoPedido estado = null;
        if (opcionEstado == 1) estado = EstadoPedido.PENDIENTE;
        else if (opcionEstado == 2) estado = EstadoPedido.CONFIRMADO;
        else if (opcionEstado == 3) estado = EstadoPedido.ENTREGADO;
        else if (opcionEstado == 4) estado = EstadoPedido.CANCELADO;

        if (estado == null) {
            System.out.println("Error (el estado no es válido).");
            return;
        }

        List<Pedido> pedidos = restaurante.consultarPedidosPorEstado(estado);
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos en estado " + estado + ".");
        }
        for (Pedido pedido : pedidos) {
            mostrarPedido(pedido);
        }
    }

    static void mostrarPedido(Pedido pedido) {
        System.out.println("Pedido " + pedido.getCodigo() + " | " + pedido.getFecha()
                + " | Cliente: " + pedido.getCliente().getNombreCompleto()
                + " | " + pedido.getModalidad() + " | ESTADO: " + pedido.getEstado());
        for (DetallePedido detalle : pedido.getDetalles()) {
            System.out.println("     - " + detalle.getProducto().getNombre() + " x" + detalle.getCantidad()
                    + " = " + dinero(detalle.calcularSubtotal()));
        }
        System.out.println("     Productos: " + dinero(pedido.calcularTotalProductos()));
        if (pedido.getModalidad() == ModalidadEntrega.DOMICILIO) {
            if (pedido.getServicioDomicilio() != null) {
                System.out.println("     Domicilio (" + pedido.getServicioDomicilio().getNombre() + ", "
                        + pedido.getDistanciaKm() + " km): " + dinero(pedido.calcularCostoDomicilio()));
            } else {
                System.out.println("     Domicilio: sin empresa asignada");
            }
        }
        System.out.println("     TOTAL: " + dinero(pedido.calcularTotal()));
    }


    static void mostrarReportes(Restaurante restaurante) {
        System.out.println("----Reportes de ventas (pedidos confirmados y entregados)----");
        System.out.println("Ingresos del restaurante (sin domicilios): "
                + dinero(restaurante.calcularIngresosRestaurante()));
        System.out.println("Valores cobrados por domicilios (aparte): "
                + dinero(restaurante.calcularIngresosDomicilios()));

        Producto masVendido = restaurante.obtenerProductoMasVendido();
        if (masVendido == null) {
            System.out.println("Producto más vendido: aún no se han registrado ventas.");
        } else {
            System.out.println("Producto más vendido: " + masVendido.getNombre()
                    + " [" + masVendido.getCodigo() + "]");
        }

        CategoriaProducto categoria = restaurante.obtenerCategoriaMayoresIngresos();
        if (categoria == null) {
            System.out.println("Categoría con mayores ingresos: aún no se han registrado ventas.");
        } else {
            System.out.println("Categoría con mayores ingresos: " + categoria);
        }
    }

    static String dinero(double valor) {
        return String.format("$%,.0f", valor);
    }
}
