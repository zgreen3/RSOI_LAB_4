package smirnov.bn.service_3.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import smirnov.bn.service_3.service.TokenService;
import smirnov.bn.service_3.service.TokenServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static java.lang.Boolean.TRUE;

@Component
public class TokenCheckInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TokenCheckInterceptor.class);

    @Autowired
    private TokenService tokenService; //= new TokenServiceImpl();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("TokenCheckInterceptor service_3 preHandle() - START");

        String requestAuthHeaderValueString = request.getHeader("Authorization");

        if ((requestAuthHeaderValueString != null) &&
                (!requestAuthHeaderValueString.isEmpty()) &&
                (requestAuthHeaderValueString.contains("Bearer"))) {
            //**********проверка валидности / корректности token-а:**********
            logger.info("check [AppKey & AppSecret] Token() in service_3 class in preHandle() interceptor - START");

            //проверяем наличие валидного корректного токена, отбрасываем из значения заголовка строку "Bearer ":
            String[] tokenArray = requestAuthHeaderValueString.split(" ");
            String tokenUuidAsString = "0";
            if (tokenArray.length > 1) {
                tokenUuidAsString = tokenArray[1];
            }
            Boolean boolCheckVal = false;
            if ((tokenUuidAsString != null) && (!tokenUuidAsString.equals("null")) && (!tokenUuidAsString.equals("0"))) {
                boolCheckVal = tokenService.checkTokenValidity(UUID.fromString(tokenUuidAsString));
            }

            if (TRUE.equals(boolCheckVal)) {
                return true;
            } else {
                //https://stackoverflow.com/questions/39554740/springboot-how-to-return-error-status-code-in-prehandle-of-handlerinterceptor (:)
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        } else {
            //https://stackoverflow.com/questions/39554740/springboot-how-to-return-error-status-code-in-prehandle-of-handlerinterceptor (:)
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
