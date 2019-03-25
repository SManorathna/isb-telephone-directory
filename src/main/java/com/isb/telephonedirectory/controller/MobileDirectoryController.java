package com.isb.telephonedirectory.controller;

import com.isb.telephonedirectory.model.MobileSubscriber;
import com.isb.telephonedirectory.constants.ModelStructureConstants;
import com.isb.telephonedirectory.model.Customer;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoDeletingService;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoPersistingService;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoRetrievingService;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoUpdatingService;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/mobile-subscribers")
public class MobileDirectoryController {

    private final MobileDirectoryInfoPersistingService infoPersistingService;
    private final MobileDirectoryInfoUpdatingService infoUpdatingService;
    private final MobileDirectoryInfoDeletingService infoDeletingService;
    private final MobileDirectoryInfoRetrievingService infoRetrievingService;

    @Autowired
    public MobileDirectoryController(final MobileDirectoryInfoPersistingService infoPersistingService,
                                     final MobileDirectoryInfoUpdatingService infoUpdatingService,
                                     final MobileDirectoryInfoDeletingService infoDeletingService,
                                     final MobileDirectoryInfoRetrievingService infoRetrievingService) {
        this.infoPersistingService = infoPersistingService;
        this.infoUpdatingService = infoUpdatingService;
        this.infoDeletingService = infoDeletingService;
        this.infoRetrievingService = infoRetrievingService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public IndexResponse createMobileSubscriber(@Valid @RequestBody final MobileSubscriber mobileSubscriber) throws IOException {
        return infoPersistingService.createMobileSubscriber(mobileSubscriber);
    }

    @RequestMapping(value = "/switch-plan/{mobileNumber}", method = RequestMethod.PUT)
    public String switchMobileSubscriberPlan(@PathVariable final String mobileNumber)
            throws InterruptedException, ExecutionException, IOException {
        return infoUpdatingService.switchMobileSubscriberPlan(mobileNumber);
    }

    @RequestMapping(value = "/owners/{mobileNumber}", method = RequestMethod.PUT)
    public String updateMobileSubscribeOwner(@PathVariable final String mobileNumber, @RequestBody final Customer owner)
            throws InterruptedException, ExecutionException, IOException {
        return infoUpdatingService.updateMobileSubscribeOwner(mobileNumber, owner);
    }

    @RequestMapping(value = "/users/{mobileNumber}", method = RequestMethod.PUT)
    public String updateMobileSubscribeUser(@PathVariable final String mobileNumber, @RequestBody final Customer user)
            throws InterruptedException, ExecutionException, IOException {
        return infoUpdatingService.updateMobileSubscribeUser(mobileNumber, user);
    }

    @RequestMapping(value = "/owners-users/{mobileNumber}", method = RequestMethod.PUT)
    public String updateMobileSubscribeOwnerAndUser(@PathVariable final String mobileNumber, @RequestBody final Map<String, Customer> customerMap)
            throws InterruptedException, ExecutionException, IOException {
        final Customer owner = customerMap.get(ModelStructureConstants.OWNER_OBJECT);
        final Customer user = customerMap.get(ModelStructureConstants.USER_OBJECT);

        return infoUpdatingService.updateMobileSubscribeOwnerAndUser(mobileNumber, owner, user);
    }

    @RequestMapping(value = "/{mobileNumber}", method = RequestMethod.DELETE)
    public String deleteMobileSubscriber(@PathVariable final String mobileNumber) {
        return infoDeletingService.deleteMobileSubscriber(mobileNumber);
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public void deleteAll() {
        infoDeletingService.deleteAll();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Map<String, Object>> getAllMobileSubscribers(@RequestParam final MultiValueMap<String, String> queryParams) {
        return infoRetrievingService.getAllMobileSubscribers(queryParams);
    }

    @RequestMapping(value = "/{mobileNumber}", method = RequestMethod.GET)
    public Map<String, Object> getMobileSubscriberByCriteriaText(@PathVariable final String mobileNumber) {
        return infoRetrievingService.getMobileSubscriberByMobileNumber(mobileNumber);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Map<String, Object>> getMobileSubscriberByCriteriaText(@RequestParam final MultiValueMap<String, String> queryParams) throws RuntimeException {
        return infoRetrievingService.getMobileSubscriberByCriteriaText(queryParams);
    }
}
