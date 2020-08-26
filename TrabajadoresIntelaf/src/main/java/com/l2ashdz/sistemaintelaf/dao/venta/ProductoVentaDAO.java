package com.l2ashdz.sistemaintelaf.dao.venta;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.ProductoVenta;
import java.util.List;

/**
 *
 * @author asael
 */
public interface ProductoVentaDAO extends CRUD<ProductoVenta> {
    List<ProductoVenta> getProductosInVenta(int idVenta);
}
