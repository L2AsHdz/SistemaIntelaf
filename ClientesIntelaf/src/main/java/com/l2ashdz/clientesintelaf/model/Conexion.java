package com.l2ashdz.clientesintelaf.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author asael
 */
public class Conexion {

    private static Connection conexion = null;
    private final String url = "jdbc:mariadb://localhost:3306/SistemaIntelaf";
    private final String usuario = "client";
    private final String password = "123";

    private Conexion() {
        try {
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexion establecida");
        } catch (SQLException e) {
            System.out.println("No se pudo conectar");
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConexion() {
        if (conexion == null) {
            new Conexion();
        }
        return conexion;
    }

    public static void desconectar() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("se desconecto");
            } catch (SQLException ex) {
                System.out.println("Fallo al intentar desconectar");
                System.out.println(ex.getMessage());
            }
        }
    }
}
