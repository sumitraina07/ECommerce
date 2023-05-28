package com.example.ecommerce;
import  java.sql.*;
//class to connect to the Database
public class DbConnection {

    //Create url variable, Syntax in double quotes:- "dataBaseConnector:DbmsName://urlOfDatabase:portNo./DatabaseName"
    private  final String dbUrl = "jdbc:mysql://localhost:3306/ecommerce";

    //UserName and the password
    private  final String userName = "root"; //Initially if we haven't changed, our username is "root"

    private  final String password = "sumitmysql1";

    //will make the actual connections

    private Statement getStatement(){//object used for executing a static SQL statement and returning the results it produces.
        try{
            //DriverManager:- attempts to select an appropriate driver from the set of registered JDBC drivers.
            Connection connection =  DriverManager.getConnection(dbUrl, userName, password); //It creates us a connection of a Connection class object
            //getConnection:- Attempts to establish a connection to the given database URL.
            return connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //Executing the actual query
    public ResultSet getQueryTable(String query){
        try{
            Statement statement = getStatement();
            return statement.executeQuery(query); //this executes the select query and gives the result set
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    //Method for update and insert
    public int updateDatabase(String query){
        try{
            Statement statement = getStatement();
            return statement.executeUpdate(query); //executeUpdate:- means we can update the database
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }


    //main method
    public static void main(String[] args) {
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable("SELECT * FROM customer");
        if(rs!=null){
            System.out.println("Connection Successful");
        }
        else{
            System.out.println("Connection Failed");
        }
    }

}
