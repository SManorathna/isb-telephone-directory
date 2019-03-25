package com.isb.telephonedirectory.service.impl.helper;

import com.isb.telephonedirectory.model.internal.SearchCriteriaObject;
import com.isb.telephonedirectory.constants.Constants;
import com.isb.telephonedirectory.constants.RegexConstants;
import com.isb.telephonedirectory.service.api.helper.InputParamFormatterService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class InputParamFormatterServiceImpl implements InputParamFormatterService {

    @Override
    public SearchCriteriaObject getFormattedSearchCriteriaInput(Map<String, List<String>> queryParams) throws RuntimeException {
        final Map<String, List<String>> searchParamMap = getSearchParamMap(queryParams);
        final String[] requiredFields = getRequiredFields(queryParams);
        final int limit = getLimit(queryParams);

        return new SearchCriteriaObject(searchParamMap, requiredFields, limit);
    }

    @Override
    public SearchCriteriaObject getFormattedPagingParamInput(Map<String, List<String>> queryParams) {
        final String[] requiredFields = getRequiredFields(queryParams);
        final int limit = getLimit(queryParams);
        final int offset = getOffset(queryParams);

        return new SearchCriteriaObject(requiredFields, limit, offset);
    }

    private int getLimit(final Map<String, List<String>> queryParams) throws RuntimeException {
        return getPagingValue(queryParams, Constants.REQ_PARAM_RECORD_LIMIT, Constants.DATA_RETRIEVE_DEFAULT_LIMIT);
    }

    private int getOffset(final Map<String, List<String>> queryParams) throws RuntimeException {
        return getPagingValue(queryParams, Constants.REQ_PARAM_RECORD_OFFSET, Constants.DATA_RETRIEVE_DEFAULT_OFFSET);
    }

    private int getPagingValue(final Map<String, List<String>> queryParams, final String key, final int defaultValue) {
        final List<String> value = queryParams.get(key);
        return value != null && !value.isEmpty() ? Integer.parseInt(value.get(0)) : defaultValue;
    }

    private String[] getRequiredFields(final Map<String, List<String>> queryParams) {
        final String fields = queryParams.getOrDefault(
                Constants.REQ_PARAM_RECORD_FIELDS,
                Collections.singletonList(Constants.EMPTY)).get(0);

        return fields.isEmpty() ? null : fields.split(RegexConstants.COMMA_SPLIT_WITH_TRUNCATE_REGEX);
    }

    private Map<String, List<String>> getSearchParamMap(final Map<String, List<String>> queryParams) {
        queryParams.remove(Constants.REQ_PARAM_RECORD_FIELDS);
        queryParams.remove(Constants.REQ_PARAM_RECORD_LIMIT);

        return queryParams;
    }
}
