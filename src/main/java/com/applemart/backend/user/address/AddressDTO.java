package com.applemart.backend.user.address;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
    private Integer id;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String address;
    private String addressType;
    private Boolean isDeliveryAddress;

    @JsonProperty("isDeliveryAddress")
    public Boolean getIsDeliveryAddress() {
        return isDeliveryAddress;
    }
}
