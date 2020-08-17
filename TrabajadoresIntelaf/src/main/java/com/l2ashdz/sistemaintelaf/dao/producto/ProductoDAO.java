package com.l2ashdz.sistemaintelaf.dao.producto;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.Producto;
import java.util.List;

/**
 *
 * @author asael
 */
public interface ProductoDAO extends CRUD<Producto>{
    List<Producto> getFilteredList(String fitro, int opcion);
}
