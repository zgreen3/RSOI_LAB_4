package smirnov.bn.security_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import smirnov.bn.security_service.entity.User;
import smirnov.bn.security_service.model.UserInfo;
import smirnov.bn.security_service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private UserInfo buildlingUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserLogin(user.getUserLogin());
        userInfo.setUserPasswordHash(user.getUserPasswordHash());
        userInfo.setUserEmail(user.getUserEmail());
        userInfo.setUserUuid(user.getUserUuid());
        return userInfo;
    }

    /*
    @Override
    @Transactional
    public void createUser(@Nonnull String userLogin, @Nonnull String userPasswordHash, @Nonnull String userEmail) {
        User user = new User();
        user.setUserLogin(userLogin);
        user.setUserPasswordHash(userPasswordHash);
        user.setUserEmail(userEmail);
        user.setUserUuid(UUID.randomUUID());
        userRepository.saveAndFlush(user);
    }
    //*/

    @Nullable
    @Override
    @Transactional
    public UUID createUser(UserInfo userInfo) {
        User user = new User();
        user.setUserLogin(userInfo.getUserLogin());
        user.setUserPasswordHash(userInfo.getUserPasswordHash());
        user.setUserEmail(userInfo.getUserEmail());
        user.setUserUuid(UUID.randomUUID());
        userRepository.saveAndFlush(user);
        return user.getUserUuid();
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public List<UserInfo> findAllUsersPaginated(int page, int sizeLimit) {
        List<UserInfo> userInfoList = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page, sizeLimit);
        Page<User> usersPage = userRepository.findAll(pageableRequest);
        List<User> users = usersPage.getContent();
        for (User user : users) {
            userInfoList.add(this.buildlingUserInfo(user));
        }
        return userInfoList;
    }

    /*
    @Nullable
    @Override
    @Transactional(readOnly = true)
    public UserInfo findUserById(@Nonnull Integer userId) {
        return userRepository.findById(userId).map(this::buildlingUserInfo).orElse(null);
    }
    //*/
    @Nullable
    @Override
    @Transactional(readOnly = true)
    public UserInfo findUserByUuid(UUID userUuid) {
        return this.buildlingUserInfo(userRepository.findByUuid(userUuid));
    }

    /*
    @Override
    @Transactional
    public void updateUser(@Nonnull String userLogin, @Nonnull String userPasswordHash, @Nonnull String userEmail, @Nonnull UUID userUuid) {
        userRepository.updateUser(userLogin, userPasswordHash, userEmail, userUuid);
    }
    //*/

    @Override
    @Transactional
    public void updateUser(UserInfo userInfo) {
        userRepository.updateUser(userInfo.getUserLogin(), userInfo.getUserPasswordHash(), userInfo.getUserEmail(), userInfo.getUserUuid());
    }

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public List<UserInfo> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::buildlingUserInfo)
                .collect(Collectors.toList());
    }

    /*
    @Override
    @Transactional
    public void deleteUserById(@Nonnull Integer userId) {
        userRepository.deleteById(userId);
    }
    //*/

    @Override
    @Transactional
    public void deleteUserByUuid(UUID userUuid) {
        userRepository.deleteByUuid(userUuid);
    }

    /*
    @Nullable
    @Override
    public void registerUser(UserInfo userInfo) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_API_VK)
                .queryParam("user_ids", userInfo.getVk()).queryParam("fields", "online")
                .queryParam("access_token", VK_TOKEN);
        RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method and default Headers.
        String jsonString = restTemplate.getForObject(builder.toUriString(), String.class);
        if (!jsonString.contains("error_code")) {

            if (userInfo.getUid() == null) userInfo.setUid(UUID.randomUUID());
            String encryptedPassword = passwordEncoder.encode(userInfo.getPassword());
            userInfo.setPassword(encryptedPassword);
            userRepos.saveAndFlush(createUser(userInfo));
            //return true;
        } else {
            //return false;
        }
    }
    //*/

    //N.B.: в данном методе сравниваем не введённый пользователем пароль с паролем из БД,
    //а хэши от пароля пользователя из БД и со страницы аутентификации ("логирования"):
    @Override
    public boolean authenticateUser(UserInfo userInfo) {
        User user = userRepository.findByUuid(userInfo.getUserUuid());
        if (user.getUserPasswordHash().equals(userInfo.getUserPasswordHash())) {
            return true;
        } else {
            return false;
        }
    }

}
