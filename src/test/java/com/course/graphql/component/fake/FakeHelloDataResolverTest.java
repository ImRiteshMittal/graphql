package com.course.graphql.component.fake;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FakeHelloDataResolverTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void testOneHello() {
        var graphqlQuery = """
                  {
                      oneHello {
                        text
                        randomNumber
                    }
                }
                """;
        String text = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery, "data.oneHello.text");
        Integer randomNumber = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery, "data.oneHello.randomNumber");
        Assertions.assertNotNull(randomNumber);
        Assertions.assertNotNull(text);
    }

    @Test
    void testAllHellos() {
        var graphqlQuery = """
                  {
                      allHellos {
                        text
                        randomNumber
                    }
                }
                """;
        List<String> texts = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery, "data.allHellos[*].text");
        List<Integer> randomNumbers = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery, "data.allHellos[*].randomNumber");
        Assertions.assertEquals(5, texts.size());
        Assertions.assertEquals(5, randomNumbers.size());
    }
}