package com.l2ashdz.sistemaintelaf.dao;

import java.util.List;

/**
 *
 * @author asael
 * @param <T>
 */
public interface CRUD<T> {
    
    List<T> getListado();
    void create(T t);
    T getObject(String t);
    void update(T t);
    void delete(String t);
}
