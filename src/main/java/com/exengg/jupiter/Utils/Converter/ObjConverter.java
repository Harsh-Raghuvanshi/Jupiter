package com.exengg.jupiter.Utils.Converter;

import com.exengg.jupiter.Dto.Requests.ProductRequest;
import com.exengg.jupiter.Dto.Requests.UserRequest;
import com.exengg.jupiter.Entity.Product;
import com.exengg.jupiter.Entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ObjConverter {

    private static final ObjectMapper objectMapper=new ObjectMapper();

    public static Product getProductObjFromProductRequest(ProductRequest productRequest){
        try{
            String productReqString=objectMapper.writeValueAsString(productRequest);
            return objectMapper.readValue(productReqString, Product.class);
        }catch(Exception e){
            log.error("Error occurred while transforming product request into product",e);
        }
        return null;
    }

    public static User getUserObjFromUserRequest(UserRequest userRequest){
        try{
            String userRequestString=objectMapper.writeValueAsString(userRequest);
            return objectMapper.readValue(userRequestString, User.class);
        }catch(Exception e){
            log.error("Error occurred while transforming user request into user",e);
        }
        return null;
    }
}
