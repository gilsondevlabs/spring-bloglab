package in.gilsondev.blog.domain;

import in.gilsondev.blog.builder.AuthorBuilder;
import in.gilsondev.blog.builder.PostBuilder;
import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest(LocalDateTime.class)
@SpringBootTest
@DataJpaTest
public class PostEntityTest {
    @Autowired
    private TestEntityManager entityManager;

    private DataFactory dataFactory = new DataFactory();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldHaveLombokProperties() {
        Author authorFake = new AuthorBuilder().build();

        // Criando mock para a data e hora atual usando Powermock
        mockStatic(LocalDateTime.class);
        LocalDateTime now = LocalDateTime.now();
        when(LocalDateTime.now()).thenReturn(now);

        Post post = new PostBuilder()
                .withId(1L)
                .withAuthor(authorFake)
                .withTitle("Post Title")
                .withSlug("post-title")
                .withTeaser("POst resume")
                .withBody("Post Content")
                .withCreatedAt(LocalDateTime.now())
                .withUpdatedAt(LocalDateTime.now())
                .withKeywords("Post", "Tags")
                .build();

        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getAuthor()).isEqualTo(authorFake);
        assertThat(post.getTitle()).isEqualTo("Post Title");
        assertThat(post.getSlug()).isEqualTo("post-title");
        assertThat(post.getTeaser()).isEqualTo("POst resume");
        assertThat(post.getBody()).isEqualTo("Post Content");
        assertThat(post.getCreatedAt()).isEqualTo(LocalDateTime.now());
        assertThat(post.getUpdatedAt()).isEqualTo(LocalDateTime.now());
        assertThat(post.isStatus()).isEqualTo(true);
        assertThat(post.getKeywords()).containsAll(Arrays.asList("Post", "Tags"));
    }

    @Test
    public void shouldPersistData() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder().withAuthor(authorPersisted).build();
        Post postPersisted = entityManager.persistFlushFind(post);

        assertThat(postPersisted.getId()).isNotNull();
        assertThat(postPersisted.getId()).isNotNegative();
        assertThat(postPersisted.getAuthor().getId()).isNotNull();
        assertThat(postPersisted.getAuthor().getId()).isNotNegative();
        assertThat(postPersisted.getTitle()).isEqualTo(post.getTitle());
        assertThat(postPersisted.getSlug()).isEqualTo(post.getSlug());
        assertThat(postPersisted.getTeaser()).isEqualTo(post.getTeaser());
        assertThat(postPersisted.getBody()).isEqualTo(post.getBody());
        assertThat(postPersisted.getCreatedAt()).isEqualTo(post.getCreatedAt());
        assertThat(postPersisted.getUpdatedAt()).isEqualTo(post.getUpdatedAt());
        assertThat(postPersisted.isStatus()).isEqualTo(post.isStatus());
        assertThat(postPersisted.getKeywords()).containsAll(post.getKeywords());
    }

    @Test
    public void shouldNotPersistPostWithoutTitle() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .withTitle(null)
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithTitleWithMore50Characters() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .withTitle(dataFactory.getRandomChars(51))
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithoutSlug() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .withSlug(null)
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithSlugWithMore70Characters() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .withSlug(dataFactory.getRandomChars(71))
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithoutTeaser() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .withTeaser(null)
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithTeaserWithMore70Characters() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .withTeaser(dataFactory.getRandomChars(71))
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithoutBody() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .withBody(null)
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldPersistPostWithBodyWithMore5000Characters() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .withBody(dataFactory.getRandomChars(5001))
                .build();

        Post postPersisted = entityManager.persistFlushFind(post);

        assertThat(postPersisted.getId()).isNotNull();
        assertThat(postPersisted.getId()).isNotNegative();
        assertThat(postPersisted.getBody()).isNotNull();
    }

    @Test
    public void shouldNotPersistPostWithoutCreatedAt() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .withCreatedAt(null)
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldPersistPostWithoutUpdatedAt() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .withUpdatedAt(null)
                .build();

        Post postPersisted = entityManager.persistFlushFind(post);

        assertThat(postPersisted.getId()).isNotNull();
        assertThat(postPersisted.getId()).isNotNegative();
        assertThat(postPersisted.getUpdatedAt()).isNull();

    }

    @Test
    public void shouldPersistPostWithStatusActive() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .build();

        Post postPersisted = entityManager.persistFlushFind(post);

        assertThat(postPersisted.isStatus()).isTrue();
    }

    @Test
    public void shouldPersistPostWithCreatedAtDefined() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new PostBuilder()
                .withAuthor(authorPersisted)
                .build();

        Post postPersisted = entityManager.persistFlushFind(post);

        assertThat(postPersisted.getCreatedAt()).isNotNull();
    }

    @Test
    public void shouldNotPersistPostWithoutAuthor() {
        Post post = new PostBuilder()
                .withAuthor(null)
                .build();

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }
}
