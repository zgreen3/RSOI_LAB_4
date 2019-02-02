package smirnov.bn.security_service.service;

import java.util.UUID;

public interface AuthAndTokenService {

    UUID createAuthenticationCode(String clientId, String redirectionUri);

    Boolean checkClientIdAndSecretValidity(String clientIdString, String benchmarkClientIdString,
                                           String clientSecretString, String  benchmarkClientSecretString);

    Boolean checkAuthCodeValidity(UUID authenticationCodeUuid);

    UUID createAccessToken(String clientId);

    Boolean checkAccessTokenValidity(UUID accessTokenUuid);

    UUID getRefreshTokenUuid(UUID accessTokenUuid);

    UUID getNewAccessTokenUuidByRefreshTokenUuid(UUID refreshTokenUuid, String clientId);
}
