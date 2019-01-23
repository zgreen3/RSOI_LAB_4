package smirnov.bn.web_frontend.service;

import java.util.UUID;

import smirnov.bn.web_frontend.model.UserInfo;

public interface WebFrontendService {

    UUID createUser(UserInfo userInfo);

    String hashPassword(String password);

    UserInfo findUserByLoginEmail(String userLogin, String userEmail);
}
