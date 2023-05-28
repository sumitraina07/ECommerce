package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.time.format.TextStyle;

//class for creation of all the User Interface or UI components that we need
public class UserInterface {

    VBox body;

    Customer loggedInCustomer;

    ProductList productList = new ProductList();

    VBox productPage;
    
    Button placeOrderButton = new Button("Place Order");

    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();

    GridPane loginPage;  //Creating variable 'loginPage' of type GridPane
    HBox headerBar;  // Creating variable 'headerBar' of type HBox
    HBox footerBar;

    Button signInButton;

    Label welcomeLabel;



    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(800,500);
        //To add nodes as children to the Pane
        //root.getChildren().add(loginPage);
        root.setTop(headerBar);
        //root.setCenter(loginPage);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productPage = productList.getAllProducts();

        //Adding the products to the body
         body.getChildren().add(productPage);

         root.setBottom(footerBar);


        return root;
    }

    public UserInterface() throws FileNotFoundException { //Constructor
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }

    //LOGIN PAGE
    private void createLoginPage() {
        Text userNameText = new Text("User Name");//To display the test
        Text passwordText = new Text("Password");

        //To provide Text Field for the user
        TextField userName = new TextField("sumitraina@gmail.com");
        userName.setPromptText("Enter your user name here ");
        PasswordField password = new PasswordField();
        password.setText("sumit321");
        password.setPromptText("Enter your password here ");

        Label messageLabel = new Label("Hi");

        Button loginButton = new Button("Login");

        //Now, putting these controls in our gridPane
        loginPage = new GridPane(); //Creating a gridPane
        loginPage.setStyle("-fx-background-color:grey;");
        loginPage.setAlignment(Pos.CENTER);  //setting the position
        loginPage.setHgap(20);  //Horizontal gap
        loginPage.setVgap(20);  //Vertical gap
        //Now adding the controls to the Grid pane
       // Syntax:-  add (Node child, int columnIndex, int rowIndex)
       // Adds a child to the gridPane at the specified column, row position.
        loginPage.add(userNameText,0,0);
        loginPage.add(userName,1,0);
        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);
        loginPage.add(messageLabel,0,2);
        loginPage.add(loginButton,1,2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() { //Event as soon as user presses 'Login;
            @Override
            public void handle(ActionEvent actionEvent) {
                //Fetching the user name that user entered to display when logged in
                String name = userName.getText();
                String pass = password.getText();
                //instance of Login class
                Login login = new Login();
                loggedInCustomer = login.customerLogin(name, pass);
                //Now 2 cases can be there either successful login or failed login
                if(loggedInCustomer!=null){
                    messageLabel.setText("Welcome "+loggedInCustomer.getName());
                    welcomeLabel.setText("Welcome " + loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }
                else{
                    messageLabel.setText("Login Failed ! Please provide correct credentials");
                }
                //messageLabel.setText(name);
            }
        });
    }

    //HEADER BAR
    private void createHeaderBar() throws FileNotFoundException {

        //Home Button
        Button homeButton = new Button();
        FileInputStream file = null;
        file = new FileInputStream("/Users/sumeetraina/IdeaProjects/ECommerce/src/ecom.png");
        Image image = new Image(file);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(95);
        //adding the image to the homeButton
        homeButton.setGraphic(imageView);

        //We'll have Search Bar and a button to search first in our Header Bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here ");
        searchBar.setPrefWidth(350); //To manipulate the sear  ch bar width

        //Button to search
        Button searchButton = new Button("Search");

        //sign in button
        signInButton = new Button("Sign in");
        welcomeLabel = new Label();

        Button cartButton = new Button("Cart");

        Button orderButton = new Button("Orders");




        headerBar = new HBox();
        headerBar.setStyle("-fx-background-color:grey;");
        headerBar.setPadding(new Insets(12));
        headerBar.setSpacing(10); //Spacing b/w children (here, textField and button)
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton, searchBar,searchButton,signInButton,cartButton,orderButton); //getting all children to headerBar

        //event on click of signInButton
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //on click of this button we'll remove everything from the body and put the login page
                body.getChildren().clear();//remove everything
                body.getChildren().add(loginPage);//put login page
                headerBar.getChildren().remove(signInButton);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductsInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER);//placing order button at center in cart page
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                //removing buyNow and addToCart button from cart page
                footerBar.setVisible(false);
            }
        });
        //func of placeOrderButton
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //for placing multiple orders we'll need a list pf products and a customer
                if(itemsInCart == null){//if custmr hasn't selected anything then ask him to select something
                    //"Please select a product first to place order"
                    showDialog("Please adds some products in the cart to place order!");
                    return;
                }
                if(loggedInCustomer == null){
                    showDialog("Please login first to place order");
                    return;
                }
                int count = Order.placeMultipleOrder(loggedInCustomer, itemsInCart);
                if(count != 0) {
                    showDialog("Order for "+count+" products placed successfully!");
                }
                else{
                    showDialog("Order Failed!!");
                }

            }
        });

        //Home Button Function
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //here just showing the product page
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if(loggedInCustomer == null && headerBar.getChildren().indexOf(signInButton) == -1){
                    headerBar.getChildren().add(signInButton);
                }
            }
        });
        //Search Bar function
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = searchBar.getText();
                body.getChildren().clear();
               // Product.getAllProductsByName(name);
                body.getChildren().add(productList.getProductsByName(name));
            }
        });
    }

    private void createFooterBar(){


        Button buyNowButton = new Button("Buy Now");

        Button addToCartButton = new Button("Add To Cart");

        footerBar = new HBox();
        footerBar.setStyle("-fx-background-color:grey;");
        footerBar.setPadding(new Insets(12));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addToCartButton); //getting all children to footerBar

        //Event
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product  = productList.getSelectedProduct();
                if(product == null){//if custmr hasn't selected anything then ask him to select something
                    //"Please select a product first to place order"
                    showDialog("Please select a product first to place order!");
                    return;
                }
                if(loggedInCustomer == null){
                    showDialog("Please login first to place order");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer, product);
                if(status == true) {
                    showDialog("Order placed successfully!");
                }
                else{
                    showDialog("Order Failed!!");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product  = productList.getSelectedProduct();
                if(product == null){//if custmr hasn't selected anything then ask him to select something
                    //"Please select a product first to place order"
                    showDialog("Please select a product first to add it to Cart!");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Selected item has been added to the cart successfully!");
            }
        });

    }

    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait(); //it shows an alert and it will wait for any other thing unless we click it

    }
}
