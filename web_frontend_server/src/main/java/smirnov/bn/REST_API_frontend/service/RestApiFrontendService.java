package smirnov.bn.REST_API_frontend.service;

import java.util.UUID;

import smirnov.bn.REST_API_frontend.model.UserInfo;

public interface RestApiFrontendService {

    UUID createUser(UserInfo userInfo);

    String hashPassword(String password);

    UserInfo findUserByLoginEmail(String userLogin, String userEmail);
}
