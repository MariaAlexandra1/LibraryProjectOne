package mapper;

import model.User;
import model.builder.UserBuilder;
import view.model.EmployeeDTO;
import view.model.EmployeeDTOBuilder;


import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {
    public static EmployeeDTO convertUserToEmployeeDTO(User user) {
        return new EmployeeDTOBuilder().setId(user.getId()).setUsername(user.getUsername()).setRole(user.getRoles().get(0).getRole()).build();
    }

    public static User convertEmployeeDTOToUser(EmployeeDTO employeeDTO) {
        return new UserBuilder().setUsername(employeeDTO.getUsername()).setId(employeeDTO.getId()).build();
    }

    public static List<EmployeeDTO> convertUsersToEmployeeDTOs(List<User> users) {
        return users.parallelStream().map(EmployeeMapper::convertUserToEmployeeDTO).collect(Collectors.toList());
    }

    public static List<User> convertEmployeeDTOsToUsers(List<EmployeeDTO> employeeDTOs) {
        return employeeDTOs.parallelStream().map(EmployeeMapper::convertEmployeeDTOToUser).collect(Collectors.toList());
    }
}
