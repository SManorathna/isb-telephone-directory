package com.isb.telephonedirectory.service.api;

import com.isb.telephonedirectory.model.MobileSubscriber;
import org.elasticsearch.action.index.IndexResponse;

import java.io.IOException;

public interface MobileDirectoryInfoPersistingService {

    IndexResponse createMobileSubscriber(MobileSubscriber mobileSubscriber) throws IOException;
}
