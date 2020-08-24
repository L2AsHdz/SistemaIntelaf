package com.l2ashdz.sistemaintelaf.model;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author asael
 */
public class Archivo {

    public static String obtenerRutaArchivo() {
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

    public static String guardarReporte() {
        String path = "";
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Guardar Como");
        try {
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                path = fc.getSelectedFile().getAbsolutePath() + ".html";
            }
        } catch (HeadlessException ex) {
            ex.printStackTrace(System.out);
        }
        return path;
    }

    //Lee un archivo y devuelve su contenido en una lista
    public static List<String> leerArchivo(String path) {
        File archivo = new File(path);
        List<String> textoArchivo = null;
        try (BufferedReader entrada = new BufferedReader(new FileReader(archivo))) {
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

    //Crea un archivo (Reporte) con el nombre y contenido recbido
    private static void crearArchivo(String nombreArchivo, String reporte) {
        File archivo = new File(nombreArchivo);
        try (PrintWriter salida = new PrintWriter(new FileWriter(archivo, true))) {
            salida.println(reporte);
            salida.close();
            System.out.println("Reporte creado: " + nombreArchivo);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    //Encargado de crear el archivo de los reportes de pedidos
    public static void crearReportePedido(String nombreArchivo, String[] titulos, List<Pedido> pedidos) {
        crearArchivo(nombreArchivo, cuerpoReporte(titulos, contenidoTblPedido(pedidos)));
    }

    //Encargado de crear el archivo de los reportes de ventas
    public static void crearReporteVenta(String nombreArchivo, String[] titulos, List<Venta> ventas) {
        crearArchivo(nombreArchivo, cuerpoReporte(titulos, contenidoTblVenta(ventas)));
    }

    //Encargado de crear el archivo de los reportes de productos
    public static void crearReporteProducto(String nombreArchivo, String[] titulos, List<Producto> productos) {
        crearArchivo(nombreArchivo, cuerpoReporte(titulos, contenidoTblProducto(productos)));
    }

    //Genera el cuerpo del reporte
    private static String cuerpoReporte(String[] titulos, String contenido) {
        String pagina = "";
        pagina += "<html>\n";
        pagina += "<head>\n";
        pagina += "<body>\n";
        pagina += "<h2>Tabla</h2>\n";
        pagina += "<table>\n";
        pagina += "<tr>\n";
        for (String titulo : titulos) {
            pagina += "<th style='border: 1px solid gray;text-align: left;padding: 8px;'>" + titulo + "</th>\n";
        }
        pagina += "</tr>\n";
        pagina += contenido;
        pagina += "</table>\n";
        pagina += "</body>\n";
        pagina += "</head>\n";
        pagina += "</html>";

        return pagina;
    }

    //Genera el contenido de la tabla de los reportes de pedidos
    private static String contenidoTblPedido(List<Pedido> list) {
        String contenido = "";
        for (Pedido p : list) {
            contenido += "<tr>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getCodigo() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getNitCliente() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getTiendaOrigen() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getTiendaDestino() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getFecha() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getCantProductos() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getTotal() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getEstado() + "</td>\n";
            contenido += "</tr>\n";
        }
        return contenido;
    }

    //Genera el contenido de la tabla de los reportes de ventas
    private static String contenidoTblVenta(List<Venta> list) {
        String contenido = "";
        for (Venta v : list) {
            contenido += "<tr>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + v.getIdVenta() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + v.getNitCliente() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + v.getFecha() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + v.getCodTienda() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + v.getCantProductos() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + v.getTotal() + "</td>\n";
            contenido += "</tr>\n";
        }
        return contenido;
    }

    //Genera el contenido de la tabla de los reportes de productos
    private static String contenidoTblProducto(List<Producto> list) {
        String contenido = "";
        for (Producto p : list) {
            contenido += "<tr>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getCodigo() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getNombre() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getDescripcion() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getFabricante() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getPrecio() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getGarantiaMeses() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getCantVentas() + "</td>\n";
            contenido += "</tr>\n";
        }
        return contenido;
    }
}
