package hu.study.model.dao;

import hu.study.model.JPAHibernateTest;
import hu.study.model.entity.Section;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by martin4955 on 2017. 06. 07..
 */
public class SectionDAOTest extends JPAHibernateTest {
    private static SectionDAO sectionDAO;

    @BeforeClass
    public static void initDAO() {
        sectionDAO = new SectionDAO(em);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldPresentIfIdExists() {
        Optional<Section> section = sectionDAO.find(-1);
        assertThat(section.isPresent(), is(true));
    }

    @Test
    public void shouldNotPresentIfIdNotExists() {
        Optional<Section> section = sectionDAO.find(2);
        assertThat(section.isPresent(), is(false));
    }

    @Test
    public void shouldBeInTheDBIfCreatedAndFilledWithCreationDate() {
        Section section = new Section();
        section.setDescription("asd");
        sectionDAO.create(section);
        Optional<Section> foundgSection = sectionDAO.find(section.getId());
        assertThat(foundgSection.isPresent(), is(true));
        assertThat(foundgSection.get().getCreationDate(), is(notNullValue()));
    }

    @Test
    public void shouldUpdateTheGivenSection() {
        final String description = "Some comment";
        Section section = sectionDAO.find(-1).get();
        section.setDescription(description);
        sectionDAO.update(section);

        Section foundSection = sectionDAO.find(section.getId()).get();
        assertThat(foundSection.getDescription(), is(equalTo(description)));
    }

    @Test
    public void shouldThrowExceptionIfTheCourseIsNotAlreadyExists() {
        thrown.expect(NullPointerException.class);
        Section section = new Section();
        sectionDAO.update(section);
    }

    @Test
    public void shouldNotPresentAfterDelete() {
        Section section = sectionDAO.find(-2).get();
        sectionDAO.delete(section);
        Optional<Section> foundSection = sectionDAO.find(-2);
        assertThat(foundSection.isPresent(), is(false));
    }
}
