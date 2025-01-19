package com.exengg.jupiter.Repo;

import com.exengg.jupiter.Entity.Product;
import com.exengg.jupiter.Enums.ProductType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java .util.List;

@Repository
public interface ProductRepo extends MongoRepository<Product,String> {

    List<Product> findByProductType(String productType);

}
