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
    public T getObject(Object t);
    public void update(T t);
    public void delete(int t);
}
