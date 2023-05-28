package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {

    //With help of TableView, we can view the table,and product as its type
    private TableView<Product> productTable;

    private TableView<Product> cartTable;

    //creating the table
    public  VBox createTable(ObservableList<Product> data){ //creating the table and binding the data we provide
        //columns
        //ID
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        //NAME
        TableColumn name = new TableColumn ("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        //PRICE
        TableColumn price = new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));



        //Now add these things to the table
        productTable = new TableView<>();
        productTable.getColumns().addAll(id,name,price);//added the columns
        productTable.setItems(data); //added the data
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //extra col. will be removed

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(productTable);

        return vBox;

    }

    public  VBox createCartTable(ObservableList<Product> data){ //creating the table and binding the data we provide
        //columns
        //ID
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        //NAME
        TableColumn name = new TableColumn ("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        //PRICE
        TableColumn price = new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));



        //Now add these things to the table
        cartTable = new TableView<>();
        cartTable.getColumns().addAll(id,name,price);//added the columns
        cartTable.setItems(data); //added the data
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //extra col. will be removed

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(cartTable);

        return vBox;

    }


    public VBox getAllProducts(){ //brings all the products frm d database
        ObservableList<Product> data = Product.getAllProducts(); //It will fetch all the products
        return createTable(data);
    }

    public VBox getProductsByName(String name){
        ObservableList<Product> data = Product.getAllProductsByName(name);
        return createTable(data);
    }


    public Product getSelectedProduct(){ //user selects any product is nothing but the selectedProduct
        return productTable.getSelectionModel().getSelectedItem();

    }

    public VBox getProductsInCart(ObservableList<Product> data){
        return createCartTable(data);
    }
}
