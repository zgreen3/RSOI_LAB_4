package smirnov.bn.security_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import smirnov.bn.security_service.model.AuthorizationCodeInfo;
import smirnov.bn.security_service.model.TokenInfo;
import smirnov.bn.security_service.model.TokenInfoWithCustomAuthCode;
import smirnov.bn.security_service.service.AuthAndTokenServiceImpl;

import java.util.UUID;

@RestController //@Controller
@RequestMapping("/security_service/authorization")
public class OAuth2CustomController {
    private static final String WEB_SRVC_APP_ID_STRING = "WEB_SPR_APP_1_CLT_ID0_000_1";
    private static final String WEB_SRVC_APP_SECRET_STRING = "WEB_SPR_APP_1_CLT_0SECRET0STRING0_000_1";

    @Autowired
    private AuthAndTokenServiceImpl authAndTokenService; //= new AuthAndTokenServiceImpl();

    private static final Logger logger = LoggerFactory.getLogger(OAuth2CustomController.class);

    @RequestMapping(value = {"/create-auth-code"}, method = RequestMethod.POST)
    @ResponseBody
    public String createAuthCode(@RequestBody AuthorizationCodeInfo authorizationCodeInfo) {
        try {
            UUID newAuthCodeUuid = authAndTokenService.createAuthenticationCode(authorizationCodeInfo.getClientID(), authorizationCodeInfo.getRedirectionURI());
            logger.info("/security_service/authorization : /create-auth-code, createAuthCode() - CREATING" + "\n" + "uuid param: " + String.valueOf(newAuthCodeUuid));
            return newAuthCodeUuid.toString();
        } catch (Exception e) {
            logger.error("Error in createAuthCode(...)", e);
            return null;
        }
    }

    @RequestMapping(value = {"/auth-code-validation"}, method = RequestMethod.POST)
    @ResponseBody
    public String checkAuthCodeValidity(@RequestBody AuthorizationCodeInfo authorizationCodeInfo) {
        try {
            Boolean isAuthCodeValidBoolVar = authAndTokenService.checkAuthCodeValidity(authorizationCodeInfo.getAuthCodeUuid());
            logger.info("/security_service/authorization : /auth-code-validation, checkAuthCodeValidity() - CHEKING " + isAuthCodeValidBoolVar.toString());
            return isAuthCodeValidBoolVar.toString();
        } catch (Exception e) {
            logger.error("Error in isAuthCodeValid(...)", e);
            return null;
        }
    }

    @RequestMapping(value = {"/create-access-token", "/create-access-by-refresh-token"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TokenInfo> createAccessToken(@RequestBody TokenInfoWithCustomAuthCode tokenInfoWithCustomAuthCode) {
        TokenInfo tokenInfo = new TokenInfo();
        try {
            UUID newAccessTokenUuid;
            UUID refreshTokenForNewAccesTokenUUID;
            Boolean isClientIdAndSecretValidBoolVar;
            Boolean isAuthCodeValidBoolVar;
            UUID authCodeUuid = tokenInfoWithCustomAuthCode.getCustomAuthCodeUuid();
            String clientIdString = tokenInfoWithCustomAuthCode.getClientID();
            String clientSecretString = tokenInfoWithCustomAuthCode.getClientSecret();
            UUID refreshTokenUuid = tokenInfoWithCustomAuthCode.getRefreshTokenUuid();

            if (authCodeUuid != null) {
                isClientIdAndSecretValidBoolVar = authAndTokenService.checkClientIdAndSecretValidity(clientIdString, WEB_SRVC_APP_ID_STRING,
                        clientSecretString, WEB_SRVC_APP_SECRET_STRING);
                isAuthCodeValidBoolVar = authAndTokenService.checkAuthCodeValidity(authCodeUuid);
                if (isClientIdAndSecretValidBoolVar && isAuthCodeValidBoolVar) {
                    newAccessTokenUuid = authAndTokenService.createAccessToken(tokenInfoWithCustomAuthCode.getClientID());
                    logger.info("/security_service/authorization : /create-access-token, createAccessToken() - CREATING based on authCode + Client ID & Secret" +
                            "\n" + "uuid param: " + String.valueOf(newAccessTokenUuid));
                } else {
                    return new ResponseEntity<>(tokenInfo, HttpStatus.UNAUTHORIZED);
                }
            } else {
                newAccessTokenUuid = authAndTokenService.getNewAccessTokenUuidByRefreshTokenUuid(refreshTokenUuid, tokenInfoWithCustomAuthCode.getClientID());
                logger.info("/security_service/authorization : /create-access-token, createAccessToken() - CREATING based on refreshToken" + "\n" + "uuid param: " +
                        String.valueOf(newAccessTokenUuid));
            }
            tokenInfo.setAccessTokenUuid(newAccessTokenUuid);
            refreshTokenForNewAccesTokenUUID = authAndTokenService.getRefreshTokenUuid(newAccessTokenUuid);
            tokenInfo.setRefreshTokenUuid(refreshTokenForNewAccesTokenUUID);
            return new ResponseEntity<>(tokenInfo, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in createAccessToken(...)", e);
            return new ResponseEntity<>(tokenInfo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    @RequestMapping(value = {"/create-access-by-refresh-token"}, method = RequestMethod.POST)
    @ResponseBody
    public String createAccessTokenUsingRefreshToken(@RequestBody TokenInfo tokenInfo) {
        try {
            UUID newAccessTokenUuid = authAndTokenService.createAccessToken(tokenInfo.getClientID());
            logger.info("/security_service/authorization : /create-access-by-refresh-token, createAccessTokenUsingRefreshToken() - CREATING" +
                    "\n" + "uuid param: " + String.valueOf(newAccessTokenUuid));
            return newAccessTokenUuid.toString();
        } catch (Exception e) {
            logger.error("Error in createAccessTokenUsingRefreshToken(...)", e);
            return null;
        }
    }
    //*/

    @RequestMapping(value = {"/access-token-validation"}, method = RequestMethod.POST)
    @ResponseBody
    public String checkAccessTokenValidity(@RequestBody TokenInfo tokenInfo) {
        try {
            Boolean isTokenValidBoolVar = authAndTokenService.checkAccessTokenValidity(tokenInfo.getAccessTokenUuid());
            logger.info("/security_service/authorization : /access-token-validation, checkAccessTokenValidity() - CHEKING " + isTokenValidBoolVar.toString());
            return isTokenValidBoolVar.toString();
        } catch (Exception e) {
            logger.error("Error in isAuthCodeValid(...)", e);
            return null;
        }
    }

    @RequestMapping(value = {"/get-refresh-token"}, method = RequestMethod.GET)
    public ResponseEntity<String> getRefreshToken(@RequestBody UUID accessTokenUuid) {
        try {
            UUID refreshTokenUuid = authAndTokenService.getRefreshTokenUuid(accessTokenUuid);
            logger.info("/security_service/authorization : /get-refresh-token, getRefreshToken() - SEARCHING" + "\n" + "uuid param: " + String.valueOf(refreshTokenUuid));
            return new ResponseEntity<>(refreshTokenUuid.toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getRefreshToken(...)", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
