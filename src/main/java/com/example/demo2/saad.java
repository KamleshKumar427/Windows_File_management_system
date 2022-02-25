package com.example.demo2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

class saad extends Dashboard {


    public static void main(String[] args) {
        launch(args);
    }

    HBox upload_doc_main_pane = new HBox();
    StackPane upload_doc_pane;
    Button graphic_button;
    Button single_file_upload_btn;
    Button submit_button;
    Stage file_choose_stage;
    GridPane file_chooser_stage_grid;
    Label collection_lbl;
    Label label1;
    Label label2;
    Scene scene2_upload_doc;

    HBox content_upload;
    VBox content_name_container;
    VBox content_date_container;
    VBox content_category_container;
    VBox content_extention_container;
    HBox name_label;
    HBox date_label;
    HBox category_label;
    HBox extention_label;
    StackPane name_content;
    StackPane date_content;
    StackPane category_content;
    StackPane extention_content;

    Label content_name_label;
    Label content_category_label;
    Label content_extention_label;
    Label content_date_label;

    Label Add_Doc;
    Label file_name_label;

    saad(){

        super();
        initializeDB();
        upload_doc_pane = new StackPane();
        graphic_button = new Button();
        single_file_upload_btn = new Button("Upload File");
        file_choose_stage = new Stage();
        file_chooser_stage_grid = new GridPane();
        collection_lbl = new Label("Upload Documents");
        label1 = new Label("    File Name    ");
        label2 = new Label( "    Category    ");
        content_upload = new HBox();

        content_upload = new HBox();
        content_name_container = new VBox();
        content_date_container = new VBox();
        content_category_container = new VBox();
        content_extention_container = new VBox();
        name_label = new HBox();
        date_label = new HBox();
        category_label = new HBox();
        extention_label = new HBox();
        name_content = new StackPane();
        date_content = new StackPane();
        category_content = new StackPane();
        extention_content = new StackPane();

        Add_Doc = new Label("Add Documents");

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

    public void setup_upload_doc_scene(Stage stage){

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

        setup_upload_doc_panes();
        upload_screen_controls();
        control_setup(stage);

        main_pane.setLeft(upload_doc_main_pane);
        main_pane.setPadding(new Insets(5));
        main_pane.setBottom(null);
        main_pane.setRight(null);

        upload_doc_pane.setMinHeight(600);
        upload_doc_pane.setMinWidth(1090);

        upload_doc_main_pane.setMinWidth(1090);
        upload_doc_main_pane.setMinHeight(600);

        upload_doc_main_pane.getChildren().addAll(upload_doc_pane);

        upload_doc_main_pane.setStyle("-fx-border-color: grey; -fx-border-width: 0.5;");

        Scene upload_doc_scene = new Scene(scroll_window, 1360, 700);
        upload_doc_scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(upload_doc_scene);

    }

    public void setup_upload_doc_panes(){

        Add_Doc.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD,22));
        Add_Doc.setTranslateX(-450);
        Add_Doc.setTranslateY(-140);

        content_upload.setMaxWidth(1060);
        content_upload.setMaxHeight(100);
        content_upload.setTranslateX(-5);
        content_upload.setTranslateY(-50);
        content_upload.setStyle("-fx-border-color: grey; -fx-border-width: 0.5;");

        content_name_container.setStyle("-fx-border-color: black; -fx-border-width: 0.5;");
        name_content.setAlignment(Pos.CENTER);
        name_content.setMinWidth(280);
        name_content.setMinHeight(70);

        Label name = new Label("Filename");
        name_label.setMinWidth(280);
        name_label.setMinHeight(30);
        name_label.getChildren().add(name);
        name_label.setStyle("-fx-background-color: #ECECF0;");
        name_label.setAlignment(Pos.CENTER);

        content_name_container.getChildren().addAll(name_label, name_content);


        content_extention_container.setStyle("-fx-border-color: black; -fx-border-width: 0.5;");
        extention_content.setAlignment(Pos.CENTER);
        extention_content.setMinWidth(260);
        extention_content.setMinHeight(70);

        Label extention = new Label("Extention");
        extention_label.setMinWidth(260);
        extention_label.setMinHeight(30);
        extention_label.getChildren().add(extention);
        extention_label.setStyle("-fx-background-color: #ECECF0;");
        extention_label.setAlignment(Pos.CENTER);

        content_extention_container.getChildren().addAll(extention_label, extention_content);


        content_date_container.setStyle("-fx-border-color: black; -fx-border-width: 0.5;");
        date_content.setAlignment(Pos.CENTER);
        date_content.setMinWidth(260);
        date_content.setMinHeight(70);

        Label date = new Label("Date");
        date_label.setMinWidth(260);
        date_label.setMinHeight(30);
        date_label.getChildren().add(date);
        date_label.setAlignment(Pos.CENTER);
        date_label.setStyle("-fx-background-color: #ECECF0;");

        content_date_container.getChildren().addAll(date_label, date_content);



        content_category_container.setStyle("-fx-border-color: black; -fx-border-width: 0.5;");
        category_content.setAlignment(Pos.CENTER);
        category_content.setMinWidth(260);
        category_content.setMinHeight(70);

        Label category = new Label("Category");
        category_label.setMinWidth(260);
        category_label.setMinHeight(30);
        category_label.getChildren().add(category);
        category_label.setAlignment(Pos.CENTER);
        category_label.setStyle("-fx-background-color: #ECECF0;");

        content_category_container.getChildren().addAll(category_label, category_content);

        content_upload.getChildren().addAll(content_name_container, content_extention_container, content_date_container, content_category_container);


        single_file_upload_btn.setMinWidth(150);
        single_file_upload_btn.setMinHeight(40);
        single_file_upload_btn.setTranslateX(0);
        single_file_upload_btn.setTranslateY(180);

        upload_doc_pane.getChildren().addAll(Add_Doc, content_upload, single_file_upload_btn);

        single_file_upload_btn.getStyleClass().setAll("btn", "btn-lg");
        single_file_upload_btn.setStyle("-fx-background-color: #006eff; -fx-text-fill: white;");

        file_choose_stage.setTitle("Uploading");
        label1.setFont(Font.font("Times New Roman", 14));
        label2.setFont(Font.font("Times New Roman", 14));

        file_chooser_stage_grid.setPadding(new Insets(20));

        file_chooser_stage_grid.add(label1, 2, 2);
        file_chooser_stage_grid.add(label2, 2, 4);

        scene2_upload_doc = new Scene(file_chooser_stage_grid,380,180);
        scene2_upload_doc.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        file_choose_stage.setTitle("Document Detail");
        file_choose_stage.setScene(scene2_upload_doc);
        file_choose_stage.setResizable(false);

    }

    public void upload_screen_controls(){

        single_file_upload_btn.setOnAction(e-> {
            FileChooser fc = new FileChooser();
            fc.selectedExtensionFilterProperty();
            fc.setTitle("Upload your files");

            File file = fc.showOpenDialog(stage);

            name_content.getChildren().clear();
            date_content.getChildren().clear();
            category_content.getChildren().clear();
            extention_content.getChildren().clear();
            file_chooser_stage_grid.getChildren().remove(file_name_label);

            try {

                String filename = String.valueOf(file);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String temp1 = file.getName().toString();
                String file_name_temp = " ";
                String[] Extension = temp1.split("\\.");
                if(file.getName().length() > 20){
                    file_name_temp = file.getName().substring(0, 19);
                    file_name_label = new Label(file_name_temp + "..");
                }
                else {
                    file_name_temp = file.getName();
                    file_name_label = new Label(file_name_temp);
                }
                file_name_label.setMinWidth(100);
                file_chooser_stage_grid.add(file_name_label, 3,2);

                TextField field = new TextField();
                field.setMinWidth(150);
                field.setStyle("-fx-border-color: grey; -fx-border-width: 0.5;");
                file_chooser_stage_grid.add(field, 3, 4);

                submit_button = new Button("Submit");
                file_chooser_stage_grid.add(submit_button, 2, 4);
                submit_button.setTranslateX(120);
                submit_button.setTranslateY(100);
                submit_button.getStyleClass().addAll("btn", "btn-md", "btn-success");
                file_choose_stage.show();
                submit_button.setOnAction(l-> {

                    String Category = field.getText();
                    content_name_label = new Label(Extension[0]);
                    content_date_label = new Label(dateFormat.format(file.lastModified()));
                    content_extention_label = new Label(Extension[Extension.length - 1]);
                    content_category_label = new Label(Category);

                    name_content.getChildren().add(content_name_label);
                    date_content.getChildren().add(content_date_label);
                    extention_content.getChildren().add(content_extention_label);
                    category_content.getChildren().add(content_category_label);

                    Label cover = new Label("Do you want to upload a cover photo for the file? ");
                    cover.setFont(Font.font("Times New Roman", 14));
                    cover.setTranslateX(70);
                    cover.setTranslateY(40);

                    AnchorPane cpane = new AnchorPane();
                    AnchorPane.setTopAnchor(cover, 20.0);
                    cpane.getChildren().add(cover);

                    Scene coverscene = new Scene(cpane, 475, 200);
                    Stage cstage = new Stage();
                    cstage.setScene(coverscene);
                    cstage.setTitle("Cover photo");
                    cstage.getIcons().add(new Image("C:\\Users\\HP\\IdeaProjects\\demo2\\src\\icon.png"));
                    cstage.setResizable(false);
                    cstage.show();

                    Button yes = new Button("YES");
                    Button no = new Button("NO");

                    yes.setMinWidth(120);
                    yes.setMinHeight(30);
                    yes.setTranslateX(100);
                    yes.setTranslateY(100);

                    no.setMinWidth(120);
                    no.setMinHeight(30);
                    no.setTranslateX(250);
                    no.setTranslateY(100);

                    cpane.getChildren().add(yes);
                    cpane.getChildren().add(no);

                    no.setOnAction(n -> {
                        cstage.close();
                        file_choose_stage.close();

                        String pathstr = file.getAbsolutePath();
                        pathstr = pathstr.replaceAll("\\\\", "\\\\\\\\");
                        System.out.println(pathstr);
                        System.out.println(user_login_name);

                        int size = (int) (file.length()/1024);
                        System.out.println("The file size is: " + size);
                        try {
                            String q = "insert into files(username, filename, file_category, file_extension, date_created, file_path) " +
                                    "VALUES('" + user_login_name + "', '"+ Extension[0] + "', '" +
                                    Category + "', '" + Extension[Extension.length-1] + "', '" + dateFormat.format(file.lastModified())+"', '" +
                                     pathstr + "')";

                            stmt.execute(q);

                        }
                        catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        try {

                            String p = "select storag_left from user where username = '" + user_login_name + "'";
                            ResultSet rest = stmt.executeQuery(p);
                            String storage = "";
                            while(rest.next()){
                                storage = rest.getString("storag_left");
                            }
                            int storage_space = Integer.valueOf(storage) - size;
                            String q = "update user set storag_left = " + storage_space + " where username = '" +
                                    user_login_name + "'";
                            stmt.execute(q);
                        }
                        catch (SQLException exy) {
                            exy.printStackTrace();
                        }
                    });

                    yes.setOnAction(y -> {

                        try {
                            FileChooser pic = new FileChooser();
                            pic.selectedExtensionFilterProperty();
                            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files","*.jpg","*.png","*.jpeg","*.gif");
                            pic.setTitle("Upload your cover");
                            pic.getExtensionFilters().add(extFilter);
                            File coverfile = pic.showOpenDialog(null);

                            String covername = String.valueOf(coverfile);
                            cstage.close();
                            file_choose_stage.close();

                            String coverpath = String.valueOf(covername);

                            String pathstr1=file.getAbsolutePath();
                            pathstr1=pathstr1.replaceAll("\\\\", "\\\\\\\\");
                            System.out.println(pathstr1);

                            String pathstr2=covername;
                            pathstr2=pathstr2.replaceAll("\\\\", "\\\\\\\\");
                            System.out.println(pathstr2);

                            int size = (int) (file.length()/1024);

                            try {
                                String q = "insert into files(username ,filename, file_category, file_extension, date_created, file_path, cover_page) " +
                                        "VALUES(" + "'" +  user_login_name + "', '" + Extension[0] + "', '" + Category + "', '"+
                                        Extension[Extension.length-1] + "', '" + dateFormat.format(file.lastModified()) + "', '"
                                        + pathstr1 + "', '" + pathstr2 + "')";

                                stmt.execute(q);
                            }
                            catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                            try {

                                String p = "select storag_left from user where username = '" + user_login_name + "'";
                                ResultSet rest = stmt.executeQuery(p);
                                String storage = "";
                                while(rest.next()){
                                    storage = rest.getString("storag_left");
                                }
                                int storage_space = Integer.valueOf(storage) - size;
                                String q = "update user set storag_left = " + storage_space + " where username = '" +
                                        user_login_name + "'";
                                stmt.execute(q);
                            }
                            catch (SQLException exy) {
                                exy.printStackTrace();
                            }

                        }
                        catch(Exception ex){
                            System.out.println("Error in selecting file cover page!");
                        }

                    });
                });
            }
            catch(Exception ex){
                System.out.println("The file was not submitted");
            }
        });

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
}
