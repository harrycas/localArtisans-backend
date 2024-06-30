package com.arquiproject.msvc_catalog.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "svc-artisans", url = "${svc.artisans.url}")
public interface UserClientRest {
}
