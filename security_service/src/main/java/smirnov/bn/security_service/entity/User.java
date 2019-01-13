package smirnov.bn.security_service.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "usr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "usr_login", length = 255)
    private String userLogin;

    @Column(name = "usr_psswrd_hash", length = 255)
    private String userPasswordHash;

    @Column(name = "usr_email", length = 255)
    private String userEmail;


    @Column(name = "usr_uuid", unique = true)
    private UUID userUuid;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPasswordHash() {
        return userPasswordHash;
    }

    public void setUserPasswordHash(String userPasswordHash) {
        this.userPasswordHash = userPasswordHash;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userLogin, user.userLogin) &&
                Objects.equals(userPasswordHash, user.userPasswordHash) &&
                Objects.equals(userEmail, user.userEmail) &&
                Objects.equals(userUuid, user.userUuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, userLogin, userPasswordHash, userEmail, userUuid);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userLogin='" + userLogin + '\'' +
                ", userPasswordHash='" + userPasswordHash + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userUuid=" + userUuid +
                '}';
    }
}