package repository.orders;


import model.Book;
import model.Orders;
import model.builder.BookBuilder;
import model.builder.OrdersBuilder;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersRepositoryMySQL implements OrdersRepository{

    private final Connection connection;
    public OrdersRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Orders> findAll() {
        String sql = "SELECT * FROM orders;";

        List<Orders> orders = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                orders.add(getOrderFromResultSet(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Orders> findByUserId(Long userId) {
        String sql = "SELECT * FROM book WHERE user_id= ?";

        List<Orders> orders = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                orders.add(getOrderFromResultSet(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean save(Orders order) {
        String newSql = "INSERT INTO orders VALUES(null, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setString(2, order.getTitle());
            preparedStatement.setString(3, order.getAuthor());
            preparedStatement.setDouble(4, order.getPrice());
            preparedStatement.setInt(5, order.getStock());
            preparedStatement.setDate(6, Date.valueOf(order.getOrderDate()));
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private Orders getOrderFromResultSet(ResultSet resultSet) throws SQLException{
        return new OrdersBuilder()
                .setId(resultSet.getLong(("id")))
                .setUserId(resultSet.getLong(("user_id")))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPrice(resultSet.getDouble("price"))
                .setStock(resultSet.getInt("stock"))
                .setOrderDate(new java.sql.Date(resultSet.getDate("orderDate").getTime()).toLocalDate())
                .build();
    }
}
