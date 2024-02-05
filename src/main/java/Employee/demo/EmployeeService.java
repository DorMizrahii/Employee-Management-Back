package Employee.demo;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public Page<Employee> getPageEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee createEmployee(@NotNull Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, @NotNull Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElse(null);


        if (employee == null) {
            return null;
        }

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setNickname(employeeDetails.getNickname());

        return employeeRepository.save(employee);

    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
