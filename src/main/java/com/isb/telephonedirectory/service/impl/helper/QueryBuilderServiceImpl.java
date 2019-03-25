package com.isb.telephonedirectory.service.impl.helper;

import com.isb.telephonedirectory.constants.ModelStructureConstants;
import com.isb.telephonedirectory.constants.ConditionalOpConstants;
import com.isb.telephonedirectory.constants.Constants;
import com.isb.telephonedirectory.service.api.helper.QueryBuilderService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class QueryBuilderServiceImpl implements QueryBuilderService {

    private final List<String> rangeQueryApplicableFields;

    public QueryBuilderServiceImpl() {
        rangeQueryApplicableFields = Collections.singletonList(ModelStructureConstants.MOB_SUB_SERVICE_START_DATE);
    }

    @Override
    public BoolQueryBuilder getQueryBuilder(final Map<String, List<String>> searchParamMap) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        searchParamMap.forEach((key, valueList) -> {
            valueList.forEach(value -> {
                queryBuilder.must(getQueryBuilderByMapValue(key, value));
            });

        });

        return queryBuilder;
    }

    private QueryBuilder getQueryBuilderByMapValue(final String key, final String value) {
        QueryBuilder queryBuilder;
        if(rangeQueryApplicableFields.contains(key)) {
            queryBuilder = getRangeQueryApplicableFieldQuery(key, value);
        }
        else {
            queryBuilder = QueryBuilders.queryStringQuery(value).lenient(true).field(key);
        }
        return queryBuilder;
    }

    private QueryBuilder getRangeQueryApplicableFieldQuery(final String key, final String value) {
        QueryBuilder queryBuilder;
        if(value.startsWith(ConditionalOpConstants.GREATER_THAN)) {
            queryBuilder = QueryBuilders.rangeQuery(key).gt(
                    value.replaceFirst(ConditionalOpConstants.GREATER_THAN, Constants.EMPTY));
        }
        else if(value.startsWith(ConditionalOpConstants.GREATER_THAN_EQUAL)) {
            queryBuilder = QueryBuilders.rangeQuery(key).gte(
                    value.replace(ConditionalOpConstants.GREATER_THAN_EQUAL, Constants.EMPTY));
        }
        else if(value.startsWith(ConditionalOpConstants.LESS_THAN)) {
            queryBuilder = QueryBuilders.rangeQuery(key).lt(
                    value.replace(ConditionalOpConstants.LESS_THAN, Constants.EMPTY));
        }
        else if(value.startsWith(ConditionalOpConstants.LESS_THAN_EQUAL)) {
            queryBuilder = QueryBuilders.rangeQuery(key).lte(
                    value.replace(ConditionalOpConstants.LESS_THAN_EQUAL, Constants.EMPTY));
        }
        else {
            queryBuilder = QueryBuilders.queryStringQuery(value).lenient(true).field(key);
        }

        return queryBuilder;
    }
}
