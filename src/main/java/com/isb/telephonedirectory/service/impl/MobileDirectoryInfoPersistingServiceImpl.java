package com.isb.telephonedirectory.service.impl;

import com.isb.telephonedirectory.constants.ModelStructureConstants;
import com.isb.telephonedirectory.model.MobileSubscriber;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoPersistingService;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MobileDirectoryInfoPersistingServiceImpl implements MobileDirectoryInfoPersistingService {

    private final Client client;

    @Autowired
    public MobileDirectoryInfoPersistingServiceImpl(final Client client) {
        this.client = client;
    }

    @Override
    public IndexResponse createMobileSubscriber(MobileSubscriber mobileSubscriber) throws IOException {
        return client.prepareIndex(ModelStructureConstants.INDEX_NAME, ModelStructureConstants.TYPE)
                .setSource(getJsonBuilderForMobileSubscriber(mobileSubscriber))
                .get();
    }

    private XContentBuilder getJsonBuilderForMobileSubscriber(final MobileSubscriber mobileSubscriber) throws IOException {
        return XContentFactory.jsonBuilder()
            .startObject()
            .field(ModelStructureConstants.MOB_SUB_MOBILE_NUMBER, mobileSubscriber.getMobileNumber())
            .field(ModelStructureConstants.MOB_SUB_SERVICE_TYPE, mobileSubscriber.getServiceType())
            .field(ModelStructureConstants.MOB_SUB_SERVICE_START_DATE, mobileSubscriber.getServiceStartDate())
                .startObject(ModelStructureConstants.OWNER_OBJECT)
                    .field(ModelStructureConstants.CUST_NAME, mobileSubscriber.getOwner().getName())
                    .field(ModelStructureConstants.CUST_ID_NUMBER, mobileSubscriber.getOwner().getIdNumber())
                    .field(ModelStructureConstants.CUST_OWNER_ADDRESS, mobileSubscriber.getOwner().getAddress())
                    .field(ModelStructureConstants.CUST_OWNER_GENDER, mobileSubscriber.getOwner().getGender())
                .endObject()
                .startObject(ModelStructureConstants.USER_OBJECT)
                    .field(ModelStructureConstants.CUST_NAME, mobileSubscriber.getUser().getName())
                    .field(ModelStructureConstants.CUST_ID_NUMBER, mobileSubscriber.getUser().getIdNumber())
                    .field(ModelStructureConstants.CUST_OWNER_ADDRESS, mobileSubscriber.getUser().getAddress())
                    .field(ModelStructureConstants.CUST_OWNER_GENDER, mobileSubscriber.getUser().getGender())
                .endObject()
            .endObject();
    }
}
