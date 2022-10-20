package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**This is the class that will start my application by creating the starting scene by loading my main.fxml file
 * My javadocs are in the JavaDocs folder of this file.
 * My RUNTIME error can be found in the addPartFormController
 * my FUTURE ENHANCEMENT can be found in my addProductController
 * @author madelinebaum */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Product prod1 = new Product(1, "String", 2.00, 100, 5, 500);
        Part part1 = new Outsourced(5,"Ball", 3.00, 100, 5, 120, "Amazon");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Product prod1 = new Product(1, "String", 2.00, 100, 5, 500);
        Product prod2 = new Product(2, "Feather", 0.50, 100, 25, 100);
        Product prod3 = new Product(3, "Bell", 0.25, 250, 400, 2400);
        Product prod4 = new Product(4, "cde", 1.00, 300, 5, 100);
        Product prod5 = new Product(5, "abc", 0.5, 50, 100, 400);
        Part part1 = new Outsourced(5,"Ball", 3.00, 100, 500, 120, "Amazon");
        Part part2 = new Outsourced(1, "ABC", 2.00, 100, 800, 300, "Walmart");

        Inventory.addProduct(prod1);
        Inventory.addPart(part1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);
        Inventory.addProduct(prod4);
        Inventory.addProduct(prod5);
        Inventory.addPart(part2);
        launch();
    }
}