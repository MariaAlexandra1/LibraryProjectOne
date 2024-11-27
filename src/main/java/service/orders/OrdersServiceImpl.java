package service.orders;

import model.Book;
import model.Orders;
import model.builder.OrdersBuilder;
import repository.orders.OrdersRepository;

import java.time.LocalDate;
import java.util.List;

public class OrdersServiceImpl implements OrdersService{

    private final OrdersRepository ordersRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }
    @Override
    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    @Override
    public List<Orders> findByUserId(Long user_id) {
        return ordersRepository.findByUserId(user_id);
    }

    @Override
    public boolean save(Book book, Long user_id) {
        Orders order = new OrdersBuilder().setUserId(user_id).setTitle(book.getTitle())
                .setAuthor(book.getAuthor()).setPrice(book.getPrice())
                .setStock(book.getStock()).setOrderDate(LocalDate.now()).build();
        return ordersRepository.save(order);
    }
}
