package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.model.EmployeeDTO;

import java.util.List;

public class EmployeeView {
    private TableView userTableView;
    private final ObservableList<EmployeeDTO> userObservableList;
    private TextField usernameTextField;
    private TextField titleTextField;
    private ComboBox<String> roleComboBox;
    private Label usernameLabel;
    private Label roleLabel;
    private Button addButton;
    private Button reportButton;
    private Button deleteButton;

    public EmployeeView(Stage primaryStage, List<EmployeeDTO> users) {
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        userObservableList = FXCollections.observableArrayList(users);
        initTableView(gridPane);

        initSaveOptions(gridPane);

        primaryStage.show();
    }

    public void initializeGridPage(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane){
        userTableView = new TableView<EmployeeDTO>();

        userTableView.setPlaceholder(new Label("No employees to display"));

        TableColumn<EmployeeDTO, String> idColumn = new TableColumn<EmployeeDTO, String>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<EmployeeDTO, String> usernameColumn = new TableColumn<EmployeeDTO, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<EmployeeDTO, String> roleColumn = new TableColumn<EmployeeDTO, String>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        userTableView.getColumns().addAll(idColumn, usernameColumn, roleColumn);
        userTableView.setItems(userObservableList);

        gridPane.add(userTableView, 0, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane){
        usernameLabel = new Label("Username");
        gridPane.add(usernameLabel, 1, 1);
        usernameTextField = new TextField();
        gridPane.add(usernameTextField, 2, 1);

        roleLabel = new Label("Role");
        gridPane.add(roleLabel, 1, 2);
        roleComboBox = new ComboBox<>();
        roleComboBox.getItems().add("administrator");
        roleComboBox.getItems().add("employee");
        roleComboBox.getItems().add("customer");
        gridPane.add(roleComboBox, 2, 2);

        addButton = new Button("Add");
        gridPane.add(addButton, 3, 1);

        reportButton = new Button("Generate report");
        gridPane.add(reportButton, 3, 2);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 4, 1);
    }

    public void addSaveUserButtonListener(EventHandler<ActionEvent> saveUserButtonListener){
        addButton.setOnAction(saveUserButtonListener);
    }

    public void addReportButtonListener(EventHandler<ActionEvent> reportButtonListener){
        reportButton.setOnAction(reportButtonListener);
    }

    public void deleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getUsername(){return usernameTextField.getText();}
    public String getRole(){return roleComboBox.getValue();}

    public void addUserToObservableList(EmployeeDTO user){
        userObservableList.add(user);
    }

    public void removeUserFromObservableList(EmployeeDTO user){
        userObservableList.remove(user);
    }

    public ObservableList<EmployeeDTO> getUserObservableList() {
        return userObservableList;
    }

    public TableView getUserTableView() {
        return userTableView;
    }




}
