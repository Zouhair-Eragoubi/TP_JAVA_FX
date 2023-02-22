package Services;

import dao.CategoryDaoInterface;
import dao.ProductDaoInterface;
import dao.entites.Category;
import dao.entites.Product;

import java.util.List;

public class CatalogueServiceImpl implements CatalogueServiceInterface{

    private ProductDaoInterface productDaoInterface;
    private CategoryDaoInterface categoryDaoInterface;

    public CatalogueServiceImpl(ProductDaoInterface productDaoInterface,
                                CategoryDaoInterface categoryDaoInterface)
    {
        this.productDaoInterface = productDaoInterface;
        this.categoryDaoInterface = categoryDaoInterface;
    }

    @Override
    public List<Product> getAllProduct() {
        return productDaoInterface.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        return productDaoInterface.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productDaoInterface.update(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productDaoInterface.delete(product);
    }

    @Override
    public boolean checkExisteProduct(Product product) {
        return productDaoInterface.checkExist(product);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryDaoInterface.findAll();
    }

    @Override
    public void addCategory(Category category) {
        categoryDaoInterface.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryDaoInterface.update(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryDaoInterface.delete(category);
    }

    @Override
    public List<Product> searchByQuery(String query) {
        return productDaoInterface.findByQuery(query);
    }
}
