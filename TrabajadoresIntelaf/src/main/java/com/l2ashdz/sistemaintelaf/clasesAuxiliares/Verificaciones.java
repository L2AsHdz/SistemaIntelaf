package com.l2ashdz.sistemaintelaf.clasesAuxiliares;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.empleado.EmpleadoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.pedido.ProductoPedidoDAO;
import com.l2ashdz.sistemaintelaf.dao.pedido.ProductoPedidoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAO;
import com.l2ashdz.sistemaintelaf.dao.tiempoTraslado.TiempoTrasladoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.*;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Empleado;
import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import java.time.LocalDate;

/**
 *
 * @author asael
 */
public class Verificaciones {

    private static CRUD<Tienda> tiendaDAO = TiendaDAOImpl.getTiendaDAO();
    private static TiempoTrasladoDAO tiempoDAO = TiempoTrasladoDAOImpl.getTiempoDAO();
    private static ExistenciaProductoDAO existenciaDAO = ExistenciaProductoDAOImpl.getExistenciaDAO();
    private static CRUD<Producto> productoDAO = ProductoDAOImpl.getProductoDAO();
    private static CRUD<Cliente> clienteDAO = ClienteDAOImpl.getClienteDAO();
    private static CRUD<Empleado> empleadoDAO = EmpleadoDAOImpl.getEmpleadoDAO();
    private static ProductoPedidoDAO productoPedDAO = ProductoPedidoDAOImpl.getProductoPDAO();

    //Realiza las verificaciones correspondientes con los paramtros obtenidos
    public static boolean verificarTienda(String[] parametros) throws UserInputException {
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
                throw new UserInputException("Uno o mas parametros exceden el limite de caracteres");

                //Si algun parametro es una cadena vacia lanza una exccepcion
            } else if (nombre.isEmpty() || direccion.isEmpty() || codigo.isEmpty()
                    || telefono.isEmpty()) {
                flag = false;
                throw new UserInputException("Uno o mas parametros estan vacios");

                //Si el telefono contiene caracteres que no son numericos lanza una excepcion
            } else if (!isInt(telefono)) {
                flag = false;
                throw new UserInputException("El telefono tiene que contener solo numeros enteros");

                //Si la entidad con el codigo especificado ya esxiste lanza una excepcion
            } else if (tiendaDAO.getObject(codigo) != null) {
                flag = false;
                throw new UserInputException("La tienda ya existe en el sistema");
            }

            //Lanza una excepcion si los parametros no coinciden con la estructura
        } else {
            flag = false;
            throw new UserInputException("El numero de parametros no coincide con la estructura");
        }
        return flag;
    }

    public static boolean verificarTiempo(String[] parametros) throws UserInputException {
        boolean flag = true;

        if (parametros.length == 4) {
            String codigoT1 = parametros[1];
            String codigoT2 = parametros[2];
            String tiempo = parametros[3];

            //Si algun parametro excede el limite de caracteres lanza una excepcion
            if (codigoT1.length() > 10 || codigoT2.length() > 10) {
                flag = false;
                throw new UserInputException("Uno o mas parametros exceden el limite de caracteres");

                //Si algun parametro es una cadena vacia lanza una exccepcion
            } else if (codigoT1.isEmpty() || codigoT2.isEmpty() || tiempo.isEmpty()) {
                flag = false;
                throw new UserInputException("Uno o mas parametros estan vacios");

                //Si el tiempo no es un valor numerico lanza una excepcion
            } else if (!isInt(tiempo)) {
                flag = false;
                throw new UserInputException("El parametro tiempo debe ser un valor entero");

                //Si el tiempo es menor a cero lanza una excepcion
            } else if (Integer.parseInt(tiempo) < 0) {
                flag = false;
                throw new UserInputException("El tiempo no puede ser menor a cero");

                //Si la entidad con los codigos especificados ya existe lanza una excepcion
            } else if (tiempoDAO.getTiempoT(codigoT1, codigoT2) != null
                    || tiempoDAO.getTiempoT(codigoT2, codigoT1) != null) {
                flag = false;
                throw new UserInputException("El tiempo ya esta registrado en el sistema");

                //Si la entidad a la que hace referencia no existe lanza una excepcion
            } else if (tiendaDAO.getObject(codigoT1) == null
                    || tiendaDAO.getObject(codigoT2) == null) {
                flag = false;
                throw new UserInputException("Una de las teindas a la que hace referencia no existe en el sistema");
            }

            //Lanza una excepcion si los parametros no coinciden con la estructura
        } else {
            flag = false;
            throw new UserInputException("El numero de parametros no coincide con la estructura");
        }
        return flag;
    }

    public static boolean verificarProducto(String[] parametros) throws UserInputException {
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
                throw new UserInputException("Uno o mas parametros exceden el limite de caracteres");

                //Si algun parametro es una cadena vacia lanza una exccepcion
            } else if (nombre.isEmpty() || fabricante.isEmpty() || codigo.isEmpty()
                    || existencias.isEmpty() || precio.isEmpty() || codTienda.isEmpty()) {
                flag = false;
                throw new UserInputException("Uno o mas parametros estan vacios");

                //Si las existencias no son un valor numerico lanza una excepcion
            } else if (!isInt(existencias)) {
                flag = false;
                throw new UserInputException("Las existencias del producto tienen que ser un entero");

                //Si las existencias son menores a cero lanza una excepcion
            } else if (Integer.parseInt(existencias) < 0) {
                flag = false;
                throw new UserInputException("Las existencias no pueden ser menores a cero");

                //Si el precio no es un valor numerico lanza una excepcion
            } else if (!isFloat(precio)) {
                flag = false;
                throw new UserInputException("El precio del producto tienen que ser un valor numerico");

                //Si el precio es menor o igual a cero lanza una excepcion
            } else if (!isMayorACero(precio)) {
                flag = false;
                throw new UserInputException("El precio no puede ser menor o igual a cero");

                //Si la entidad con el codigo especificado ya esxiste lanza una excepcion
            } else if (existenciaDAO.getProductoInTienda(codTienda, codigo) != null) {
                flag = false;
                throw new UserInputException("El producto en la tienda especificada ya existe en el sistema");

                //Si la entidad a la que hace referencia no existe lanza una excepcion
            } else if (tiendaDAO.getObject(codTienda) == null) {
                flag = false;
                throw new UserInputException("La tienda a la que hace referencia no existe en el sistema");
            }

            //Lanza una excepcion si los parametros no coinciden con la estructura
        } else {
            flag = false;
            throw new UserInputException("El numero de parametros no coincide con la estructura");

        }
        return flag;
    }

    //Realiza las verificaciones correspondientes con los paramtros obtenidos
    public static boolean verificarCliente(String[] parametros) throws UserInputException {
        boolean flag = true;
        if (parametros.length == 5) {
            String nombre = parametros[1];
            String nit = parametros[2];
            String telefono = parametros[3];
            String credito = parametros[4];

            //Si algun parametro excede el limite de caracteres lanza una excepcion
            if (nombre.length() > 45 || nit.length() > 10 || telefono.length() > 8) {
                flag = false;
                throw new UserInputException("Uno o mas parametros exceden el limite de caracteres");

                //Si algun parametro es una cadena vacia lanza una excepcion
            } else if (nombre.isEmpty() || nit.isEmpty() || credito.isEmpty()
                    || telefono.isEmpty()) {
                flag = false;
                throw new UserInputException("Uno o mas parametros estan vacios");

                //Si el telefono contiene caracteres que no son numericos lanza una excepcion
            } else if (!isInt(telefono)) {
                flag = false;
                throw new UserInputException("El telefono tiene que contener solo numeros enteros");

                //Si el credito contiene caracteres que no son numericos lanza una excepcion
            } else if (!isFloat(credito)) {
                flag = false;
                throw new UserInputException("El credito de compra debe ser un dato numerico");

                //Si el credito es menor a cero lanza una excepcion
            } else if (Integer.parseInt(credito) < 0) {
                flag = false;
                throw new UserInputException("El credito no puuede ser menor a cero");

                //Si la entidad con el codigo especificado ya esxiste lanza una excepcion
            } else if (clienteDAO.getObject(nit) != null) {
                flag = false;
                throw new UserInputException("El cliente ya existe en el sistema");
            }

            //Lanza una excepcion si los parametros no coinciden con la estructura
        } else {
            flag = false;
            throw new UserInputException("El numero de parametros no coincide con la estructura");
        }
        return flag;
    }

    //Realiza las verificaciones correspondientes con los paramtros obtenidos
    public static boolean verificarEmpleado(String[] parametros) throws UserInputException {
        boolean flag = true;
        if (parametros.length == 5) {
            String nombre = parametros[1];
            String codigo = parametros[2];
            String telefono = parametros[3];
            String cui = parametros[4];

            //Si algun parametro excede el limite de caracteres lanza una excepcion
            if (nombre.length() > 45 || codigo.length() > 10 || telefono.length() > 8
                    || cui.length() > 13) {
                flag = false;
                throw new UserInputException("Uno o mas parametros exceden el limite de caracteres");

                //Si algun parametro es una cadena vacia lanza una excepcion
            } else if (nombre.isEmpty() || codigo.isEmpty() || cui.isEmpty()
                    || telefono.isEmpty()) {
                flag = false;
                throw new UserInputException("Uno o mas parametros estan vacios");

                //Si el telefono contiene caracteres que no son numericos lanza una excepcion
            } else if (!isInt(telefono)) {
                flag = false;
                throw new UserInputException("El telefono tiene que contener solo numeros enteros");

                //Si el cui contiene caracteres que no son numericos lanza una excepcion
            } else if (!isInt(cui)) {
                flag = false;
                throw new UserInputException("El CUI debe ser un dato numerico");

                //Si la entidad con el codigo especificado ya esxiste lanza una excepcion
            } else if (empleadoDAO.getObject(codigo) != null) {
                flag = false;
                throw new UserInputException("El empleado ya existe en el sistema");
            }

            //Lanza una excepcion si los parametros no coinciden con la estructura
        } else {
            flag = false;
            throw new UserInputException("El numero de parametros no coincide con la estructura");
        }
        return flag;
    }

    public static boolean verificarPedido(String[] parametros) throws UserInputException {
        boolean flag = true;

        if (parametros.length == 10) {
            String codigo = parametros[1];
            String codTO = parametros[2];
            String codTD = parametros[3];
            String fecha = parametros[4];
            String nit = parametros[5];
            String codP = parametros[6];
            String cantidad = parametros[7];
            String total = parametros[8];
            String anticipo = parametros[9];

            //Si algun parametro excede el limite de caracteres lanza una excepcion
            if (nit.length() > 10 || codTO.length() > 10 || codTD.length() > 10
                    || codP.length() > 15) {
                flag = false;
                throw new UserInputException("Uno o mas parametros exceden el limite de caracteres");

                //Si algun parametro es una cadena vacia lanza una exccepcion
            } else if (codigo.isEmpty() || codTO.isEmpty() || codTD.isEmpty()
                    || fecha.isEmpty() || nit.isEmpty() || codP.isEmpty()
                    || cantidad.isEmpty() || total.isEmpty() || anticipo.isEmpty()) {
                flag = false;
                throw new UserInputException("Uno o mas parametros estan vacios");

                //Si el codigo o la cantidad no son un valor numerico lanza una excepcion
            } else if (!isInt(codigo) || !isInt(cantidad)) {
                flag = false;
                throw new UserInputException("El codigo y la cantidad solo debe contener numeros");

                //Si el total o el anticipo no es un valor numerico lanza una excepcion
            } else if (!isFloat(total) || !isFloat(anticipo)) {
                flag = false;
                throw new UserInputException("El total o anticipo ingresado no es un valor numerico");

                //Si la fecha ingresada no tiene el formato correcto lanza una excepcion
            } else if (!isFecha(fecha)) {
                flag = false;
                throw new UserInputException("El formato de la fecha no es correcto (yyyy-MM-dd)");

                //Si la entidad con el codigo especificado ya esxiste lanza una excepcion
            } else if (productoPedDAO.getProductoInPedido(Integer.parseInt(codigo), codP) != null) {
                flag = false;
                throw new UserInputException("El producto ya esta registrado en un pedido en el sistema");

                //Si la entidad a la que hace referencia no existe lanza una excepcion
            } else if (tiendaDAO.getObject(codTD) == null || tiendaDAO.getObject(codTO) == null) {
                flag = false;
                throw new UserInputException("Una o ambas tiendas no existen en el sistema");

                //Si el producto no existe en el sistema lanza una excepcion
            } else if (productoDAO.getObject(codP) == null) {
                flag = false;
                throw new UserInputException("El producto no existe en el sistema");

                //Si el producto no existe en la tienda especificada lanza una excepcion
            } else if (existenciaDAO.getProductoInTienda(codTO, codP) == null) {
                flag = false;
                throw new UserInputException("El producto no existe en la tienda especificada");

                //Si la entidad a la que hace referencia no existe lanza una excepcion
            } else if (clienteDAO.getObject(nit) == null) {
                flag = false;
                throw new UserInputException("El cliente al que hace referencia no existe en el sistema");

                //Si la cantidad excede las existencias lanza una excepcion
            } else if (Integer.parseInt(cantidad) > existenciaDAO.getExistencias(codTO, codP)) {
                flag = false;
                throw new UserInputException("La cantidad excede las existencias disponibles en la tienda origen");

                //Si la cantidad, el total o el anticipo es igual o menor a cero lanza una excepcion
            } else if (!isMayorACero(cantidad) || !isMayorACero(total) || !isMayorACero(anticipo)) {
                flag = false;
                throw new UserInputException("La cantidad, el total y el anticipo deben ser datos mayores a cero");

                //Si el total dado no coincide con los datos en el sistema lanza una excepcion
            } else if (!verificarTotalProducto(codP, cantidad, total)) {
                flag = false;
                throw new UserInputException("El total proporcionado no coincide con los datos en el sistema");

                //Si la tienda origen es la misma que el destino lanza una excepcion
            } else if (codTO.equals(codTD)) {
                flag = false;
                throw new UserInputException("La tienda origen no puede ser la misma que la tienda destino");
            }

            //Lanza una excepcion si los parametros no coinciden con la estructura
        } else {
            flag = false;
            throw new UserInputException("El numero de parametros no coincide con la estructura");

        }
        return flag;
    }

    //Verifica si la cadena es un float
    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Verifica si la cadena es un entero
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Verifica si el formato de la fecha es el correcto
    public static boolean isFecha(String s) {
        try {
            LocalDate.parse(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Verifica si el numero es mayor a cero
    public static boolean isMayorACero(String s) {
        return (Float.parseFloat(s) > 0);
    }

    public static boolean verificarTotalProducto(String codigo, String cantidad, String total) {
        float precioDB = productoDAO.getObject(codigo).getPrecio();
        float totalReal = (Integer.parseInt(cantidad) * precioDB);
        System.out.println(total + " - "+totalReal);
        return Float.parseFloat(total) == totalReal;
    }
}
