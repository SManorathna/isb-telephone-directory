package com.isb.telephonedirectory.service.api;

import org.elasticsearch.action.delete.DeleteResponse;

public interface MobileDirectoryInfoDeletingService {
    DeleteResponse deleteMobileSubscriber(String mobileNumber);

    void deleteAll();
}
