package smirnov.bn.security_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.util.UriComponentsBuilder;
import smirnov.bn.security_service.model.UserInfo;
import smirnov.bn.security_service.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController //@Controller
@RequestMapping("/security_service")
public class UserController {

    //private static final String MAIN_HOST_STRING = "http://localhost:";
    //private static final String SECURITY_SERVICE_URI_DIR_STR = "/security_service";
    //private static final String SECURITY_SERVICE_PORT_STRING = "8194" + SECURITY_SERVICE_URI_DIR_STR;

    ////https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
    ////@Autowired
    //private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private UserServiceImpl userService; //= new UserServiceImpl();

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //hash password in Java(:)
    //https://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java
    //+/-*** https://crackstation.net/hashing-security.htm
    //https://www.baeldung.com/java-password-hashing
    //https://stackoverflow.com/questions/19348501/pbkdf2withhmacsha512-vs-pbkdf2withhmacsha1
    //https://crypto.stackexchange.com/questions/15602/what-is-a-secret-key-factory-what-precisely-is-it-doing

    //approximately status code 401 (“Unauthorized”) for bad password(:)
    //https://stackoverflow.com/questions/1959947/whats-an-appropriate-http-status-code-to-return-by-a-rest-api-service-for-a-val
    //https://ru.wikipedia.org/wiki/Список_кодов_состояния_HTTP

    //Spring Boot with multiple controllers(:)
    //https://stackoverflow.com/questions/37370948/spring-boot-api-with-multiple-controllers
    //https://smarterco.de/java-spring-boot-mvc-ontroller-not-called/
    //https://stackoverflow.com/questions/38240619/how-to-run-multiple-controllers-in-a-spring-boot-application/38242764
    //https://stackoverflow.com/questions/40501285/call-a-controller-from-another-controller-in-spring-boot

    //servlet redirection to another page:
    //https://stackoverflow.com/questions/40440230/spring-controller-redirect-to-another-module
    //https://stackoverflow.com/questions/14432167/make-a-rest-url-call-to-another-service-by-filling-the-details-from-the-form
    //https://docs.spring.io/spring/docs/3.1.x/spring-framework-reference/html/remoting.html#rest-resttemplate
    //https://stackoverflow.com/questions/27022741/redirecting-servlet-to-another-html-page

    //Spring boot interceptor (:)
    //https://www.tutorialspoint.com/spring_boot/spring_boot_interceptor.htm
    //***https://o7planning.org/en/11689/spring-boot-interceptors-tutorial
    //http://www.tutorialspoint.com/spring_boot/spring_boot_interceptor.htm
    //https://stackoverflow.com/questions/31082981/spring-boot-adding-http-request-interceptors

    //OAuth2:
    //https://habr.com/company/mailru/blog/115163/
    //https://tools.ietf.org/html/draft-ietf-oauth-v2-13#section-5.2
    //https://www.baeldung.com/rest-api-spring-oauth2-angularjs
    //https://www.digitalocean.com/community/tutorials/oauth-2-ru
    //https://stackoverflow.com/questions/50083358/options-to-setup-oauth-2-signup-signin-policy-for-customers-login-to-digital-oce
    //https://stackoverflow.com/questions/10296681/is-there-an-oauth-2-0-provider-implementation-in-java-not-oauth-client
    //https://github.com/SenseGrow/java-oauth2-provider
    //https://blog.overops.com/tutorial-how-to-implement-java-oauth-2-0-to-sign-in-with-github-and-google/
    //https://stackoverflow.com/questions/4153022/is-there-a-oauth2-library-for-java-android-already
    //https://softwarerecs.stackexchange.com/questions/1870/java-library-to-implement-oauth-2-0-based-authentication-in-a-web-application
    //https://www.javacodegeeks.com/2015/05/tutorial-how-to-implement-java-oauth-2-0-to-sign-in-with-github-and-google.html

    //GitHubs (:)
    //https://github.com/akashkinKV/api-statonline/blob/bb512b350eebcb2981dd37a211534bf387288379/src/test/java/resources/templates/index.html
    //https://github.com/akashkinKV/laba2-akashkin/blob/master/api.gateway/src/main/java/hello/api/gateway/GateWay.java#L100
    //https://github.com/akashkinKV/laba2-akashkin/blob/master/api.user/src/main/java/hello/api/users/service/UserServiceImpl.java
    //https://github.com/akashkinKV/laba2-akashkin/blob/master/api.user/src/main/java/hello/api/users/model/UserInfo.java
    //https://github.com/akashkinKV/laba2-akashkin/blob/master/api.user/src/main/java/hello/api/users/web/UsersController.java
    //
    //https://habr.com/post/332482/
    //https://github.com/z17/GamePro100/blob/master/user-service/src/main/resources/application.properties
    //https://github.com/z17/GamePro100/blob/master/user-service/src/main/java/user/service/UserService.java

    /*
    //https://github.com/akashkinKV/laba2-akashkin/blob/master/api.user/src/main/java/hello/api/users/service/UserServiceImpl.java (:)

    //HttpSession logon and logged-in check (:)
    //https://stackoverflow.com/questions/24759384/checking-if-user-is-logged-in-or-not-using-sessions-java-servlet
    @RequestMapping(value = {"/registration"}, method = RequestMethod.POST)
    //@PostMapping("/registration")
    public ResponseEntity<String>  registerUser(UserInfo userInfo) {
        logger.info("security_srevice_controller registerUser(...) - START");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_API_VK)
                .queryParam("user_ids", userInfo.getVk()).queryParam("fields", "online")
                .queryParam("access_token", VK_TOKEN);
        //RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method and default Headers.
        String jsonString = restTemplate.getForObject(builder.toUriString(), String.class);
        if (!jsonString.contains("error_code")) {
            if (userInfo.getUid() == null) userInfo.setUid(UUID.randomUUID());
            String encryptedPassword = passwordEncoder.encode(userInfo.getPassword());
            userInfo.setPassword(encryptedPassword);
            userRepos.saveAndFlush(createUser(userInfo));
            return true;
        } else {
            return false;
        }
    }
    //*/

    /*
    //"/ling_var_dict/create-ling_var" (:)
    @PostMapping(SERVICE_1_URI_COMMON_DIR_STRING + CREATE_LNG_VR_POST_URI_STRING)
    public ResponseEntity<String> createLingVar(HttpServletRequest request, @RequestBody LingVarInfo lingVarInfo)
            throws URISyntaxException {
        logger.info("API_Gateway_controller createLingVar() - START");
        return this.proxingExternalRequests(lingVarInfo, HttpMethod.POST, request,
                CREATE_LNG_VR_POST_URI_TMPLT);
    }
    //*/

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody UserInfo userInfo) {
        try {
            UUID newUserUuid = userService.createUser(userInfo);
            logger.info("/security_service : /create-user, createUser() - CREATING" + "\n" + "uuid param: " + String.valueOf(newUserUuid));
            return new ResponseEntity<>(newUserUuid.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error in createUser(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*
    @GetMapping("/read-{id}")
    public ResponseEntity<UserInfo> findUserById(@PathVariable Integer id) {
        try {
            return new ResponseEntity(userService.findUserById(id), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in findUserById(...)", e);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }
    //*/

    @GetMapping("/read-by-emp-uuid-{userUuid}")
    public ResponseEntity<UserInfo> findUserByUuid(@PathVariable String userUuid) {
        try {
            logger.info("/security_service : findUserByUuid() - START" + "\n" + "uuid param: " + String.valueOf(userUuid));
            return new ResponseEntity<>(userService.findUserByUuid(UUID.fromString(userUuid)), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in findUserByUuid(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/read-by-emp-uuid-{userUuid}")
    public ResponseEntity<UserInfo> findUserByUuid(@PathVariable String userUuid) {
        try {
            logger.info("/security_service : findUserByUuid() - START" + "\n" + "uuid param: " + String.valueOf(userUuid));
            return new ResponseEntity<>(userService.findUserByUuid(UUID.fromString(userUuid)), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in findUserByUuid(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/read-all") //http://localhost:8193/users/read-all
    public ResponseEntity<List<UserInfo>> findAllUsers() {
        try {
            logger.info("/security_service : findAllUsers() - START");
            List<UserInfo> usersInfo = userService.findAllUsers();

            if (usersInfo != null && usersInfo.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(usersInfo, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error in findAllUsers(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/read-all-paginated", params = {"page", "sizeLimit"}, method = GET)
    @ResponseBody
    public ResponseEntity<List<UserInfo>> findAllUsersPaginated(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                        @RequestParam(value = "sizeLimit", defaultValue = "100")
                                                                                int sizeLimit) {
        try {
            logger.info("/security_service : findAllUsersPaginated() - START" + "\n" + "page param: " + String.valueOf(page) + "\n" +
                    "sizeLimit param: " + String.valueOf(sizeLimit));
            List<UserInfo> usersInfo = userService.findAllUsersPaginated(page, sizeLimit);

            if (usersInfo != null && usersInfo.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(usersInfo, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error in findAllUsersPaginated(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //curl -i -X PUT -H "Content-Type: application/json;charset=UTF-8" --data "{\"userId\":\"\",\"userName\":\"User_2\",\"userEmail\":\"myEmail_2_3@example.com\",\"userLogin\":\"MyName_2_3\",\"userUuid\":\"9cbc6e2a-417d-4313-955c-fb58c2da7dc8\"}" http://localhost:8193/users/update-user
    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestBody UserInfo userInfo) {
        try {
            logger.info("/security_service : updateUser() - START" + "\n");
            if (userInfo.getUserUuid() != null) {
                logger.info("uuid param: " + String.valueOf(userInfo.getUserUuid()));
            } else {
                logger.info("uuid param: null");
            }

            userService.updateUser(userInfo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateUser(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*
    @DeleteMapping("/delete-{id}")
    public ResponseEntity deleteUserById(@RequestParam Integer userId) {
        try {
            userService.deleteUserById(userId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in deleteUserById(...)", e);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }
    //*/

    //curl -X DELETE http://localhost:8193/users/delete-d843ca38-5b63-405e-8bb3-abfeaeded3a4
    @DeleteMapping("/delete-{userUuid}")
    public ResponseEntity<String> deleteUserByUuid(@PathVariable UUID userUuid) {
        try {
            logger.info("/security_service : deleteUserByUuid() - START" + "\n" + "uuid param: " + String.valueOf(userUuid));
            userService.deleteUserByUuid(userUuid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in deleteUserByUuid(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
