package neo.dmcs.dao;

import java.io.Serializable;

/**
 * @Author Mateusz Wieczorek, 16.04.16.
 */
public interface GenericDao<T, PK extends Serializable> {
    void save(T t);
    T findById(PK id);
    void update(T t);
    void delete(T t);
}
