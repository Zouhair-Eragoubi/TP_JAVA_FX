package dao;

import dao.entites.Product;

import java.util.List;

public interface ProductDaoInterface extends Dao<Product> {
    public List<Product> findByQuery(String query);
}
