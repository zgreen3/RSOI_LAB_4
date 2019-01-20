package smirnov.bn.apigateway.info_model_patterns;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthorizationCodeInfo {

    private Integer authCodeId;

    private UUID authCodeUuid;

    private Boolean isExpired;

    private Boolean isUsed;

    private LocalDateTime createdDateTime;

    private Long expiresInSeconds;

    private String clientID;

    private String redirectionURI;

    public Integer getAuthCodeId() {
        return authCodeId;
    }

    public void setAuthCodeId(Integer authCodeId) {
        this.authCodeId = authCodeId;
    }

    public UUID getAuthCodeUuid() {
        return authCodeUuid;
    }

    public void setAuthCodeUuid(UUID authCodeUuid) {
        this.authCodeUuid = authCodeUuid;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
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

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getRedirectionURI() {
        return redirectionURI;
    }

    public void setRedirectionURI(String redirectionURI) {
        this.redirectionURI = redirectionURI;
    }
}
