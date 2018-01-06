package hu.study.model.dao;

import hu.study.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Date;
import java.util.Optional;
import java.util.List;
import hu.study.model.entity.Course;

/**
 * DAO object for {@link Course}
 */
public class CourseDAO extends BasicDAO<Course> {

    private static final Logger LOG = LogManager.getLogger( UserDAO.class );

    public CourseDAO(EntityManager em) {
        super(em, Course.class);
    }

    /**
     * Returns an {@link Optional} with the searched {@link Course}.
     *
     * @param name the name of the course.
     * @return and {@link Optional} containing the requested course, or null.
     */
    public Optional<Course> find(final String name) {
        Query query = em.createNamedQuery("Course.findByName");
        query.setParameter("name", name);
        try {
            Course course = (Course) query.getSingleResult();
            return Optional.ofNullable(course);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns a list with courses where the courses names are like the given name.
     *
     * @param name the name of the course.
     * @return a {@link List} with courses. Or an empty list when there are no courses with names like the parameter.
     */
    public List<Course> findLikeName(final String name, String orderType, String direction, int limitNum) {
        StringBuilder queryStr = new StringBuilder();
        boolean ordering = true;
        switch(orderType) {
            case "name":
                queryStr.append("SELECT c FROM Course c WHERE LOWER(name) LIKE LOWER(:name) ORDER BY c.name ");
                break;
            case "creation_date":
                queryStr.append("SELECT c FROM Course c WHERE LOWER(name) LIKE LOWER(:name) ORDER BY c.creationDate ");
                break;
            case "ratings":
                queryStr.append("select c from Course c left join Rating r on c.id = r.targetCourse  " +
                        "where LOWER(name) LIKE LOWER(:name) GROUP BY c.id, c.name, c.creationDate, c.description," +
                        " c.creator order by SUM(score)/COUNT(score) ");
                break;
            default:
                queryStr.append("SELECT c FROM Course c WHERE name LIKE :name");
                LOG.warn("The given orderType was wrong, not using ordering. OrderType: " +orderType);
                ordering = false;
        }

        if(ordering) {
            switch (direction) {
                case "desc":
                    queryStr.append("desc");
                    break;
                default:
                    queryStr.append("asc");
            }
        }

        Query query = em.createQuery(queryStr.toString());
        query.setParameter("name", "%" + name + "%");
        return (List<Course>) query.setMaxResults(limitNum).getResultList();
    }

    /**
     * Update the given {@link Course}.
     *
     * @param course the course to update.
     * @throws IllegalArgumentException when the given Course is not exist.
     */
    @Override
    public void update(Course course) throws IllegalArgumentException {
        if(isCourseExist(course)) {
            em.merge(course);
            LOG.info("Course with name: " + course.getName() + "updated");
        } else {
            throw new IllegalArgumentException("The given course is not exist in the database");
        }
    }

    /**
     * Create the given course in the Database.
     *
     * @param course the course to create.
     * @throws IllegalArgumentException when the given Course is exist.
     */
    @Override
    public void create(Course course) throws IllegalArgumentException {
        if(!isCourseExist(course)) {
            course.setCreationDate(new Date(new java.util.Date().getTime()));
            super.create(course);
        } else {
            throw new IllegalArgumentException("The course is already exist in the database");
        }
    }

    private boolean isCourseExist(Course course) {
        Optional<Course> foundCourse = find(course.getName());
        return foundCourse.isPresent();
    }

}
