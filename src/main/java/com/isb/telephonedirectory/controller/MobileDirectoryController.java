package com.isb.telephonedirectory.controller;

import com.isb.telephonedirectory.constants.ModelStructureConstants;
import com.isb.telephonedirectory.exception.ResultNotFoundException;
import com.isb.telephonedirectory.model.Customer;
import com.isb.telephonedirectory.model.MobileSubscriber;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoDeletingService;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoPersistingService;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoRetrievingService;
import com.isb.telephonedirectory.service.api.MobileDirectoryInfoUpdatingService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/mobile-subscribers")
public class MobileDirectoryController {

    private final MobileDirectoryInfoPersistingService infoPersistingService;
    private final MobileDirectoryInfoUpdatingService infoUpdatingService;
    private final MobileDirectoryInfoDeletingService infoDeletingService;
    private final MobileDirectoryInfoRetrievingService infoRetrievingService;
    private final MessageSource messageSource;

    @Autowired
    public MobileDirectoryController(final MobileDirectoryInfoPersistingService infoPersistingService,
                                     final MobileDirectoryInfoUpdatingService infoUpdatingService,
                                     final MobileDirectoryInfoDeletingService infoDeletingService,
                                     final MobileDirectoryInfoRetrievingService infoRetrievingService,
                                     final MessageSource messageSource) {
        this.infoPersistingService = infoPersistingService;
        this.infoUpdatingService = infoUpdatingService;
        this.infoDeletingService = infoDeletingService;
        this.infoRetrievingService = infoRetrievingService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> createMobileSubscriber(@Valid @RequestBody final MobileSubscriber mobileSubscriber,
                                                         @RequestHeader(name = "Accept-Language", required = false) final Locale locale) throws IOException {
        IndexResponse response = infoPersistingService.createMobileSubscriber(mobileSubscriber);

        if(response == null || response.status() != RestStatus.CREATED) {
            throw new ResultNotFoundException(messageSource.getMessage("persisting.failed", null, locale));
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/switch-plan/{mobileNumber}", method = RequestMethod.PUT)
    public ResponseEntity<String> switchMobileSubscriberPlan(@PathVariable final String mobileNumber,
                                                             @RequestHeader(name = "Accept-Language", required = false) final Locale locale)
            throws InterruptedException, ExecutionException, IOException {
        final UpdateResponse response = infoUpdatingService.switchMobileSubscriberPlan(mobileNumber);

        if(response == null) {
            throw new ResultNotFoundException(messageSource.getMessage("updating.failed", null, locale));
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/owners/{mobileNumber}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateMobileSubscribeOwner(@PathVariable final String mobileNumber,
                                                             @Valid @RequestBody final Customer owner,
                                                             @RequestHeader(name = "Accept-Language", required = false) final Locale locale)
            throws InterruptedException, ExecutionException, IOException {
        final UpdateResponse response = infoUpdatingService.updateMobileSubscribeOwner(mobileNumber, owner);

        if(response == null) {
            throw new ResultNotFoundException(messageSource.getMessage("updating.failed", null, locale));
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{mobileNumber}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateMobileSubscribeUser(@PathVariable final String mobileNumber,
                                                            @Valid @RequestBody final Customer user,
                                                            @RequestHeader(name = "Accept-Language", required = false) final Locale locale)
            throws InterruptedException, ExecutionException, IOException {
        final UpdateResponse response = infoUpdatingService.updateMobileSubscribeUser(mobileNumber, user);

        if(response == null) {
            throw new ResultNotFoundException(messageSource.getMessage("updating.failed", null, locale));
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/owners-users/{mobileNumber}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateMobileSubscribeOwnerAndUser(@PathVariable final String mobileNumber,
                                                                    @Valid @RequestBody final Map<String, Customer> customerMap,
                                                                    @RequestHeader(name = "Accept-Language", required = false) final Locale locale)
            throws InterruptedException, ExecutionException, IOException {
        final Customer owner = customerMap.get(ModelStructureConstants.OWNER_OBJECT);
        final Customer user = customerMap.get(ModelStructureConstants.USER_OBJECT);

        final UpdateResponse response = infoUpdatingService.updateMobileSubscribeOwnerAndUser(mobileNumber, owner, user);

        if(response == null) {
            throw new ResultNotFoundException(messageSource.getMessage("updating.failed", null, locale));
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{mobileNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMobileSubscriber(@PathVariable final String mobileNumber,
                                                         @RequestHeader(name = "Accept-Language", required = false) final Locale locale) {
        DeleteResponse response = infoDeletingService.deleteMobileSubscriber(mobileNumber);

        if(response == null) {
            throw new ResultNotFoundException(messageSource.getMessage("deleting.failed", null, locale));
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public void deleteAll() {
        infoDeletingService.deleteAll();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> getAllMobileSubscribers(@RequestParam final MultiValueMap<String, String> queryParams,
                                                                             @RequestHeader(name = "Accept-Language", required = false) final Locale locale) {
        final List<Map<String, Object>> result = infoRetrievingService.getAllMobileSubscribers(queryParams);

        if(result == null || result.isEmpty()) {
            handleResultNotFoundException(locale);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{mobileNumber}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getMobileSubscriberByCriteriaText(@PathVariable final String mobileNumber,
                                                                                 @RequestHeader(name = "Accept-Language", required = false) final Locale locale) {
        final Map<String, Object> result = infoRetrievingService.getMobileSubscriberByMobileNumber(mobileNumber);

        if(result == null || result.isEmpty()) {
            handleResultNotFoundException(locale);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> getMobileSubscriberByCriteriaText(@RequestParam final MultiValueMap<String, String> queryParams,
                                                                                       @RequestHeader(name = "Accept-Language", required = false) final Locale locale) throws RuntimeException {
        final List<Map<String, Object>> result = infoRetrievingService.getMobileSubscriberByCriteriaText(queryParams);

        if(result == null || result.isEmpty()) {
            handleResultNotFoundException(locale);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private void handleResultNotFoundException(final Locale locale) {
        throw new ResultNotFoundException(messageSource.getMessage("reading.failed", null, locale));
    }
}
