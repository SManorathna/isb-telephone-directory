package com.isb.telephonedirectory.service.api;

public interface MobileDirectoryInfoDeletingService {
    String deleteMobileSubscriber(String mobileNumber);

    void deleteAll();
}
