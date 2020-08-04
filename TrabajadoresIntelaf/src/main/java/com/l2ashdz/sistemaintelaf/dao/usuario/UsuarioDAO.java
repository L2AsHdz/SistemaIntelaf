package com.l2ashdz.sistemaintelaf.dao.usuario;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.Usuario;

public interface UsuarioDAO extends CRUD<Usuario>{
    
    public void disableUser(String DPI);
    public void update(Usuario u, String DPI);
}
