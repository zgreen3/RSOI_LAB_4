package smirnov.bn.service_3.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smirnov.bn.service_3.model.EmployeeInfo;
import smirnov.bn.service_3.service.EmployeeService;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

//curl -X POST --data "{\"employeeName\":\"Employee_1\",\"employeeEmail\":\"myEmail_1@example.com\",\"employeeLogin\":\"MyName_1\"}" http://localhost:8193/employees/create-employee --header "Content-Type:application/json"
//curl -X POST --data "{\"employeeName\":\"Employee_2\",\"employeeEmail\":\"myEmail_2@example.com\",\"employeeLogin\":\"MyName_2\"}" http://localhost:8193/employees/create-employee --header "Content-Type:application/json"
//curl -X POST --data "{\"employeeName\":\"Employee_3\",\"employeeEmail\":\"myEmail_3@example.com\",\"employeeLogin\":\"MyName_3\"}" http://localhost:8193/employees/create-employee --header "Content-Type:application/json"
//curl -X POST --data "{\"employeeName\":\"Employee_4\",\"employeeEmail\":\"myEmail_4@example.com\",\"employeeLogin\":\"MyName_4\"}" http://localhost:8193/employees/create-employee --header "Content-Type:application/json"
    @PostMapping("/create-employee")
    public ResponseEntity createEmployee(@RequestBody EmployeeInfo employeeInfo) {
        try {
            UUID newEmployeeUuid = employeeService.createEmployee(employeeInfo);
            return new ResponseEntity(newEmployeeUuid.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error in createEmployee(...)", e);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    /*
    @GetMapping("/read-{id}")
    public ResponseEntity<EmployeeInfo> findEmployeeById(@PathVariable Integer id) {
        try {
            return new ResponseEntity(employeeService.findEmployeeById(id), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in findEmployeeById(...)", e);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }
    //*/

    @GetMapping("/read-{employeeUuid}")
    public ResponseEntity<EmployeeInfo> findEmployeeByUuid(@PathVariable UUID employeeUuid) {
        try {
            return new ResponseEntity(employeeService.findEmployeeByUuid(employeeUuid), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in findEmployeeByUuid(...)", e);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/read-all")
    public ResponseEntity<List<EmployeeInfo>> findAllEmployees() {
        try {
            List<EmployeeInfo> employeesInfo = employeeService.findAllEmployees();

            if (employeesInfo.isEmpty()) {

                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {

                return new ResponseEntity<List<EmployeeInfo>>(employeesInfo, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error in findAllEmployees(...)", e);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/read-all-paginated", params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<List<EmployeeInfo>>findAllEmployeesPaginated(@RequestParam(value = "page", defaultValue = "0", required = true) int page,
                                                                       @RequestParam(value = "sizeLimit", defaultValue = "100", required = true)
                                                                               int sizeLimit) {
        try {
            List<EmployeeInfo> employeesInfo = employeeService.findAllEmployeesPaginated(page, sizeLimit);

            if (employeesInfo.isEmpty()) {

                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {

                return new ResponseEntity<List<EmployeeInfo>>(employeesInfo, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error in findAllEmployeesPaginated(...)", e);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

//curl -X PUT --data "{\"employeeName\":\"Employee_2\",\"employeeEmail\":\"myEmail_2_3@example.com\",\"employeeLogin\":\"MyName_2_3\"\"employeeUuid\":\"9cbc6e2a-417d-4313-955c-fb58c2da7dc8\"}" http://localhost:8193/employees/update-employee
    @PutMapping("/update-employee")
    public ResponseEntity updateEmployee(@RequestBody EmployeeInfo employeeInfo) {
        try {
            employeeService.updateEmployee(employeeInfo);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateEmployee(...)", e);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    /*
    @DeleteMapping("/delete-{id}")
    public ResponseEntity deleteEmployeeById(@RequestParam Integer employeeId) {
        try {
            employeeService.deleteEmployeeById(employeeId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in deleteEmployeeById(...)", e);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }
    //*/

//curl -X DELETE http://localhost:8193/employees/delete-d843ca38-5b63-405e-8bb3-abfeaeded3a4
    @DeleteMapping("/delete-{employeeUuid}")
    public ResponseEntity deleteEmployeeByUuid(@PathVariable UUID employeeUuid) {
        try {
            employeeService.deleteEmployeeByUuid(employeeUuid);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in deleteEmployeeByUuid(...)", e);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }
}
