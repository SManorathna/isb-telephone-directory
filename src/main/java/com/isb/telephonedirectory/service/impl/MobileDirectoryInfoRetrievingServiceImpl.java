package com.isb.telephonedirectory.service.impl;

import com.isb.telephonedirectory.constants.ModelStructureConstants;
import com.isb.telephonedirectory.model.internal.SearchCriteriaObject;
import com.isb.telephonedirectory.service.api.helper.InputParamFormatterService;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoRetrievingService;
import com.isb.telephonedirectory.service.api.helper.QueryBuilderService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class MobileDirectoryInfoRetrievingServiceImpl implements MobileDirectoryInfoRetrievingService {

    private final Client client;
    private final InputParamFormatterService inputParamFormatterService;
    private final QueryBuilderService queryBuilderService;

    @Autowired
    public MobileDirectoryInfoRetrievingServiceImpl(final Client client,
                                                    final InputParamFormatterService inputParamFormatterService,
                                                    final QueryBuilderService queryBuilderService) {
        this.client = client;
        this.inputParamFormatterService = inputParamFormatterService;
        this.queryBuilderService = queryBuilderService;
    }

    @Override
    public Map<String, Object> getMobileSubscriberByMobileNumber(String mobileNumber) {
        SearchHit searchHit = getSearchHitByMobileNumber(mobileNumber);
        return searchHit != null ? searchHit.getSourceAsMap() : null;
    }

    @Override
    public List<Map<String, Object>> getMobileSubscriberByCriteriaText(final Map<String, List<String>> queryParams) {
        final SearchCriteriaObject searchCriteriaObject = inputParamFormatterService.getFormattedSearchCriteriaInput(queryParams);
        final BoolQueryBuilder queryBuilder = queryBuilderService.getQueryBuilder(searchCriteriaObject.getSearchParamMap());

        final SearchResponse response =
            client.prepareSearch(ModelStructureConstants.INDEX_NAME)
                    .setTypes(ModelStructureConstants.TYPE)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setFetchSource(searchCriteriaObject.getRequiredFields(), null)
                    .setQuery(queryBuilder)
                    .setSize(searchCriteriaObject.getLimit())
                    .get();

        return getSourceMapListFromSearchHitList(response);
    }

    @Override
    public List<Map<String, Object>> getAllMobileSubscribers(final Map<String, List<String>> queryParams) {
        final SearchCriteriaObject searchCriteriaObject = inputParamFormatterService.getFormattedPagingParamInput(queryParams);

        final SearchResponse response =
            client.prepareSearch(ModelStructureConstants.INDEX_NAME)
                .setTypes(ModelStructureConstants.TYPE)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setFetchSource(searchCriteriaObject.getRequiredFields(), null)
                .setQuery(QueryBuilders.matchAllQuery())
                .setSize(searchCriteriaObject.getLimit())
                .setFrom(searchCriteriaObject.getOffset())
                .get();

        return getSourceMapListFromSearchHitList(response);
    }

    private List<Map<String,Object>> getSourceMapListFromSearchHitList(final SearchResponse response) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        for (SearchHit hit : searchHits) {
            resultList.add(hit.getSourceAsMap());
        }

        return resultList;
    }

    @Override
    public SearchHit getSearchHitByMobileNumber(final String mobileNumber) {
        final SearchResponse response =
                client.prepareSearch(ModelStructureConstants.INDEX_NAME)
                        .setTypes(ModelStructureConstants.TYPE)
                        .setSearchType(SearchType.QUERY_THEN_FETCH)
                        .setQuery(QueryBuilders.matchQuery(ModelStructureConstants.MOB_SUB_MOBILE_NUMBER, mobileNumber)).get();

        return response.getHits().getHits().length > 0 ? Arrays.asList(response.getHits().getHits()).get(0) : null;
    }
}
