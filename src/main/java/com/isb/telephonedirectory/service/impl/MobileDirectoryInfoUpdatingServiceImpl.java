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
    public UpdateResponse switchMobileSubscriberPlan(String mobileNumber) throws InterruptedException, ExecutionException, IOException {
        final SearchHit searchHit = infoRetrievingService.getSearchHitByMobileNumber(mobileNumber);

        if(searchHit == null) {
            return null;
        }

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
        return client.update(updateRequest).get();
    }

    @Override
    public UpdateResponse updateMobileSubscribeOwner(String mobileNumber, Customer owner) throws InterruptedException, ExecutionException, IOException {
        final SearchHit searchHit = infoRetrievingService.getSearchHitByMobileNumber(mobileNumber);

        if(searchHit == null) {
            return null;
        }

        return updateCustomer(searchHit.getId(), ModelStructureConstants.OWNER_OBJECT, owner);
    }

    @Override
    public UpdateResponse updateMobileSubscribeUser(String mobileNumber, Customer user) throws InterruptedException, ExecutionException, IOException {
        final SearchHit searchHit = infoRetrievingService.getSearchHitByMobileNumber(mobileNumber);

        if(searchHit == null) {
            return null;
        }

        return updateCustomer(searchHit.getId(), ModelStructureConstants.USER_OBJECT, user);
    }

    private UpdateResponse updateCustomer(final String id, final String objectIdentifier, final Customer customer) throws InterruptedException, ExecutionException, IOException{
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(ModelStructureConstants.INDEX_NAME)
                .type(ModelStructureConstants.TYPE)
                .id(id)
                .doc(jsonBuilder()
                        .startObject()
                        .startObject(objectIdentifier)
                        .field(ModelStructureConstants.CUST_NAME, customer.getName())
                        .field(ModelStructureConstants.CUST_ID_NUMBER, customer.getIdNumber())
                        .field(ModelStructureConstants.CUST_OWNER_ADDRESS, customer.getAddress())
                        .field(ModelStructureConstants.CUST_OWNER_GENDER, customer.getGender())
                        .endObject()
                        .endObject());
        return client.update(updateRequest).get();
    }

    @Override
    public UpdateResponse updateMobileSubscribeOwnerAndUser(String mobileNumber, Customer owner, Customer user) throws InterruptedException, ExecutionException, IOException {
        final SearchHit searchHit = infoRetrievingService.getSearchHitByMobileNumber(mobileNumber);

        if(searchHit == null) {
            return null;
        }

        final String id = searchHit.getId();

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
        return client.update(updateRequest).get();
    }

    private ServiceType getChangedServiceType(final String serviceType) {
        if(ServiceType.MOBILE_POSTPAID.toString().equals(serviceType)) {
            return ServiceType.MOBILE_PREPAID;
        }

        return ServiceType.MOBILE_POSTPAID;
    }
}
