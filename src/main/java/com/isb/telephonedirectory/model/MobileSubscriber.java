package com.isb.telephonedirectory.model;

import com.isb.telephonedirectory.constants.ModelStructureConstants;
import com.isb.telephonedirectory.customvalidator.mobilenumber.ValidMobileNumber;
import com.isb.telephonedirectory.customvalidator.servicetype.ValidServiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = ModelStructureConstants.INDEX_NAME, type = ModelStructureConstants.TYPE)
public class MobileSubscriber{
    @NotBlank(message = "{mobileSubscriber.mobileNumber.NotBlank}")
    @ValidMobileNumber(message = "{mobileSubscriber.mobileNumber.ValidMobileNumber}")
    @Field(type = FieldType.Text)
    private String mobileNumber;

    @NotNull(message = "{mobileSubscriber.serviceType.NotNull}")
    @Field(type = FieldType.Text)
    @ValidServiceType(enumClass = ServiceType.class, message = "{mobileSubscriber.serviceType.ValidServiceType}")
    private String serviceType;

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

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType.toUpperCase();
    }
}
