package Employee.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String first_name) {
        firstName=first_name;
    }

    public void setLastName(String last_name) {
        lastName = last_name;
    }

    public void setEmail(String email_address) {
        email = email_address;
    }



}
