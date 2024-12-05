package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import mapper.EmployeeMapper;
import model.Orders;
import model.Role;
import model.User;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import service.employee.EmployeeService;
import service.orders.OrdersService;
import view.EmployeeView;
import view.model.BookDTO;
import view.model.EmployeeDTO;
import view.model.EmployeeDTOBuilder;

import java.util.List;


public class EmployeeController {
    private final EmployeeView employeeView;
    private final EmployeeService employeeService;
    private final OrdersService ordersService;

    public EmployeeController(EmployeeView employeeView, EmployeeService employeeService, OrdersService ordersService) {
        this.employeeView = employeeView;
        this.employeeService = employeeService;
        this.ordersService = ordersService;


        this.employeeView.addSaveUserButtonListener(new SaveUserButtonListener());
        this.employeeView.addReportButtonListener(new ReportButtonListener());
        this.employeeView.deleteButtonListener(new DeleteButtonListener());
    }

    private class SaveUserButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            String username = employeeView.getUsername();
            String role = employeeView.getRole();

            if(username.isEmpty() || role == null) {
                employeeView.addDisplayAlertMessage("Add Error", "Problem at username or role fields", "Please fill all the fields!");
            }else{
                EmployeeDTO employeeDTO = new EmployeeDTOBuilder().setUsername(username).build();
                Role userRole = employeeService.findRole(role);
                User user = EmployeeMapper.convertEmployeeDTOToUser(employeeDTO);
                user.setRoles(List.of(userRole));
                boolean savedEmployee = employeeService.save(user);

                if(savedEmployee) {
                    employeeDTO.setId(user.getId());
                    employeeDTO.setRole(role);
                    employeeView.addDisplayAlertMessage("Add Successful", "Employee Added", "Employee was successfully added to the database.");
                    if(role.equals("employee"))
                        employeeView.addUserToObservableList(employeeDTO);
                }else{
                    employeeView.addDisplayAlertMessage("Add Error", "Problem at adding employee", "There was a problem at adding employee to the database. Please try again!");
                }
            }
        }
    }

    private class ReportButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            EmployeeDTO employeeDTO = (EmployeeDTO) employeeView.getUserTableView().getSelectionModel().getSelectedItem();
            if(employeeDTO != null) {
                Long userId = employeeDTO.getId();
                List<Orders> orders = ordersService.findByUserIdLastMonth(userId);
                if (orders.isEmpty()) {
                    employeeView.addDisplayAlertMessage("Report", "No Orders", "The selected employee has no orders in the last month.");
                }else{
                    int totalOrders = orders.size();
                    int totalBooksSold = ordersService.totalBooksSoldByUserIdLastMonth(userId);
                    double totalPrice = ordersService.totalPriceByUserIdLastMonth(userId);

                    try {
                        employeeService.generatePdfReport(employeeDTO.getUsername(), orders, totalOrders, totalBooksSold, totalPrice);
                        employeeView.addDisplayAlertMessage("Report", "Report Generated", "The report was successfully generated.");
                    } catch (Exception e) {
                        e.printStackTrace();
                        employeeView.addDisplayAlertMessage("Error", "Report Generation Failed", "Failed to generate the report.");
                    }
                }
            }else {
                employeeView.addDisplayAlertMessage("Report Error", "No Employee Selected", "Please select an employee to generate the report.");
            }

        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            EmployeeDTO employeeDTO = (EmployeeDTO) employeeView.getUserTableView().getSelectionModel().getSelectedItem();
            if(employeeDTO != null){
                boolean deletionSuccessful = employeeService.delete(EmployeeMapper.convertEmployeeDTOToUser(employeeDTO));

                if (deletionSuccessful){
                    employeeView.addDisplayAlertMessage("Delete Successful", "Employee Deleted", "Employee was successfully deleted from the database.");
                    employeeView.removeUserFromObservableList(employeeDTO);
                }else{
                    employeeView.addDisplayAlertMessage("Delete Error", "Problem at deleting employee", "There was a problem with the database. Please try again!");
                }
            }else{
                employeeView.addDisplayAlertMessage("Delete Error", "Problem at deleting employee", "You must select a book before pressing the delete button.");
            }


        }
    }
}
