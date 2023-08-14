package Employee.demo;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    private GlobalExceptionHandler gex;


    boolean isValid(Employee employeeToCheck) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        return Stream.of(
                        employeeToCheck.getFirstName(),
                        employeeToCheck.getLastName(),
                        employeeToCheck.getEmail()
                )
                .allMatch(value -> value != null && !value.trim().isEmpty())
                && pat.matcher(employeeToCheck.getEmail()).matches();
    }

    public ResponseEntity<ApiResponse> HandlingPutPost(@Valid Employee employee, long id, @NotNull BindingResult bindingResult){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (!isValid(employee)) {
            ApiResponse response = new ApiResponse("failed", "Validation error", null);
            return ResponseEntity.badRequest().body(response);
        }
            //Edit
        if(id != -1) {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            if (updatedEmployee == null) {
                ApiResponse response = new ApiResponse("failed", "Employee not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(response);
            }

            ApiResponse response = new ApiResponse("success", "Employee updated successfully", updatedEmployee);
            return ResponseEntity.ok().headers(headers).body(response);
        }
        //Create
        else {
            Employee createdEmployee = employeeService.createEmployee(employee);
            ApiResponse response = new ApiResponse("success", "Employee created successfully", createdEmployee);
            return ResponseEntity.ok().headers(headers).body(response);

            }
        }

        @GetMapping ("/getNumberOfEmployees")
        public ResponseEntity<ApiResponse> GetNumberOfEmployees(){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            int NumberOfEmployees = employeeService.getNumberOfEmployees().size();
            ApiResponse response = new ApiResponse("success", "Employees fetched successfully", NumberOfEmployees);
            return ResponseEntity.ok().headers(headers).body(response);
        }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllEmployees(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "4") int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Employee> employeePage = employeeService.getAllEmployees(pageable);
        ApiResponse response = new ApiResponse("success", "Employees fetched successfully", employeePage.getContent());
        return ResponseEntity.ok().headers(headers).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getEmployeeById(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee employee = employeeService.getEmployeeById(id);
        ApiResponse response;
        if(employee != null) {
            response = new ApiResponse("success", "Employee fetched successfully", employee);
        }
        else {
            response = new ApiResponse("failed", "Employee wasn't found", null);
        }
        return ResponseEntity.ok().headers(headers).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
        return HandlingPutPost(employee,-1, bindingResult);
    }


    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ApiResponse> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee, BindingResult bindingResult) {
       return HandlingPutPost(employee,id,bindingResult);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Employee employee = employeeService.getEmployeeById(id);
        ApiResponse response;
        if(employee != null) {
            employeeService.deleteEmployee(id);
            response = new ApiResponse("success", "Employee deleted successfully", employee);
        }
        else {
            response = new ApiResponse("failed", "Employee wasn't found", null);
        }
        return ResponseEntity.ok().headers(headers).body(response);
    }
}
