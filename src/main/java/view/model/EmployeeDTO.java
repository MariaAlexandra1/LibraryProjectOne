package view.model;

import javafx.beans.property.*;

public class EmployeeDTO {
    
    private LongProperty id;
    
    public void setId(long id) {
        idProperty().set(id);
    }
    
    public long getId() {
        return idProperty().get();
    }
    
    public LongProperty idProperty() {
        if (id == null) {
            id = new SimpleLongProperty(this, "id");
        }
        return id;
    }


    private StringProperty username;

    public void setUsername(String username) {
        usernameProperty().set(username);
    }

    public String getUsername() {
        return usernameProperty().get();
    }

    public StringProperty usernameProperty() {
        if (username == null) {
            username = new SimpleStringProperty(this, "username");
        }
        return username;
    }


    private StringProperty role;

    public void setRole(String role) {
        roleProperty().set(role);
    }

    public String getRole() {
        return roleProperty().get();
    }

    public StringProperty roleProperty() {
        if (role == null) {
            role = new SimpleStringProperty(this, "role");
        }
        return role;
    }
}
