package hu.study.model.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;

import hu.study.model.entity.Course;
import hu.study.model.entity.QCourse;
import hu.study.model.entity.QRating;
import lombok.NonNull;
import lombok.val;
import lombok.extern.log4j.Log4j2;

/**
 * DAO object for {@link Course}
 */
@Log4j2
public class CourseDAO extends BasicDAO<Course> {

    public CourseDAO( final EntityManager em ) {
        super(em, Course.class);
    }

    /**
     * Returns an {@link Optional} with the searched {@link Course}.
     *
     * @param name the name of the course.
     * @return and {@link Optional} containing the requested course, or null.
     */
    public Optional<Course> find( @NonNull final String name ) {
        val query = em.createNamedQuery("Course.findByName");
        query.setParameter("name", name);
        try {
            val course = (Course) query.getSingleResult();
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
    public List<Course> findLikeName( @NonNull final String name, @NonNull final String orderType, @NonNull final String direction,
        final int limitNum ) {
        String escapeName = "%" + name + "%";
        QCourse course = QCourse.course;
        QRating rating = QRating.rating;
        JPAQuery<?> query = new JPAQuery<Void>(em);
        query.select(course).from(course).where(course.name.lower().like(escapeName.toLowerCase()));

        switch ( orderType ) {
            case "name":
                query.orderBy(getOrdering(course.name, direction));
                break;
            case "creation_date":
                query.orderBy(getOrdering(course.creationDate, direction));
                break;
            case "ratings":
                query.select(course).from(course).leftJoin(course.ratings, rating).groupBy(course)
                    .where(course.name.lower().like(escapeName.toLowerCase())).orderBy(getOrdering(QRating.rating.score.avg(), direction));
                break;

        }
        List<Course> courseList = (List<Course>) query.fetch();
        return courseList;
    }

    private OrderSpecifier<?> getOrdering( final ComparableExpressionBase<?> name, final String direction ) {
        if ( direction.equals("desc") ) {
            return name.desc();
        } else {
            return name.asc();
        }
    }

    /**
     * Update the given {@link Course}.
     *
     * @param course the course to update.
     * @throws IllegalArgumentException when the given Course is not exist.
     */
    @Override
    public void update( @NonNull final Course course ) throws IllegalArgumentException {
        if ( isCourseExist(course) ) {
            em.merge(course);
            log.info("Course with name: " + course.getName() + "updated");
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
    public void create( @NonNull final Course course ) throws IllegalArgumentException {
        if ( !isCourseExist(course) ) {
            course.setCreationDate(new Date(new java.util.Date().getTime()));
            super.create(course);
        } else {
            throw new IllegalArgumentException("The course is already exist in the database");
        }
    }

    private boolean isCourseExist( @NonNull final Course course ) {
        if ( course.getName() != null ) {
            return find(course.getName()).isPresent();
        }
        return false;
    }

}
