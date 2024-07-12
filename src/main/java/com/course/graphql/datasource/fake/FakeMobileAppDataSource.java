package com.course.graphql.datasource.fake;

import com.course.graphql.generated.types.Address;
import com.course.graphql.generated.types.Author;
import com.course.graphql.generated.types.MobileApp;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FakeMobileAppDataSource {
    public static final List<MobileApp> MOBILE_APP_LIST = new ArrayList<>();
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
            MobileApp mobileApp = MobileApp.newBuilder()
                    .appId(UUID.randomUUID().toString())
                    .name(faker.app().name())
                    .author(author)
                    .version(faker.app().version())
                    .platform(List.of(faker.app().author()))
                    .build();
            MOBILE_APP_LIST.add(mobileApp);
        }
    }
}
