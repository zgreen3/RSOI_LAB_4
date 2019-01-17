package smirnov.bn.web_spring_app_1.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import smirnov.bn.web_spring_app_1.controller.MainController;

@Component
public class LogInCheckInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LogInCheckInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
            /*
            long startTime = System.currentTimeMillis();
            System.out.println("\n-------- LogInterception.preHandle --- ");
            System.out.println("Request URL: " + request.getRequestURL());
            System.out.println("Start Time: " + System.currentTimeMillis());

            request.setAttribute("startTime", startTime);
            //*/

        logger.info("LogInCheckInterceptor web_spring_app_1 preHandle() - START");

        //https://o7planning.org/en/11689/spring-boot-interceptors-tutorial (:)
        response.sendRedirect(request.getContextPath() + "/loginUser");
        return false;
    }
}
