package com.l2ashdz.sistemaintelaf.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asael
 */
public class Archivo {
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
    
    public static void crearReporte(String nombreArchivo, String[] titulos, List<Pedido> list) {
        File archivo = new File(nombreArchivo+".html");
        try (PrintWriter salida = new PrintWriter(new FileWriter(archivo, true))){
            salida.println(html(titulos,list));
            salida.close();
            System.out.println("Reporte creado " +nombreArchivo);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    private static String html(String [] titulos,List<Pedido> list) {
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
        pagina += contenidoTblPedido(list);
        pagina += "</table>\n";
        pagina += "</body>\n";
        pagina += "</head>\n";
        pagina += "</html>";

        return pagina;
    }
    
    private static String contenidoTblPedido(List<Pedido> list) {
        String contenido = "";
        for (Pedido p : list) {
            contenido += "<tr>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getCodigo() + "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getNitCliente()+ "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getTiendaOrigen()+ "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getTiendaDestino()+ "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getFecha()+ "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getCantProductos()+ "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getTotal()+ "</td>\n";
            contenido += "<td style='border: 1px solid gray;text-align: left;padding: 8px;'>" + p.getEstado()+ "</td>\n";
            contenido += "</tr>\n";
        }
        return contenido;
    }
}
