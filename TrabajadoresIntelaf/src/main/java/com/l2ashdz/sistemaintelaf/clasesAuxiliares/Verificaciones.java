package com.l2ashdz.sistemaintelaf.clasesAuxiliares;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAO;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.*;
import com.l2ashdz.sistemaintelaf.model.Tienda;

/**
 *
 * @author asael
 */
public class Verificaciones {

    private static CRUD<Tienda> tiendaDAO = TiendaDAOImpl.getTiendaDAO();
    private static TiempoTrasladoDAO tiempoDAO = TiempoTrasladoDAOImpl.getTiempoDAO();
    private static ExistenciaProductoDAO existenciaDAO = ExistenciaProductoDAOImpl.getExistenciaDAO();

    //Realiza las verificaciones correspondientes con los paramtros obtenidos
    public static boolean verificarTienda(String[] parametros) throws Exception {
        boolean flag = true;
        if (parametros.length == 5) {
            String nombre = parametros[1];
            String direccion = parametros[2];
            String codigo = parametros[3];
            String telefono = parametros[4];

            //Si algun parametro excede el limite de caracteres lanza una excepcion
            if (nombre.length() > 30 || codigo.length() > 10 || direccion.length() > 50
                    || telefono.length() > 8) {
                flag = false;
                throw new CharacterLimitException("Uno o mas parametros exceden el limite de caracteres");

                //Si algun parametro es una cadena vacia lanza una exccepcion
            } else if (nombre.isEmpty() || direccion.isEmpty() || codigo.isEmpty()
                    || telefono.isEmpty()) {
                flag = false;
                throw new EmptyParameterException("Uno o mas parametros estan vacios");

                //Si el telefono contiene caracteres que no son numericos lanza una excepcion
            } else if (!isInt(telefono)) {
                flag = false;
                throw new IncompatibleTypeException("El telefono tiene que contener solo numeros");

                //Si la entidad con el codigo especificado ya esxiste lanza una excepcion
            } else if (tiendaDAO.getObject(codigo) != null) {
                flag = false;
                throw new DuplicateEntityException("La tienda ya existe en la base de datos");
            }

            //Lanza una excepcion si los parametros no coinciden con la estructura
        } else {
            flag = false;
            throw new ParameterNotFoundException("El numero de parametros no coincide con la estructura");
        }
        return flag;
    }

    public static boolean verificarTiempo(String[] parametros) throws Exception {
        boolean flag = true;

        if (parametros.length == 4) {
            String codigoT1 = parametros[1];
            String codigoT2 = parametros[2];
            String tiempo = parametros[3];

            //Si algun parametro excede el limite de caracteres lanza una excepcion
            if (codigoT1.length() > 10 || codigoT2.length() > 10) {
                flag = false;
                throw new CharacterLimitException("Uno o mas parametros exceden el limite de caracteres");

                //Si algun parametro es una cadena vacia lanza una exccepcion
            } else if (codigoT1.isEmpty() || codigoT2.isEmpty() || tiempo.isEmpty()) {
                flag = false;
                throw new EmptyParameterException("Uno o mas parametros estan vacios");

                //Si el tiempo no es un valor numerico lanza una excepcion
            } else if (!isInt(tiempo)) {
                flag = false;
                throw new IncompatibleTypeException("El parametro tiempo debe ser un valor entero");

                //Si la entidad con los codigos especificados ya existe lanza una excepcion
            } else if (tiempoDAO.getTiempoT(codigoT1, codigoT2) != null
                    || tiempoDAO.getTiempoT(codigoT2, codigoT1) != null) {
                flag = false;
                throw new DuplicateEntityException("El tiempo ya esta registrado en la base de datos");

                //Si la entidad a la que hace referencia no existe lanza una excepcion
            } else if (tiendaDAO.getObject(codigoT1) == null
                    || tiendaDAO.getObject(codigoT2) == null) {
                flag = false;
                throw new EntityNotFoundException("La entidad a la que hace referencia no existe en la BD");
            }

            //Lanza una excepcion si los parametros no coinciden con la estructura
        } else {
            flag = false;
            throw new ParameterNotFoundException("El numero de parametros no coincide con la estructura");
        }
        return flag;
    }

    public static boolean verificarProducto(String[] parametros) throws Exception {
        boolean flag = true;

        if (parametros.length == 7) {
            String nombre = parametros[1];
            String fabricante = parametros[2];
            String codigo = parametros[3];
            String existencias = parametros[4];
            String precio = parametros[5];
            String codTienda = parametros[6];

            //Si algun parametro excede el limite de caracteres lanza una excepcion
            if (nombre.length() > 50 || fabricante.length() > 30 || codigo.length() > 15
                    || codTienda.length() > 10) {
                flag = false;
                throw new CharacterLimitException("Uno o mas parametros exceden el limite de caracteres");

                //Si algun parametro es una cadena vacia lanza una exccepcion
            } else if (nombre.isEmpty() || fabricante.isEmpty() || codigo.isEmpty()
                    || existencias.isEmpty() || precio.isEmpty() || codTienda.isEmpty()) {
                flag = false;
                throw new EmptyParameterException("Uno o mas parametros estan vacios");

                //Si las existencias no son un valor numerico lanza una excepcion
            } else if (!isInt(existencias)) {
                flag = false;
                throw new IncompatibleTypeException("Las existencias del producto tienen que ser un entero");

                //Si eel precio no es un valor numerico lanza una excepcion
            } else if (!isFloat(precio)) {
                flag = false;
                throw new IncompatibleTypeException("El precio del producto tienen que ser un valor numerico");

                //Si la entidad con el codigo especificado ya esxiste lanza una excepcion
            } else if (existenciaDAO.getExistenciasP(codTienda, codigo) != null) {
                flag = false;
                throw new DuplicateEntityException("El producto ya existe en la base de datos");

                //Si la entidad a la que hace referencia no existe lanza una excepcion
            } else if (tiendaDAO.getObject(codTienda) == null) {
                flag = false;
                throw new EntityNotFoundException("La entidad a la que hace referencia no existe en la BD");
            }

            //Lanza una excepcion si los parametros no coinciden con la estructura
        } else {
            flag = false;
            throw new ParameterNotFoundException("El numero de parametros no coincide con la estructura");

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
