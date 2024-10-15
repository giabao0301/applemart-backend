package com.applemart.order;

import com.applemart.order.payment.PaymentMethod;
import com.applemart.order.shipping.ShippingMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private LocalDateTime orderDate;

    private Integer addressId;

    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false, length = 50)
    private String paymentStatus; // Đang chờ / Hoàn tất

    @ManyToOne
    @JoinColumn(name = "shipping_method_id", nullable = false)
    private ShippingMethod shippingMethod;

    @Column(nullable = false, length = 50)
    private String orderStatus; // Chờ xác nhận / Đang chuẩn bị hàng / Đang giao / Đã giao / Đã hủy / Trả hàng

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();
}
