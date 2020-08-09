package com.l2ashdz.sistemaintelaf.controller.archivoEntrada;

import com.l2ashdz.sistemaintelaf.controller.LoginController;
import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.empleado.EmpleadoDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Empleado;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import com.l2ashdz.sistemaintelaf.ui.ArchivoEntradaView;
import com.l2ashdz.sistemaintelaf.ui.LoginView;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.*;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.EntidadFabrica.*;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Conexion;
import com.l2ashdz.sistemaintelaf.model.ExistenciaProducto;
import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.TiempoTraslado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author asael
 */
public class ArchivoEntradaController implements ActionListener {

    private ArchivoEntradaView archivoEV;
    private LoginView loginV;
    private LoginController loginC;

    private Connection conexion;

    private CRUD<Tienda> tiendaDAO;
    private CRUD<TiempoTraslado> tiempoDAO;
    private CRUD<Producto> productoDAO;
    private CRUD<ExistenciaProducto> existenciaPDAO;

    private CRUD<Empleado> empleadoDAO;
    private List<Empleado> empleados;

    private String path = "";
    private List<String> entrada;
    private List<String> errores;

    public ArchivoEntradaController(ArchivoEntradaView archivoEView) {
        conexion = Conexion.getConexion();
        empleadoDAO = EmpleadoDAOImpl.getEmpleadoDAO();
        tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        tiempoDAO = TiempoTrasladoDAOImpl.getTiempoDAO();
        productoDAO = ProductoDAOImpl.getProductoDAO();
        existenciaPDAO = ExistenciaProductoDAOImpl.getExistenciaDAO();

        loginV = new LoginView();
        this.archivoEV = archivoEView;
        this.archivoEV.getBtnBuscar().addActionListener(this);
        this.archivoEV.getBtnCancelar().addActionListener(this);
        this.archivoEV.getBtnIniciar().addActionListener(this);
        this.archivoEV.getBtnContinuar().addActionListener(this);

    }

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
        if (archivoEV.getBtnBuscar() == ae.getSource()) {
            path = obtenerRutaArchivo();
            if (!path.isEmpty()) {
                archivoEV.getLblNombreArchivo().setText(path);
                archivoEV.getBtnIniciar().setEnabled(true);
            }
        } else if (archivoEV.getBtnCancelar() == ae.getSource()) {
            System.exit(0);
        } else if (archivoEV.getBtnIniciar() == ae.getSource()) {
            if (!path.isEmpty()) {
                entrada = leerArchivo(path);
                noseComoLlamarlo(entrada);
                archivoEV.getBtnContinuar().setEnabled(true);
            }
        } else if (archivoEV.getBtnContinuar() == ae.getSource()) {
            try {
                conexion.commit();
                conexion.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    private void noseComoLlamarlo(List<String> entrada) {
        String[] parametros;
        errores = new ArrayList();
        JTextArea textA = archivoEV.getTxtAreaInformacion();
        String mensaje;
        try {
            conexion.setAutoCommit(false);
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
                                }
                                existenciaPDAO.create(nuevaExistenciaProducto(parametros));
                                textA.append("Se registrara el producto: " + parametros[1] + 
                                        " en la tienda: "+parametros[6]+"\n");
                            }
                            break;
                        case "EMPLEADO":
                            mensaje = "empleado\n";
                            textA.append(mensaje);
                            break;
                        case "CLIENTE":
                            mensaje = "cliente\n";
                            textA.append(mensaje);
                            break;
                        case "PEDIDO":
                            mensaje = "pedido\n";
                            textA.append(mensaje);
                            break;
                        default:
                            mensaje = "Linea " + (i + 1) + ": No coincide con el inicio de alguna estructura\n";
                            errores.add(mensaje);
                    }
                } catch (Exception e) {
                    mensaje = "Linea " + (i + 1) + ": " + e.getMessage() + "\n";
                    errores.add(mensaje);
                }
            }

            textA.append("\n\nSe detectaron los siguientes errores en el archivo: \n");
            errores.forEach(error -> textA.append(error));

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

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

    private String obtenerRutaArchivo() {
        String path = "";
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("TXT Documents", "txt"));
        fc.setAcceptAllFileFilterUsed(false);
        fc.showOpenDialog(null);
        try {
            path = fc.getSelectedFile().getAbsolutePath();
        } catch (Exception e) {
            System.out.println("se cancelo");
            e.printStackTrace(System.out);
        }
        return path;
    }

    private List<String> leerArchivo(String path) {
        File archivo = new File(path);
        List<String> textoArchivo = null;
        try {
            BufferedReader entrada = new BufferedReader(new FileReader(archivo));

            textoArchivo = new ArrayList();
            String lineaArchivo = entrada.readLine();
            while (lineaArchivo != null) {
                textoArchivo.add(lineaArchivo);
                lineaArchivo = entrada.readLine();
            }
            entrada.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return textoArchivo;
    }
}
