package com.isb.telephonedirectory.service.api.helper;

import com.isb.telephonedirectory.model.internal.SearchCriteriaObject;

import java.util.List;
import java.util.Map;

public interface InputParamFormatterService {
    SearchCriteriaObject getFormattedSearchCriteriaInput(Map<String, List<String>> queryParams) throws RuntimeException;

    SearchCriteriaObject getFormattedPagingParamInput(Map<String, List<String>> queryParams);
}
