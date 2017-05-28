package hu.study.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.study.model.dao.UserDAO;

/**
 * Session Bean implementation class UserEJBIF
 */
@Stateless
@LocalBean
public class UserEJB implements UserEJBIF {
	
	@PersistenceContext(unitName = "ProStu")
    private EntityManager entityManager;

    /**
     * Default constructor. 
     */
    public UserEJB() {
    }

	@Override
	public void createUser() {
		UserDAO userDAO = new UserDAO(entityManager);
		userDAO.find(123);
	}

}
