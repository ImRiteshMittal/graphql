package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeBookDataSource;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Book;
import com.course.graphql.generated.types.ReleaseHistoryInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@DgsComponent
public class FakeBookDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Books)
    public List<Book> booksWrittenBy(@InputArgument(DgsConstants.QUERY.BOOKS_INPUT_ARGUMENT.Author) String authorName) {
        if (StringUtils.isBlank(authorName)) {
            return FakeBookDataSource.BOOK_LIST;
        }
        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(b -> StringUtils.containsIgnoreCase(b.getAuthor().getName(), authorName)).toList();
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.BooksByRelease)
    public List<Book> getBooksByRelease(DataFetchingEnvironment dfe) {
        Map<String, Object> releasedInputMap = dfe.getArgument(DgsConstants.QUERY.BOOKSBYRELEASE_INPUT_ARGUMENT.ReleasedInput);
        var releaseInput = ReleaseHistoryInput.newBuilder()
                .year((Integer) releasedInputMap.get(DgsConstants.RELEASEHISTORYINPUT.Year))
                .printedEdition((Boolean) releasedInputMap.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
                .build();
        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(b -> b.getReleased().getPrintedEdition()
                        .equals(releaseInput.getPrintedEdition()) && b.getReleased().getYear() == releaseInput.getYear())
                .toList();
    }

}
