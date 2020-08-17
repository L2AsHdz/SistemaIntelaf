package com.l2ashdz.sistemaintelaf.dao.cliente;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.Cliente;
import java.util.List;

/**
 *
 * @author asael
 */
public interface ClienteDAO extends CRUD<Cliente>{
    List<Cliente> getFilteredList(String filtro, int opcion);
}
