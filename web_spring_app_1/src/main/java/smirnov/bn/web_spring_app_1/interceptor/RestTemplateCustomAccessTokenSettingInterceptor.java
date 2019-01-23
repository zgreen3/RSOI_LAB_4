package smirnov.bn.web_spring_app_1.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

public class RestTemplateCustomAccessTokenSettingInterceptor implements ClientHttpRequestInterceptor {

    //[http://springinpractice.com/2013/10/27/how-to-send-an-http-header-with-every-request-with-spring-resttemplate] [:]

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        /*
        //получаем access token из cookies-"хранилища" (в ОЗУ / in JVM memory):
        String accessTokenUuidStr = "0";
        //https://stackoverflow.com/questions/33690741/httpservletrequest-getcookies-or-getheader (:)
        if (request.getHeaders().getFirst("Cookie") != null) {
            String rawCookie = request.getHeaders().getFirst("Cookie");
            String[] rawCookieParams = rawCookie.split(";");
            for(String rawCookieNameAndValue : rawCookieParams)
            {
                String[] rawCookieNameAndValuePair = rawCookieNameAndValue.split("=");
                if (rawCookieNameAndValuePair[0].equals("AccessTokenID")) {
                    accessTokenUuidStr = rawCookieNameAndValuePair[1];
                    break;
                }
            }

//            accessTokenUuidStr = Arrays.stream(rawCookieParams)
//                    .filter(c -> c.getName().equals("AccessTokenID"))
//                    .findFirst()
//                    .map(Cookie::getValue)
//                    .orElse(null);
        }
        //https://stackoverflow.com/questions/2811769/adding-an-http-header-to-the-request-in-a-servlet-filter (:)
        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", "Bearer " + accessTokenUuidStr);

        ////https://www.baeldung.com/spring-rest-template-interceptor (:)
        //ClientHttpResponse response = execution.execute(request, body);
        //response.getHeaders().add("Foo", "bar");
        //return response;

        //*/
        return execution.execute(request, body);
    }
}