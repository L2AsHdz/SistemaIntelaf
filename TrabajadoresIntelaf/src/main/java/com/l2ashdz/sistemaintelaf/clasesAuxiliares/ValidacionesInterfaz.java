package com.l2ashdz.sistemaintelaf.clasesAuxiliares;

import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isInt;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isFloat;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isMayorACero;
import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isFecha;
import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.dao.cliente.ClienteDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.empleado.EmpleadoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAO;
import com.l2ashdz.sistemaintelaf.dao.producto.ExistenciaProductoDAOImpl;
import com.l2ashdz.sistemaintelaf.dao.tienda.TiendaDAOImpl;
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Empleado;
import com.l2ashdz.sistemaintelaf.model.Tienda;

/**
 *
 * @author asael
 */
public class ValidacionesInterfaz {

    private static CRUD<Tienda> tiendaDAO = TiendaDAOImpl.getTiendaDAO();
    private static ExistenciaProductoDAO existenciaDAO = ExistenciaProductoDAOImpl.getExistenciaDAO();
    private static CRUD<Cliente> clienteDAO = ClienteDAOImpl.getClienteDAO();
    private static CRUD<Empleado> empleadoDAO = EmpleadoDAOImpl.getEmpleadoDAO();

    public static void validarAddTienda(String codigo, String nombre, String direccion,
            String tel1) throws UserInputException {
        
        if (nombre.isEmpty() || codigo.isEmpty() || tel1.isEmpty() || direccion.isEmpty()) {
            throw new UserInputException("Los datos con * son obligatorios");
        } else if (tiendaDAO.getObject(codigo) != null) {
            throw new UserInputException("La tienda ya existe en el sistema");
        }
    }

    public static void validarUpdateTienda(String nombre, String direccion, String tel1) throws UserInputException {

        if (nombre.isEmpty() || tel1.isEmpty() || direccion.isEmpty()) {
            throw new UserInputException("Los datos con * son obligatorios");
        }
    }

    public static void validarUpdateTiempo(String tiempo) throws UserInputException {

        if (tiempo.isEmpty()) {
            throw new UserInputException("Debe ingresar un tiempo");
        } else if (!isInt(tiempo)) {
            throw new UserInputException("El tiempo debe ser un valor numerico");
        } else if (Integer.parseInt(tiempo) < 0) {
            throw new UserInputException("El tiempo no pude ser negativo");
        }
    }

    public static void validarAddProducto(String cod, String name, String fabricante,
            String existencias, String precio, String garantia, String codTienda)
            throws UserInputException {

        if (cod.isEmpty() || name.isEmpty() || fabricante.isEmpty() || precio.isEmpty()
                || existencias.isEmpty()) {
            throw new UserInputException("Los datos con * son obligatorios");
        } else if (!isInt(existencias)) {
            throw new UserInputException("Las existencias deben ser un numero entero");
        } else if (Integer.parseInt(existencias) < 0) {
            throw new UserInputException("Las existecias no pueden ser menores a cero");
        } else if (!isFloat(precio)) {
            throw new UserInputException("El precio debe ser un dato numerico");
        } else if (!isMayorACero(precio)) {
            throw new UserInputException("El precio no puede ser menor o igual a cero");
        } else if (!garantia.isEmpty() && !isInt(garantia)) {
            throw new UserInputException("La garantia debe ser un dato numerico");
        } else if (existenciaDAO.getProductoInTienda(codTienda, cod) != null) {
            throw new UserInputException("El producto ya existe en la tienda actual");
        }
    }

    public static void validarUpdateProducto(String name, String fabricante,
            String existencias, String precio, String garantia)
            throws UserInputException {

        if (name.isEmpty() || fabricante.isEmpty() || precio.isEmpty()
                || existencias.isEmpty()) {
            throw new UserInputException("Los datos con * son obligatorios");
        } else if (!isInt(existencias)) {
            throw new UserInputException("Las existencias deben ser un numero entero");
        } else if (Integer.parseInt(existencias) < 0) {
            throw new UserInputException("Las existecias no pueden ser menores a cero");
        } else if (!isFloat(precio)) {
            throw new UserInputException("El precio debe ser un dato numerico");
        } else if (!isMayorACero(precio)) {
            throw new UserInputException("El precio no puede ser menor o igual a cero");
        } else if (!garantia.isEmpty() && !isInt(garantia)) {
            throw new UserInputException("La garantia debe ser un dato numerico");
        }
    }

    public static void validarAddEmpleado(String codigo, String nombre, String cui,
            String direccion, String telefono, String correo) throws UserInputException {

        if (codigo.isEmpty() || nombre.isEmpty() || cui.isEmpty() || correo.isEmpty()
                || direccion.isEmpty() || telefono.isEmpty()) {
            throw new UserInputException("Los datos con * son obligatorios");
        } else if (empleadoDAO.getObject(codigo) != null) {
            throw new UserInputException("El empleado ya existe en el sistema");
        }
    }

    public static void validarUpdateEmpleado(String nombre, String cui, String direccion,
            String telefono, String correo) throws UserInputException {

        if (nombre.isEmpty() || cui.isEmpty() || direccion.isEmpty()
                || telefono.isEmpty() || correo.isEmpty()) {
            throw new UserInputException("Los datos con * son obligatorios");
        }
    }

    public static void validarAddCliente(String nombre, String nit, String telefono) throws UserInputException {
        if (nombre.isEmpty() || nit.isEmpty() || telefono.isEmpty()) {
            throw new UserInputException("Los campos con * son obligatorios");
        } else if (clienteDAO.getObject(nit) != null) {
            throw new UserInputException("El cliente ya existe en el sistema");
        }
    }
    
    public static void validarUpdateCliente(String nombre, String telefono) throws UserInputException{
        if (nombre.isEmpty() || telefono.isEmpty()) {
            throw new UserInputException("Los campos con * son obligatorios");
        }
    }
    
    public static void validarVenta(String nitCliente, String fecha, String porcentEfectivo, 
            String porcentCredito, String nombre, String telefono, String efectivo, 
            String credito) throws UserInputException {
        if (nitCliente.isEmpty() || fecha.isEmpty() || porcentCredito.isEmpty() 
                || porcentEfectivo.isEmpty() || nombre.isEmpty() || telefono.isEmpty()
                || efectivo.isEmpty() || credito.isEmpty()) {
            throw new UserInputException("Los datos con * son obligatorios");
        } else if (!isFecha(fecha)) {
            throw new UserInputException("La fecha no tiene el formato correcto");
        } else if (!isPorcentajeValido(porcentCredito) || !isPorcentajeValido(porcentEfectivo)) {
            throw new UserInputException("Los porcentajes no son validos");
        } else if (!porcentajeTotal(porcentCredito, porcentEfectivo)) {
            throw new UserInputException("Los porcentajes deben sumar 1");
        } else if (!isMayorACero(credito) || !isMayorACero(efectivo)) {
            throw new UserInputException("Los valores efectivo y credito deben ser mayores a cero");
        }
        
    }
    
    private static boolean isPorcentajeValido(String percent){
        int porcentaje = Integer.parseInt(percent);
        return (porcentaje>=0 && porcentaje<=1);
    }
    
    private static boolean porcentajeTotal(String p1, String p2){
        int porcentaje1 = Integer.parseInt(p1);
        int porcentaje2 = Integer.parseInt(p2);
        return ((porcentaje1+porcentaje2) == 1);
    }
}
