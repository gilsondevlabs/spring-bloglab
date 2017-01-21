package in.gilsondev.blog.builder;

import in.gilsondev.blog.domain.Author;
import org.fluttercode.datafactory.impl.DataFactory;

public class AuthorBuilder {
    private DataFactory dataFactory = new DataFactory();
    private Author author;

    public AuthorBuilder() {
        this.author = new Author();
        this.author.setId((long) this.dataFactory.getNumberBetween(1, 9999));
        this.author.setFirstName(this.dataFactory.getFirstName());
        this.author.setLastName(this.dataFactory.getLastName());
        this.author.setEmail(this.dataFactory.getEmailAddress());
    }

    public AuthorBuilder withId(Long id) {
        this.author.setId(id);
        return this;
    }

    public AuthorBuilder withFirstName(String firstName) {
        this.author.setFirstName(firstName);
        return this;
    }

    public AuthorBuilder withLastName(String lastName) {
        this.author.setLastName(lastName);
        return this;
    }

    public AuthorBuilder withEmail(String email) {
        this.author.setEmail(email);
        return this;
    }

    public Author build() {
        return this.author;
    }
}
