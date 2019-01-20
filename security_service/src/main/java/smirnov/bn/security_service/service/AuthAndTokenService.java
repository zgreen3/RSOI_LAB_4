package smirnov.bn.security_service.service;

import java.util.UUID;

public interface AuthAndTokenService {

    UUID createAuthenticationCode(String clientId, String redirectionUri);

    Boolean isAuthCodeValid(UUID authenticationCodeUuid);

    UUID createAccessToken(String clientId);

    Boolean isAccessTokenValid(UUID accessTokenUuid);

}
