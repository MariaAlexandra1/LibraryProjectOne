package repository.orders;

import model.Orders;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository {

    List<Orders> findAll();

    List<Orders> findByUserId(Long user_id);
    List<Orders> findByUserIdLastMonth(Long user_id);
    boolean save(Orders orders);
    int totalBooksSoldByUserIdLastMonth(Long user_id);
    double totalPriceByUserIdLastMonth(Long user_id);

}
