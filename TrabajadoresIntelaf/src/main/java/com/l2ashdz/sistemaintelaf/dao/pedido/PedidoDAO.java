package com.l2ashdz.sistemaintelaf.dao.pedido;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.Pedido;

/**
 *
 * @author asael
 */
public interface PedidoDAO extends CRUD<Pedido>{
    int getCodigoPedido();
}
