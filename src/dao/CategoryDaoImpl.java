package dao;

import dao.entites.Category;
import dao.Connection.*;
import dao.entites.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDaoInterface {
    @Override
    public Category find(long id) {
        return null;
    }

    @Override
    public boolean checkExist(Category object) {
        return false;
    }

    @Override
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM category");
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Category category = new Category();
                category.setId(result.getLong("id"));
                category.setName(result.getString("name"));
                list.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public Category save(Category object) {
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO CATEGORY(name) values(?)");
            preparedStatement.setString(1,object.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    @Override
    public void delete(Category object) {

    }

    @Override
    public void update(Category object) {

    }
}
