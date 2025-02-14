package com.mycompany.javafxapplication1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.javafxapplication1.LoadBalancer;
import com.mycompany.javafxapplication1.StorageContainer;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize load balancer with 3 storage containers
        List<StorageContainer> containers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            containers.add(new StorageContainer("container" + (i+1)));
        }
        LoadBalancer loadBalancer = new LoadBalancer(containers);
        
        // Initialize and test databases
        DB myObj = new DB();
        myObj.initializeDatabases();
        
        // Test SQLite connection
        myObj.setUseMySQL(false);
        if (myObj.testConnection()) {
            myObj.log("SQLite connection successful");
        } else {
            myObj.log("SQLite connection failed");
        }
        
        // Test MySQL connection
        myObj.setUseMySQL(true);
        if (myObj.testConnection()) {
            myObj.log("MySQL connection successful");
        } else {
            myObj.log("MySQL connection failed");
        }
        
        Stage secondaryStage = new Stage();
        myObj.log("-------- Simple Tutorial on how to make JDBC connection to SQLite DB ------------");
        myObj.log("\n---------- Drop table ----------");
        try {
            myObj.delTable(myObj.getTableName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        myObj.log("\n---------- Create table ----------");
        try {
            myObj.createTable(myObj.getTableName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Primary View");
            secondaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Add artificial network delay simulation
        System.setProperty("network.delay", "true");
        
        // Set default database to SQLite
        DB myObj = new DB();
        myObj.setUseMySQL(false);
        
        launch();
    }

}
