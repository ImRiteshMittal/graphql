package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeMobileAppDataSource;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.MobileApp;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@DgsComponent
public class FakeMobileAppsDataResolver {
    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.MobileApps)
    public List<MobileApp> getMobileApps(DataFetchingEnvironment dfe) {
        Map<String, Object> mobileAppFilter = dfe.getArgument(DgsConstants.QUERY.MOBILEAPPS_INPUT_ARGUMENT.MobileAppFilter);
        if (Objects.isNull(mobileAppFilter) || mobileAppFilter.isEmpty()) {
            return FakeMobileAppDataSource.MOBILE_APP_LIST;
        }

        String name = (String) mobileAppFilter.get(DgsConstants.MOBILEAPPFILTER.Name);
        String author = (String) mobileAppFilter.get(DgsConstants.MOBILEAPPFILTER.Author);
        String version = (String) mobileAppFilter.get(DgsConstants.MOBILEAPPFILTER.Version);
        String platform = (String) mobileAppFilter.get(DgsConstants.MOBILEAPPFILTER.Platform);
        return null;
    }
}
