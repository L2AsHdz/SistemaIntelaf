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
import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.ProductoPedido;
import com.l2ashdz.sistemaintelaf.model.ProductoVenta;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import java.util.List;

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
            throw new UserInputException("El tiempo debe ser un numero entero");
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
            throw new UserInputException("La garantia debe ser un numero entero");
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
    
    public static void validarAddCliente2(String nombre, String nit, String telefono) throws UserInputException {
        if (nombre.isEmpty() || nit.isEmpty() || telefono.isEmpty()) {
            throw new UserInputException("Los campos con * son obligatorios");
        }
    }

    public static void validarUpdateCliente(String nombre, String telefono) throws UserInputException {
        if (nombre.isEmpty() || telefono.isEmpty()) {
            throw new UserInputException("Los campos con * son obligatorios");
        }
    }

    public static void validarVenta(String fecha, String porcentEfectivo,
            String porcentCredito) throws UserInputException {
        if (fecha.isEmpty() || porcentCredito.isEmpty() || porcentEfectivo.isEmpty()) {
            throw new UserInputException("Los datos con * son obligatorios");
        } else if (!isFecha(fecha)) {
            throw new UserInputException("La fecha no tiene el formato correcto");
        }
    }
    
    public static void validarPedido(String porcentEfectivo,
            String porcentCredito, String porcentajePagado) throws UserInputException {
        if (porcentCredito.isEmpty() || porcentEfectivo.isEmpty() || porcentajePagado.isEmpty()) {
            throw new UserInputException("Los datos con * son obligatorios");
        }
    }

    public static void validarAddProducVenta(String cantidad, Producto p, List<ProductoVenta> pList) throws UserInputException {
        if (Integer.parseInt(cantidad) > p.getExistencias()) {
            throw new UserInputException("No hay existencias suficientes, unidades disponibles: "
                    + p.getExistencias());
        } else if (isProductoInVenta(pList, p)) {
            throw new UserInputException("El producto ya esta agregado");
        }
    }
    
    public static void validarAddProducPedido(String cantidad, Producto p, List<ProductoPedido> pList) throws UserInputException {
        if (Integer.parseInt(cantidad) > p.getExistencias()) {
            throw new UserInputException("No hay existencias suficientes, unidades disponibles: "
                    + p.getExistencias());
        } else if (isProductoInPedido(pList, p)) {
            throw new UserInputException("El producto ya esta agregado");
        }
    }

    public static void validarExistencias(String cantidad, Producto p) throws UserInputException {
        if (Integer.parseInt(cantidad) > p.getExistencias()) {
            throw new UserInputException("No hay existencias suficientes, unidades disponibles: "
                    + p.getExistencias());
        }
    }

    private static boolean isProductoInVenta(List<ProductoVenta> pList, Producto p) {
        boolean flag = false;
        for (Producto producto : pList) {
            if (producto.getCodigo().equals(p.getCodigo())) {
                flag = true;
            }
        }
        return flag;
    }
    
    private static boolean isProductoInPedido(List<ProductoPedido> pList, Producto p) {
        boolean flag = false;
        for (Producto pp : pList) {
            if (pp.getCodigo().equals(p.getCodigo())) {
                flag = true;
            }
        }
        return flag;
    }
}
