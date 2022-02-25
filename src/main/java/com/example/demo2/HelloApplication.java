package com.example.demo2;
//4b5d67
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;


public class HelloApplication extends Application {

    // scene and stage declaration
    Stage stage = new Stage();
    public static String user_login_name;
    public static String user_login_address;
    public static String user_login_phone;
    public static int subscribe;
    public static int user_login_storage_allocation;
    public static String user_login_cover_photo;

    public static int thumbnail_row_count;
    public static int thumbnail_col_count;
    Statement stmt;
    private final TableView<FileInfo> table = new TableView<FileInfo>();

    private final ObservableList<FileInfo> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws IOException {

        initializeDB();

        Stage login_stage = new Stage();

        StackPane user_login_centre = new StackPane();
        StackPane user_login_node_holder = new StackPane();

        Button login = new Button("Login");
        login.setMinWidth(70);
        login.setMinHeight(30);
        login.setTranslateX(125);
        login.setTranslateY(120);
        login.setStyle("-fx-background-color: yellow; -fx-text-fill: #201a4b;");

        Button sign_in = new Button("Sign in");
        sign_in.setMinWidth(70);
        sign_in.setMinHeight(30);
        sign_in.setTranslateX(50);
        sign_in.setTranslateY(120);
        sign_in.setStyle("-fx-background-color: yellow; -fx-text-fill: #201a4b;");

        Label username_label = new Label("Username");
        username_label.setFont(Font.font("New Times Roman", FontWeight.BOLD,12));
        username_label.setStyle("-fx-text-fill: white;");
        username_label.setTranslateX(-165);
        username_label.setTranslateY(-60);

        TextField username_field = new TextField();
        username_field.setStyle("-fx-background-color: #201a3b; -fx-text-fill: yellow; -fx-border-color:yellow; -fx-border-width: 0.5;");
        username_field.setTranslateX(-20);
        username_field.setTranslateY(-20);
        username_field.setMaxWidth(360);

        Label password_label = new Label("Password");
        password_label.setFont(Font.font("New Times Roman", FontWeight.BOLD,12));
        password_label.setStyle("-fx-text-fill: white;");
        password_label.setTranslateX(-165);
        password_label.setTranslateY(30);

        TextField password_field = new TextField();
        password_field.setStyle("-fx-background-color: #201a3b; -fx-text-fill: yellow; -fx-border-color:yellow; -fx-border-width: 0.5;");
        password_field.setTranslateX(-20);
        password_field.setTranslateY(70);
        password_field.setMaxWidth(360);

        Label title_user_login_page = new Label("SCOUTER");
        title_user_login_page.setFont(new Font("Ferro Rosso", 18));
        title_user_login_page.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        ImageView login_page_logo = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\login_page_logo.png");
        login_page_logo.setFitHeight(30);
        login_page_logo.setFitWidth(30);
        title_user_login_page.setTranslateY(-130);
        title_user_login_page.setGraphic(login_page_logo);

        user_login_node_holder.getChildren().addAll(title_user_login_page, username_label, username_field, password_label, password_field, login, sign_in);
        user_login_node_holder.setStyle("-fx-background-color: #201a4b;");
        user_login_node_holder.setMaxWidth(480);
        user_login_node_holder.setMaxHeight(350);
        ImageView background_img = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\login_page_background.png");
        background_img.setFitWidth(580);
        background_img.setFitHeight(450);

        user_login_centre.getChildren().addAll(background_img,user_login_node_holder);

        Scene login_page_scene = new Scene(user_login_centre, 580, 450);
        login_stage.initStyle(StageStyle.TRANSPARENT);
        login_stage.setResizable(false);
        login_stage.setTitle("Login");
        login_stage.setScene(login_page_scene);
        login_stage.getIcons().add(new Image("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\icon.png"));
        login_stage.show();

        login.setOnAction(e->{

            user_login_name = username_field.getText();

            String username = username_field.getText();
            String password = password_field.getText();

            try{

                String a = "update user set subscription_date = current_date(), amount = amount - 10 " +
                "where ((year(current_date()) - year(subscription_date) > 1) or " +
                        "((year(current_date()) - year(subscription_date) = 1) and month(current_date) > month(subscription_date))) and " +
                "amount >= 10 and subscribed = 0";

                String b = "update user set subscribed = 0 where ((year(current_date()) - year(subscription_date) > 1) or " +
                        "((year(current_date()) - year(subscription_date) = 1) and month(current_date) > month(subscription_date))) and " +
                "amount < 100";

                String c = "update user set subscription_date = current_date(), amount = amount - 10 " +
                "where ((year(current_date()) - year(subscription_date) > 1) or " +
                        "((year(current_date()) - year(subscription_date) = 1) and month(current_date) > month(subscription_date))) and " +
                 " amount >= 10 and subscribed = 1" ;

                stmt.execute(a);
                stmt.execute(b);
                stmt.execute(c);

            }catch(Exception ex){
                ex.printStackTrace();
            }

            try {
                String q = "Select * from user where username = '" + username + "'";
                ResultSet rs = stmt.executeQuery(q);

                while (rs.next()) {

                    String result = rs.getString("passwords");
                    if(password.equals(rs.getString("passwords"))){

                        user_login_address = rs.getString("address");
                        user_login_phone = rs.getString("phone");
                        user_login_cover_photo = rs.getString("cover_photo");
                        user_login_storage_allocation = rs.getInt("storag_left");
                        subscribe = rs.getInt("subscribed");

                        login_stage.close();
                        Dashboard dashboard = new Dashboard();
                        dashboard.set_dashboard_scene(stage);
                        dashboard.create_thumbnails("");

                        stage.setTitle("SCOUTER");
                        stage.getIcons().add(new Image("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\icon.png"));
                        stage.sizeToScene();
                        stage.setResizable(false);
                        stage.show();
                        break;
                    }
                    else{
                        System.out.println("not found");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("error");
            }
        });

        sign_in.setOnAction(e->{

            login_stage.close();
            Signin();

        });

    }

    public void Signin()
    {
        Stage signstage = new Stage();

        StackPane signpane = new StackPane();
        Scene signscene = new Scene(signpane,700,650);

        ImageView sign_in_img = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\sign_in_background.png");
        sign_in_img.setFitWidth(700);
        sign_in_img.setFitHeight(650);
        signpane.getChildren().add(sign_in_img);

        Label title_user_sign_page = new Label("SCOUTER");
        title_user_sign_page.setFont(new Font("Ferro Rosso", 18));
        title_user_sign_page.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        title_user_sign_page.setTranslateY(-250);

        Label username_l1 = new Label("Username");
        username_l1.setFont(Font.font("New Times Roman", FontWeight.BOLD, 12));
        username_l1.setStyle("-fx-text-fill: white");
        Label password_l2 = new Label("Password");
        password_l2.setFont(Font.font("New Times Roman", FontWeight.BOLD, 12));
        password_l2.setStyle("-fx-text-fill: white");
        Label address_l3 = new Label("Address");
        address_l3.setFont(Font.font("New Times Roman", FontWeight.BOLD, 12));
        address_l3.setStyle("-fx-text-fill: white");
        Label phone_numberl4 = new Label("Phone Number");
        phone_numberl4.setFont(Font.font("New Times Roman", FontWeight.BOLD, 12));
        phone_numberl4.setStyle("-fx-text-fill: white");
        Label subscription_l5 = new Label("Subscription");
        subscription_l5.setFont(Font.font("New Times Roman", FontWeight.BOLD, 12));
        subscription_l5.setStyle("-fx-text-fill: white");

        Button b1 = new Button("Upload Profile Picture");
        b1.setMinWidth(100);
        b1.setMinHeight(40);
        b1.setTranslateY(155);
        b1.setTranslateX(110);
        b1.setStyle("-fx-background-color: #001220; -fx-text-fill: yellow; -fx-border-color: yellow; -fx-border-width: 1; -fx-border-radius: 10;");

        Button sign_in_submit = new Button("Submit");
        sign_in_submit.setMinWidth(100);
        sign_in_submit.setMinHeight(40);
        sign_in_submit.setTranslateY(245);
        sign_in_submit.setStyle("-fx-background-color: #001220; -fx-text-fill: yellow; -fx-border-color: yellow; -fx-border-width: 1; -fx-border-radius: 10;");

        TextField username_text1 = new TextField();
        TextField password_text2 = new TextField();
        TextField address_text3 = new TextField();
        TextField phone_num_text4 = new TextField();

        username_l1.setTranslateX(-150);
        username_l1.setTranslateY(-200);

        password_l2.setTranslateX(-150);
        password_l2.setTranslateY(-120);

        address_l3.setTranslateX(-150);
        address_l3.setTranslateY(-40);

        phone_numberl4.setTranslateX(-130);
        phone_numberl4.setTranslateY(40);

        subscription_l5.setTranslateX(-140);
        subscription_l5.setTranslateY(120);

        username_text1.setMaxWidth(350);
        password_text2.setMaxWidth(350);
        address_text3.setMaxWidth(350);
        phone_num_text4.setMaxWidth(350);

        username_text1.setTranslateY(-170);
        password_text2.setTranslateY(-90);
        address_text3.setTranslateY(-10);
        phone_num_text4.setTranslateY(70);


        final ComboBox signComboBox = new ComboBox();
        signComboBox.getItems().addAll(
                "Subscription (10$/year)",
                "No subscription"
        );

        signComboBox.setTranslateY(155);
        signComboBox.setTranslateX(-100);
        signComboBox.setMaxWidth(150);

        signpane.getChildren().addAll(title_user_sign_page,username_l1,password_l2,address_l3,phone_numberl4,subscription_l5,b1);
        signpane.getChildren().addAll(username_text1,password_text2,address_text3,phone_num_text4, sign_in_submit);
        signpane.getChildren().add(signComboBox);

        signstage.setScene(signscene);
        signstage.setTitle("Sign In");
        signstage.setResizable(false);
        signstage.show();


        signComboBox.setOnAction((event) -> {

            Object selectedItem = signComboBox.getSelectionModel().getSelectedItem();
            if(selectedItem.toString() == "Subscription (10$/year)"){
                subscribe = 1;
            }
            else{
                subscribe = 0;
            }
        });

        b1.setOnAction(handle->{

            FileChooser pic=new FileChooser();
            pic.selectedExtensionFilterProperty();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files","*.jpg","*.png","*.jpeg","*.gif");
            pic.setTitle("Upload your cover");
            pic.getExtensionFilters().add(extFilter);
            File profile= pic.showOpenDialog(null);

            String profilepic = String.valueOf(profile);
            profilepic = profilepic.replaceAll("\\\\", "\\\\\\\\");
            System.out.println(profilepic);
            if(profilepic == null){
                user_login_cover_photo = "C:\\Users\\HP\\IdeaProjects\\demo2\\src\\user_pic.png";
            }
            else{
                user_login_cover_photo = profilepic;
            }
            System.out.println("The path of the profile photo is: " + profilepic);
        });

        sign_in_submit.setOnAction(e->{

            user_login_name = username_text1.getText();
            user_login_address = address_text3.getText();
            user_login_phone = phone_num_text4.getText();

            String username = username_text1.getText();
            String password = password_text2.getText();
            String phone_num = phone_num_text4.getText();
            String address = address_text3.getText();

            if(subscribe == 0){
                subscribe = 0;
            }
            else{
                subscribe = 1;
            }

            LocalDate now = LocalDate.now();

            if(user_login_cover_photo == null){
                    user_login_cover_photo = "C:\\\\Users\\\\HP\\\\IdeaProjects\\\\demo2\\\\src\\\\user_pic.png";
            }
            int amount = 1024*1024;
            if(subscribe == 1){
                amount = amount * 5;
            }
            user_login_storage_allocation = amount;
            try {
                String q = "insert into user(username, passwords, subscribed, storag_left, subscription_date, phone, address, amount, cover_photo) " +
                        "values ('" + username + "', '" + password + "', '" + subscribe + "', " + amount + ", '" + now + "', " +
                        "" + phone_num + ", '" + address + "', " + 950 + ", '" + user_login_cover_photo + "')";
                stmt.execute(q);
                System.out.println(q);
                signstage.close();

                Dashboard dashboard = new Dashboard();

                dashboard.set_dashboard_scene(stage);
                stage.setTitle("SCOUTER");
                stage.getIcons().add(new Image("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\icon.png"));
                stage.sizeToScene();
                stage.setResizable(false);
                stage.show();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    }

    public void initializeDB(){
        try{
            // load jdbc driver
            Class.forName("com.mysql.jdbc.Driver");
            // establish connection with a database
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/file_management", "root", "Orpheus@1");
            // create a statement
            stmt = con.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}


class Dashboard extends HelloApplication{

    // dashboard scene
    Scene dashboard_scene;
    Statement stmt;

    // main nodes for containing nodes
    BorderPane panel;
    BorderPane central_pane;
    VBox criteria_box;
    HBox search_box;
    HBox main_head;
    BorderPane main_pane;
    GridPane thumbnail_pane;
    ScrollPane scroll_window;

    // search bar nodes
    TextField search_bar;
    Button search_button;

    // menu options nodes
    Button dashboard_button;
    Button User_Record;
    Button Upload_Files_button;
    Button document_Search_button;
    Button History;

    // top left user details
    Label telephone;
    Label Address;

    //top right user label and button
    Button top_user;
    Label u_name;

    // three lines collection symbol and label
    Button collection_btn;
    Label collection_lbl;

    // thumbnails nodes
    VBox[][] tn_grid;
    ImageView[][] tn1_img;
    Label[][] tn_lbl;
    Button[][] tn_like_btn;
    HBox[][] tn_pane_lbl;
    Rectangle sep[];

    // side menu options containers
    HBox his_search;
    HBox upload;
    HBox docsearch;
    HBox userr;
    HBox dashbox;

    // thumbnail count
    static int tn = 0;

    // top left user introduction detail segement
    GridPane intro_detail;
    HBox user_intro;

    // menu chosen presently
    int menu_option = 0;


    Dashboard(){

        super();
        initializeDB();
        // main nodes for containing nodes
        panel = new BorderPane();
        central_pane = new BorderPane();
        criteria_box = new VBox();
        search_box = new HBox();
        main_head = new HBox();
        main_pane = new BorderPane();
        thumbnail_pane = new GridPane();
        scroll_window = new ScrollPane();

        // search bar nodes
        search_bar = new TextField();
        search_button = new Button();

        // menu options nodes
        dashboard_button = new Button(" Dashboard \t\t\t");
        User_Record = new Button(" User Detail \t\t\t");
        Upload_Files_button = new Button(" Upload Files\t\t\t");
        document_Search_button = new Button(" Request Document\t\t");
        History = new Button(" History\t\t\t\t");

        // top left user details
        if(HelloApplication.user_login_phone == null || HelloApplication.user_login_phone == " "){
            HelloApplication.user_login_phone = "  ---";
        }
        if(HelloApplication.user_login_phone.length() > 12){
            HelloApplication.user_login_phone = HelloApplication.user_login_phone.substring(0, 11) + "...";
        }
        if(HelloApplication.user_login_address == null || HelloApplication.user_login_address == " "){
            HelloApplication.user_login_address = "  ---";
        }
        if(HelloApplication.user_login_address.length() > 25){
            HelloApplication.user_login_address = HelloApplication.user_login_address.substring(0, 23) + "..";
        }
        telephone = new Label(HelloApplication.user_login_phone);
        Address = new Label(HelloApplication.user_login_address);

        //top right user label and button
        top_user = new Button();
        u_name = new Label(HelloApplication.user_login_name);

        // three lines collection symbol and label
        collection_btn = new Button();
        collection_lbl = new Label(" Collection ");


        // thumbnails nodes
        thumbnail_pane = new GridPane();
        tn_grid = new VBox[5][4];
        tn1_img = new ImageView[5][4];
        tn_lbl = new Label[5][4];
        tn_pane_lbl = new HBox[5][4];
        tn_like_btn = new Button[5][4];
        sep = new Rectangle[100];

        // side menu options containers
        his_search = new HBox();
        upload = new HBox();
        docsearch = new HBox();
        userr = new HBox();
        dashbox = new HBox();

        // thumbnail count
        tn = 0;

        // top left user introduction detail segement
        intro_detail = new GridPane();
        user_intro = new HBox();

    }

    public void initializeDB(){
        try {
            // load jdbc driver
            Class.forName("com.mysql.jdbc.Driver");
            // establish connection with a database
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/file_management", "root", "Orpheus@1");
            // create a statement
            stmt = con.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void control_setup(Stage stage){

        dashboard_button.setOnAction(e->{
            Dashboard new_dashboard = new Dashboard();
            menu_option = 0;
            new_dashboard.create_thumbnails("");
            new_dashboard.set_dashboard_scene(stage);


        });

        document_Search_button.setOnAction(e->{
            muneeb request = new muneeb();
            menu_option = 1;
            request.set_request_document_scene(stage);

        });

        Upload_Files_button.setOnAction(e->{
            saad upload = new saad();
            menu_option = 2;
            upload.setup_upload_doc_scene(stage);
        });

        User_Record.setOnAction(e->{
            User_History history = new User_History();
            history.setup_user_detail_nodes(stage);
        });

        search_button.setOnAction(e->{
            String search_query = search_bar.getText();
            Dashboard new_dashboard = new Dashboard();
            new_dashboard.set_dashboard_scene(stage);
            new_dashboard.create_thumbnails(search_query);

        });

    }


    // p is the path of the file and this file is opened by openFile if it exists
    public static void openFile(String p) {
        // the path provided should have double '\\' e.g: c:\\Desktop\\Computer\\file.txt
        try {
            File file = new File(p);
            // check if Desktop is supported
            if (!Desktop.isDesktopSupported()) {
                System.out.println("Desktop not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            System.out.println("desktop");
            if (file.exists()) {  // check if file exists
                desktop.open(file); // open the file
                System.out.println("yes");
            }
            else{
                System.out.println("file does not exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void create_thumbnails(String filename_constraint) {

        tn_grid = new VBox[5][4];
        tn1_img = new ImageView[5][4];
        tn_lbl = new Label[5][4];
        tn_pane_lbl = new HBox[5][4];
        tn_like_btn = new Button[5][4];
        sep = new Rectangle[100];

        try {
            String q = "select filename, file_path, cover_page, liked from files where username like '"+ user_login_name +
                    "' and filename like '%" + filename_constraint + "%'";
            ResultSet tn_rs = stmt.executeQuery(q);
            int result_count = 0;
            int i = 0, j = 0;
            while(tn_rs.next()){

                result_count++;
                String queried_filename = tn_rs.getString("filename");
                String file_path = tn_rs.getString("file_path");
                String liked_status = tn_rs.getString("liked");

                try {
                    tn1_img[i][j] = new ImageView(tn_rs.getString("cover_page"));
                }
                catch(Exception ex){
                    tn1_img[i][j] = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\default_image.png");
                }
                tn_like_btn[i][j] = new Button();
                tn_like_btn[i][j].setMaxHeight(20);
                tn_like_btn[i][j].setMaxWidth(20);
                tn_like_btn[i][j].setStyle("-fx-border-color: transparent; -fx-border-width: 0;-fx-background-color: transparent;");

                sep[i] = new Rectangle(0.5, 30);
                sep[i].setStroke(Color.GRAY);

                if(liked_status != null && liked_status.equals("1")) {
                    ImageView temp = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\like_btn_after.png");
                    temp.setFitHeight(20);
                    temp.setFitWidth(20);
                    tn_like_btn[i][j].setGraphic(temp);
                }
                else{
                    ImageView temp = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\like_btn.png");
                    temp.setFitHeight(20);
                    temp.setFitWidth(20);
                    tn_like_btn[i][j].setGraphic(temp);
                }

                int finalI = i;
                int finalJ = j;
                String finalQueried_filename = queried_filename;
                tn_like_btn[i][j].setOnAction(lb->{
                    ImageView temp_new = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\like_btn_after.png");
                    temp_new.setFitHeight(20);
                    temp_new.setFitWidth(20);
                    tn_like_btn[finalI][finalJ].setGraphic(temp_new);
                    try {

                        String m = "Update files set liked = '1' where filename = '" + finalQueried_filename +
                                "' and username = '" + user_login_name + "'";
                        stmt.execute(m);
                    }
                    catch(SQLException ex){
//                        System.out.println("Like document error");
                        ex.printStackTrace();
                    }
                });

                tn_pane_lbl[i][j] = new HBox();
                if(queried_filename.length() > 20){
                    queried_filename = queried_filename.substring(0, 19);
                }
                tn_lbl[i][j] = new Label(queried_filename);
                tn_lbl[i][j].setAlignment(Pos.CENTER);
                tn_lbl[i][j].setFont(Font.font("Times New Roman", 12));
                tn_lbl[i][j].setMinWidth(135);
                tn_lbl[i][j].setMinHeight(30);
                tn_pane_lbl[i][j].setPadding(new Insets(5));
                tn_pane_lbl[i][j].setStyle("-fx-border-width: 0.5; -fx-border-color: grey; -fx-background-color: #EFEFF0;");
                tn_pane_lbl[i][j].getChildren().addAll(tn_lbl[i][j], sep[i], tn_like_btn[i][j]);
                tn1_img[i][j].setFitWidth(185);
                tn1_img[i][j].setFitHeight(185);
                tn_grid[i][j] = new VBox();
                tn_grid[i][j].setPadding(new Insets(10));
                tn_grid[i][j].getChildren().addAll(tn1_img[i][j], tn_pane_lbl[i][j]);

                tn1_img[i][j].setOnMouseClicked(kk -> {
                    System.out.println(file_path);
                    openFile(file_path);
                });

                thumbnail_pane.add(tn_grid[i][j], i, j);
                i++;
                if(i % 5 == 0){
                    i = 0;
                    j++;
                }
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void create_history_menu_option() {

        History.setStyle("-fx-background-color: #333350; -fx-text-fill: white;");
        History.getStyleClass().setAll("btn", "btn-md");
        ImageView history_icon = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\history.png");
        history_icon.setFitWidth(25);
        history_icon.setFitHeight(25);
        his_search.setPadding(new Insets(15));
        his_search.getChildren().add(History);
        his_search.setOnMouseEntered(e -> {
            History.setStyle("-fx-background-color: #006eff; -fx-text-fill: white;");
        });
        his_search.setOnMouseExited(e -> {
            History.setStyle("-fx-background-color: #333350; -fx-text-fill: white;");
        });
        History.setGraphic(history_icon);
    }


    public void create_upload_file_menu_option() {

        Upload_Files_button.setStyle("-fx-background-color: #333350; -fx-text-fill: white;");
        Upload_Files_button.getStyleClass().setAll("btn", "btn-md");
        ImageView upload_icon = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\upload.png");
        upload_icon.setFitWidth(25);
        upload_icon.setFitHeight(25);
        upload.setPadding(new Insets(15));
        upload.getChildren().add(Upload_Files_button);
        upload.setOnMouseEntered(e -> {
            Upload_Files_button.setStyle("-fx-background-color: #006eff; -fx-text-fill: white;");
        });
        upload.setOnMouseExited(e -> {
            Upload_Files_button.setStyle("-fx-background-color: #333350; -fx-text-fill: white;");
        });
        upload.setOnMouseClicked(e->{
            Upload_Files_button.setStyle("-fx-background-color: #006eff; -fx-text-fill: white;");
        });
        Upload_Files_button.setGraphic(upload_icon);
    }


    public void create_document_search_menu_option() {

        document_Search_button.setStyle("-fx-background-color: #333350; -fx-text-fill: white;");
        document_Search_button.getStyleClass().setAll("btn", "btn-md");
        ImageView docsearch_icon = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\searchd.png");
        docsearch_icon.setFitWidth(25);
        docsearch_icon.setFitHeight(25);
        docsearch.setPadding(new Insets(15));
        docsearch.getChildren().add(document_Search_button);
        docsearch.setOnMouseEntered(e -> {
            document_Search_button.setStyle("-fx-background-color: #006eff; -fx-text-fill: white;");
        });
        docsearch.setOnMouseExited(e -> {
            document_Search_button.setStyle("-fx-background-color: #333350; -fx-text-fill: white;");
        });
        docsearch.setOnMouseClicked(e->{
            document_Search_button.setStyle("-fx-background-color: #006eff; -fx-text-fill: white;");
        });
        document_Search_button.setGraphic(docsearch_icon);
    }


    public void create_user_record_menu_option() {

        User_Record.setStyle("-fx-background-color: #333350; -fx-text-fill: white;");
        User_Record.getStyleClass().setAll("btn", "btn-md");
        ImageView userr_icon = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\user_admin.png");
        userr_icon.setFitWidth(25);
        userr_icon.setFitHeight(25);
        userr.setPadding(new Insets(15));
        userr.getChildren().add(User_Record);
        userr.setOnMouseEntered(e -> {
            User_Record.setStyle("-fx-background-color: #006eff; -fx-text-fill: white;");
        });
        userr.setOnMouseExited(e -> {
            User_Record.setStyle("-fx-background-color: #333350; -fx-text-fill: white;");
        });
        userr.setOnMouseClicked(e->{
            User_Record.setStyle("-fx-background-color: #006eff; -fx-text-fill: white;");
        });
        User_Record.setGraphic(userr_icon);
    }


    public void create_dashboard_menu_option() {

        dashboard_button.setStyle("-fx-background-color: #333350; -fx-text-fill: white;");
        dashboard_button.getStyleClass().setAll("btn", "btn-md");
        ImageView DASH_icon = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\dashboard.png");
        DASH_icon.setFitWidth(25);
        DASH_icon.setFitHeight(25);
        dashbox.setPadding(new Insets(15));
        dashbox.getChildren().add(dashboard_button);
        dashbox.setOnMouseEntered(e -> {
            dashboard_button.setStyle("-fx-background-color: #006eff; -fx-text-fill: white;");
        });
        dashbox.setOnMouseExited(e -> {
            dashboard_button.setStyle("-fx-background-color: #333350; -fx-text-fill: white;");
        });
        dashbox.setOnMouseClicked(e->{
            dashboard_button.setStyle("-fx-text-fill: white; -fx-border-width: 1.0; -fx-border-color: #006eff;");
        });
        dashboard_button.setGraphic(DASH_icon);
    }


    public void create_user_intro_segment() {

        StackPane s_pane = new StackPane();
        Circle circle = new Circle(25);
        ImageView subscribers = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\subscriber.png");
        subscribers.setFitWidth(35);
        subscribers.setFitHeight(35);
        s_pane.getChildren().addAll(circle, subscribers);
        user_intro.setPadding(new Insets(15));

        intro_detail.setPadding(new Insets(5));

        ImageView tel_icon = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\telephone.png");
        tel_icon.setFitWidth(15);
        tel_icon.setFitHeight(15);

        ImageView loc_icon = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\location1.png");
        loc_icon.setFitWidth(15);
        loc_icon.setFitHeight(15);

        Address.setStyle("-fx-text-fill: white;");
        telephone.setStyle("-fx-text-fill: white;");

        intro_detail.add(tel_icon, 0, 0);
        intro_detail.add(telephone, 1, 0);
        intro_detail.add(Address, 1, 1);
        intro_detail.add(loc_icon, 0, 1);

        user_intro.getChildren().addAll(s_pane, intro_detail);
    }


    public void create_top_search_segment() {

        search_bar.setPrefColumnCount(45);
        search_bar.setPrefHeight(38);

        ImageView search_icon = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\search.png");
        search_icon.setFitWidth(25);
        search_icon.setFitHeight(25);

        search_button.setGraphic(search_icon);
        search_button.setMaxWidth(25);
        search_button.setMaxHeight(25);
        search_button.setStyle("-fx-background-color: #ffffff; -fx-border-width: 2; -fx-border-color: #ffffff;");

        final Pane spacerH = new Pane();
        HBox.setHgrow(spacerH, Priority.ALWAYS);
        spacerH.setMinSize(9, 1);

        ImageView top_user_logo = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\top_user.png");
        top_user.setGraphic(top_user_logo);
        top_user.setStyle("-fx-background-color: #333350; -fx-text-fill: white;");
        top_user_logo.setFitHeight(27);
        top_user_logo.setFitWidth(30);

        u_name.setStyle("-fx-text-fill: white;");
        u_name.setFont(Font.font("Times New Roman", 14));
        u_name.setAlignment(Pos.CENTER);
        u_name.setMinHeight(36);
        u_name.setMinWidth(60);
        u_name.setText("_" + user_login_name + "_  ");

        search_box.setStyle("-fx-background-color: #333645;");
        search_box.getChildren().addAll(search_bar, search_button, spacerH, top_user, u_name);
    }


    public void setup_collection_label() {

        ImageView collection_icon = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\collection_icon.png");
        collection_btn.setStyle("-fx-background-color: #D3D3D3;");
        collection_icon.setFitHeight(15);
        collection_icon.setFitWidth(15);
        collection_btn.setGraphic(collection_icon);

        Rectangle sep_line = new Rectangle(0.5, 30);
        sep_line.setStroke(Color.GRAY);
        collection_lbl.setFont(Font.font("Times New Roman", 16));
        main_head.setAlignment(Pos.CENTER_LEFT);
        main_head.setSpacing(5);
        main_head.getChildren().addAll(collection_btn, sep_line, collection_lbl);
    }


    public void create_user_selection_criteria_pane() {

        criteria_box.setStyle("-fx-background-color: #333350;");

        // header name symbol
        Button button = new Button(" SCOUTER\t\t\t\t");
        button.getStyleClass().setAll("btn", "btn-md");
        ImageView img_head_icon = new ImageView("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\icon_1.png");
        img_head_icon.setFitWidth(20);
        img_head_icon.setFitHeight(20);
        button.setGraphic(img_head_icon);
        button.setStyle("-fx-font-weight: bold; -fx-background-color: #333645; -fx-text-fill: white; -fx-border-color: #202332; -fx-border-width: 3;");

        create_user_intro_segment();

        HBox name_lbl = new HBox();
        name_lbl.setAlignment(Pos.CENTER);
        name_lbl.setPadding(new Insets(6));
        name_lbl.getChildren().add(u_name);

        create_dashboard_menu_option();
        create_user_record_menu_option();
        create_upload_file_menu_option();
        create_document_search_menu_option();
        create_history_menu_option();

        HBox team = new HBox();
        team.setPadding(new Insets(30));
        team.setStyle("-fx-background-color: #333350;");
        Button add_team = new Button("Add Teammates");
        team.setAlignment(Pos.CENTER);
        add_team.getStyleClass().setAll("btn", "btn-lg");
        add_team.setStyle("-fx-background-color: #006eff; -fx-text-fill: white;");
        team.getChildren().addAll(add_team);

        final Pane spacerV = new Pane();
        VBox.setVgrow(spacerV, Priority.ALWAYS);
        spacerV.setMinSize(5, 1);
        spacerV.setStyle("-fx-background-color: #333350;");

        criteria_box.getChildren().addAll(button, user_intro, name_lbl, dashbox, userr, upload, docsearch, team, spacerV);

    }


    public void set_dashboard_scene(Stage stage) {

        panel.setLeft(criteria_box);
        panel.setCenter(central_pane);

        central_pane.setTop(search_box);
        central_pane.setCenter(main_pane);

        main_pane.setStyle("-fx-background-color: white;");
        main_pane.setPadding(new Insets(10));
        main_pane.setMinWidth(1090);
        main_pane.setTop(main_head);
        main_pane.setCenter(thumbnail_pane);

        main_head.setStyle("-fx-background-color: #ECECF0; -fx-border-color: grey; -fx-border-width: 0.5;");
        main_head.setPadding(new Insets(5));

        thumbnail_pane.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-width: 0.5;");
        thumbnail_pane.setMinWidth(1080);
        thumbnail_pane.setMaxWidth(1080);
        thumbnail_pane.setMinHeight(600);
        thumbnail_pane.setPadding(new Insets(25));

        scroll_window.setPannable(true);
        scroll_window.setContent(panel);
        scroll_window.setPrefSize(thumbnail_pane.getMaxWidth(), thumbnail_pane.getMaxHeight());

        setup_collection_label();
        create_user_selection_criteria_pane();
        create_top_search_segment();
        control_setup(stage);

        Scene dashboard_scene = new Scene(scroll_window, 1360, 700);
        dashboard_scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(dashboard_scene);

    }

}