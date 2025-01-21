package com.exengg.jupiter.Dto;

import lombok.Data;


@Data
public class DealRequest {
    private String productId;
    private String ownerId;
    private String customerId;
}
