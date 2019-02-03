package smirnov.bn.apigateway.info_model_patterns;

import java.time.LocalDateTime;
import java.util.UUID;

public class TokenInfoWithAppKeyAndSecret {

    private Integer tokenId;

    private UUID accessTokenUuid;

    private Boolean isInvalidated;

    private Boolean isExpired;

    private String tokenType;

    private LocalDateTime createdDateTime;

    private Long expiresInSeconds;

    private LocalDateTime lastUsedDateTime;

    private String appKey;

    private String appSecret;

    public TokenInfoWithAppKeyAndSecret() {
    }

    public TokenInfoWithAppKeyAndSecret(UUID accessTokenUuid) {
        this.accessTokenUuid = accessTokenUuid;
    }

    public TokenInfoWithAppKeyAndSecret(String accessTokenUuidAsString) {
        this.accessTokenUuid = UUID.fromString(accessTokenUuidAsString);
    }

    public TokenInfoWithAppKeyAndSecret(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
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

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
