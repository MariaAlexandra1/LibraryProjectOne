package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.book.Cache;
import repository.orders.OrdersRepository;
import repository.orders.OrdersRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.orders.OrdersService;
import service.orders.OrdersServiceImpl;
import view.BookView;
import view.model.BookDTO;
import java.sql.Connection;

import java.util.List;

public class EmployeeComponentFactory {

    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final OrdersRepository ordersRepository;
    private final OrdersService ordersService;
    private static EmployeeComponentFactory instance;
    private static Boolean componentsForTests;
    private static Stage stage;

    public static EmployeeComponentFactory getInstance(Boolean aComponentsForTest, Stage aStage){
        if (instance == null){
            synchronized (AdminComponentFactory.class) {
                if(instance == null){
                    componentsForTests = aComponentsForTest;
                    stage = aStage;
                    instance = new EmployeeComponentFactory(componentsForTests, stage);
                }
            }
        }
        return instance;
    }

    private EmployeeComponentFactory(Boolean componentsForTest, Stage stage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySQL(connection), new Cache<>());
        this.bookService = new BookServiceImpl(bookRepository);
        this.ordersRepository = new OrdersRepositoryMySQL(connection);
        this.ordersService = new OrdersServiceImpl(ordersRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(this.bookService.findAll());
        this.bookView = new BookView(stage, bookDTOs);
        this.bookController = new BookController(bookView, bookService, ordersService);
    }

    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

    public static Stage getStage(){
        return stage;
    }

    public static Boolean getComponentsForTests(){
        return componentsForTests;
    }



}
