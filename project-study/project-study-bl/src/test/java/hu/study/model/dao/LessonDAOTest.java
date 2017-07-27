package hu.study.model.dao;

import hu.study.model.JPAHibernateTest;
import hu.study.model.entity.Lesson;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by martin4955 on 2017. 06. 07..
 */
public class LessonDAOTest extends JPAHibernateTest {
    private static LessonDAO lessonDAO;

    @BeforeClass
    public static void initDAO() {
        lessonDAO = new LessonDAO(em);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldPresentIfIdExists() {
        Optional<Lesson> lesson = lessonDAO.find(-1);
        assertThat(lesson.isPresent(), is(true));
    }

    @Test
    public void shouldNotPresentIfIdNotExists() {
        Optional<Lesson> lesson = lessonDAO.find(2);
        assertThat(lesson.isPresent(), is(false));
    }

    @Test
    public void shouldBeInTheDBIfCreatedAndFilledWithCreationDate() {
        Lesson lesson = new Lesson();
        lesson.setContentText("asd");
        lessonDAO.create(lesson);
        Optional<Lesson> foundLesson = lessonDAO.find(lesson.getId());
        assertThat(foundLesson.isPresent(), is(true));
        assertThat(foundLesson.get().getCreationDate(), is(notNullValue()));
    }

    @Test
    public void shouldUpdateTheGivenComment() {
        final String content = "Some comment";
        Lesson lesson = lessonDAO.find(-1).get();
        lesson.setContentText(content);
        lessonDAO.update(lesson);

        Lesson foundLesson = lessonDAO.find(lesson.getId()).get();
        assertThat(foundLesson.getContentText(), is(equalTo(content)));
    }

    @Test
    public void shouldThrowExceptionIfTheCourseIsNotAlreadyExists() {
        thrown.expect(NullPointerException.class);
        Lesson lesson = new Lesson();
        lessonDAO.update(lesson);
    }

    @Test
    public void shouldNotPresentAfterDelete() {
        Lesson comment = lessonDAO.find(-2).get();
        lessonDAO.delete(comment);
        Optional<Lesson> foundLesson = lessonDAO.find(-2);
        assertThat(foundLesson.isPresent(), is(false));
    }
}
