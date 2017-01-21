package in.gilsondev.blog.domain;

import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Before;
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
        Author author = new Author();
        author.setId(1L);
        author.setFirstName("Author");
        author.setLastName("Test");
        author.setEmail("author.test@mail.com");

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getFirstName()).isEqualTo("Author");
        assertThat(author.getLastName()).isEqualTo("Test");
        assertThat(author.getEmail()).isEqualTo("author.test@mail.com");
    }

    @Test
    public void shouldPersistData() {
        Author author = new Author();
        author.setFirstName("Author");
        author.setLastName("Test");
        author.setEmail("author.test@mail.com");

        Author authorPersisted = entityManager.persistFlushFind(author);

        assertThat(authorPersisted.getId()).isNotNull();
        assertThat(authorPersisted.getId()).isNotNegative();
        assertThat(authorPersisted.getFirstName()).isEqualTo(author.getFirstName());
        assertThat(authorPersisted.getLastName()).isEqualTo(author.getLastName());
        assertThat(authorPersisted.getEmail()).isEqualTo(author.getEmail());
    }

    @Test
    public void shouldNotPersistAuthorWithoutFirstName() {
        Author author = new Author();
        author.setFirstName(null);
        author.setLastName("Test");
        author.setEmail("author.test@mail.com");

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(author);
    }

    @Test
    public void shouldNotPersistAuthorWithoutLastName() {
        Author author = new Author();
        author.setFirstName("Author");
        author.setLastName(null);
        author.setEmail("author.test@mail.com");

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(author);
    }

    @Test
    public void shouldNotPersistAuthorWithoutEmail() {
        Author author = new Author();
        author.setFirstName("Author");
        author.setLastName("Test");
        author.setEmail(null);

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(author);
    }

    @Test
    public void shouldNotPersistAuthorWithFirstNameWithMore50Characters() {
        Author author = new Author();
        author.setFirstName(dataFactory.getRandomChars(51));
        author.setLastName("Test");
        author.setEmail("author.test@mail.com");

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(author);
    }
}
