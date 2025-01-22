package com.exengg.jupiter.Dto.Requests;

import lombok.Data;


@Data
public class DealRequest {
    private String productId;
    private String ownerId;
    private String customerId;
}
