package com.example.ecommerce;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

//this is to make sure we are able to view this in table which we'll create
public class Product {
    //creating some properties

    private SimpleIntegerProperty id;  //property wrapping of int value.(it wraps the values and display in the table views)

    //creating other data items(id,name,price)
    private SimpleStringProperty name;

    private SimpleDoubleProperty price;

    //constructor
    public Product(int id, String name, Double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public static  ObservableList<Product> getAllProducts(){
        String selectAllProducts = "SELECT id,name,price FROM product";
        return fetchProductData(selectAllProducts);
    }

    public static  ObservableList<Product> getAllProductsByName(String name){
        String selectAllProducts = "SELECT id,name,price FROM product WHERE name like '%"+name+"%'";
        return fetchProductData(selectAllProducts);
    }

    //create method to fire this query
    public static ObservableList<Product> fetchProductData(String query){
        ObservableList<Product> data = FXCollections.observableArrayList();
        DbConnection dbConnection = new DbConnection();
        try{
            ResultSet rs = dbConnection.getQueryTable(query);
            while(rs.next()){
                Product product  = new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
                data.add(product);
            }
            return data;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //getters
    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }

}
