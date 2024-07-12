package com.course.graphql.datasource.fake;

import com.course.graphql.generated.types.Address;
import com.course.graphql.generated.types.Author;
import com.course.graphql.generated.types.Book;
import com.course.graphql.generated.types.ReleaseHistory;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FakeProblemzDataSource {
    public static final List<Book> BOOK_LIST = new ArrayList<>();
    @Autowired
    Faker faker;

    @PostConstruct
    public void postConstruct() {
        for (int i = 0; i < 5; i++) {
            var addresses = new ArrayList<Address>();
            for (int j = 0; j < ThreadLocalRandom.current().nextInt(1, 3); j++) {
                var address = Address.newBuilder()
                        .city(faker.address().city())
                        .country(faker.address().country())
                        .street(faker.address().streetAddress())
                        .zipCode(faker.address().zipCode())
                        .build();
                addresses.add(address);
            }
            var author = Author.newBuilder()
                    .addresses(addresses)
                    .name(faker.book().author())
                    .originCountry(faker.country().name())
                    .build();
            var release = ReleaseHistory.newBuilder()
                    .releaseCountry(faker.country().name())
                    .printedEdition(faker.bool().bool())
                    .year(faker.number().numberBetween(2011, 2023))
                    .build();
            var book = Book.newBuilder()
                    .title(faker.book().title())
                    .author(author)
                    .publisher(faker.book().publisher())
                    .released(release)
                    .build();
            BOOK_LIST.add(book);
        }
    }
}
