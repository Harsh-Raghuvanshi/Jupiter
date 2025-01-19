package com.exengg.jupiter.Entity;

import com.exengg.jupiter.Enums.GiveAs;
import com.exengg.jupiter.Enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product")
public class Product
{
    @Id
    private String id;

    private ProductType productType;
    private String name;
    private String description;
    private Double amount;
    private GiveAs giveAs;
    private Integer durationInMonths;
    private Integer quantity;
    private String ownerId;
}
