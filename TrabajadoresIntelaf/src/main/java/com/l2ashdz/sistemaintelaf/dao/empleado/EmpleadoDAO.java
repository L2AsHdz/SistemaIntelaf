package com.l2ashdz.sistemaintelaf.dao.empleado;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.Empleado;

public interface EmpleadoDAO extends CRUD<Empleado>{
    
    void disableUser(String codigo);
}
