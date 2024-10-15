package com.applemart.order;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDTOMapper {
    private final ModelMapper modelMapper = new ModelMapper();


    public OrderDTO toDTO(Order order) {
        modelMapper
                .typeMap(Order.class, OrderDTO.class)
                .addMapping(src -> src.getPaymentMethod().getName(), OrderDTO::setPaymentMethod)
                .addMapping(src -> src.getShippingMethod().getName(), OrderDTO::setShippingMethod);
        return modelMapper.map(order, OrderDTO.class);
    }

    public Order toEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    public Order toEntity(OrderUpdateRequest orderUpdateRequest) {
        return modelMapper.map(orderUpdateRequest, Order.class);
    }

    public Order toEntity(OrderCreationRequest orderCreationRequest) {
        return modelMapper.map(orderCreationRequest, Order.class);
    }

    public Order toEntity(OrderUpdateRequestForUser orderCreationRequest) {
        return modelMapper.map(orderCreationRequest, Order.class);
    }
}
