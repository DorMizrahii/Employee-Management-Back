package Employee.demo;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmployeeDto {
    @NotEmpty(message = "First Name is required")
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    private String lastName;

    @Email(regexp = "^(.+)@(.+)$" ,message = "Invalid email format")
    @NotEmpty(message = "Email is required")
    private String email;


    public Employee toEmployee(){
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        return employee;
    }
}
