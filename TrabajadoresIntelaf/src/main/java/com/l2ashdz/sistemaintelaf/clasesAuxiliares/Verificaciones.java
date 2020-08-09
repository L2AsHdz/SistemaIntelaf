/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.l2ashdz.sistemaintelaf.clasesAuxiliares;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.EntidadDuplicadaException;
import com.l2ashdz.sistemaintelaf.excepciones.LimiteCaracteresException;
import com.l2ashdz.sistemaintelaf.excepciones.ParametroVacioException;
import com.l2ashdz.sistemaintelaf.excepciones.ParametrosException;
import com.l2ashdz.sistemaintelaf.excepciones.TipoIncompatibleException;
import com.l2ashdz.sistemaintelaf.model.Tienda;

/**
 *
 * @author asael
 */
public class Verificaciones {

    //Realiza las verificaciones correspondientes con los paramtros obtenidos
    public static boolean verificarTienda(String[] parametros) throws Exception{
        boolean flag = true;
        CRUD<Tienda> tiendaDAO = TiendaDAOImpl.getTiendaDAO();
        if (parametros.length == 5) {
            String nombre = parametros[1];
            String direccion = parametros[2];
            String codigo = parametros[3];
            String telefono = parametros[4];
            
            //Si algun parametro excede el limite de caracteres lanza una excepcion
            if (nombre.length() > 30 || codigo.length() > 10 || direccion.length() > 50
                    || telefono.length() > 8) {
                flag = false;
                throw new LimiteCaracteresException("Uno o mas parametros exceden el limite de caracteres");

            //Si algun parametro es una cadena vacia lanza una exccepcion
            } else if (nombre.isEmpty() || direccion.isEmpty() || codigo.isEmpty()
                    || telefono.isEmpty()) {
                flag = false;
                throw new ParametroVacioException("Uno o mas parametros estan vacios");

            //Si el telefono contiene caracteres que no son numericos lanza una excepcion
            } else if (!isInt(telefono)) {
                flag = false;
                throw new TipoIncompatibleException("El telefono tiene que contener solo numeros");
                
            //Si la entidad con el codigo especificado ya esxiste lanza una excepcion
            } else if (tiendaDAO.getObject(codigo) != null) {
                flag = false;
                throw new EntidadDuplicadaException("La tienda ya existe en la base de datos");
            }

        //Lanza una excepcion si los parametros no coinciden con la estructura
        } else {
            flag = false;
            throw new ParametrosException("El numero de parametros no coincide con la estructura");
        }
        return flag;
    }

    //Verifica si la cadena es un float
    private static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Verifica si la cadena es un entero
    private static boolean isInt(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
