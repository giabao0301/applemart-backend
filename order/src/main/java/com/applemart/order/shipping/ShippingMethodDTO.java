package com.applemart.order.shipping;

import com.applemart.order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShippingMethodDTO {
    private Integer id;
    private String name;
    private Double price;
}
