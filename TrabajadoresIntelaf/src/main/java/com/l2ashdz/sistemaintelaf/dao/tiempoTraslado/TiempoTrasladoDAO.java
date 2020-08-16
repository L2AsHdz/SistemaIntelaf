package com.l2ashdz.sistemaintelaf.dao.tiempoTraslado;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.TiempoTraslado;
import java.util.List;

/**
 *
 * @author asael
 */
public interface TiempoTrasladoDAO extends CRUD<TiempoTraslado>{
    public TiempoTraslado getTiempoT(String codT1, String codT2);
    public List<TiempoTraslado> getTiemposOfT(String codTActual);
}
