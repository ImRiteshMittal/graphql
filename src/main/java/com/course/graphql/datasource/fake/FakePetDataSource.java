package com.course.graphql.datasource.fake;

import com.course.graphql.generated.types.Cat;
import com.course.graphql.generated.types.Dog;
import com.course.graphql.generated.types.Pet;
import com.course.graphql.generated.types.PetFoodType;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FakePetDataSource {
    public static final List<Pet> PET_LIST = new ArrayList<>();
    @Autowired
    Faker faker;

    @PostConstruct
    public void postConstruct() {
        for (int i = 0; i < 10; i++) {
            Pet animal = switch (i % 2) {
                case 0:
                    yield Dog.newBuilder()
                            .name(faker.dog().name())
                            .breed(faker.dog().breed())
                            .food(PetFoodType.OMNIVORE)
                            .coatLength(faker.dog().coatLength())
                            .size(faker.dog().size())
                            .build();
                case 1:
                    yield Cat.newBuilder()
                            .name(faker.cat().name())
                            .breed(faker.cat().breed())
                            .food(PetFoodType.CARNIVORE)
                            .registry(faker.cat().registry())
                            .build();
                default:
                    throw new IllegalStateException("Unexpected value: " + i % 2);
            };
            PET_LIST.add(animal);
        }
    }
}
