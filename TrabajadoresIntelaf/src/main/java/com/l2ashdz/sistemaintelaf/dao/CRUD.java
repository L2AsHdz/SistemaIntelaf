package com.l2ashdz.sistemaintelaf.dao;

import java.util.List;

/**
 *
 * @author asael
 * @param <T>
 */
public interface CRUD<T> {
    
    public List<T> getListado();
    public void create(T t);
    public T getObject(String t);
    public void update(T t);
    public void delete(String t);
}
