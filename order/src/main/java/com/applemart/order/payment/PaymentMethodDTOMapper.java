package com.applemart.order.payment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMethodDTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();


    public PaymentMethodDTO toDTO(PaymentMethod paymentMethod) {
        return modelMapper.map(paymentMethod, PaymentMethodDTO.class);
    }

    public PaymentMethod toEntity(PaymentMethodDTO paymentMethodDTO) {
        return modelMapper.map(paymentMethodDTO, PaymentMethod.class);
    }
}
