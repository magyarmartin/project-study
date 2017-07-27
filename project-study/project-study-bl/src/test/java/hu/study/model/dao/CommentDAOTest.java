package hu.study.model.dao;

import hu.study.model.JPAHibernateTest;
import hu.study.model.entity.Comment;
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
public class CommentDAOTest extends JPAHibernateTest {

    private static CommentDAO commentDAO;

    @BeforeClass
    public static void initDAO() {
        commentDAO = new CommentDAO(em);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldPresentIfIdExists() {
        Optional<Comment> comment = commentDAO.find(-1);
        assertThat(comment.isPresent(), is(true));
    }

    @Test
    public void shouldNotPresentIfIdNotExists() {
        Optional<Comment> comment = commentDAO.find(2);
        assertThat(comment.isPresent(), is(false));
    }

    @Test
    public void shouldBeInTheDBIfCreatedAndFilledWithCreationDate() {
        Comment comment = new Comment();
        comment.setContent("asd");
        commentDAO.create(comment);
        Optional<Comment> foundComment = commentDAO.find(comment.getId());
        assertThat(foundComment.isPresent(), is(true));
        assertThat(foundComment.get().getCreationDate(), is(notNullValue()));
    }

    @Test
    public void shouldUpdateTheGivenComment() {
        final String content = "Some comment";
        Comment comment = commentDAO.find(-1).get();
        comment.setContent(content);
        commentDAO.update(comment);

        Comment foundComment = commentDAO.find(comment.getId()).get();
        assertThat(foundComment.getContent(), is(equalTo(content)));
    }

    @Test
    public void shouldThrowExceptionIfTheCourseIsNotAlreadyExists() {
        thrown.expect(NullPointerException.class);
        Comment comment = new Comment();
        commentDAO.update(comment);
    }

    @Test
    public void shouldNotPresentAfterDelete() {
        Comment comment = commentDAO.find(-2).get();
        commentDAO.delete(comment);
        Optional<Comment> foundComment = commentDAO.find(-2);
        assertThat(foundComment.isPresent(), is(false));
    }

}
