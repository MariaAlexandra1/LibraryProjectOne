package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.Book;
import service.book.BookService;
import service.orders.OrdersService;
import view.BookView;
import view.model.BookDTO;
import view.model.BookDTOBuilder;

public class BookController {
    private final BookView bookView;
    private final BookService bookService;
    private final OrdersService ordersService;

    public BookController(BookView bookView, BookService bookService, OrdersService ordersService) {
        this.bookView = bookView;
        this.bookService = bookService;
        this.ordersService = ordersService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSaleButtonListener(new SaleButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            Double price = bookView.getPrice();
            Integer stock = bookView.getStock();

            if(title.isEmpty() || author.isEmpty()) {
                bookView.addDisplayAlertMessage("Save Error", "Problem at Title or Author fields", "Can not have empty Author or Title fields. Please fill in the fields before submitting Save!");
                bookView.getBookObservableList().get(0).setTitle("No Name");
            } else {
                BookDTO bookDTO = new BookDTOBuilder().setAuthor(author).setTitle(title).setPrice(price).setStock(stock).build();
                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if (savedBook){
                    bookView.addDisplayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    bookView.addBookToObservableList(bookDTO);
                }else{
                    bookView.addDisplayAlertMessage("Save Error", "Problem at adding Book", "There was a problem at adding book to the database. Please try again!");
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO != null){
                boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));

                if (deletionSuccessful){
                    bookView.addDisplayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from the database.");
                    bookView.removeBookFromObservableList(bookDTO);
                }else{
                    bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book", "There was a problem with the database. Please try again!");
                }
            }else{
                bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book", "You must select a book before pressing the delete button.");
            }


        }
    }

    private class SaleButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null) {
                Book book = BookMapper.convertBookDTOToBook(bookDTO);
                bookView.addDisplayAlertMessageWithInput("Sale Successful", "Book Saled", "Book was successfully saled from the database.");
                boolean saleSuccessful = bookService.sale(book, bookView.getSale());
                if (saleSuccessful) {
                        boolean orderSuccessful = ordersService.save(book, 1L, bookView.getSale());
                        if (orderSuccessful)
                            bookView.updateBookFromObservableList(bookDTO, bookView.getSale());
                } else {
                    bookView.addDisplayAlertMessage("Sale Error", "Problem at selling book", "There was a problem with the database. Please try again!");
                }
            } else {
                bookView.addDisplayAlertMessage("Sale Error", "Problem at selling book", "You must select a book before pressing the sale button.");
            }

        }
    }
}
