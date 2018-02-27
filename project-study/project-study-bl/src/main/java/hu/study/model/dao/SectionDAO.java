package hu.study.model.dao;

import hu.study.model.entity.Course;
import hu.study.model.entity.Section;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Date;
import java.util.Optional;

/**
 * DAO object for {@link Section}
 */
@Log4j2
public class SectionDAO extends BasicDAO<Section> {

    public SectionDAO(EntityManager em) {
        super(em, Section.class);
    }

    /**
     * Update the given {@link Section}.
     *
     * @param section the section to update.
     * @throws IllegalArgumentException when the given section is not exist.
     */
    @Override
    public void update(@NonNull Section section) throws IllegalArgumentException {
        val sec = find(section.getId());
        if(sec.isPresent()) {
            em.getTransaction().begin();
            em.merge(section);
            em.getTransaction().commit();
            log.info("Section with name: " + section.getName() + "updated");
        } else {
            throw new IllegalArgumentException("The given section is not exist in the database");
        }
    }

    /**
     * Create the given object in the Database.
     *
     * @param section the section to create.
     */
    @Override
    public void create(@NonNull Section section) {
        section.setCreationDate(new Date(new java.util.Date().getTime()));
        super.create(section);
    }

}
