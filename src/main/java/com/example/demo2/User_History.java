package com.example.demo2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class User_History extends Dashboard{

    BorderPane user_detail_main_pane;
    StackPane top_detail_pane;
    StackPane top_detail_pane_node_holder;
    StackPane bottom_detail_pane;
    StackPane bottom_detail_pane_node_holder;
    ImageView user_image;

    HBox bottom_pane_heading;
    Label name_heading;
    Label category_heading;
    Label extension_heading;

    Label user_detail_name;
    Label subscription;
    Label address_detail;
    Label phone_detail;
    Label storage_space_detail;

    Label collection_lbl;

    HBox[] file_detail;

    Statement stmt;

    User_History() {

        super();
        initializeDB();
        user_detail_main_pane = new BorderPane();
        top_detail_pane = new StackPane();
        top_detail_pane_node_holder = new StackPane();
        bottom_detail_pane = new StackPane();
        bottom_detail_pane_node_holder = new StackPane();
        collection_lbl = new Label(" User History ");

        name_heading = new Label("Filename");
        category_heading = new Label("Category");
        extension_heading = new Label("Extension");

        bottom_pane_heading = new HBox();
        bottom_detail_pane_node_holder.getChildren().add(bottom_pane_heading);

        user_detail_main_pane.setCenter(bottom_detail_pane);
        user_detail_main_pane.setTop(top_detail_pane);

        top_detail_pane.getChildren().add(top_detail_pane_node_holder);
        bottom_detail_pane.getChildren().add(bottom_detail_pane_node_holder);

        user_detail_name = new Label(HelloApplication.user_login_name);
        address_detail = new Label(HelloApplication.user_login_address);
        phone_detail = new Label(HelloApplication.user_login_phone);

        String subscription_detail;
        if(subscribe == 1){
            subscription_detail = "Subscribed";
        }else{
            subscription_detail = "Unsubscribed";
        }

        subscription = new Label(subscription_detail);
        storage_space_detail = new Label(HelloApplication.user_login_storage_allocation + "");

        file_detail = new HBox[200];

    }

    public void user_history_node_design(){

        user_detail_main_pane.setStyle("-fx-background-color: #282c47;");

        user_detail_main_pane.setMaxWidth(1090);
        user_detail_main_pane.setMinWidth(1090);
        user_detail_main_pane.setMinHeight(700);

        top_detail_pane_node_holder.setStyle("-fx-background-color: white; -fx-background-radius: 30; -fx-border-radius: 30;");
        top_detail_pane_node_holder.setMinHeight(220);
        top_detail_pane_node_holder.setMaxWidth(950);
        top_detail_pane_node_holder.setTranslateY(30);

        bottom_detail_pane_node_holder.setMinHeight(220);
        bottom_detail_pane_node_holder.setMaxWidth(850);


        user_image = new ImageView(HelloApplication.user_login_cover_photo);
        user_image.setFitHeight(150);
        user_image.setFitWidth(150);
        user_image.setTranslateX(-320);
        user_image.setTranslateY(-15);

        user_detail_name.setTranslateX(-320);
        user_detail_name.setTranslateY(90);
        user_detail_name.setStyle("-fx-text-fill: #282c47;");
        user_detail_name.setFont(Font.font("New Times Romans", FontWeight.BOLD, 15));


        address_detail.setTranslateX(-100);
        address_detail.setTranslateY(-65);
        address_detail.setStyle("-fx-text-fill: #282c47;");
        address_detail.setFont(Font.font("New Times Romans", FontWeight.BOLD, 17));

        Label l1 = new Label("Address");
        l1.setFont(Font.font("New Times Romans", FontWeight.BOLD, 14));
        l1.setTranslateX(-100);
        l1.setTranslateY(-45);

        phone_detail.setTranslateX(-100);
        phone_detail.setTranslateY(40);
        phone_detail.setStyle("-fx-text-fill: #831542;");
        phone_detail.setFont(Font.font("New Times Romans", FontWeight.BOLD, 17));

        Label l2 = new Label("Phone");
        l2.setFont(Font.font("New Times Romans", FontWeight.BOLD, 14));
        l2.setTranslateX(-100);
        l2.setTranslateY(60);

        storage_space_detail.setTranslateX(140);
        storage_space_detail.setTranslateY(-65);
        storage_space_detail.setStyle("-fx-text-fill: red;");
        storage_space_detail.setFont(Font.font("New Times Romans", FontWeight.BOLD, 17));

        Label l3 = new Label("Storage Left");
        l3.setFont(Font.font("New Times Romans", FontWeight.BOLD, 14));
        l3.setTranslateX(140);
        l3.setTranslateY(-45);


        subscription.setTranslateX(140);
        subscription.setTranslateY(40);
        subscription.setStyle("-fx-text-fill: #13b58e;");
        subscription.setFont(Font.font("New Times Romans", FontWeight.BOLD, 17));

        Label l4 = new Label("Subscription");
        l4.setFont(Font.font("New Times Romans", FontWeight.BOLD, 14));
        l4.setTranslateX(140);
        l4.setTranslateY(60);

        top_detail_pane_node_holder.getChildren().addAll(user_image, user_detail_name, l1, address_detail, l2, phone_detail,
                l3, storage_space_detail, l4, subscription);

        bottom_detail_pane_node_holder.setPadding(new Insets(30));
        name_heading.setStyle("-fx-text-fill: white");
        category_heading.setStyle("-fx-text-fill: white");
        extension_heading.setStyle("-fx-text-fill: white");
        bottom_pane_heading.getChildren().addAll(name_heading, category_heading, extension_heading);
        bottom_pane_heading.setTranslateY(-140);
        bottom_pane_heading.setAlignment(Pos.CENTER);
        bottom_pane_heading.setMaxHeight(50);
        bottom_pane_heading.setSpacing(180);
        bottom_pane_heading.setStyle("-fx-background-color: #c64750;");



    }

    public void setup_file_detail_panes(){

        try {
            String q = "Select * from files where username = '" + HelloApplication.user_login_name + "'";
            ResultSet rs = stmt.executeQuery(q);
            int i = 0;
            int start = -80;
            while (rs.next()) {

                String temp_name = rs.getString("filename");
                if(temp_name.length() > 20){
                    temp_name = temp_name.substring(0, 19);
                }
                Label filename_label = new Label(temp_name);
                Label category_label = new Label(rs.getString("file_category"));
                Label extension_label = new Label(rs.getString("file_extension"));

                file_detail[i] = new HBox();

                filename_label.setStyle("-fx-text-fill: white");
                category_label.setStyle("-fx-text-fill: white");
                extension_label.setStyle("-fx-text-fill: white");

                StackPane stack_1 = new StackPane();
                StackPane stack_2 = new StackPane();
                StackPane stack_3 = new StackPane();

                stack_1.getChildren().add(filename_label);
                stack_1.setMinWidth(120);
                stack_1.setMinWidth(120);
                stack_2.getChildren().add(category_label);
                stack_2.setMinWidth(120);
                stack_2.setMinWidth(120);
                stack_3.getChildren().add(extension_label);
                stack_3.setMinWidth(120);
                stack_3.setMinWidth(120);

                file_detail[i].getChildren().addAll(stack_1, stack_2, stack_3);
                file_detail[i].setTranslateY(start + (i*40));
                file_detail[i].setAlignment(Pos.CENTER);
                file_detail[i].setMaxHeight(30);
                file_detail[i].setSpacing(115);
                file_detail[i].setStyle("-fx-background-color: #13b58e;");
                bottom_detail_pane_node_holder.getChildren().add(file_detail[i]);

                i++;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }



    }

    public void setup_user_detail_nodes(Stage stage){

        panel.setLeft(criteria_box);
        panel.setCenter(central_pane);

        central_pane.setTop(search_box);
        central_pane.setCenter(main_pane);

        main_pane.setStyle("-fx-background-color: white;");
        main_pane.setPadding(new Insets(10));
        main_pane.setTop(main_head);
        main_pane.setCenter(thumbnail_pane);


        main_head.setStyle("-fx-background-color: #ECECF0; -fx-border-color: grey; -fx-border-width: 0.5;");
        main_head.setPadding(new Insets(5));

        scroll_window.setPannable(true);
        scroll_window.setContent(panel);
        scroll_window.setPrefSize(thumbnail_pane.getMaxWidth(), thumbnail_pane.getMaxHeight());

        setup_collection_label();
        create_user_selection_criteria_pane();
        create_top_search_segment();

        user_history_node_design();
        setup_file_detail_panes();

        control_setup(stage);

        main_pane.setCenter(user_detail_main_pane);
        main_pane.setPadding(new Insets(5));
        central_pane.setRight(null);
        central_pane.setBottom(null);

        Scene request_document_scene = new Scene(scroll_window, 1360, 700);
        request_document_scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(request_document_scene);


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
        upload.setOnMouseClicked(e -> {
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
        docsearch.setOnMouseClicked(e -> {
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
        userr.setOnMouseClicked(e -> {
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
            create_thumbnails(search_query);

        });

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










}
