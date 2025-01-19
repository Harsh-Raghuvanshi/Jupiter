package com.exengg.jupiter.Controller;

import com.exengg.jupiter.Entity.Product;
import com.exengg.jupiter.Enums.ProductType;
import com.exengg.jupiter.Service.ProductService;
import com.exengg.jupiter.Service.UserProdService;
import com.exengg.jupiter.Utils.ProductValidators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserProdService userProdService;

    @GetMapping("/single/{productId}")
    public ResponseEntity<?> getProductWithId(@PathVariable String productId){
        try{
            return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while fetching single product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductWithCategory(@PathVariable String category){
        try{
            ProductValidators.validateProductCategory(category);
            return new ResponseEntity<>(productService.getProductCategoryWise(category),HttpStatus.OK);
        }catch(Exception e){
            log.error("Error while fetching products as per category");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/my_products")
    public ResponseEntity<?> getMyProducts(){
        try{
            return new ResponseEntity<>(userProdService.getMyProducts(),HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while fetching user's self uploaded products");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/my_watchlist")
    public ResponseEntity<?> getMyWatchList(){
        try{
            return new ResponseEntity<>(userProdService.getMyWatchList(),HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while fetching user's watchlist");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        try{
            productService.createProduct(product);
            return new ResponseEntity<>("Product Created Successfully",HttpStatus.CREATED);
        }catch (Exception e){
            log.error("Error while creating product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product,@PathVariable String productId){
        try{
            productService.updateProduct(product,productId);
            return new ResponseEntity<>("Product Updated Successfully",HttpStatus.CREATED);
        }catch (Exception e){
            log.error("Error while updating product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@RequestBody Product product,@PathVariable String productId){
        try{
            productService.deleteProduct(productId);
            return new ResponseEntity<>("Product Deleted Successfully",HttpStatus.CREATED);

        }catch (Exception e){
            log.error("Error while deleting product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
