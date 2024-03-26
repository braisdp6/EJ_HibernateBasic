package controller;

import java.util.List;

public interface Repository<T> {
    void save(T t);

    List<T> findAll();

    T findOneById(long id);
    T findOneById(String id);

    void update(T t);

    void delete(T t);

}
