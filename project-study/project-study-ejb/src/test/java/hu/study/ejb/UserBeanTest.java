package hu.study.ejb;

import hu.study.model.entity.Token;
import hu.study.model.entity.User;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.mock;

/**
 * Created by martin4955 on 2017. 06. 20..
 */
public class UserBeanTest {

    UserBean userBean;
    EntityManager entityManager;

    @Before
    public void setUp() {
        userBean = new UserBean();

        entityManager = mock(EntityManager.class);
        userBean.setEntityManager(entityManager);
        userBean.setUp();
    }


}
