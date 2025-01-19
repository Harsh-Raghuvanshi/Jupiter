package com.exengg.jupiter.Utils;

import com.exengg.jupiter.Enums.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class ProductValidators {
    public static void validateProductCategory(String category) throws Exception {
        boolean match = Arrays.stream(ProductType.values()).anyMatch(productType -> productType.name().equals(category));
        if(!match) {
            log.error("Invalid Category in URL category : {}",category);
            throw new Exception("Invalid Category in URL category");
        }
    }
}
