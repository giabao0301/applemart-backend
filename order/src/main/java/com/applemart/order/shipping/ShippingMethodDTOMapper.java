package com.applemart.order.shipping;

import com.applemart.order.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShippingMethodDTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();


    public ShippingMethodDTO toDTO(ShippingMethod shippingMethod) {
        return modelMapper.map(shippingMethod, ShippingMethodDTO.class);
    }

    public ShippingMethod toEntity(ShippingMethodDTO shippingMethodDTO) {
        return modelMapper.map(shippingMethodDTO, ShippingMethod.class);
    }
}
