package smirnov.bn.service_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import smirnov.bn.service_2.entity.Token;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TokenRepository
            extends JpaRepository<Token, Integer> {

        @Query(value = "SELECT t.* FROM tokens t",
                nativeQuery = true)
        List<Token> findAll();

        @Query(value = "SELECT t.* FROM tokens t WHERE t.token_uuid = :tokenUuid",
                nativeQuery = true)
        Token findByUuid(@Param("tokenUuid") UUID tokenUuid);

        /*
        @Query(value = "SELECT t.* FROM tokens t WHERE t.refresh_token_uuid = :refreshTokenUuid",
                nativeQuery = true)
        Token findByRefreshTokenUuid(@Param("refreshTokenUuid") UUID refreshTokenUuid);
        //*/

        @Transactional
        @Modifying
        @Query(value = "UPDATE tokens SET is_invalidated = :isInvalidated, is_expired = :isExpired, last_used_date_time = :lastUsedDateTime WHERE token_uuid = :tokenUuid",
                nativeQuery = true)
        void updateToken(@Param("isInvalidated") Boolean isInvalidated, @Param("isExpired") Boolean isExpired,
                         @Param("lastUsedDateTime") Date lastUsedDateTime, @Param("tokenUuid") UUID accessTokenUuid);

        @Transactional
        @Modifying
        @Query(value = "UPDATE tokens SET is_invalidated = true, is_expired = true, last_used_date_time = current_timestamp WHERE token_uuid = :tokenUuid AND ((current_timestamp > last_used_date_time + interval '1' HOUR * 0.5) OR (current_timestamp > created_date_time + interval '1' HOUR * (2)))",
                nativeQuery = true)
        void updateTokenValidity(@Param("tokenUuid") UUID accessTokenUuid);

        @Transactional
        @Modifying
        @Query(value = "UPDATE tokens SET is_invalidated = true, is_expired = true, last_used_date_time = current_timestamp WHERE ((current_timestamp > last_used_date_time + interval '1' HOUR * 0.5) OR (current_timestamp > created_date_time + interval '1' HOUR * (2)))",
                nativeQuery = true)
        void updateAllFilteredTokensValidity();

        @Transactional
        @Modifying
        @Query(value = "DELETE FROM tokens t WHERE t.token_uuid = :tokenUuid",
                nativeQuery = true)
        void deleteByUuid(@Param("tokenUuid") UUID tokenUuid);
}
