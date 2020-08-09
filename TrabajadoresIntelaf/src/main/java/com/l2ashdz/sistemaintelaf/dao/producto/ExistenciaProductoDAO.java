package com.l2ashdz.sistemaintelaf.dao.producto;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.ExistenciaProducto;

/**
 *
 * @author asael
 */
public interface ExistenciaProductoDAO extends CRUD<ExistenciaProducto>{
    public ExistenciaProducto getExistenciasP (String codT, String codP);
}
