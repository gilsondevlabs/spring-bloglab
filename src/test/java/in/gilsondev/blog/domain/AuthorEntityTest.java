package in.gilsondev.blog.domain;

import in.gilsondev.blog.builder.AuthorBuilder;
import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class AuthorEntityTest {
    @Autowired
    private TestEntityManager entityManager;

    private DataFactory dataFactory = new DataFactory();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldHaveLombokProperties() {
        Author author = new AuthorBuilder()
                .withId(1L)
                .withFirstName("Author")
                .withLastName("Test")
                .withEmail("author.test@mail.com")
                .build();

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getFirstName()).isEqualTo("Author");
        assertThat(author.getLastName()).isEqualTo("Test");
        assertThat(author.getEmail()).isEqualTo("author.test@mail.com");
    }

    @Test
    public void shouldPersistData() {
        Author author = new AuthorBuilder()
                .withId(null)
                .build();

        Author authorPersisted = entityManager.persistFlushFind(author);

        assertThat(authorPersisted.getId()).isNotNull();
        assertThat(authorPersisted.getId()).isNotNegative();
        assertThat(authorPersisted.getFirstName()).isEqualTo(author.getFirstName());
        assertThat(authorPersisted.getLastName()).isEqualTo(author.getLastName());
        assertThat(authorPersisted.getEmail()).isEqualTo(author.getEmail());
    }

    @Test
    public void shouldNotPersistAuthorWithoutFirstName() {
        Author author = new AuthorBuilder()
                .withId(null)
                .withFirstName(null)
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(author);
    }

    @Test
    public void shouldNotPersistAuthorWithoutLastName() {
        Author author = new AuthorBuilder()
                .withId(null)
                .withLastName(null)
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(author);
    }

    @Test
    public void shouldNotPersistAuthorWithoutEmail() {
        Author author = new AuthorBuilder()
                .withId(null)
                .withEmail(null)
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(author);
    }

    @Test
    public void shouldNotPersistAuthorWithFirstNameWithMore50Characters() {
        Author author = new AuthorBuilder()
                .withId(null)
                .withFirstName(dataFactory.getRandomChars(51))
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(author);
    }

    @Test
    public void shouldNotPersistAuthorWithLastNameWithMore50Characters() {
        Author author = new AuthorBuilder()
                .withId(null)
                .withLastName(dataFactory.getRandomChars(51))
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(author);
    }

    @Test
    public void shouldNotPersistAuthorWithEmailWithMore60Characters() {
        Author author = new AuthorBuilder()
                .withId(null)
                .withEmail(dataFactory.getRandomChars(60) + "@mail.com")
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(author);
    }

    @Test
    public void shouldNotPersistAuthorWithInvalidEmail() {
        Author author = new AuthorBuilder()
                .withId(null)
                .withEmail(dataFactory.getRandomChars(30))
                .build();

        exception.expect(ConstraintViolationException.class);
        entityManager.persistFlushFind(author);
    }
}
