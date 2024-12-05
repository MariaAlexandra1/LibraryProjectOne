package database;

import model.Role;
import model.User;
import model.validator.Notification;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.ROLES;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.getRolesRights;

//Script - code ce automatizeaza ceva pasi sau procese

public class Bootstrap {
    private static RightsRolesRepository rightsRolesRepository;
    private static UserRepository userRepository;
    private static AuthenticationService authenticationService;

    public static void main(String[] args) throws SQLException {
        dropAll();

        bootstrapTables();

        bootstrapUserData();

    }

    private static void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables in schema: " + schema);

            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "TRUNCATE `role_right`;",
                    "DROP TABLE `role_right`;",
                    "TRUNCATE `right`;",
                    "DROP TABLE `right`;",
                    "TRUNCATE `user_role`;",
                    "DROP TABLE `user_role`;",
                    "TRUNCATE `role`;",
                    "TRUNCATE `orders`;",
                    "DROP TABLE  `book`, `orders`, `role`, `user`;"
            };

            Arrays.stream(dropStatements).forEach(dropStatement -> {
                try {
                    statement.execute(dropStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapTables() throws SQLException {
        SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping " + schema + " schema");


            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            Connection connection = connectionWrapper.getConnection();

            Statement statement = connection.createStatement();

            for (String table : Constants.Tables.ORDERED_TABLES_FOR_CREATION) {
                String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
                statement.execute(createTableSQL);
            }
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapUserData() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping user data for " + schema);

            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
            userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);
            authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);

            bootstrapRoles();
            bootstrapRights();
            bootstrapRoleRight();
            bootstrapUserRoles();
        }
    }

    private static void bootstrapRoles() throws SQLException {
        for (String role : ROLES) {
            rightsRolesRepository.addRole(role);
        }
    }

    private static void bootstrapRights() throws SQLException {
        for (String right : RIGHTS) {
            rightsRolesRepository.addRight(right);
        }
    }

    private static void bootstrapRoleRight() throws SQLException {
        Map<String, List<String>> rolesRights = getRolesRights();

        for (String role : rolesRights.keySet()) {
            Long roleId = rightsRolesRepository.findRoleByTitle(role).getId();

            for (String right : rolesRights.get(role)) {
                Long rightId = rightsRolesRepository.findRightByTitle(right).getId();

                rightsRolesRepository.addRoleRight(roleId, rightId);
            }
        }
    }

    private static void bootstrapUserRoles() throws SQLException {
        Map<String, String> usersForRoles = Map.of(
                "admin@admin.com", Constants.Roles.ADMINISTRATOR,
                "alexandra23@gmail.com", Constants.Roles.EMPLOYEE,
                "eduard@yahoo.com", Constants.Roles.CUSTOMER
        );

        for (Map.Entry<String, String> person : usersForRoles.entrySet()) {
            String username = person.getKey();
            String role = person.getValue();

            Notification<Boolean> authentification = new Notification<>();
            if(username.equals("admin@admin.com")){
                authentification = authenticationService.register(username, "password@23");
            }else if(username.equals("alexandra23@gmail.com")){
                authentification = authenticationService.register(username, "password@23");
            }else if(username.equals("eduard@yahoo.com")){
                authentification = authenticationService.register(username, "password@23");
            }
            if(!authentification.hasErrors()){
                Role userRole = rightsRolesRepository.findRoleByTitle(role);
                Notification<User> userNotification = userRepository.findByUsername(username);
                if(!userNotification.hasErrors()){
                    User user = userNotification.getResult();

                    rightsRolesRepository.removeRolesFromUser(user, user.getRoles());

                    rightsRolesRepository.addRolesToUser(user, List.of(userRole));
                }
            }

        }
    }
}
