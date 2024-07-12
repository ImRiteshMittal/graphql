package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeBookDataSource;
import com.course.graphql.generated.client.BooksByReleaseGraphQLQuery;
import com.course.graphql.generated.client.BooksByReleaseProjectionRoot;
import com.course.graphql.generated.client.BooksGraphQLQuery;
import com.course.graphql.generated.client.BooksProjectionRoot;
import com.course.graphql.generated.types.ReleaseHistoryInput;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FakeBookDataResolverTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void booksWrittenBy() {
        BooksGraphQLQuery graphQlQuery = new BooksGraphQLQuery.Builder().build();
        var projectionRoot = new BooksProjectionRoot<>()
                .title().released().year().getRoot().author().originCountry();
        @Language("GraphQL") var graphQLQueryRequest = new GraphQLQueryRequest(graphQlQuery, projectionRoot).serialize();
        List<String> titles = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest, "data.books[*].title");
        List<Integer> years = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest, "data.books[*].released.year");
        System.out.println(titles);
        System.out.println(years);
        Assertions.assertNotNull(titles);
        Assertions.assertNotNull(years);

    }

    @Test
    void getBooksByRelease() {
        var graphQlQuery = BooksByReleaseGraphQLQuery.newRequest()
                .releasedInput(ReleaseHistoryInput.newBuilder()
                        .year(FakeBookDataSource.BOOK_LIST.get(0).getReleased().getYear())
                        .printedEdition(FakeBookDataSource.BOOK_LIST.get(0).getReleased().getPrintedEdition())
                        .build()).build();
        var projectionRoot = new BooksByReleaseProjectionRoot<>()
                .title().released().year().getRoot().author().originCountry();
        @Language("GraphQL") var graphQLQueryRequest = new GraphQLQueryRequest(graphQlQuery, projectionRoot).serialize();

        List<String> titles = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest, "data.booksByRelease[*].title");
        List<Integer> years = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest, "data.booksByRelease[*].released.year");
        System.out.println(titles);
        System.out.println(years);
        Assertions.assertNotNull(titles);
        Assertions.assertNotNull(years);
    }
}