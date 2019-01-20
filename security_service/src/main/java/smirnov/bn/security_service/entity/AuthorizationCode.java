package smirnov.bn.security_service.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "authorization_codes")
public class AuthorizationCode {

    @Id
    @Column(name = "auth_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authCodeId;

    @Column(name = "auth_code_uuid", unique = true)
    private UUID authCodeUuid;

    @Column(name = "is_expired", columnDefinition = "boolean default false", nullable = false)
    private Boolean isExpired;

    @Column(name = "is_used", columnDefinition = "boolean default false", nullable = false)
    private Boolean isUsed;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date_time")
    private LocalDateTime createdDateTime;

    @Column(name="expires_in_seconds", columnDefinition = "BIGINT")
    private Long expiresInSeconds;

    @Column(name = "client_id", length = 255)
    private String clientID;

    @Column(name = "redirection_uri", length = 255)
    private String redirectionURI;

    public AuthorizationCode() {}

    public AuthorizationCode(Boolean isExpired, Boolean isUsed, LocalDateTime createdDateTime, Long expiresInSeconds, String clientID, String redirectionURI) {
        this.isExpired = isExpired;
        this.isUsed = isUsed;
        this.createdDateTime = createdDateTime;
        this.expiresInSeconds = expiresInSeconds;
        this.clientID = clientID;
        this.redirectionURI = redirectionURI;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationCode that = (AuthorizationCode) o;
        return Objects.equals(authCodeUuid, that.authCodeUuid) &&
                Objects.equals(isExpired, that.isExpired) &&
                Objects.equals(isUsed, that.isUsed) &&
                Objects.equals(createdDateTime, that.createdDateTime) &&
                Objects.equals(expiresInSeconds, that.expiresInSeconds) &&
                Objects.equals(clientID, that.clientID) &&
                Objects.equals(redirectionURI, that.redirectionURI);
    }

    @Override
    public int hashCode() {

        return Objects.hash(authCodeUuid, isExpired, isUsed, createdDateTime, expiresInSeconds, clientID, redirectionURI);
    }

    @Override
    public String toString() {
        return "AuthorizationCode{" +
                "authCodeUuid=" + authCodeUuid +
                ", isExpired=" + isExpired +
                ", isUsed=" + isUsed +
                ", createdDateTime=" + createdDateTime +
                ", expiresInSeconds=" + expiresInSeconds +
                ", clientID='" + clientID + '\'' +
                ", redirectionURI='" + redirectionURI + '\'' +
                '}';
    }
}
