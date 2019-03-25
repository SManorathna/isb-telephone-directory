package com.isb.telephonedirectory.service.api;

import com.isb.telephonedirectory.model.Customer;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface MobileDirectoryInfoUpdatingService {

    String switchMobileSubscriberPlan(String mobileNumber) throws InterruptedException, ExecutionException, IOException;

    String updateMobileSubscribeOwner(String mobileNumber, Customer owner) throws InterruptedException, ExecutionException, IOException;

    String updateMobileSubscribeUser(String mobileNumber, Customer user) throws InterruptedException, ExecutionException, IOException;

    String updateMobileSubscribeOwnerAndUser(String mobileNumber, Customer owner, Customer user) throws InterruptedException, ExecutionException, IOException;

}
