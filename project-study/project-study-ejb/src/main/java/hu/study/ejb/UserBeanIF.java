package hu.study.ejb;


import hu.study.model.entity.User;

import java.io.Serializable;

/**
 * Created by martin4955 on 2017. 07. 05..
 */
public interface UserBeanIF extends Serializable{
    void validatingUser(User user) throws Exception;
    String issueToken(String username) throws Exception;
}
