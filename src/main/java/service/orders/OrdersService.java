package service.orders;



import model.Book;
import model.Orders;

import java.util.List;

public interface OrdersService {
    List<Orders> findAll();
    List<Orders> findByUserId(Long user_id);
    List<Orders> findByUserIdLastMonth(Long user_id);
    boolean save(Book book, Long user_id, Integer stock);
    int totalBooksSoldByUserIdLastMonth(Long user_id);
    double totalPriceByUserIdLastMonth(Long user_id);

}
