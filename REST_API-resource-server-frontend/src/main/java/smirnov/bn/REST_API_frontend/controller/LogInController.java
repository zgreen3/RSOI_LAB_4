package smirnov.bn.REST_API_frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.UUID;

import smirnov.bn.REST_API_frontend.form.UserForm;
import smirnov.bn.REST_API_frontend.model.UserInfo;
import smirnov.bn.REST_API_frontend.service.PasswordHashingHelper;
import smirnov.bn.REST_API_frontend.service.RestApiFrontendServiceImpl;

@Controller
public class LogInController {

    private RestApiFrontendServiceImpl service = new RestApiFrontendServiceImpl();

    private static final Logger logger = LoggerFactory.getLogger(LogInController.class);

    @RequestMapping(value = {"/loginUser"}, method = RequestMethod.GET)
    //, produces = "text/html;charset=UTF-8")
    public String showLoginUserPage(Model model) throws URISyntaxException {
        logger.info("MainController web_spring_app_1 showLoginUserPage() request to API_Gateway_controller - START");
        model.addAttribute("userForm", new UserForm());

        /*
        logger.info("MainController web_spring_app_1 createUser() request API_Gateway_controller - START");
        String userPasswordHash;
        try {
            userPasswordHash = PasswordHashingHelper.createHash("12345");
        } catch (PasswordHashingHelper.CannotPerformOperationException e) {
            userPasswordHash = "0";
                String exceptionString = "PasswordHashingHelper.CannotPerformOperationException: " + e.toString();
                System.out.println(exceptionString);
        }

        UserInfo newUserInfo = new UserInfo("Tester_1", userPasswordHash, "tester_1@test.com");

        UUID newUserUuid = service.createUser(newUserInfo);

        logger.info("MainController web_spring_app_1 createUser() request API_Gateway_controller - newUserInfo UUID: " +
                newUserUuid.toString());
        //*/

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

            String correctPasswordHash = service.findUserByLoginEmail(userLogin, userEmail).getUserPasswordHash();

            boolean authenticationIsSuccess;
            if (correctPasswordHash != null) {
                logger.info("MainController web_spring_app_1 authenticateUser() request to API_Gateway_controller - " +
                        "correctPasswordHash: " + correctPasswordHash);

                String exceptionString;
                try {
                    authenticationIsSuccess = PasswordHashingHelper.verifyPassword(userPassword, correctPasswordHash);
                } catch (PasswordHashingHelper.CannotPerformOperationException | PasswordHashingHelper.InvalidHashException e) {
                    exceptionString = "PasswordHashingHelper.CannotPerformOperationException: " + e.toString();
                    System.out.println(exceptionString);
                    return "loginUser";
                }
            } else {
                authenticationIsSuccess = false;
            }

            //только в случае успешной аутентификации перенаправляем на другую страницу (:)
            if (authenticationIsSuccess) {
                return "redirect:/index";
            } else {
                String customErrorMessage = "Can't find user with this combination of name, email & password!";
                model.addAttribute("errorMessageAttr", customErrorMessage);
                model.addAttribute("isErrorMessageAttrPresent", true);
                return "loginUser";
            }
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

    ///*
    //curl -X POST --data "{\"userLogin\":\"Tester_2\",\"userEmail\":\"tester_2@example.com\",\"userPassword\":\"123\"}" http://localhost:8201/user-backdoor-create --header "Content-Type:application/json"
    @RequestMapping(value = {"/user-backdoor-create"}, method = RequestMethod.POST)
    public String registerUser(@RequestBody UserForm userForm) {
        try {
            logger.info("MainController web_spring_app_1 createUser() request API_Gateway_controller - START");

            String userPasswordHash = PasswordHashingHelper.createHash(userForm.getUserPassword());

            UserInfo newUserInfo = new UserInfo(userForm.getUserLogin(), userPasswordHash, userForm.getUserEmail());

            UUID newUserUuid = service.createUser(newUserInfo);

            logger.info("MainController web_spring_app_1 createUser() request API_Gateway_controller - newUserInfo UUID: " +
                    newUserUuid.toString());

            //return HttpStatus.CREATED.toString();

        } catch (Exception e) {

            logger.error("Error in createUser(...)", e);

            //return HttpStatus.NO_CONTENT.toString();
        }
        return "loginUser";
    }
    //*/
}
