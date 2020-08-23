package com.l2ashdz.sistemaintelaf.dao.venta;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.Venta;
import java.util.List;

/**
 *
 * @author asael
 */
public interface VentaDAO extends CRUD<Venta>{
    int getIdVenta();
    List<Venta> getComprasDeUnCliente(String nitCliente);
}
