package smirnov.bn.service_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

import smirnov.bn.service_1.entity.Token;
import smirnov.bn.service_1.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {

    private static final int TOKEN_HOURS_OF_LIFE = 1;

    @Autowired
    private TokenRepository tokenRepository;

    /*
    public Boolean checkAppKeyAndSecretValidity(String AppKeyString, String benchmarkAppKeyString,
                                         String AppSecretString, String  benchmarkAppSecretString) {
        if ((AppKeyString != null) && AppKeyString.equals(benchmarkAppKeyString)
                && (AppSecretString != null)
                && AppSecretString.equals(benchmarkAppSecretString)) {
            return true;
        } else {
            return false;
        }
    }
    //*/

    public UUID createToken(String appKey) {
        tokenRepository.updateAllFilteredTokensValidity();
        Token token = new Token();
        token.setTokenUuid(UUID.randomUUID());
        token.setClientID(appKey);
        token.setCreatedDateTime(LocalDateTime.now());
        token.setTokenType("password"); //"Password" OAuth2 authorization Grant Type (https://oauth.net/2/grant-types/)
        token.setLastUsedDateTime(token.getCreatedDateTime());
        token.setExpiresInSeconds((long) (TOKEN_HOURS_OF_LIFE * 60 * 60));
        token.setExpired(false);
        token.setInvalidated(false);
        //token.setRefreshTokenUuid(UUID.randomUUID());
        tokenRepository.saveAndFlush(token);
        return token.getTokenUuid();
    }

    public Boolean checkTokenValidity(UUID tokenUuid) {
        //tokenRepository.updateTokenValidity(tokenUuid);
        tokenRepository.updateAllFilteredTokensValidity();
        Token tokenToCheck = tokenRepository.findByUuid(tokenUuid);
        if ((tokenToCheck != null) && tokenToCheck.getInvalidated().equals(false)
                && tokenToCheck.getExpired().equals(false)) {
            return true;
        } else {
            return false;
        }
    }
}
