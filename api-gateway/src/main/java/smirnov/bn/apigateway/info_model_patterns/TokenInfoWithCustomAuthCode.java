package smirnov.bn.apigateway.info_model_patterns;

import java.time.LocalDateTime;
import java.util.UUID;

public class TokenInfoWithCustomAuthCode {

    private Integer tokenId;

    private UUID accessTokenUuid;

    private Boolean isInvalidated;

    private Boolean isExpired;

    private String tokenType;

    private LocalDateTime createdDateTime;

    private Long expiresInSeconds;

    private LocalDateTime lastUsedDateTime;

    private String clientID;

    private String clientSecret;

    private UUID refreshTokenUuid;

    private UUID customAuthCodeUuid;

    public TokenInfoWithCustomAuthCode() {
    }

    public TokenInfoWithCustomAuthCode(UUID accessTokenUuid) {
        this.accessTokenUuid = accessTokenUuid;
    }

    public TokenInfoWithCustomAuthCode(String accessTokenUuidAsString) {
        this.accessTokenUuid = UUID.fromString(accessTokenUuidAsString);
    }

    public TokenInfoWithCustomAuthCode(UUID customAuthCodeUuid, String clientID, String clientSecret) {
        this.customAuthCodeUuid = customAuthCodeUuid;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public UUID getAccessTokenUuid() {
        return accessTokenUuid;
    }

    public void setAccessTokenUuid(UUID accessTokenUuid) {
        this.accessTokenUuid = accessTokenUuid;
    }

    public Boolean getInvalidated() {
        return isInvalidated;
    }

    public void setInvalidated(Boolean invalidated) {
        isInvalidated = invalidated;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(Long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public LocalDateTime getLastUsedDateTime() {
        return lastUsedDateTime;
    }

    public void setLastUsedDateTime(LocalDateTime lastUsedDateTime) {
        this.lastUsedDateTime = lastUsedDateTime;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public UUID getRefreshTokenUuid() {
        return refreshTokenUuid;
    }

    public void setRefreshTokenUuid(UUID refreshTokenUuid) {
        this.refreshTokenUuid = refreshTokenUuid;
    }

    public UUID getCustomAuthCodeUuid() {
        return customAuthCodeUuid;
    }

    public void setCustomAuthCodeUuid(UUID customAuthCodeUuid) {
        this.customAuthCodeUuid = customAuthCodeUuid;
    }

}
