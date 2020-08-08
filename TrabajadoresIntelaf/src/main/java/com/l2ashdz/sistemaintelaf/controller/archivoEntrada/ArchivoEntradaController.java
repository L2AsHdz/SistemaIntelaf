package com.l2ashdz.sistemaintelaf.controller.archivoEntrada;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.empleado.EmpleadoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.model.Empleado;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
Quiza sea posible usar funciones lambdas pasando como comportamiento el Tipo y los
parametros, dependiendo de ellos se ejecutaran las acciones
 */
/**
 *
 * @author asael
 */
public class ArchivoEntradaController {

    private Tienda tienda;
    private CRUD<Tienda> tiendaDAO;
    private List<Tienda> tiendas;

    private Empleado empleado;
    private CRUD<Empleado> empleadoDAO;
    private List<Empleado> empleados;

    public ArchivoEntradaController() {
        tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        empleadoDAO = EmpleadoDAOImpl.getEmpleadoDAO();
        iniciar();
    }

    private void iniciar() {
        String path;
        List<String> entrada;
        if (verificarInfoSistema()) {
            path = obtenerRutaArchivo();
            if (!path.isEmpty()) {
                entrada = leerArchivo(path);
                noseComoLlamarlo(entrada);
            } else {

            }
        }
    }

    private void noseComoLlamarlo(List<String> entrada) {
        String[] parametros;
        for (String linea : entrada) {
            parametros = linea.split(",");
            switch (parametros[0]) {
                case "TIENDA":
                    //aprender a usar el patro fabrica
                    String nombre = parametros[1];
                    String direccion = parametros[2];
                    String codigo = parametros[3];
                    String telefono = parametros[4];
                    nuevaTienda(nombre, codigo, direccion, telefono);
                    tiendaDAO.create(tienda);
                    break;
                case "TIEMPO":
                    System.out.println("tiempo");
                    break;
                case "PRODUCTO":
                    System.out.println("producto");
                    break;
                case "EMPLEADO":
                    System.out.println("empeado");
                    break;
                case "CLIENTE":
                    System.out.println("cliente");
                    break;
                case "PEDIDO":
                    System.out.println("pedido");
                    break;
                default:
                    System.out.println("no hay ninguna estructura correcta");
                    break;
            }
        }
    }

    private void nuevaTienda(String nombre, String codigo, String dir, String tel) {
        tienda = new Tienda();
        tienda.setCodigo(codigo);
        tienda.setDireccion(dir);
        tienda.setNombre(nombre);
        tienda.setTelefono1(tel);
    }

    private void verificarDatosTienda(String[] parametros) {
        boolean flag = false;
        String nombre;
        String direccion;
        String codigo;
        String telefono;
        String mensajeError;
        if (parametros.length == 5) {
//            nombre = parametros[1];
//            direccion = parametros[2];
//            codigo = parametros[3];
//            telefono = parametros[4];
//            if (!nombre.isEmpty() || nombre.length() < 30) {
//                if (direccion.isEmpty() || direccion.length() < 50) {
//                    if (codigo) {
//                        
//                    }
//                } else {
//                    mensajeError = "La direccion excede el limite de caracteres o esta vacio";
//                }
//            } else {
//                mensajeError = "El nombre excede el limite de caracteres o esta vacio";
//            }

        } else {
            //posiblemente habra que crear un objeto errorLectura
            mensajeError = "Los parametros no cumplen con la estructura definida";
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
            }
        }
        return verificacion;
    }

    private String obtenerRutaArchivo() {
        String path = "";
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        try {
            path = fc.getSelectedFile().getAbsolutePath();
        } catch (Exception e) {
            System.out.println("se cancelo");
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
    /* Algoritmo para la lectura del archivo
    1.  Verificar si el sistema tiene un empleado para poder ingresar al sistema
    2.  Mostrar interfaz donde se abrira el archivo
    3.  Mostrar ventana para escoger el archivo
    4.  mostrar ruta del archivo y esperar a que el user presione el boton iniciar
    5.  leer archivo (devuelve todas las lineas)
    6.  separar linea en parametros
    7.  verificar si el primer parametro coincide con el inicio de alguna estructura
    8.  si coincide proceder de lo contrario saltar linea y reportar error
    9.  verificar si los parametros coinciden con la estructura respectiva
    10. si no coinciden saltar linea y reportar error
    11. verificar que los parametros no contengan errores por parte del usuario
    12. si no hay errores crear un nueva Entidad
    13. Insertar entidad en la base de datos
    
    */
}
