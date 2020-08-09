package com.l2ashdz.sistemaintelaf.dao.tiempoTraslado;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.TiempoTraslado;

/**
 *
 * @author asael
 */
public interface TiempoTrasladoDAO extends CRUD<TiempoTraslado>{
    public TiempoTraslado getTiempoT(String codT1, String codT2);
}
