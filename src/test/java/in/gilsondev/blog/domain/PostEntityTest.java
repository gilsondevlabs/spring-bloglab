package in.gilsondev.blog.domain;

import in.gilsondev.blog.builder.AuthorBuilder;
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

        Post post = new Post();
        post.setId(1L);
        post.setAuthor(authorFake);
        post.setTitle("Post Title");
        post.setSlug("post-title");
        post.setTeaser("POst resume");
        post.setBody("Post Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

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

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle("Post Title");
        post.setSlug("post-title");
        post.setTeaser("POst resume");
        post.setBody("Post Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

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

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle(null);
        post.setSlug("post-title");
        post.setTeaser("POst resume");
        post.setBody("Post Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithTitleWithMore50Characters() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle(dataFactory.getRandomChars(51));
        post.setSlug("post-title");
        post.setTeaser("POst resume");
        post.setBody("Post Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithoutSlug() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle("Post Title");
        post.setSlug(null);
        post.setTeaser("POst resume");
        post.setBody("Post Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithSlugWithMore70Characters() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle("Post Title");
        post.setSlug(dataFactory.getRandomChars(71));
        post.setTeaser("POst resume");
        post.setBody("Post Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithoutTeaser() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle("Post Title");
        post.setSlug("post-title");
        post.setTeaser(null);
        post.setBody("Post Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithTeaserWithMore70Characters() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle("Post Title");
        post.setSlug("post-title");
        post.setTeaser(dataFactory.getRandomChars(71));
        post.setBody("Post Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithoutBody() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle("Post Title");
        post.setSlug("post-title");
        post.setTeaser("POst resume");
        post.setBody(null);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldPersistPostWithBodyWithMore5000Characters() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle("Post Title");
        post.setSlug("post-title");
        post.setTeaser("POst resume");
        post.setBody(dataFactory.getRandomChars(5001));
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldNotPersistPostWithoutCreatedAt() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle("Post Title");
        post.setSlug("post-title");
        post.setTeaser("POst resume");
        post.setBody("Post content");
        post.setCreatedAt(null);
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldPersistPostWithoutUpdatedAt() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle("Post Title");
        post.setSlug("post-title");
        post.setTeaser("POst resume");
        post.setBody("Post content");
        post.setCreatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

        entityManager.persistFlushFind(post);
    }

    @Test
    public void shouldPersistPostWithStatusActive() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle("Post Title");
        post.setSlug("post-title");
        post.setTeaser("POst resume");
        post.setBody("Post content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setKeywords(Arrays.asList("Post", "Tags"));

        Post postPersisted = entityManager.persistFlushFind(post);

        assertThat(postPersisted.isStatus()).isTrue();
    }

    @Test
    public void shouldPersistPostWithCreatedAtDefined() {
        Author authorPersisted = entityManager.persistFlushFind(new AuthorBuilder().withId(null).build());

        Post post = new Post();
        post.setAuthor(authorPersisted);
        post.setTitle("Post Title");
        post.setSlug("post-title");
        post.setTeaser("POst resume");
        post.setBody("Post content");
        post.setKeywords(Arrays.asList("Post", "Tags"));

        Post postPersisted = entityManager.persistFlushFind(post);

        assertThat(postPersisted.getCreatedAt()).isNotNull();
    }

    @Test
    public void shouldNotPersistPostWithoutAuthor() {
        Post post = new Post();
        post.setTitle("Post Title");
        post.setSlug("post-title");
        post.setTeaser("POst resume");
        post.setBody("Post content");
        post.setKeywords(Arrays.asList("Post", "Tags"));

        exception.expect(PersistenceException.class);
        entityManager.persistFlushFind(post);
    }
}
