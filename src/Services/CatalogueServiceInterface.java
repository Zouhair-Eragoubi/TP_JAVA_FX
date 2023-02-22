package Services;

import dao.entites.Category;
import dao.entites.Product;

import java.util.List;

public interface CatalogueServiceInterface {

    List<Product> getAllProduct();
    Product addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
    boolean checkExisteProduct(Product product);


    List<Category> getAllCategory();
    void addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Category category);

    List<Product> searchByQuery(String query);


}
