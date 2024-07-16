package com.applemart.backend.user.address;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String address;
    private String addressType;

    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    private boolean isDeliveryAddress;

    @JsonProperty("isDeliveryAddress")
    public boolean isDeliveryAddress() {
        return isDeliveryAddress;
    }
}
