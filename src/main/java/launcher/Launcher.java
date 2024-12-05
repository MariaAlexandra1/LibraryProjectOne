package launcher;


import database.DatabaseConnectionFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryMock;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;


public class Launcher extends Application {
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        LoginComponentFactory.getInstance(false, primaryStage);
    }
}
