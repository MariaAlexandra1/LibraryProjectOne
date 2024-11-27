package model.builder;

import model.Orders;

import java.time.LocalDate;

public class OrdersBuilder {

    private Orders order;

    public OrdersBuilder() {
        order = new Orders();
    }

    public OrdersBuilder setId(Long id) {
        order.setId(id);
        return this;
    }

    public OrdersBuilder setUserId(Long userId) {
        order.setUserId(userId);
        return this;
    }

    public OrdersBuilder setTitle(String title) {
        order.setTitle(title);
        return this;
    }

    public OrdersBuilder setAuthor(String author) {
        order.setAuthor(author);
        return this;
    }

    public OrdersBuilder setPrice(double price) {
        order.setPrice(price);
        return this;
    }

    public OrdersBuilder setStock(int stock) {
        order.setStock(stock);
        return this;
    }

    public OrdersBuilder setOrderDate(LocalDate orderDate) {
        order.setOrderDate(orderDate);
        return this;
    }

    public Orders build() {
        return order;
    }
}
