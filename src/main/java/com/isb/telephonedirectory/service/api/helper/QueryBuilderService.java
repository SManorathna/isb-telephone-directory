package com.isb.telephonedirectory.service.api.helper;

import org.elasticsearch.index.query.BoolQueryBuilder;

import java.util.List;
import java.util.Map;

public interface QueryBuilderService {
    BoolQueryBuilder getQueryBuilder(Map<String, List<String>> searchParamMap);
}
