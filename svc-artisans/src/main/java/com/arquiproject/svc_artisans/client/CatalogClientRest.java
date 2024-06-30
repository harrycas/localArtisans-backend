package com.arquiproject.svc_artisans.client;

import com.arquiproject.svc_artisans.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Now is connected directly with the application name in app properties
@FeignClient(name = "msvc-catalog")
public interface CatalogClientRest {

    @PutMapping("/update/userDelete/{userId}")
    void updateProductsToInactive(@PathVariable int userId);

}

// When still using the url from app properties to connect with other microservice
// @FeignClient(name = "msvc-catalog", url = "${msvc.catalog.url}")
