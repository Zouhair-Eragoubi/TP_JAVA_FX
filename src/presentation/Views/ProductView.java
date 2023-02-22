package presentation.Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ProductView  extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(getClass().getResource("../productView.fxml"));
        BorderPane root= FXMLLoader.load(getClass().getResource("../productView.fxml"));
        Scene scene=new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("style.css").toString());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
