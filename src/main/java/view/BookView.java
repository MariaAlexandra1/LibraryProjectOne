package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.model.BookDTO;


import java.util.List;
import java.util.Optional;

public class BookView {
    private TableView bookTableView;
    private final ObservableList<BookDTO> bookObservableList;
    private TextField authorTextField;
    private TextField titleTextField;
    private TextField priceTextField;
    private TextField stockTextField;
    private TextField saleTextField;
    private Label authorLabel;
    private Label titleLabel;
    private Label priceLabel;
    private Label stockLabel;
    private Label saleLabel;
    private Button saveButton;
    private Button deleteButton;
    private Button saleButton;
    private ButtonType okSaleButton;
    private ButtonType cancelSaleButton;

    public BookView(Stage primaryStage, List<BookDTO> books){
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        bookObservableList = FXCollections.observableArrayList(books);
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
        bookTableView = new TableView<BookDTO>();

        bookTableView.setPlaceholder(new Label("No Books to display"));

        TableColumn<BookDTO, String> titleColumn = new TableColumn<BookDTO, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<BookDTO, String> authorColumn = new TableColumn<BookDTO, String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<BookDTO, Integer> priceColumn = new TableColumn<BookDTO, Integer>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<BookDTO, Integer> stockColumn = new TableColumn<BookDTO, Integer>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        bookTableView.getColumns().addAll(titleColumn, authorColumn, priceColumn, stockColumn);
        bookTableView.setItems(bookObservableList);

        gridPane.add(bookTableView, 0, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane){
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);
        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 3, 1);
        authorTextField = new TextField();
        gridPane.add(authorTextField, 4, 1);

        priceLabel = new Label("Price");
        gridPane.add(priceLabel, 1, 2);
        priceTextField = new TextField();
        gridPane.add(priceTextField, 2, 2);

        stockLabel = new Label("Stock");
        gridPane.add(stockLabel, 3, 2);
        stockTextField = new TextField();
        gridPane.add(stockTextField, 4, 2);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 5, 1);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 6, 1);

        saleButton = new Button("Sale");
        gridPane.add(saleButton, 7, 1);
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addSaleButtonListener(EventHandler<ActionEvent> saleButtonListener){
        saleButton.setOnAction(saleButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public void addDisplayAlertMessageWithInput(String title, String header, String content) {

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);


        saleLabel = new Label("How many books would you like to add?");
        saleTextField = new TextField();


        VBox vbox = new VBox(saleLabel, saleTextField);
        vbox.setSpacing(10);
        alert.getDialogPane().setContent(vbox);



         okSaleButton = new ButtonType("OK");
         cancelSaleButton = new ButtonType("Cancel");
        alert.getButtonTypes().addAll(okSaleButton, cancelSaleButton);


        Optional<ButtonType> buttonType;
        do {
            buttonType = alert.showAndWait();
            if (buttonType.isPresent() && buttonType.get() == okSaleButton) {
                if (saleTextField.getText().trim().isEmpty()) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("You must enter a value for the sale!");
                    errorAlert.showAndWait();
                }
            }else{
                addDisplayAlertMessage("Cancel", "Sale operation was canceled", "You press the cancel button!");
                saleTextField.setText("0");
                break;
            }
        } while (saleTextField.getText().trim().isEmpty());

    }

    public String getTitle(){
        return titleTextField.getText();
    }

    public String getAuthor(){
        return authorTextField.getText();
    }

    public Double getPrice() {return Double.valueOf(priceTextField.getText());}

    public Integer getStock() {return Integer.valueOf(stockTextField.getText());}

    public Integer getSale() {return Integer.valueOf(saleTextField.getText());}

    public void addBookToObservableList(BookDTO book){
        this.bookObservableList.add(book);
    }

    public void removeBookFromObservableList(BookDTO book){
        this.bookObservableList.remove(book);
    }

    public ObservableList<BookDTO> getBookObservableList(){
        return bookObservableList;
    }

    public void updateBookFromObservableList(BookDTO book, Integer stock){
        bookObservableList.stream()
                .filter(bookDTO -> bookDTO.getTitle().equals(book.getTitle())
                        && bookDTO.getAuthor().equals(book.getAuthor()))
                .findFirst()
                .ifPresent(bookDTO -> {

                    bookDTO.setStock(bookDTO.getStock() - stock);
                    //forteaza update-ul lui TableView - pt schimbarea in "timp real"
                    bookTableView.refresh();

                });

    }

    public TableView getBookTableView(){
        return bookTableView;
    }
}
