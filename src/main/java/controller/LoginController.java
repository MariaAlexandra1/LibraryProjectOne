package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import launcher.AdminComponentFactory;
import launcher.EmployeeComponentFactory;
import launcher.LoginComponentFactory;
import model.Role;
import model.User;

import model.validator.Notification;
import service.security.RightsRolesService;
import service.user.AuthenticationService;
import view.LoginView;

import java.util.List;


public class LoginController {
    //deloc repository in controller

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final RightsRolesService rightsRolesService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, RightsRolesService rightsRolesService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.rightsRolesService = rightsRolesService;


        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else{
                loginView.setActionTargetText("LogIn Successfull!");
                User user = loginNotification.getResult();
                List<Role> roles = rightsRolesService.findRolesForUser(user.getId());
                System.out.println(roles.size());
                Role role = roles.get(0);
                if(role.getRole().equals("administrator")){
                        AdminComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
                }else{
                        EmployeeComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
                }


            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);
            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            }else{
                loginView.setActionTargetText("Register Successfull!");
            }
        }
    }
}