package smirnov.bn.web_frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;
import smirnov.bn.web_frontend.form.UserForm;
import smirnov.bn.web_frontend.model.UserInfo;
import smirnov.bn.web_frontend.service.PasswordHashingHelper;
import smirnov.bn.web_frontend.service.WebFrontendServiceImpl;

@Controller
public class LogInController {

    private static final String WEB_SRVC_APP_ID_STRING = "WEB_SPR_APP_1_CLT_ID0_000_1";
    private static final String WEB_SRVC_APP_SECRET_STRING = "WEB_SPR_APP_1_CLT_0SECRET0STRING0_000_1";

    private String afterSigningInRedirectionUriString;
    private String clientIdFromRequestParam;

    private WebFrontendServiceImpl service = new WebFrontendServiceImpl();

    private static final Logger logger = LoggerFactory.getLogger(LogInController.class);

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public class ForbiddenException extends RuntimeException {
    }

    @RequestMapping(value = {"/loginUser", "oauth/authorize"},
            method = RequestMethod.GET)
    //, produces = "text/html;charset=UTF-8")
    public String showLoginUserPage(Model model,
                                    @RequestParam(value = "client_id", required = false) String clientServiceId,
                                    //@RequestParam(value = "client_secret", required = false) String clientSecret,
                                    @RequestParam(value = "redirect_uri", required = false) String callBackRedirectUri
    ) throws URISyntaxException {
        logger.info("LogInController rest_api_frontend showLoginUserPage() request to API_Gateway_controller - START");

        //проверка, что clientID //и clientSecret
        //совпада[ю]ет с заданным[и] в коде (hard-coded) значени[ями]ем соответствующ[их]ей констант[]ы:
        if ((!clientServiceId.equals(WEB_SRVC_APP_ID_STRING)) //&& (!clientSecret.equals(WEB_SRVC_APP_SECRET_STRING))
                ) {
            //String customErrorMessage = "Client ID or secret do not match the ones in DB!";
            //model.addAttribute("errorMessageAttr", customErrorMessage);
            //model.addAttribute("isErrorMessageAttrPresent", true);
            //System.out.println(customErrorMessage);
            //return "loginUser" //"error";
            throw new ForbiddenException();
        }

        //сохраняем (локально, в переменной экземпляра класса, в ОЗУ, в JVM memory)
        //redirect URL для последующей передачи по этому адресу authorization code(:)
        afterSigningInRedirectionUriString = callBackRedirectUri;
        //client_id для последующего возврата вместе с redirect URL
        //(для предотвращения CSRF-атак, https://habr.com/ru/company/mailru/blog/115163/ ; https://en.wikipedia.org/wiki/Cross-site_request_forgery)
        clientIdFromRequestParam = clientServiceId;

        model.addAttribute("userForm", new UserForm());
        model.addAttribute("clientServiceId", clientServiceId);

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

    @RequestMapping(value = {"/loginUser", "oauth/authorize"}, method = RequestMethod.POST)
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

            //ищем хэш выбранного пользователем при регистрации пароля в БД:
            String correctPasswordHash = service.findUserByLoginEmail(userLogin, userEmail).getUserPasswordHash();

            //сравниваем хэш из БД с хешем от пароля, введённого пользователем на форме аутентификации:
            boolean authenticationIsSuccess;
            if (correctPasswordHash != null) {
                logger.info("MainController web_spring_app_1 authenticateUser() request to API_Gateway_controller - " +
                        "correctPasswordHash: " + correctPasswordHash);

                String exceptionString;
                try {
                    authenticationIsSuccess = PasswordHashingHelper.verifyPassword(userPassword, correctPasswordHash);
                } catch (PasswordHashingHelper.CannotPerformOperationException | PasswordHashingHelper.InvalidHashException e) {
                    exceptionString = "PasswordHashingHelper.CannotPerformOperationException: " + e.toString();
                    model.addAttribute("errorMessageAttr", exceptionString);
                    model.addAttribute("isErrorMessageAttrPresent", true);
                    System.out.println(exceptionString);
                    return "loginUser"; //throw new ForbiddenException();
                }
            } else {
                authenticationIsSuccess = false;
            }

            //только в случае успешной аутентификации выдаём authorizationCode и перенаправляем на другую страницу (:)
            if (authenticationIsSuccess) {
                //запрос authorizationCode через GatewayAPI у SecurityServer:
                UUID authorizationCodeUuid = service.createAuthenticationCode(WEB_SRVC_APP_ID_STRING, afterSigningInRedirectionUriString);

                //URIBuilder uriBuilder = new URIBuilder();

                //uriBuilder.addParameter("abc", "xyz");

                String uriForRedirectionAsString;
                try {
                    uriForRedirectionAsString =
                    /*
                            UriComponentsBuilder
                                    .fromHttpUrl(afterSigningInRedirectionUriString)
                                    .queryParam("code", URLEncoder.encode(authorizationCodeUuid.toString(), "UTF-8"))
                                    .build().encode().toUriString();
                    //*/
                            UriComponentsBuilder//.newInstance()
                                    //.scheme("http").host("localhost").port(8201).path(URLDecoder.decode(afterSigningInRedirectionUriString, "UTF-8"))
                                    .fromUriString(URLDecoder.decode(afterSigningInRedirectionUriString + "/" + clientIdFromRequestParam, "UTF-8"))
                                    .queryParam("code", URLEncoder.encode(authorizationCodeUuid.toString(), "UTF-8"))
                                    .build().toString();
                } catch (UnsupportedEncodingException e) {
                    return "UnsupportedEncodingException: " + e.toString();
                }
                return "redirect:" + uriForRedirectionAsString;
            } else {
                String customErrorMessage = "Can't find user with this combination of name, email & password!";
                model.addAttribute("errorMessageAttr", customErrorMessage);
                model.addAttribute("isErrorMessageAttrPresent", true);
                System.out.println(customErrorMessage);
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
            return "loginUser";
        }
    }

    ///*
    //curl -X POST --data "{\"userLogin\":\"Tester_2\",\"userEmail\":\"tester_2@example.com\",\"userPassword\":\"12345\"}" http://localhost:8202/security_service/create-user --header "Content-Type:application/json"
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
        //N.B., TODO: [после 19.01.19 желательно сделать отдельную форму регистрации нового пользователя, Ex, registerUser,
        //которую и возвращать в return-коде далее]:
        return "loginUser";
    }
    //*/
}
