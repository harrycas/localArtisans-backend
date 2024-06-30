package com.arquiproject.svc_artisans.client;

import com.arquiproject.svc_artisans.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-catalog", url = "${msvc.catalog.url}")
public interface CatalogClientRest {

    @PutMapping("/update/userDelete/{userId}")
    void updateProductsToInactive(@PathVariable int userId);

}

//@FeignClient(name = "msvc-catalog", url = "${msvc.catalog.url}")
//When still using the url from app properties to connect with other microservice