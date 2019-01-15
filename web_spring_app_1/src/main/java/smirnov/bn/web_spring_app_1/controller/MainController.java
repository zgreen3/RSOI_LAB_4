package smirnov.bn.web_spring_app_1.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import smirnov.bn.web_spring_app_1.form.EmployeeForm;
import smirnov.bn.web_spring_app_1.form.UserForm;
import smirnov.bn.web_spring_app_1.model.EmployeeInfo;
import smirnov.bn.web_spring_app_1.model.UserInfo;
import smirnov.bn.web_spring_app_1.service.WebAppServiceImpl;


@Controller
public class MainController {
    private static final String MAIN_WEB_SERVER_HOST_STRING = "http://localhost:";
    private static final String API_SERVICE_URI_CMN_DIR_STRING = "/gateway_API";
    private static final String API_SERVICE_PORT_STRING = "8194" + API_SERVICE_URI_CMN_DIR_STRING;

    private static final String SERVICE_3_PORT_STRING = API_SERVICE_PORT_STRING; //"8193";
    private static final String SERVICE_3_URI_COMMON_DIR_STRING = "/employees/";
    private static final String SERVICE_3_ABS_URI_COMMON_STRING = MAIN_WEB_SERVER_HOST_STRING + SERVICE_3_PORT_STRING + SERVICE_3_URI_COMMON_DIR_STRING;

    private static final String READ_ALL_EMP_GET_URI_STRING = "read-all";
    private static final String READ_ALL_EMP_GET_URI_TMPLT = SERVICE_3_ABS_URI_COMMON_STRING + READ_ALL_EMP_GET_URI_STRING;

    ////https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
    ////@Autowired
    //private RestTemplate restTemplate = new RestTemplate();

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

    //@Value("${error.message}")
    //private String errorMessage;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    @RequestMapping(value = {"/employeeList"}, method = RequestMethod.GET)
    public String employeeList(Model model) {

        logger.info("MainController web_spring_app_1 showemployeeInfoPage() request API_Gateway_controller findAllEmployees() - START");

        model.addAttribute("employees", service.findAllEmployees());

        return "employeeList";
    }

    //[Spring MVC will give you the HttpRequest if you just add it to your controller method signature]
    //[https://stackoverflow.com/questions/8504258/spring-3-mvc-accessing-httprequest-from-controller] (:)
    //[https://stackoverflow.com/questions/40899494/how-to-get-input-values-from-spring-boot-thyme-leaf-to-java-class](:)
    //https://stackoverflow.com/questions/17821731/spring-mvc-how-to-indicate-whether-a-path-variable-is-required-or-not (:)
    //https://stackoverflow.com/questions/4904092/with-spring-3-0-can-i-make-an-optional-path-variable (:)
    @RequestMapping(value = {"/employee/{employeeUUID}", "/employee", "/employee/"}, method = RequestMethod.GET)
    //, produces = "text/html;charset=UTF-8")
    public String showEmployeePage(Model model, @PathVariable("employeeUUID") Optional<UUID> employeeUuid) throws URISyntaxException {
        logger.info("MainController web_spring_app_1 showEmployeePage() request API_Gateway_controller - START");
        EmployeeForm employeeForm;
        if (!employeeUuid.isPresent()) {
            employeeForm = new EmployeeForm();
            //employeeCreating (:)
            model.addAttribute("employeeEditing", false);
        } else {
            EmployeeInfo newEmployeeInfo = service.findEmployeeByUuid(employeeUuid.get());
            employeeForm = new EmployeeForm(newEmployeeInfo.getEmployeeId(), newEmployeeInfo.getEmployeeName(),
                    newEmployeeInfo.getEmployeeEmail(), newEmployeeInfo.getEmployeeLogin(), newEmployeeInfo.getEmployeeUuid());
            model.addAttribute("employeeEditing", true);
        }
        model.addAttribute("employeeForm", employeeForm);
        return "employee";
    }

    //[https://stackoverflow.com/questions/40899494/how-to-get-input-values-from-spring-boot-thyme-leaf-to-java-class](:)
    //[The [HTML employee.html] form uses Thymeleaf's th:object notation which we can refer to
    //using Spring's ModelAttribute method parameter]
    //[https://stackoverflow.com/questions/8504258/spring-3-mvc-accessing-httprequest-from-controller] (:)
    //[Spring MVC will give you the HttpRequest if you just add it to your controller method signature]
    @RequestMapping(value = {"/employee/{employeeUUID}", "/employee", "/employee/"}, method = RequestMethod.POST)
    public String saveEmployee(Model model,
                               @Valid @ModelAttribute("employeeForm") EmployeeForm employeeForm,
                               BindingResult result,
                               @PathVariable("employeeUUID") Optional<UUID> employeeUuid
    ) {
        logger.info("MainController web_spring_app_1 saveEmployee() request API_Gateway_controller - START");
        if (result.hasErrors()) {
            return "employee";
        }

        String employeeName = employeeForm.getEmployeeName();
        String employeeEmail = employeeForm.getEmployeeEmail();
        String employeeLogin = employeeForm.getEmployeeLogin();

        if (employeeName != null && employeeName.length() > 0
                && employeeEmail != null && employeeEmail.length() > 0
                && employeeLogin != null && employeeLogin.length() > 0) {
            EmployeeInfo newEmployeeInfo = new EmployeeInfo(employeeName, employeeEmail, employeeLogin);

            if (employeeUuid.isPresent()) {
                newEmployeeInfo.setEmployeeUuid(employeeUuid.get());
                service.updateEmployee(newEmployeeInfo);
                logger.info("MainController web_spring_app_1 saveEmployee() request API_Gateway_controller - employee being edited UUID: " +
                        employeeUuid.get().toString());
            } else {
                UUID newEmployeeUUID = service.createEmployee(newEmployeeInfo);
                //employees.add(newEmployeeInfo);
                logger.info("MainController web_spring_app_1 saveEmployee() request API_Gateway_controller - newEmployeeInfo UUID: " +
                        newEmployeeUUID.toString());
            }
            return "redirect:/employeeList";
        } else {
            if (employeeLogin == null || employeeLogin.length() == 0) {
                String customErrorMessage = "Employee's login should be filled!";
                model.addAttribute("errorMessageAttr", customErrorMessage);
                model.addAttribute("isErrorMessageAttrPresent", true);
            }
            return "employee";
        }
    }

    //no PUT and DELETE methods on HTML forms (:)
    //https://softwareengineering.stackexchange.com/questions/114156/why-are-there-are-no-put-and-delete-methods-on-html-forms
    //(+) https://stackoverflow.com/questions/44170932/attempting-to-make-a-delete-button-in-thyme-leaf-spring
    //(+) https://stackoverflow.com/questions/22794057/thymeleaf-send-parameter-from-html-to-controller
    //(+) https://stackoverflow.com/questions/43606063/spring-thymeleaf-delete-object-from-html-table-and-pass-id-to-controller?rq=1
    //https://javatalks.ru/topics/question/46293
    //https://stackoverflow.com/questions/13629653/using-put-and-delete-methods-in-spring-mvc
    //https://stackoverflow.com/questions/18418132/handling-delete-in-rest?noredirect=1&lq=1
    //https://docs.spring.io/spring/docs/4.2.x/javadoc-api/org/springframework/web/filter/HiddenHttpMethodFilter.html
    //https://stackoverflow.com/questions/24256051/delete-or-put-methods-in-thymeleaf
    //https://www.w3schools.com/tags/att_input_type_hidden.asp
    //https://stackoverflow.com/questions/38370011/thymeleaf-button-click-to-call-http-delete-method?noredirect=1&lq=1
    @RequestMapping(value = {"/employee/{employeeUuid}/delete"}, method = RequestMethod.DELETE)
    //RequestMethod.DELETE) //RequestMethod.POST)
    //@PostMapping("/employee_delete_{employeeUuid}")
    public //@ResponseBody
    String deleteEmployee(Model model,
                          @PathVariable("employeeUuid") String employeeUuid
    ) {
        logger.info("MainController web_spring_app_1 deleteEmployee() request API_Gateway_controller - START");
        service.deleteEmployeeByUuid(UUID.fromString(employeeUuid));
        return "redirect:/employeeList";
    }

    @RequestMapping(value = {"/loginUser"}, method = RequestMethod.GET)
    //, produces = "text/html;charset=UTF-8")
    public String showLoginUserPage(Model model) throws URISyntaxException {
        logger.info("MainController web_spring_app_1 showLoginUserPage() request to API_Gateway_controller - START");

        return "loginUser";
    }

    @RequestMapping(value = {"/loginUser"}, method = RequestMethod.POST)
    public String authenticateUser(Model model,
                                   @Valid @ModelAttribute("userForm") UserForm userForm,
                                   BindingResult result) {
        logger.info("MainController web_spring_app_1 authenticateUser() request to API_Gateway_controller - START");
        if (result.hasErrors()) {
            return "loginUser";
        }

        String userLogin = userForm.getUserLogin();
        String userPassword = userForm.getUserPassword();
        String userEmail = userForm.getUserEmail();

        if (userLogin != null && userLogin.length() > 0
            && userPassword != null && userPassword.length() > 0
                && userEmail != null && userEmail.length() > 0) {

            //UUID detectedUserUUID = service.findUserByLoginEmail(userLogin, userEmail);
            //String correctPasswordHash = service.findUserPasswordHashByUuid(detectedUserUUID);
            //PasswordHashingHelper.verifyPassword(userPassword, correctPasswordHash);

            //String userPasswordHash = service.hashPassword(userPassword);

            //UserInfo userInfo = new UserInfo(userLogin, userPasswordHash, userEmail);



            logger.info("MainController web_spring_app_1 authenticateUser() request to API_Gateway_controller - " +
                        "detectedUserInfo UUID: " //+
                        //detectedUserUUID.toString()
                        );

            return "redirect:/index";
        } else {
            if (userLogin == null || userLogin.length() == 0) {
                String customErrorMessage = "User's login should be filled!";
                model.addAttribute("errorMessageAttr", customErrorMessage);
                model.addAttribute("isErrorMessageAttrPresent", true);
            } else if (userPassword == null || userPassword.length() == 0) {
                String customErrorMessage = "User's password should be filled!";
                model.addAttribute("errorMessageAttr", customErrorMessage);
                model.addAttribute("isErrorMessageAttrPresent", true);
            } else if (userEmail == null || userEmail.length() == 0) {
                String customErrorMessage = "User's e-mail should be filled!";
                model.addAttribute("errorMessageAttr", customErrorMessage);
                model.addAttribute("isErrorMessageAttrPresent", true);
            }
        }
            return "loginUser";
        }
}
