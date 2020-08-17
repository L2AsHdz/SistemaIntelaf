package com.l2ashdz.sistemaintelaf.dao.empleado;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.Empleado;
import java.util.List;

public interface EmpleadoDAO extends CRUD<Empleado>{
    List<Empleado> getFilteredList(String codigo, String nombre, int opcion);
    void disableUser(String codigo);
}
