package launcher;

import controller.BookController;
import controller.EmployeeController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import mapper.EmployeeMapper;
import repository.book.Cache;
import repository.orders.OrdersRepository;
import repository.orders.OrdersRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookServiceImpl;
import service.employee.EmployeeService;
import service.employee.EmployeeServiceImpl;
import service.orders.OrdersService;
import service.orders.OrdersServiceImpl;
import view.BookView;
import view.EmployeeView;
import view.model.BookDTO;
import view.model.EmployeeDTO;

import java.sql.Connection;
import java.util.List;

public class AdminComponentFactory {

    private final EmployeeView employeeView;
    private final EmployeeController employeeController;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final OrdersRepository ordersRepository;
    private final EmployeeService employeeService;
    private final OrdersService ordersService;
    private static AdminComponentFactory instance;
    private static Boolean componentsForTests;
    private static Stage stage;

    public static AdminComponentFactory getInstance(Boolean aComponentsForTest, Stage aStage){
        if (instance == null){
            synchronized (AdminComponentFactory.class) {
                if(instance == null){
                    componentsForTests = aComponentsForTest;
                    stage = aStage;
                    instance = new AdminComponentFactory(componentsForTests, stage);
                }
            }
        }
        return instance;
    }

    private AdminComponentFactory(Boolean  componentsForTest, Stage stage){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.ordersRepository = new OrdersRepositoryMySQL(connection);

        this.employeeService = new EmployeeServiceImpl(userRepository, rightsRolesRepository);
        this.ordersService = new OrdersServiceImpl(ordersRepository);

        List<EmployeeDTO> userDTOs = EmployeeMapper.convertUsersToEmployeeDTOs(this.employeeService.findAll());
        this.employeeView = new EmployeeView(stage, userDTOs);
        this.employeeController = new EmployeeController(employeeView, employeeService, ordersService);
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public EmployeeController getEmployeeController() {
        return employeeController;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public static Stage getStage(){
        return stage;
    }

    public static Boolean getComponentsForTests(){
        return componentsForTests;
    }


}
