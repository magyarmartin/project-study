package hu.study.ejb;


import hu.study.model.entity.Course;
import hu.study.model.entity.Token;
import hu.study.model.entity.User;

import java.io.Serializable;

/**
 * Created by martin4955 on 2017. 07. 05..
 */
public interface UserBeanIF extends Serializable{
    User getUserIfValid(String email, String password) throws Exception;
    User getUserByEmail(String userId) throws Exception;
    void modifyUser(User user, String principalEmail) throws Exception;
    void deleteTokenIfExists(User user);
    Token issueToken(User user) throws Exception;
    void registerUser(User user) throws Exception;
    boolean isExistingUser(User user);
    void deleteUser(String email);
    void signUpCourse(String email, Course course);
}
