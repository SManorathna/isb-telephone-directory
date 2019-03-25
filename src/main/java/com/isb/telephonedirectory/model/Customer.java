package com.isb.telephonedirectory.model;

import com.isb.telephonedirectory.validator.customerid.IsValidTaxIdentityNumber;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Customer {
    @NotBlank(message = "{customer.name.NotBlank}")
    @Field(type = FieldType.Text)
    private String name;

    @NotBlank(message = "{customer.idNumber.NotBlank}")
    @IsValidTaxIdentityNumber(message = "{customer.idNumber.IsValidTaxIdentityNumber}")
    @Field(type = FieldType.Text)
    private String idNumber;

    @Field(type = FieldType.Text)
    private String address;

    @NotNull(message = "{customer.gender.NotNull}")
    @Field(type = FieldType.Text)
    private Gender gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
