package com.applemart.order.payment;

import com.applemart.order.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaymentMethodDTO {
    private Integer id;
    private Integer userId;
    private String name;
    private String provider;
    private String accountNumber;
    private LocalDate expiryDate;
    private Boolean isDefault;
}
