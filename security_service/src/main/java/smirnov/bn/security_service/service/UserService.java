package smirnov.bn.security_service.service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import smirnov.bn.security_service.model.UserInfo;

public interface UserService {

    /*
    @Nullable
    void createUser(String userLogin, String userPasswordHash, String userEmail);
    //*/
    UUID createUser(UserInfo userInfo);

    @Nullable
    List<UserInfo> findAllUsers();

    @Nullable
    List<UserInfo> findAllUsersPaginated(int page, int sizeLimit);

    /*
    @Nullable
    EmployeeInfo findUserById(@Nonnull Integer userId);
    //*/

    @Nullable
    UserInfo findUserByUuid(UUID userUuid);

    @Nullable
    UserInfo findUserByLoginEmail(String userLogin, String userEmail);

    //void updateUser(String userLogin, String userPasswordHash, String userEmail, UUID userUuid);

    void updateUser(UserInfo userInfo);

    //void deleteUserById(Integer userId);

    void deleteUserByUuid(UUID userUuid);

    //void registerUser(UserInfo userInfo);

    boolean authenticateUser(UserInfo userInfo);
}
