package com.exengg.jupiter.Dto.Requests;

import com.exengg.jupiter.Enums.GiveAs;
import com.exengg.jupiter.Enums.ProductType;
import lombok.Data;

@Data
public class ProductRequest {
    private ProductType productType;
    private String name;
    private String description;
    private Double amount;
    private GiveAs giveAs;
    private Integer durationInMonths;
    private Integer quantity;
}
