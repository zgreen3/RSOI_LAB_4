package smirnov.bn.web_spring_app_1.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import smirnov.bn.web_spring_app_1.form.EmployeeForm;
import smirnov.bn.web_spring_app_1.model.EmployeeInfo;
import smirnov.bn.web_spring_app_1.service.WebAppServiceImpl;


@Controller
public class MainController {
    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";

    private static final String SERVICE_3_PORT_STRING = "8193";
    private static final String SERVICE_3_URI_COMMON_DIR_STRING = "/employees/";
    private static final String SERVICE_3_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_3_PORT_STRING + SERVICE_3_URI_COMMON_DIR_STRING;

    private static final String READ_ALL_EMP_GET_URI_STRING = "read-all";
    private static final String READ_ALL_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + READ_ALL_EMP_GET_URI_STRING;

    //https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
    //@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private WebAppServiceImpl service = new WebAppServiceImpl();

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    //private static List<EmployeeInfo> employees = new ArrayList<EmployeeInfo>();

    /*
    static {
        employees.add(new EmployeeInfo("Bob", "b2b_log"));
        employees.add(new EmployeeInfo("Xman", "xman_log"));
    }
    //*/

    // Inject via application.properties
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    @RequestMapping(value = { "/employeeList" }, method = RequestMethod.GET)
    public String employeeList(Model model) {

        logger.info("MainController web_spring_app_1 showAddEmployeeInfoPage() request API_Gateway_controller findAllEmployees() - START");

        model.addAttribute("employees", service.findAllEmployees());

        return "employeeList";
    }

    //[Spring MVC will give you the HttpRequest if you just add it to your controller method signature]
    //[https://stackoverflow.com/questions/8504258/spring-3-mvc-accessing-httprequest-from-controller] (:)
    //[https://stackoverflow.com/questions/40899494/how-to-get-input-values-from-spring-boot-thyme-leaf-to-java-class](:)
    @RequestMapping(value = { "/addEmployee" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String showAddEmployeeInfoPage(Model model) throws URISyntaxException {
        logger.info("MainController web_spring_app_1 showAddEmployeeInfoPage() request API_Gateway_controller - START");
        EmployeeForm employeeForm = new EmployeeForm();
        model.addAttribute("employeeForm", employeeForm);

        return "addEmployee";
    }

    //[https://stackoverflow.com/questions/40899494/how-to-get-input-values-from-spring-boot-thyme-leaf-to-java-class](:)
    //[The [HTML addEmployee.html] form uses Thymeleaf's th:object notation which we can refer to
    //using Spring's ModelAttribute method parameter]
    //[https://stackoverflow.com/questions/8504258/spring-3-mvc-accessing-httprequest-from-controller] (:)
    //[Spring MVC will give you the HttpRequest if you just add it to your controller method signature]
    @RequestMapping(value = { "/addEmployee" }, method = RequestMethod.POST)
    public String saveEmployee(Model model,
                               @ModelAttribute("employeeFormObj") EmployeeForm employeeFormObj
                              ) {
        logger.info("MainController web_spring_app_1 saveEmployee() request API_Gateway_controller - START");
        String employeeName = employeeFormObj.getEmployeeName();
        String employeeEmail = employeeFormObj.getEmployeeEmail();
        String employeeLogin = employeeFormObj.getEmployeeLogin();

        if (employeeName != null && employeeName.length() > 0
                && employeeEmail != null && employeeEmail.length() > 0
                && employeeLogin != null && employeeLogin.length() > 0) {
            EmployeeInfo newEmployeeInfo = new EmployeeInfo(employeeName, employeeEmail, employeeLogin);


            UUID newEmployeeUUID = service.createEmployee(newEmployeeInfo);
            //employees.add(newEmployeeInfo);
            logger.info("MainController web_spring_app_1 saveEmployee() request API_Gateway_controller - newEmployeeInfo UUID: " +
                        newEmployeeUUID.toString());
            return "redirect:/employeeList";
        }
        else {
            String customErrorMessage = "Employee name, e-mail, login should be filled!";
            model.addAttribute("errorMessageAttr", customErrorMessage);
            String changeAttrErrorMessage;
            if (employeeName == null || employeeName.length() == 0) {
                changeAttrErrorMessage = "Fill in employee Name, please!";
                model.addAttribute("errorInNameMessageAttr", changeAttrErrorMessage);
            }
            return "addEmployee";
        }
    }

/*
    //[https://stackoverflow.com/questions/8504258/spring-3-mvc-accessing-httprequest-from-controller] (:)
    //[Spring MVC will give you the HttpRequest if you just add it to your controller method signature]:
    @RequestMapping(value = "/report/{objectId}", method = RequestMethod.GET)
    public @ResponseBody void generateReport(
            @PathVariable("objectId") Long objectId,
            HttpServletRequest request,
            HttpServletResponse response) {

        // ...
        // Here you can use the request and response objects like:
        // response.setContentType("application/pdf");
        // response.getOutputStream().write(...);

    }
//*/
}
