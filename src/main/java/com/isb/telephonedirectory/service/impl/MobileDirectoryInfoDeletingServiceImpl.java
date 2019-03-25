package com.isb.telephonedirectory.service.impl;

import com.isb.telephonedirectory.constants.ModelStructureConstants;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoDeletingService;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoRetrievingService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileDirectoryInfoDeletingServiceImpl implements MobileDirectoryInfoDeletingService {

    private final Client client;
    private final MobileDirectoryInfoRetrievingService infoRetrievingService;

    @Autowired
    public MobileDirectoryInfoDeletingServiceImpl(final Client client,
                                                  final MobileDirectoryInfoRetrievingService infoRetrievingService) {
        this.client = client;
        this.infoRetrievingService = infoRetrievingService;
    }

    @Override
    public DeleteResponse deleteMobileSubscriber(String mobileNumber) {
        final SearchHit searchHit = infoRetrievingService.getSearchHitByMobileNumber(mobileNumber);

        if(searchHit == null) {
            return null;
        }

        DeleteResponse deleteResponse = client.prepareDelete(ModelStructureConstants.INDEX_NAME,
                ModelStructureConstants.TYPE,
                searchHit.getId()).get();

        return deleteResponse;
    }

    @Override
    public void deleteAll() {
        client.admin().indices().delete(new DeleteIndexRequest(ModelStructureConstants.INDEX_NAME)).actionGet();
        client.admin().indices().prepareCreate(ModelStructureConstants.INDEX_NAME).get();
    }
}
