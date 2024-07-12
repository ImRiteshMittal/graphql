package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakePetDataSource;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Cat;
import com.course.graphql.generated.types.Dog;
import com.course.graphql.generated.types.Pet;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@DgsComponent
public class FakePetDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Pets)
    public List<Pet> getPets(DataFetchingEnvironment dataFetchingEnvironment) {
        Map<String, Object> petFilter = dataFetchingEnvironment.getArgument(DgsConstants.QUERY.PETS_INPUT_ARGUMENT.PetFilter);
        if (Objects.isNull(petFilter) || petFilter.isEmpty()) {
            return FakePetDataSource.PET_LIST;
        }
        String petType = (String) petFilter.get(DgsConstants.PETFILTER.PetType);


        return FakePetDataSource.PET_LIST.stream().filter(p -> matchFilter(petType, p)).toList();
    }

    private boolean matchFilter(String petType, Pet p) {
        if (StringUtils.isBlank(petType)) {
            return true;
        }
        if (StringUtils.endsWithIgnoreCase(petType, Dog.class.getSimpleName())) {
            return p instanceof Dog;
        }
        if (StringUtils.endsWithIgnoreCase(petType, Cat.class.getSimpleName())) {
            return p instanceof Cat;
        }
        return false;
    }
}
