package com.isb.telephonedirectory.model;

import com.isb.telephonedirectory.constants.ModelStructureConstants;
import com.isb.telephonedirectory.validator.mobilenumber.IsValidMobileNumber;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Document(indexName = ModelStructureConstants.INDEX_NAME, type = ModelStructureConstants.TYPE)
public class MobileSubscriber{
    @NotBlank(message = "{mobileSubscriber.mobileNumber.NotBlank}")
    @IsValidMobileNumber(message = "{mobileSubscriber.mobileNumber.IsValidMobileNumber}")
    @Field(type = FieldType.Text)
    private String mobileNumber;

    @NotNull(message = "${mobileSubscriber.serviceType.NotNull}")
    @Field(type = FieldType.Text)
    private ServiceType serviceType;

    @NotNull(message = "{mobileSubscriber.serviceStartDate.NotNull}")
    @Field(type = FieldType.Long)
    private Long serviceStartDate;

    @NotNull(message = "{mobileSubscriber.owner.NotNull}")
    @Valid
    @Field(type = FieldType.Nested)
    private Customer owner;

    @NotNull(message = "{mobileSubscriber.user.NotNull}")
    @Valid
    @Field(type = FieldType.Nested)
    private Customer user;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Long getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(Long serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }
}
