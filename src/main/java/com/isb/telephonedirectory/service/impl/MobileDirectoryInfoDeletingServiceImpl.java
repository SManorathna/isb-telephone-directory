package com.isb.telephonedirectory.service.impl;

import com.isb.telephonedirectory.constants.ModelStructureConstants;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoDeletingService;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoRetrievingService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
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
    public String deleteMobileSubscriber(String mobileNumber) {
        DeleteResponse deleteResponse =
                client.prepareDelete(ModelStructureConstants.INDEX_NAME,
                ModelStructureConstants.TYPE,
                infoRetrievingService.getSearchHitByMobileNumber(mobileNumber).getId())
                .get();

        return deleteResponse.getResult().toString();
    }

    @Override
    public void deleteAll() {
        client.admin().indices().delete(new DeleteIndexRequest(ModelStructureConstants.INDEX_NAME)).actionGet();
        client.admin().indices().prepareCreate(ModelStructureConstants.INDEX_NAME).get();
    }
}
