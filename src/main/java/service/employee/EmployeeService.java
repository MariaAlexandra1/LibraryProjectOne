package service.employee;

import model.Orders;
import model.Role;
import model.User;

import java.util.List;

public interface EmployeeService {

    List<User> findAll();
    boolean save(User user);
    Role findRole(String role);
    boolean delete(User user);
    void generatePdfReport(String employeeUsername, List<Orders> orders, int totalOrders, int totalBooksSold, double totalPrice) throws Exception;
}
