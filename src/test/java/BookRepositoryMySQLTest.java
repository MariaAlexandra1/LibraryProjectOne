import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BookRepository;
import repository.BookRepositoryMySQL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMySQLTest {

    private static BookRepository bookRepository;
    private static Connection connection;

    @BeforeAll
    public static void setup(){
        connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();
        bookRepository = new BookRepositoryMySQL(connection);
    }

    @BeforeEach
    public void setUpEach(){
        bookRepository.removeAll();
        bookRepository.save(new BookBuilder().setTitle("Ion").setAuthor("Liviu Rebreanu").setPublishedDate(LocalDate.of(1950, 10, 20)).build());
        bookRepository.save(new BookBuilder().setTitle("Moara cu Noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1950, 2, 10)).build());
    }

    @Test
    public void findAll(){
        List <Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
    }

    @Test
    public void findById(){
        final Optional<Book> book = bookRepository.findById(1L);
        assertTrue(book.isEmpty());
    }

    @Test
    public void save(){
        assertTrue(bookRepository.save(new BookBuilder().setTitle("Ion").setAuthor("Liviu Rebreanu").setPublishedDate(LocalDate.of(1950, 10, 20)).build()));
    }

    @Test
    public void delete() {
        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
        Book book = new BookBuilder().setTitle("Ion").setAuthor("Liviu Rebreanu").setPublishedDate(LocalDate.of(1950, 10, 20)).build();

        assertTrue(bookRepository.delete(book));

        books = bookRepository.findAll();
        assertEquals(1, books.size());
    }
}
