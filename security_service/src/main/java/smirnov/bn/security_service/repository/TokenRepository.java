package smirnov.bn.security_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import smirnov.bn.security_service.entity.Token;

public interface TokenRepository
            extends JpaRepository<Token, Integer> {

        @Query(value = "SELECT t.* FROM tokens t",
                nativeQuery = true)
        List<Token> findAll();

        @Query(value = "SELECT t.* FROM tokens t WHERE t.access_token_uuid = :accessTokenUuid",
                nativeQuery = true)
        Token findByUuid(@Param("accessTokenUuid") UUID accessTokenUuid);

        /*
        @Query(value = "SELECT t.* FROM tokens t WHERE t.refresh_token_uuid = :refreshTokenUuid",
                nativeQuery = true)
        Token findByRefreshTokenUuid(@Param("refreshTokenUuid") UUID refreshTokenUuid);
        //*/

        @Transactional
        @Modifying
        @Query(value = "UPDATE tokens SET is_invalidated = :isInvalidated, is_expired = :isExpired, last_used_date_time = :lastUsedDateTime WHERE access_token_uuid = :accessTokenUuid",
                nativeQuery = true)
        void updateToken(@Param("isInvalidated") Boolean isInvalidated, @Param("isExpired") Boolean isExpired,
                        @Param("lastUsedDateTime") Date lastUsedDateTime, @Param("accessTokenUuid") UUID accessTokenUuid);

        @Transactional
        @Modifying
        @Query(value = "UPDATE tokens SET is_invalidated = true, is_expired = true WHERE access_token_uuid = :accessTokenUuid AND ((current_timestamp > last_used_date_time + interval '1' HOUR * (30/60)) OR (current_timestamp > created_date_time + interval '1' HOUR * (2)))",
                nativeQuery = true)
        void updateTokenValidity(@Param("accessTokenUuid") UUID accessTokenUuid);

        @Transactional
        @Modifying
        @Query(value = "DELETE FROM tokens t WHERE t.access_token_uuid = :accessTokenUuid",
                nativeQuery = true)
        void deleteByUuid(@Param("accessTokenUuid") UUID accessTokenUuid);
}
