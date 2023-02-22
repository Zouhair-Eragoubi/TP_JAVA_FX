package dao;


import java.sql.Connection;

import dao.Connection.DbConnection;
import dao.entites.Category;
import dao.entites.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDaoInterface {
    @Override
    public Product find(long id) {
        return null;
    }

    @Override
    public boolean checkExist(Product product) {
        boolean existe;
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT PRODUCTS.id FROM PRODUCTS " +
                            " WHERE PRODUCTS.name = ? AND PRODUCTS.reference = ? AND PRODUCTS.price = ? AND PRODUCTS.id_cat = ? ");
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getReference());
            preparedStatement.setDouble(3,product.getPrice());
            preparedStatement.setLong(4,product.getCategory().getId());

            ResultSet result = preparedStatement.executeQuery();
            existe = result.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existe;
    }

    @Override
    public List findAll() {
        List<Product> list = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT PRODUCTS.* ,CATEGORY.name as cat " +
                            "FROM PRODUCTS LEFT JOIN CATEGORY ON CATEGORY.id = PRODUCTS.id_cat " +
                            "ORDER BY 1");
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Product product = new Product();
                Category category = new Category();
                product.setId(result.getLong("id"));
                product.setName(result.getString("name"));
                product.setReference(result.getString("reference"));
                product.setPrice(result.getDouble("price"));
                category.setId(result.getLong("id_cat"));
                category.setName(result.getString("cat"));
                product.setCategory(category);
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    @Override
    public Product save(Product object) {
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO products(name,reference,price,id_cat) values(?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,object.getName());
            preparedStatement.setString(2,object.getReference());
            preparedStatement.setDouble(3,object.getPrice());
            preparedStatement.setLong(4,object.getCategory().getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                object.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
    @Override
    public void delete(Product object) {
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM products WHERE id = ? ");
            preparedStatement.setLong(1,object.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void update(Product object) {
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE PRODUCTS SET name = ? , reference = ? ,price = ?,id_cat = ?");
            preparedStatement.setString(1,object.getName());
            preparedStatement.setString(2,object.getReference());
            preparedStatement.setDouble(3,object.getPrice());
            preparedStatement.setLong(4,object.getCategory().getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public List<Product> findByQuery(String query) {
        List<Product> list = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT PRODUCTS.* ,CATEGORY.name as cat " +
                            "FROM PRODUCTS LEFT JOIN CATEGORY ON CATEGORY.id = PRODUCTS.id_cat " +
                            " WHERE PRODUCTS.name like ? OR PRODUCTS.reference like ? OR CATEGORY.name like ? "+
                            "ORDER BY 1");
            preparedStatement.setString(1,"%"+query+"%");
            preparedStatement.setString(2,"%"+query+"%");
            preparedStatement.setString(3,"%"+query+"%");

            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Product product = new Product();
                Category category = new Category();
                product.setId(result.getLong("id"));
                product.setName(result.getString("name"));
                product.setReference(result.getString("reference"));
                product.setPrice(result.getDouble("price"));
                category.setId(result.getLong("id_cat"));
                category.setName(result.getString("cat"));
                product.setCategory(category);
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
