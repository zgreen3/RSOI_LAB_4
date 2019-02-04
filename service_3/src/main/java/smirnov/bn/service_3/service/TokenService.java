package smirnov.bn.service_3.service;

import java.util.UUID;

public interface TokenService {

    //Boolean checkAppKeyAndSecretValidity(String AppKeyString, String benchmarkAppKeyString,
    //                                       String AppSecretString, String  benchmarkAppSecretString);

    UUID createToken(String appKey);

    Boolean checkTokenValidity(UUID tokenUuid);

}
