package com.exengg.jupiter.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "deals")
public class Deals {
    @Id
    private String id;

    private String productId;
    private String ownerId;
    private String customerId;
    private LocalDateTime localDateTime;
}
