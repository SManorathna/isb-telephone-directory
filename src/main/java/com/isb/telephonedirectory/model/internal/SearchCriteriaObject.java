package com.isb.telephonedirectory.model.internal;

import com.isb.telephonedirectory.constants.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCriteriaObject {
    private Map<String, List<String>> searchParamMap;
    private String[] requiredFields;
    private int limit;
    private int offset;

    public SearchCriteriaObject(Map<String, List<String>> searchParamMap, String[] requiredFields, int limit) {
        this.searchParamMap = searchParamMap;
        this.requiredFields = requiredFields;
        this.limit = limit;
        this.offset = Constants.DATA_RETRIEVE_DEFAULT_OFFSET - 1;
    }

    public SearchCriteriaObject(String[] requiredFields, int limit, int offset) {
        this.searchParamMap = new HashMap<>();
        this.requiredFields = requiredFields;
        this.limit = limit;
        this.offset = offset - 1;
    }

    public Map<String, List<String>> getSearchParamMap() {
        return searchParamMap;
    }

    public String[] getRequiredFields() {
        return requiredFields;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }
}
