package hu.study.model.dao;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.persistence.Query;

import hu.study.model.entity.User;

public class UserDAO {

	private EntityManager em;
	private static final Logger LOG = LogManager.getLogger( UserDAO.class );
	private static final String USER_BY_NAME = "SELECT COUNT(u) FROM User u WHERE ";

	public UserDAO(EntityManager em) {
		super();
		this.em = em;
	}
	
	public Optional<User> find(int id) {
		User user = em.find(User.class, id);
		return Optional.of(user);
	}
	
	public void update(User user) throws IllegalArgumentException {
		if(isUserExist(user)) {
			em.merge(user);
			LOG.info("User with email: " + user.getEmail() + "updated");
		} else {
			throw new IllegalArgumentException("The given user is not exist in the database");
		}
	}
	
	public void create(User user) {
		if(!isUserExist(user)) {
			em.merge(user);
			LOG.info("User created with email: " + user.getEmail());
		} else {
			throw new IllegalArgumentException("The user is already exist in the database");
		}
	}
	
	public void delete(User user) {
		em.remove(user);
	}
	
	public boolean isUserExist(User user) {
		Query query = em.createNamedQuery("is user already exist");
		query.setParameter("email", user.getEmail());
		Integer i = (Integer) query.getSingleResult();
		if(i == 0) {
			return false;
		}
		return true;
	}
}
