package presentation.Controller;

import Services.CatalogueServiceImpl;
import dao.CategoryDaoImpl;
import dao.ProductDaoImpl;
import dao.entites.Category;
import dao.entites.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import javax.swing.event.ChangeEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    private CatalogueServiceImpl catalogueService;
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private ObservableList<Product> listProduct = FXCollections.observableArrayList();


    @FXML
    private TextField refrerence;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private ComboBox<Category> idCat;

    @FXML
    private TextField nameSearch;

    @FXML
    private TableView<Product> tableProduct;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> referenceColumn;

    @FXML
    private TableColumn<?, ?> nomColumn;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;

    @FXML
    void addProduct(ActionEvent event) {
        boolean add = true;
        Double priceValue;
        String errorMsg="Les champs suivants sont obligatoire";
        String refText = refrerence.getText();
        String nameText = name.getText();
        String priceText = price.getText();
        Category category = idCat.getSelectionModel().getSelectedItem();
        System.out.println(refText);
        if(refText == null || refText.equals("")){
            errorMsg +="\t\n * Référence";
            add = false;

        }
        if(nameText == null || nameText.equals("")){
            errorMsg +="\t\n * Nom";
            add = false;

        }
        if(priceText == null || priceText.equals("")){
            errorMsg +="\t\n * Prix";
            add = false;

        }else{
            try {
                priceValue = Double.valueOf(priceText);
            }catch (Exception e){
                errorMsg +="\t\n * Prix invalide";
                add = false;
            }
        }
        if(category == null){
            errorMsg +="\t\n * Categorie";
            add = false;

        }

        if(add){
            Product product = new Product();
            product.setName(nameText);
            product.setReference(refText);
            product.setPrice(Double.valueOf(priceText));
            product.setCategory(category);
            if(catalogueService.checkExisteProduct(product)){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("");
                alert.setContentText("Cette produit existe deja !");
                alert.show();
            }else{
                catalogueService.addProduct(product);
                tableProduct.getItems().add(product);
                viderChamps();
            }

        }else{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("");
            alert.setContentText(errorMsg);
            alert.show();
        }
    }

    @FXML
    void editProduct(ActionEvent event) {
        Product product = tableProduct.getSelectionModel().getSelectedItem();
        int productIndex = tableProduct.getSelectionModel().getSelectedIndex();
        if(product == null){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("");
            alert.setContentText("Attention il faut sélectionner un élément !!!");
            alert.show();
        }else{
            boolean edit = true;
            Double priceValue;
            String errorMsg="Les champs suivants sont obligatoire";
            String refText = refrerence.getText();
            String nameText = name.getText();
            String priceText = price.getText();
            Category category = idCat.getSelectionModel().getSelectedItem();
            if(refText.equals("")){
                errorMsg +="\t\n * Référence";
                edit = false;

            }
            if(nameText.equals("")){
                errorMsg +="\t\n * Nom";
                edit = false;

            }
            if(priceText.equals("")){
                errorMsg +="\t\n * Prix";
                edit = false;

            }
            if(!priceText.equals("")){
                try {
                    priceValue = Double.valueOf(priceText);
                }catch (Exception e){
                    errorMsg +="\t\n * Prix invalide";
                    edit = false;
                }
            }
            if(category == null){
                errorMsg +="\t\n * Categorie";
                edit = false;

            }

            if(edit){
                product.setName(nameText);
                product.setReference(refText);
                product.setPrice(Double.valueOf(priceText));
                product.setCategory(category);
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("");
                alert.setContentText("Clique sur OK pour confirmer la modification");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        if(catalogueService.checkExisteProduct(product)){
                            Alert alert2=new Alert(Alert.AlertType.INFORMATION);
                            alert2.setHeaderText("");
                            alert2.setContentText("Cette produit existe deja !");
                            alert2.show();
                        }else{
                            catalogueService.updateProduct(product);
                            tableProduct.getItems().set(productIndex,product);
                            tableProduct.getSelectionModel().select(null);
                            viderChamps();
                        }

                    }
                });

            }else{
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("");
                alert.setContentText(errorMsg);
                alert.show();
            }


        }
    }
    @FXML
    void deleteProduct(ActionEvent event) {
        Product product = tableProduct.getSelectionModel().getSelectedItem();
        if(product == null){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("");
            alert.setContentText("Attention il faut sélectionner un élément !!!");
            alert.show();
        }else{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("");
            alert.setContentText("Clique sur OK pour confirmer la suppression");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    catalogueService.deleteProduct(product);
                    listProduct.remove(product);
                    tableProduct.getSelectionModel().select(null);
                    viderChamps();
                }
            });

        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         catalogueService = new CatalogueServiceImpl(new ProductDaoImpl(),
                new CategoryDaoImpl());
        categories.addAll(catalogueService.getAllCategory());
        idCat.getItems().addAll(categories);

        listProduct = FXCollections.observableArrayList(catalogueService.getAllProduct());
        tableProduct.setItems(listProduct);

        idColumn.setCellValueFactory( new PropertyValueFactory<>("id"));
        referenceColumn.setCellValueFactory( new PropertyValueFactory<>("reference"));
        nomColumn.setCellValueFactory( new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory( new PropertyValueFactory<>("category"));

        nameSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("")){
                    listProduct = FXCollections.observableArrayList(catalogueService.getAllProduct());
                }else{
                    listProduct = FXCollections.observableArrayList(catalogueService.searchByQuery(newValue));
                }
                tableProduct.setItems(listProduct);
            }
        });

        ObservableList<Product> selectedItems = tableProduct.getSelectionModel().getSelectedItems();
        selectedItems.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> c) {
                 if(c.next() == true){
                     if(c.getAddedSize() == 1){
                         Product product = c.getList().get(0);
                         refrerence.setText(product.getReference());
                         name.setText(product.getName());
                         price.setText(String.valueOf(product.getPrice()));
                         idCat.getSelectionModel().select(product.getCategory());
                         System.out.println(product);
                         remplirChamps(product);
                     }

                 }


            }
        });


    }
    public void remplirChamps(Product product){
        refrerence.setText(product.getReference());
        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        idCat.getSelectionModel().select(product.getCategory());

    }
    public void viderChamps(){
        refrerence.setText(null);
        name.setText(null);
        price.setText(null);
        idCat.getSelectionModel().select(null);

    }

}

