package com.l2ashdz.sistemaintelaf.dao.producto;

import com.l2ashdz.sistemaintelaf.dao.CRUD;
import com.l2ashdz.sistemaintelaf.model.Producto;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author asael
 */
public interface ProductoDAO extends CRUD<Producto>{
    List<Producto> getFilteredList(String fitro, int opcion);
    Producto getProducto(String codT, String codP);
    List<Producto> getMostSelledProducts(LocalDate fechaInicial, LocalDate fechaFinal, int opcion);
    List<Producto> getMostSelledProdPorTienda(String codT, LocalDate fechaInicial, LocalDate fechaFinal, int opcion);
    List<Producto> getProductosSinVentas(String codTienda);
}
