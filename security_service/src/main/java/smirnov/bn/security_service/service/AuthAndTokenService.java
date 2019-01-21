package smirnov.bn.security_service.service;

import java.util.UUID;

public interface AuthAndTokenService {

    UUID createAuthenticationCode(String clientId, String redirectionUri);

    Boolean checkAuthCodeValidity(UUID authenticationCodeUuid);

    UUID createAccessToken(String clientId);

    Boolean checkAccessTokenValidity(UUID accessTokenUuid);

}
