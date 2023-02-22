import Services.CatalogueServiceImpl;
import dao.CategoryDaoImpl;
import dao.ProductDaoImpl;
import dao.entites.*;

public class Main {

    public static void main(String[] args) {
        //dao.Connection connection = DbConnection.getConnection();
        CatalogueServiceImpl catalogueService = new CatalogueServiceImpl(new ProductDaoImpl(),
                new CategoryDaoImpl());
        Category category = new Category();
        category.setName("pc portable");
        catalogueService.addCategory(category);
        Product product = new Product();
        product.setName("HP2");
        product.setReference("R0002");
        category.setId(1);
        product.setCategory(category);
        product.setPrice(12456.22);

        catalogueService.addProduct(product);


    }
}
