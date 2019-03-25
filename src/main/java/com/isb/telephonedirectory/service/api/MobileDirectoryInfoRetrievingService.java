package com.isb.telephonedirectory.service.api;

import org.elasticsearch.search.SearchHit;

import java.util.List;
import java.util.Map;

public interface MobileDirectoryInfoRetrievingService {
    Map<String, Object> getMobileSubscriberByMobileNumber(String mobileNumber);

    List<Map<String, Object>> getMobileSubscriberByCriteriaText(Map<String, List<String>> queryParams) throws RuntimeException;

    List<Map<String, Object>> getAllMobileSubscribers(Map<String, List<String>> queryParams);

    SearchHit getSearchHitByMobileNumber(String mobileNumber);
}
