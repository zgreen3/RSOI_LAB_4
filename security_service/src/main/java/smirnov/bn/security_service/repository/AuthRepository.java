package smirnov.bn.security_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

import smirnov.bn.security_service.entity.AuthorizationCode;

public interface AuthRepository
            extends JpaRepository<AuthorizationCode, Integer> {

        @Query(value = "SELECT ac.* FROM authorization_codes ac",
                nativeQuery = true)
        List<AuthorizationCode> findAll();

        @Query(value = "SELECT ac.* FROM authorization_codes ac WHERE ac.auth_code_uuid = :authCodeUuid",
                nativeQuery = true)
        AuthorizationCode findByUuid(@Param("authCodeUuid") UUID authCodeUuid);

        @Transactional
        @Modifying
        @Query(value = "UPDATE authorization_codes SET is_expired = :isExpired, is_used = :isUsed WHERE auth_code_uuid = :authCodeUuid",
                nativeQuery = true)
        void updateAuthorizationCode(@Param("isExpired") Boolean isExpired, @Param("isUsed") Boolean isUsed,
                         @Param("authCodeUuid") UUID authCodeUuid);

        @Transactional
        @Modifying
        @Query(value = "DELETE FROM authorization_codes ac WHERE ac.auth_code_uuid = :authCodeUuid",
                nativeQuery = true)
        void deleteByUuid(@Param("authCodeUuid") UUID authCodeUuid);
    }