package smirnov.bn.security_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Nullable;

import smirnov.bn.security_service.entity.AuthorizationCode;
import smirnov.bn.security_service.entity.Token;
import smirnov.bn.security_service.repository.AuthRepository;
import smirnov.bn.security_service.repository.TokenRepository;

@Service
public class AuthAndTokenServiceImpl implements AuthAndTokenService {

    private static final int AUTHENTICATION_CODE_MINUTES_OF_LIFE = 60;
    private static final int ACCESS_TOKEN_HOURS_OF_LIFE = 8;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Nullable
    @Override
    @Transactional
    public UUID createAuthenticationCode(String clientId, String redirectionUri) {
        AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setAuthCodeUuid(UUID.randomUUID());
        authorizationCode.setClientID(clientId);
        authorizationCode.setRedirectionURI(redirectionUri);
        //https://stackoverflow.com/questions/2305973/java-util-date-vs-java-sql-date/2306051#2306051 (,)
        //https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java (,)
        //https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/ (,)
        //https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html (:)
        authorizationCode.setCreatedDateTime(LocalDateTime.now());
        //время жизни access токена (не принимая во внимание его инвалидацию после 30 мин. бездействия):
        authorizationCode.setExpiresInSeconds((long) (AUTHENTICATION_CODE_MINUTES_OF_LIFE * 60));
        authorizationCode.setExpired(false);
        authorizationCode.setUsed(false);
        authRepository.saveAndFlush(authorizationCode);
        return authorizationCode.getAuthCodeUuid();
    }

    public Boolean checkAuthCodeValidity(UUID authenticationCodeUuid) {
        AuthorizationCode authorizationCodeToCheck = authRepository.findByUuid(authenticationCodeUuid);
        if ((authorizationCodeToCheck != null) && authorizationCodeToCheck.getUsed().equals(false)
                && authorizationCodeToCheck.getExpired().equals(false)) {
            return true;
        } else {
            return false;
        }
    }

    public UUID createAccessToken(String clientId) {
        Token accessToken = new Token();
        accessToken.setAccessTokenUuid(UUID.randomUUID());
        accessToken.setTokenType("code"); //"authorisation code [flow]"
        accessToken.setClientID(clientId);
        accessToken.setCreatedDateTime(LocalDateTime.now());
        accessToken.setLastUsedDateTime(accessToken.getCreatedDateTime());
        accessToken.setExpiresInSeconds((long) (ACCESS_TOKEN_HOURS_OF_LIFE * 60 * 60));
        accessToken.setExpired(false);
        accessToken.setInvalidated(false);
        accessToken.setRefreshTokenUuid(UUID.randomUUID());
        tokenRepository.saveAndFlush(accessToken);
        return accessToken.getAccessTokenUuid();
    }

    public Boolean checkAccessTokenValidity(UUID accessTokenUuid) {
        Token accessTokenToCheck = tokenRepository.findByUuid(accessTokenUuid);
        if ((accessTokenToCheck != null) && accessTokenToCheck.getInvalidated().equals(false)
                && accessTokenToCheck.getExpired().equals(false)) {
            return true;
        } else {
            return false;
        }
    }
}
