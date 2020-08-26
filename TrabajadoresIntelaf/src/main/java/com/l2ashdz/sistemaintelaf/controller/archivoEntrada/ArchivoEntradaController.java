package com.l2ashdz.sistemaintelaf.controller.archivoEntrada;

import com.l2ashdz.sistemaintelaf.controller.LoginController;
import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.empleado.EmpleadoDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Empleado;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import com.l2ashdz.sistemaintelaf.ui.ArchivoEntradaView;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.*;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.*;
import static com.l2ashdz.sistemaintelaf.model.Archivo.leerArchivo;
import static com.l2ashdz.sistemaintelaf.model.Archivo.obtenerRutaArchivo;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAO;
import com.l2ashdz.sistemaintelaf.dao.pedido.PedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.ProductoPedidoDAO;
import com.l2ashdz.sistemaintelaf.dao.pedido.ProductoPedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.ExistenciaProducto;
import com.l2ashdz.sistemaintelaf.model.Pedido;
import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.ProductoPedido;
import com.l2ashdz.sistemaintelaf.model.TiempoTraslado;
import com.l2ashdz.sistemaintelaf.ui.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author asael
 */
public class ArchivoEntradaController implements ActionListener {

    private ArchivoEntradaView archivoEV;

    //Vista y controlador para login
    private LoginView loginV;
    private LoginController loginC;

    private Connection conexion;

    private CRUD<Empleado> empleadoDAO;
    private CRUD<Tienda> tiendaDAO;
    private CRUD<TiempoTraslado> tiempoDAO;
    private CRUD<Producto> productoDAO;
    private CRUD<ExistenciaProducto> existenciaPDAO;
    private CRUD<Cliente> clienteDAO;
    private PedidoDAO pedidoDAO;
    private ProductoPedidoDAO productoPDAO;

    private List<Empleado> empleados;
    private List<Pedido> pedidos;
    private List<ProductoPedido> productosP;

    private String pedidoConError = "0";
    private String path = "";
    private List<String> entrada;
    private List<String> errores;

    public ArchivoEntradaController(ArchivoEntradaView archivoEView) {
        pedidos = new ArrayList<>();
        productosP = new ArrayList<>();
        conexion = Conexion.getConexion();
        empleadoDAO = EmpleadoDAOImpl.getEmpleadoDAO();
        tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        tiempoDAO = TiempoTrasladoDAOImpl.getTiempoDAO();
        productoDAO = ProductoDAOImpl.getProductoDAO();
        existenciaPDAO = ExistenciaProductoDAOImpl.getExistenciaDAO();
        clienteDAO = ClienteDAOImpl.getClienteDAO();
        pedidoDAO = PedidoDAOImpl.getPedidoDAO();
        productoPDAO = ProductoPedidoDAOImpl.getProductoPDAO();

        loginV = new LoginView();
        this.archivoEV = archivoEView;
        this.archivoEV.getBtnBuscar().addActionListener(this);
        this.archivoEV.getBtnCancelar().addActionListener(this);
        this.archivoEV.getBtnIniciar().addActionListener(this);
        this.archivoEV.getBtnContinuar().addActionListener(this);

    }

    //inicia la interfaz para leer el archivo de entrada
    public void iniciar() {
        if (verificarInfoSistema()) {
            archivoEV.pack();
            archivoEV.setLocationRelativeTo(null);
            archivoEV.setVisible(true);
        } else {
            loginC = new LoginController(loginV);
            loginC.iniciar();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        //Obtiene la ruta del archivo
        if (archivoEV.getBtnBuscar() == ae.getSource()) {
            path = obtenerRutaArchivo();
            if (!path.isEmpty()) {
                archivoEV.getLblNombreArchivo().setText(path);
                archivoEV.getBtnIniciar().setEnabled(true);
            }

        } else if (archivoEV.getBtnCancelar() == ae.getSource()) {
            System.exit(0);

            //Empieza el analisis del archivo
        } else if (archivoEV.getBtnIniciar() == ae.getSource()) {
            if (!path.isEmpty()) {
                entrada = leerArchivo(path);
                analizarArchivo(entrada);
                archivoEV.getBtnContinuar().setEnabled(true);
            }

            //Si ya existe al menos un empleado muestra el login de lo contrario
            //limpia la interfaz para leer un nuevo archivo
        } else if (archivoEV.getBtnContinuar() == ae.getSource()) {
            if (verificarInfoSistema()) {
                limpiarInterfaz();
            } else {
                archivoEV.dispose();
                loginC = new LoginController(loginV);
                loginC.iniciar();
            }
        }
    }

    //Analiza el archivo
    private void analizarArchivo(List<String> entrada) {
        String[] parametros;
        errores = new ArrayList();
        JTextArea textA = archivoEV.getTxtAreaInformacion();
        String mensaje;
        
        for (int i = 0; i < entrada.size(); i++) {
            String linea = entrada.get(i);
            parametros = linea.split(",");
            try {
                switch (parametros[0]) {
                    case "TIENDA":
                        if (verificarTienda(parametros)) {
                            tiendaDAO.create(nuevaTienda(parametros));
                            textA.append("Se ingresara la tienda: " + parametros[3] + "\n");
                        }
                        break;
                    case "TIEMPO":
                        if (verificarTiempo(parametros)) {
                            tiempoDAO.create(nuevoTiempo(parametros));
                            textA.append("Se registrara el tiempo: " + parametros[3]
                                    + " entre las tiendas: " + parametros[1] + " y "
                                    + parametros[2] + "\n");
                        }
                        break;
                    case "PRODUCTO":
                        if (verificarProducto(parametros)) {
                            if (productoDAO.getObject(parametros[3]) == null) {
                                productoDAO.create(nuevoProducto(parametros));
                                textA.append("Se registrara el producto: " + parametros[1] + "\n");
                            }
                            existenciaPDAO.create(nuevaExistenciaProducto(parametros));
                            textA.append("Se registraran las existencias del producto: "
                                    + "" + parametros[1] + " en la tienda: " + parametros[6] + "\n");
                        }
                        break;
                    case "CLIENTE":
                        if (verificarCliente(parametros)) {
                            clienteDAO.create(nuevoCliente(parametros));
                            textA.append("Se registrara el cliente con el nit: "
                                    + parametros[2] + "\n");
                        }
                        break;
                    case "EMPLEADO":
                        if (verificarEmpleado(parametros)) {
                            empleadoDAO.create(nuevoEmpleado(parametros));
                            textA.append("Se registrara el empleado con codigo: "
                                    + parametros[2] + "\n");
                        }
                        break;
                    case "PEDIDO":
                        if (verificarPedido(parametros, productosP) && !parametros[1].equals(pedidoConError)) {
                            if (!isExistPedido(pedidos, parametros[1])) {
                                pedidos.add(nuevoPedido(parametros));
                                textA.append("Se registrara el pedido: " + parametros[1] + "\n");
                            }
                            productosP.add(nuevoProductoPedido(parametros));
                            textA.append("Se registrara el producto: " + parametros[6]
                                    + " en el pedido: " + parametros[1] + "\n");
                        }
                        break;
                    default:
                        mensaje = "Linea " + (i + 1) + ": No coincide con el inicio de alguna estructura\n";
                        errores.add(mensaje);
                }
            } catch (UserInputException e) {
                mensaje = "Linea " + (i + 1) + ": " + e.getMessage() + "\n";
                errores.add(mensaje);
                e.printStackTrace(System.out);
                eliminarPedidoConError(parametros[1]);
            }
        }
        
        verificarTotalAnticipo();
        
        if (!errores.isEmpty()) {
            textA.append("\n\nSe detectaron los siguientes errores en el archivo: \n");
            errores.forEach(error -> textA.append(error));
        } else {
            textA.append("\n\nNo se detectaron errores en el archio de entrada\n"
                    + "Presione el boton continuar para registrar los datos\n");
        }
        pedidos.forEach(p -> pedidoDAO.create(p));
        productosP.forEach(pp -> productoPDAO.create(pp));
        pedidoDAO.setNextCodigo(pedidoDAO.getCodigoPedido());
        pedidos.forEach(p -> System.out.println(p.getCodigo()));
        productosP.forEach(pp -> System.out.println(pp.getCodigoPedido() + " - " + pp.getCodigo()));
        //}
    }

    //Verifica si existe al menos un empleado con el cual iniciar en el sistema
    private boolean verificarInfoSistema() {
        boolean verificacion = false;
        String mensaje = "Actualmente no hay ningun empleado con el cual inciar en el\n"
                + "sistema. A continuacion se abrira una ventana que le permitira\n"
                + "abrir un archivo para ingresar informacion al sistema.";
        empleados = empleadoDAO.getListado();
        if (empleados.isEmpty()) {
            int opcion = JOptionPane.showConfirmDialog(null, mensaje,
                    "Informacion", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == 0) {
                verificacion = true;
            } else {
                System.exit(0);
            }
        }
        return verificacion;
    }

    //Limpia la interfaz
    private void limpiarInterfaz() {
        archivoEV.getLblNombreArchivo().setText("*No se ha escogido un archio");
        archivoEV.getTxtAreaInformacion().setText("");
        archivoEV.getBtnContinuar().setEnabled(false);
        archivoEV.getBtnIniciar().setEnabled(false);
        path = "";
        pedidoConError = "";
    }

    private void eliminarPedidoConError(String codPedido) {
        if (!pedidos.isEmpty()) {
            for (int j = 0; j < productosP.size(); j++) {
                ProductoPedido pp = productosP.get(j);
                if (pp.getCodigoPedido() == Integer.parseInt(codPedido)) {
                    productosP.remove(j);
                    j--;
                }
            }
            for (int j = 0; j < pedidos.size(); j++) {
                Pedido p = pedidos.get(j);
                if (p.getCodigo() == Integer.parseInt(codPedido)) {
                    pedidos.remove(j);
                    j--;
                }
            }
            String mensaje = "No se registrara el pedido " + codPedido + " y sus productos debido a errores\n";
            errores.add(mensaje);
            pedidoConError = codPedido;
        }
    }

    private void verificarTotalAnticipo() {
        String codPedido = "";
        try {
            for (Pedido p : pedidos) {
                codPedido = String.valueOf(p.getCodigo());
                float total = validarAnticipo(productosP, p.getCodigo(), p.getPorcentajePagado());
                float anticipo = p.getPorcentajePagado();
                float porcentajeP = anticipo / total;
                p.setPorcentajePagado(porcentajeP);
            }

        } catch (UserInputException e) {
            e.printStackTrace(System.out);
            errores.add(e.getMessage()+"\n");
            eliminarPedidoConError(codPedido);
        }
    }
}
