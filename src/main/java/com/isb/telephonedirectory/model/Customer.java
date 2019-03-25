package com.isb.telephonedirectory.model;

import com.isb.telephonedirectory.validator.customerid.IsValidTaxIdentityNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
