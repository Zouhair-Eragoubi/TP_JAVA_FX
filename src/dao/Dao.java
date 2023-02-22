package dao;

import java.util.List;

public interface Dao<T> {

    public T find(long id);
    public boolean checkExist(T object);
    public List<T> findAll();
    public T save(T object);
    public void delete(T object);
    public void update(T object);

}
