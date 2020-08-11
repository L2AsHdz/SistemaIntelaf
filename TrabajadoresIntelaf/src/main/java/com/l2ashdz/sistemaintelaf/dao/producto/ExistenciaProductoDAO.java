package com.l2ashdz.sistemaintelaf.dao.producto;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.ExistenciaProducto;

/**
 *
 * @author asael
 */
public interface ExistenciaProductoDAO extends CRUD<ExistenciaProducto>{
    public ExistenciaProducto getProductoInTienda (String codT, String codP);
    public int getExistencias(String codT, String codP);
}
