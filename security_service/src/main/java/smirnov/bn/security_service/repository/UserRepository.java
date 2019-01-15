package smirnov.bn.security_service.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import smirnov.bn.security_service.entity.User;

public interface UserRepository
        extends JpaRepository<User, Integer> {

    @Query(value = "SELECT u.* FROM users u",
            nativeQuery = true)
    List<User> findAll();

    @Query(value = "SELECT u.* FROM users u WHERE u.usr_uuid = :userUuid",
            nativeQuery = true)
    User findByUuid(@Param("userUuid") UUID userUuid);

    @Query(value = "SELECT u.* FROM users u WHERE u.usr_login = :userLogin AND u.usr_email = :userEmail LIMIT 1",
            nativeQuery = true)
    User findByLoginEmail(@Param("userLogin") String userLogin, @Param("userEmail") String userEmail);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET usr_login = :userLogin, usr_email = :userEmail, usr_psswrd_hash = :userPasswordHash WHERE usr_uuid = :userUuid",
            nativeQuery = true)
    void updateUser(@Param("userLogin") String userLogin, @Param("userPasswordHash") String userPasswordHash,
                    @Param("userEmail") String userEmail, @Param("userUuid") UUID userUuid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users u WHERE u.usr_uuid = :userUuid",
            nativeQuery = true)
    void deleteByUuid(@Param("userUuid") UUID userUuid);
}
