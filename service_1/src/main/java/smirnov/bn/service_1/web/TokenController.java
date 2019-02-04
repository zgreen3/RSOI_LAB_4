package smirnov.bn.service_1.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;

import smirnov.bn.service_1.model.TokenInfo;
import smirnov.bn.service_1.service.TokenServiceImpl;

@RestController
@RequestMapping("/ling_var_dict/authorization")
public class TokenController {
    //App key and App Secret from corresponding service (:)
    private static final String API_GATEWAY_KEY_STRING = "API_GATEWAY_APP_KEY0_000_1";
    private static final String API_GATEWAY_SECRET_STRING = "API_GATEWAY_APP_0SECRET0STRING0_000_1";

    @Autowired
    private TokenServiceImpl tokenService;

    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    //https://stackoverflow.com/questions/19556039/how-to-get-access-to-http-header-information-in-spring-mvc-rest-controller (:)
    @RequestMapping(value = {"/get-token"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TokenInfo> createToken(@RequestHeader HttpHeaders headers) {
        logger.info("TokenController service_1 createToken() - START");

        TokenInfo tokenInfo = new TokenInfo();
        try {
            String authHeaderValueString = headers.getFirst("Authorization");
            //создаём токен только, если переданные API_GATEWAY_KEY_STRING и API_GATEWAY_SECRET_STRING совпадают
            //с известными заранее (hardcoded) данному сервису значениями:
            if ((authHeaderValueString != null) && (!authHeaderValueString.isEmpty()) &&
                    authHeaderValueString.equals("Basic " +
                            Base64.getEncoder().encodeToString((API_GATEWAY_KEY_STRING + ":" + API_GATEWAY_SECRET_STRING).getBytes()))) {
                tokenInfo.setTokenType("password"); //"Password" OAuth2 authorization Grant Type (https://oauth.net/2/grant-types/)
                tokenInfo.setAccessTokenUuid(tokenService.createToken(API_GATEWAY_KEY_STRING));
                return new ResponseEntity<>(tokenInfo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(tokenInfo, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.error("Error in createToken(...)", e);
            return new ResponseEntity<>(tokenInfo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
