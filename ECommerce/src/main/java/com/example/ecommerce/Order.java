package com.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {

    //this is how we are ordering a single product and for multiple orders group_order_id must be same
    public static boolean placeOrder(Customer customer, Product product){//customer and product are needed to place an order
        //calling this fn with valid cust. and product will place the order

        String groupOrderId = "SELECT max(group_order_id) +1 id FROM ecommerce.`orders`";
        DbConnection dbConnection = new DbConnection();
        try{
            ResultSet rs = dbConnection.getQueryTable(groupOrderId);
            if(rs.next()){
                String placeOrder = "INSERT INTO ecommerce.`orders`(group_order_id, customer_id, product_id)VALUES("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                return dbConnection.updateDatabase(placeOrder) != 0; //if it is 1 or something that means insertion is successful
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //FOR placing multiple orders
    public static int placeMultipleOrder(Customer customer, ObservableList<Product> productList){//customer and product are needed to place an order
        //calling this fn with valid cust. and product will place the order

        String groupOrderId = "SELECT max(group_order_id) +1 id FROM ecommerce.`orders`";
        DbConnection dbConnection = new DbConnection();
        try{
            ResultSet rs = dbConnection.getQueryTable(groupOrderId);
            int count = 0;
            if(rs.next()){
                for(Product product: productList){ //one by one all elements will come into this
                    String placeOrder = "INSERT INTO ecommerce.`orders`(group_order_id, customer_id, product_id)VALUES("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                    count += dbConnection.updateDatabase(placeOrder);
                }

                return count;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
