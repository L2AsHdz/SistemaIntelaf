package com.l2ashdz.sistemaintelaf.dao.pedido;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.Pedido;
import java.util.List;

/**
 *
 * @author asael
 */
public interface PedidoDAO extends CRUD<Pedido>{
    int getCodigoPedido();
    List<Pedido> getPedidos(String codTD);
    void setEstado(int codigo, int estado);
    void setFecha(int codigo, String fecha, int opcion);
    List<Pedido> getPedidosEnRuta(String codT);
}
