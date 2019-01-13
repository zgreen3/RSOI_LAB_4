package smirnov.bn.security_service.controller;

public class UserController {

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
    @Nullable
    @Override
    public boolean registrationUser(@Nonnull UserInfo userInfo) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_API_VK)
                .queryParam("user_ids", userInfo.getVk()).queryParam("fields", "online")
                .queryParam("access_token", VK_TOKEN);
        RestTemplate restTemplate = new RestTemplate();

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

}
