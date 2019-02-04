package smirnov.bn.service_1.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(name = "token_uuid", unique = true)
    private UUID tokenUuid;

    @Column(name = "is_invalidated", columnDefinition = "boolean default false", nullable = false)
    private Boolean isInvalidated;

    @Column(name = "is_expired", columnDefinition = "boolean default false", nullable = false)
    private Boolean isExpired;

    @Column(name = "token_type", length = 255)
    private String tokenType;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @Column(name = "expires_in_seconds", columnDefinition = "BIGINT")
    private Long expiresInSeconds;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_used_date_time")
    private LocalDateTime lastUsedDateTime;

    @Column(name = "client_id", length = 255)
    private String clientID;

    //@Column(name = "refresh_token_uuid", unique = true)
    //private UUID refreshTokenUuid;

    public Token() {
    }

    public Token(Boolean isInvalidated, Boolean isExpired, String tokenType, LocalDateTime createdDateTime, Long expiresInSeconds, LocalDateTime lastUsedDateTime, String clientID) {
        this.isInvalidated = isInvalidated;
        this.isExpired = isExpired;
        this.tokenType = tokenType;
        this.createdDateTime = createdDateTime;
        this.expiresInSeconds = expiresInSeconds;
        this.lastUsedDateTime = lastUsedDateTime;
        this.clientID = clientID;
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public UUID getTokenUuid() {
        return tokenUuid;
    }

    public void setTokenUuid(UUID accessTokenUuid) {
        this.tokenUuid = accessTokenUuid;
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

    /*
    public UUID getRefreshTokenUuid() {
        return refreshTokenUuid;
    }

    public void setRefreshTokenUuid(UUID refreshTokenUuid) {
        this.refreshTokenUuid = refreshTokenUuid;
    }
    //*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(tokenId, token.tokenId) &&
                Objects.equals(tokenUuid, token.tokenUuid) &&
                Objects.equals(isInvalidated, token.isInvalidated) &&
                Objects.equals(isExpired, token.isExpired) &&
                Objects.equals(tokenType, token.tokenType) &&
                Objects.equals(createdDateTime, token.createdDateTime) &&
                Objects.equals(expiresInSeconds, token.expiresInSeconds) &&
                Objects.equals(lastUsedDateTime, token.lastUsedDateTime) &&
                Objects.equals(clientID, token.clientID) //&&
        //Objects.equals(refreshTokenUuid, token.refreshTokenUuid)
        ;
    }

    @Override
    public int hashCode() {

        return Objects.hash(tokenId, tokenUuid, isInvalidated, isExpired, tokenType, createdDateTime, expiresInSeconds,
                lastUsedDateTime, clientID//, refreshTokenUuid
        );
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenId=" + tokenId +
                ", tokenUuid=" + tokenUuid +
                ", isInvalidated=" + isInvalidated +
                ", isExpired=" + isExpired +
                ", tokenType='" + tokenType + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", expiresInSeconds=" + expiresInSeconds +
                ", lastUsedDateTime=" + lastUsedDateTime +
                ", clientID='" + clientID + '\'' +
                //", refreshTokenUuid=" + refreshTokenUuid +
                '}';
    }
}
