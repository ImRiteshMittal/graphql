package com.course.graphql.datasource.fake;

import com.course.graphql.generated.types.Hello;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FakeHelloDataSource {

    public static final List<Hello> HELLO_LIST = new ArrayList<>();
    @Autowired
    Faker faker;

    @PostConstruct
    public void postConstruct() {
        for (int i = 0; i < 5; i++) {
            var hello = Hello.newBuilder().randomNumber(faker.random().nextInt(5000))
                    .text(faker.company().name()).build();
            HELLO_LIST.add(hello);
        }
    }
}
