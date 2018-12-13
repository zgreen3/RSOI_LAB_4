package smirnov.bn.web_spring_app_1.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import smirnov.bn.web_spring_app_1.form.EmployeeForm;
import smirnov.bn.web_spring_app_1.model.EmployeeInfo;

@Controller
public class MainController {

    private static List<EmployeeInfo> employees = new ArrayList<EmployeeInfo>();

    static {
        employees.add(new EmployeeInfo("Bob", "b2b_log"));
        employees.add(new EmployeeInfo("Xman", "xman_log"));
    }

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

        model.addAttribute("employees", employees);

        return "employeeList";
    }

    @RequestMapping(value = { "/addEmployee" }, method = RequestMethod.GET)
    public String showAddEmployeeInfoPage(Model model) {

        EmployeeForm employeeForm = new EmployeeForm();
        model.addAttribute("employeeForm", employeeForm);

        return "addEmployee";
    }

    @RequestMapping(value = { "/addEmployee" }, method = RequestMethod.POST)
    public String saveEmployee(Model model, //
                             @ModelAttribute("employeeForm") EmployeeForm employeeForm) {

        String employeeName = employeeForm.getEmployeeName();
        String employeeLogin = employeeForm.getEmployeeLogin();

        if (employeeName != null && employeeName.length() > 0 //
                && employeeLogin != null && employeeLogin.length() > 0) {
            EmployeeInfo newEmployee = new EmployeeInfo(employeeName, employeeLogin);
            employees.add(newEmployee);

            return "redirect:/employeeList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addEmployee";
    }

}
