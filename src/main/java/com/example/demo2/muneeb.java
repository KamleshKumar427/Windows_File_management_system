package com.example.demo2;
import com.example.demo2.FileInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import com.example.demo2.FileInfo;
import java.awt.*;
import java.io.File;
import java.sql.*;

public class muneeb extends Dashboard {

    //muneeb
    Statement stmt;
    TableView<FileInfo> table = new TableView<FileInfo>();
    ObservableList<FileInfo> data = FXCollections.observableArrayList();

    // class main scene
    Scene request_document_scene;

    // node declaration
    BorderPane Request_doc_mainPane;
    VBox filter_segment;
    Button[] linkbt;
    Button btSearch;
    Button btRe;
    Label filename_label;
    Label pb;
    Label catg;
    Label yr;
    Label ft;
    Label space;
    TextField tfnm;
    TextField tfpb;
    ComboBox comboctg;
    ComboBox comboYear;
    ComboBox comboext;
    TableColumn fileNamecol;
    TableColumn file_categorycol;
    TableColumn authorcol;
    TableColumn date_createdcol;
    TableColumn file_extensioncol;
    VBox titlePane;
    HBox btTable;
    GridPane gp;
    Label collection_lbl;


    muneeb() {

        super();
        initializeDB();
        // class main scene
        Scene request_document_scene;
        // node declaration
        Request_doc_mainPane = new BorderPane();
        filter_segment = new VBox();
        linkbt = new Button[100];
        btSearch = new Button("Search");
        btRe = new Button("Reset");
        filename_label = new Label("File:");
        pb = new Label("Prepared by:");
        catg = new Label("Category:");
        yr = new Label("Year");
        ft = new Label("File Extension:");
        space = new Label("       ");
        tfnm = new TextField();
        tfpb = new TextField();
        comboctg = new ComboBox();
        comboYear = new ComboBox();
        comboext = new ComboBox();
        fileNamecol = new TableColumn("File Name");
        file_categorycol = new TableColumn("File Category");
        authorcol = new TableColumn("Author");
        date_createdcol = new TableColumn("Year");
        file_extensioncol = new TableColumn("File extension");
        titlePane = new VBox(10);
        btTable = new HBox(10);
        gp = new GridPane();
        collection_lbl = new Label(" Request Documents ");

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

    public void set_request_document_scene(Stage stage) {

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


        setup_request_document_panes();
        set_request_documents_nodes_details();
        request_document_nodes_design();
        setup_request_document_controls();
        control_setup(stage);

        central_pane.setCenter(Request_doc_mainPane);
        main_pane.setPadding(new Insets(5));
        central_pane.setRight(null);
        central_pane.setBottom(null);

        Scene request_document_scene = new Scene(scroll_window, 1360, 700);
        request_document_scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(request_document_scene);


    }

    public void set_request_documents_nodes_details() {

        comboYear.getItems().addAll(2015, 2016, 2017, 2018, 2019, 2020, 2021);
        comboext.getItems().addAll("Pdf", "Word", "PowerPoint", "" + "mp3", "text");
        comboctg.getItems().addAll("Science", "Comic", "Action", "Poetry", "Politics", "Geography", "History");
        fileNamecol.setCellValueFactory(new PropertyValueFactory<FileInfo, String>("fileName"));
        file_categorycol.setCellValueFactory(new PropertyValueFactory<FileInfo, String>("file_category"));
        date_createdcol.setCellValueFactory(new PropertyValueFactory<FileInfo, String>("date_created"));
        file_extensioncol.setCellValueFactory(new PropertyValueFactory<FileInfo, String>("file_extension"));
        tfnm.setPromptText("File Name");
        fileNamecol.setMinWidth(200);
        file_categorycol.setMinWidth(200);
        date_createdcol.setMinWidth(200);
        file_extensioncol.setMinWidth(170);
        table.setItems(data);
        table.setMinHeight(580);
        table.setMinWidth(770);
        table.setPadding(new Insets(5));
        table.getColumns().addAll(fileNamecol, file_categorycol, date_createdcol, file_extensioncol);
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

    public void request_document_nodes_design() {

        // add design to the nodes

        filename_label.setFont(Font.font("Times New Roman", 14));
        yr.setFont(Font.font("Times New Roman", 14));
        ft.setFont(Font.font("Times New Roman", 14));
        catg.setFont(Font.font("Times New Roman", 14));
        pb.setFont(Font.font("Times New Roman", 14));

        btSearch.getStyleClass().addAll("btn", "btn-success");
        btRe.getStyleClass().addAll("btn", "btn-danger");

        Request_doc_mainPane.setPadding(new Insets(5));
        btTable.setStyle("-fx-border-color: grey; -fx-border-width: 0.5;");

        titlePane.setSpacing(20);
        titlePane.setPadding(new Insets(5));
        titlePane.setMinWidth(235);
        filter_segment.setMinWidth(235);
        titlePane.setStyle("-fx-border-color: grey; -fx-border-width: 0.5;");

        btTable.setSpacing(10);
        btTable.setPadding(new Insets(10));

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

    public void setup_request_document_panes() {
        // set panes

        VBox filename_box = new VBox(3);
        filename_box.getChildren().addAll(filename_label, tfnm);

        VBox year_box = new VBox(3);
        year_box.getChildren().addAll(yr, comboYear);

        VBox extention_box = new VBox(3);
        extention_box.getChildren().addAll(ft, comboext);

        VBox category_box = new VBox(3);
        category_box.getChildren().addAll(catg, comboctg);

        VBox prepared_box = new VBox(3);
        prepared_box.getChildren().addAll(pb, tfpb);

        HBox search_reset = new HBox(5);
        search_reset.setAlignment(Pos.CENTER);
        search_reset.getChildren().addAll(btSearch, btRe);

        titlePane.getChildren().addAll(filename_box, year_box, extention_box, category_box, space, search_reset);
        final Pane spacerH = new Pane();
        HBox.setHgrow(spacerH, Priority.ALWAYS);
        spacerH.setMinSize(9, 1);

        btTable.getChildren().addAll(titlePane, table, gp, spacerH);

        Request_doc_mainPane.setCenter(null);
        Request_doc_mainPane.setBottom(null);

        Request_doc_mainPane.setLeft(null);
        Request_doc_mainPane.setRight(btTable);
        Request_doc_mainPane.setTop(main_head);
        table.setEditable(true);
    }

    public void setup_request_document_controls() {

        // action when Reset button is clicked
        btRe.setOnAction(e -> {
            int ll = table.getItems().size();
            table.getItems().clear();  // remove table
            for (int i = 0; i < ll; i++) {
                // remove buttons
                linkbt[i].setVisible(false);
                linkbt[i].setManaged(false);
            }

            tfnm.clear();
            tfpb.clear();
            comboext.valueProperty().set(null);
            comboYear.valueProperty().set(null);
            comboctg.valueProperty().set(null);
        });

        // action when Seach button is clicked
        btSearch.setOnAction(e -> {
            String nameChoose, yearChoose, typeChoose, writerChoose, ctgChoose;
            int ll = table.getItems().size();
            table.getItems().clear(); // remove table
            for (int i = 0; i < ll; i++) {
                // remove buttons
                linkbt[i].setVisible(false);
                linkbt[i].setManaged(false);
            }
            // check which conditions were selected and accordingly choose query
            if (tfnm.getText().isEmpty()) {
                nameChoose = "";
            } else {
                nameChoose = tfnm.getText();
            }
            if (comboYear.getSelectionModel().isEmpty()) {
                yearChoose = "";
            } else {
                yearChoose = comboYear.getValue().toString();
            }
            if (comboext.getSelectionModel().isEmpty()) {
                typeChoose = "";
            } else {
                typeChoose = comboext.getValue().toString();
            }
            if (comboctg.getSelectionModel().isEmpty()) {
                ctgChoose = "";
            } else {
                ctgChoose = comboctg.getValue().toString();
            }

            try {
                String q = "Select * from files where filename like '%" + nameChoose + "%' and file_category like '%" + ctgChoose + "%'"
                        + " and date_created like '%" + yearChoose + "%' and file_extension like '%" + typeChoose + "%'" +
                        "and username = '" + HelloApplication.user_login_name + "'";

                System.out.println(q);
                ResultSet rs = stmt.executeQuery(q);
                int i = 0;
                gp.add(new Button("Links"), 0, 0);

                while (rs.next()) {
                    data.add(new FileInfo(rs.getString("filename"), rs.getString("file_category"),
                            rs.getString("date_created"), rs.getString("file_extension")));
                    String paths = rs.getString("file_path");
                    linkbt[i] = new Button();
                    linkbt[i].setOnAction(kk -> openFile(paths));
                    gp.add(linkbt[i], 0, i + 1);
                    i++;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
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

}
