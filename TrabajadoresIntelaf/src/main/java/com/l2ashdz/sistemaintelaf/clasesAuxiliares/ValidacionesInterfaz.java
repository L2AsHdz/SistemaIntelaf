package com.l2ashdz.sistemaintelaf.clasesAuxiliares;

import static com.l2ashdz.sistemaintelaf.clasesAuxiliares.Verificaciones.isInt;
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
import com.l2ashdz.sistemaintelaf.excepciones.UserInputException;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import com.l2ashdz.sistemaintelaf.model.Empleado;
import com.l2ashdz.sistemaintelaf.model.Producto;
import com.l2ashdz.sistemaintelaf.model.Tienda;

/**
 *
 * @author asael
 */
public class ValidacionesInterfaz {
    
    private static CRUD<Tienda> tiendaDAO = TiendaDAOImpl.getTiendaDAO();
    private static TiempoTrasladoDAO tiempoDAO = TiempoTrasladoDAOImpl.getTiempoDAO();
    private static ExistenciaProductoDAO existenciaDAO = ExistenciaProductoDAOImpl.getExistenciaDAO();
    private static CRUD<Producto> productoDAO = ProductoDAOImpl.getProductoDAO();
    private static CRUD<Cliente> clienteDAO = ClienteDAOImpl.getClienteDAO();
    private static CRUD<Empleado> empleadoDAO = EmpleadoDAOImpl.getEmpleadoDAO();
    private static ProductoPedidoDAO productoPedDAO = ProductoPedidoDAOImpl.getProductoPDAO();
    
    public static boolean validarAddTienda(String codigo, String nombre, String direccion,
            String tel1) throws UserInputException {
        
        boolean flag = true;
        if (nombre.isEmpty() || codigo.isEmpty() || tel1.isEmpty() || direccion.isEmpty()) {
            flag = false;
            throw new UserInputException("Los datos con * son obligatorios");
        } else if (tiendaDAO.getObject(codigo) != null) {
            flag = false;
            throw new UserInputException("La tienda ya existe en el sistema");
        }
        return flag;
    }
    
    public static boolean validarUpdateTienda(String nombre, String direccion,String tel1) throws UserInputException{
        
        boolean flag = true;
        if (nombre.isEmpty() || tel1.isEmpty() || direccion.isEmpty()) {
            flag = false;
            throw new UserInputException("Los datos con * son obligatorios");
        }
        return flag;
    }
    
    public static boolean validarUpdateTiempo(String tiempo) throws UserInputException{
        
        boolean flag = true;
        if (tiempo.isEmpty()) {
            flag = false;
            throw new UserInputException("Debe ingresar un tiempo");
        } else if (!isInt(tiempo)) {
            flag = false;
            throw new UserInputException("El tiempo debe ser un valor numerico");
        }
        return flag;
    }
}
