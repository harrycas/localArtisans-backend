package com.arquiproject.svc_artisans.client;

import com.arquiproject.svc_artisans.model.DTOs.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-catalog", url = "${msvc.catalog.url}")
public interface CatalogClientRest {

    @PutMapping("/product/userDelete/{userId}")
    void updateProductsToInactive(@PathVariable Long userId);

    @GetMapping("/product/info/{productId}")
    ProductInfo getProductById(@PathVariable("productId") Long productId);

}

/* When still using the url from app properties to connect with other microservice
@FeignClient(name = "msvc-catalog", url = "${msvc.catalog.url}")*/

/*
When connected directly with the application name in app properties
@FeignClient(name = "msvc-catalog")*/
