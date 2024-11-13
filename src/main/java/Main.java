import database.DatabaseConnectionFactory;
import model.builder.BookBuilder;
import model.Book;
import model.builder.BookBuilder;
import repository.BookRepository;
import repository.BookRepositoryMock;
import repository.BookRepositoryMySQL;
import service.BookService;
import service.BookServiceImp;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Book book = new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1910, 10, 20))
                .build();

        System.out.println(book);

//        BookRepository bookRepository = new BookRepositoryMock();
//        bookRepository.save(book);
//        bookRepository.save(new BookBuilder().setTitle("Moara cu Noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1950, 2, 10)).build());
//        System.out.println(bookRepository.findAll());
//        bookRepository.removeAll();
//        System.out.println(bookRepository.findAll());
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
        BookRepository bookRepository = new BookRepositoryMySQL(connection);
        BookService bookService = new BookServiceImp(bookRepository);

        bookService.save(book);
        System.out.println(bookService.findAll());

        Book bookMoaracuNoroc = new BookBuilder().setTitle("Moara cu Noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1950, 2, 10)).build();
        bookService.save(bookMoaracuNoroc);
        System.out.println(bookService.findAll());
        //bookService.delete(bookMoaracuNoroc);
       // bookService.delete(book);
        System.out.println(bookService.findAll());
    }
}
