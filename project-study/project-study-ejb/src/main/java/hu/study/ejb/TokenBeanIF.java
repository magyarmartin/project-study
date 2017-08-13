package hu.study.ejb;

import hu.study.model.entity.Token;

/**
 * Created by martin4955 on 2017. 08. 12..
 */
public interface TokenBeanIF {
    boolean isValidToken(String tokenStr);
    Token getToken(String token);
}
