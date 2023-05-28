package com.example.ecommerce;

import java.sql.ResultSet;

public class Login {

    public Customer customerLogin(String userName,String password){

        String query = "SELECT * FROM customer WHERE email = '"+userName+"' AND password = '"+password+"'";
        //Now, we''ll fire it
        DbConnection connection = new DbConnection();

        try{
           ResultSet rs = connection.getQueryTable(query);
           if(rs.next()){
               return new Customer(rs.getInt("id"),rs.getString("name"),
                       rs.getString("email"),rs.getString("mobile")); //this is how we can fetch data from the result set each item by item
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Login login = new Login();
        Customer customer = login.customerLogin("sumitraina@gmail.com", "sumit321");
        System.out.println("Welcome : "+ customer.getName());
      //  System.out.println(login.customerLogin("sumitraina@gmail.com", "sumit321"));
    }
}
