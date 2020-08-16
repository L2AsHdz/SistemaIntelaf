package com.l2ashdz.sistemaintelaf.dao.tienda;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.Tienda;
import java.util.List;

/**
 *
 * @author asael
 */
public interface TiendaDAO extends CRUD<Tienda>{
    public List<Tienda> getFilteredList(String nombre, String codigo, int opcion);
}
