package com.isb.telephonedirectory.service.impl;

import com.isb.telephonedirectory.constants.ModelStructureConstants;
import com.isb.telephonedirectory.model.Customer;
import com.isb.telephonedirectory.model.ServiceType;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoRetrievingService;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoUpdatingService;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class MobileDirectoryInfoUpdatingServiceImpl implements MobileDirectoryInfoUpdatingService {

    private final Client client;
    private final MobileDirectoryInfoRetrievingService infoRetrievingService;

    @Autowired
    public MobileDirectoryInfoUpdatingServiceImpl(final Client client,
                                                  final MobileDirectoryInfoRetrievingService infoRetrievingService) {
        this.client = client;
        this.infoRetrievingService = infoRetrievingService;
    }

    @Override
    public String switchMobileSubscriberPlan(String mobileNumber) throws InterruptedException, ExecutionException, IOException {
        final SearchHit searchHit = infoRetrievingService.getSearchHitByMobileNumber(mobileNumber);
        final Map<String, Object> map = searchHit.getSourceAsMap();
        final String id = searchHit.getId();

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(ModelStructureConstants.INDEX_NAME)
                .type(ModelStructureConstants.TYPE)
                .id(id)
                .doc(jsonBuilder()
                        .startObject()
                        .field(ModelStructureConstants.MOB_SUB_SERVICE_TYPE, getChangedServiceType((String)map.get(ModelStructureConstants.MOB_SUB_SERVICE_TYPE)))
                        .endObject());
        UpdateResponse updateResponse = client.update(updateRequest).get();
        return updateResponse.status().toString();
    }

    @Override
    public String updateMobileSubscribeOwner(String mobileNumber, Customer owner) throws InterruptedException, ExecutionException, IOException {
        final String id = infoRetrievingService.getSearchHitByMobileNumber(mobileNumber).getId();

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(ModelStructureConstants.INDEX_NAME)
                .type(ModelStructureConstants.TYPE)
                .id(id)
                .doc(jsonBuilder()
                        .startObject()
                        .startObject(ModelStructureConstants.OWNER_OBJECT)
                        .field(ModelStructureConstants.CUST_NAME, owner.getName())
                        .field(ModelStructureConstants.CUST_ID_NUMBER, owner.getIdNumber())
                        .field(ModelStructureConstants.CUST_OWNER_ADDRESS, owner.getAddress())
                        .field(ModelStructureConstants.CUST_OWNER_GENDER, owner.getGender())
                        .endObject()
                        .endObject());
        UpdateResponse updateResponse = client.update(updateRequest).get();
        return updateResponse.status().toString();
    }

    @Override
    public String updateMobileSubscribeUser(String mobileNumber, Customer user) throws InterruptedException, ExecutionException, IOException {
        final String id = infoRetrievingService.getSearchHitByMobileNumber(mobileNumber).getId();

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(ModelStructureConstants.INDEX_NAME)
                .type(ModelStructureConstants.TYPE)
                .id(id)
                .doc(jsonBuilder()
                        .startObject()
                        .startObject(ModelStructureConstants.USER_OBJECT)
                        .field(ModelStructureConstants.CUST_NAME, user.getName())
                        .field(ModelStructureConstants.CUST_ID_NUMBER, user.getIdNumber())
                        .field(ModelStructureConstants.CUST_OWNER_ADDRESS, user.getAddress())
                        .field(ModelStructureConstants.CUST_OWNER_GENDER, user.getGender())
                        .endObject()
                        .endObject());
        UpdateResponse updateResponse = client.update(updateRequest).get();
        return updateResponse.status().toString();
    }

    @Override
    public String updateMobileSubscribeOwnerAndUser(String mobileNumber, Customer owner, Customer user) throws InterruptedException, ExecutionException, IOException {
        final String id = infoRetrievingService.getSearchHitByMobileNumber(mobileNumber).getId();

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(ModelStructureConstants.INDEX_NAME)
                .type(ModelStructureConstants.TYPE)
                .id(id)
                .doc(jsonBuilder()
                        .startObject()
                        .startObject(ModelStructureConstants.OWNER_OBJECT)
                        .field(ModelStructureConstants.CUST_NAME, owner.getName())
                        .field(ModelStructureConstants.CUST_ID_NUMBER, owner.getIdNumber())
                        .field(ModelStructureConstants.CUST_OWNER_ADDRESS, owner.getAddress())
                        .field(ModelStructureConstants.CUST_OWNER_GENDER, owner.getGender())
                        .endObject()
                        .startObject(ModelStructureConstants.USER_OBJECT)
                        .field(ModelStructureConstants.CUST_NAME, user.getName())
                        .field(ModelStructureConstants.CUST_ID_NUMBER, user.getIdNumber())
                        .field(ModelStructureConstants.CUST_OWNER_ADDRESS, user.getAddress())
                        .field(ModelStructureConstants.CUST_OWNER_GENDER, user.getGender())
                        .endObject()
                        .endObject());
        UpdateResponse updateResponse = client.update(updateRequest).get();
        return updateResponse.status().toString();
    }

    private ServiceType getChangedServiceType(final String serviceType) {
        if(ServiceType.POST_PAID.toString().equals(serviceType)) {
            return ServiceType.PRE_PAID;
        }

        return ServiceType.POST_PAID;
    }
}
