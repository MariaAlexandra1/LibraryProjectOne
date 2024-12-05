package view.model;

import model.Role;

public class EmployeeDTOBuilder {

    private EmployeeDTO employeeDTO;

    public EmployeeDTOBuilder() {
        employeeDTO = new EmployeeDTO();
    }

    public EmployeeDTOBuilder setId(Long id){
        employeeDTO.setId(id);
        return this;
    }

    public EmployeeDTOBuilder setUsername(String username) {
        employeeDTO.setUsername(username);
        return this;
    }

    public EmployeeDTOBuilder setRole(String role){
        employeeDTO.setRole(role);
        return this;
    }

    public EmployeeDTO build(){
        return employeeDTO;
    }
}
