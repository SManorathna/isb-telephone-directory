package com.isb.telephonedirectory.service.api;

import com.isb.telephonedirectory.model.Customer;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface MobileDirectoryInfoUpdatingService {

    UpdateResponse switchMobileSubscriberPlan(String mobileNumber) throws InterruptedException, ExecutionException, IOException;

    UpdateResponse updateMobileSubscribeOwner(String mobileNumber, Customer owner) throws InterruptedException, ExecutionException, IOException;

    UpdateResponse updateMobileSubscribeUser(String mobileNumber, Customer user) throws InterruptedException, ExecutionException, IOException;

    UpdateResponse updateMobileSubscribeOwnerAndUser(String mobileNumber, Customer owner, Customer user) throws InterruptedException, ExecutionException, IOException;

}
