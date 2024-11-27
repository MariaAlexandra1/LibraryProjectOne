package repository.book;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository {
    private final List<Book> books;

    public BookRepositoryMock() {
        books = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public boolean delete(Book book) {
        return books.remove(book);
    }

    @Override
    public boolean sale(Book book) {
            Optional<Book> saledBook = books.stream()
                                            .filter(it -> it.getId().equals(book.getId()))
                                            .findFirst();
            if (saledBook.isPresent()) {
                saledBook.get().setStock(book.getStock() - 1);
                return true;
            }
            return false;
    }


    @Override
    public void removeAll() {
         books.clear();
    }
}
