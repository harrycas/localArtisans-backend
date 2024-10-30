package com.arquiproject.svc_artisans.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class AppConfig {
  private static final String UPLOAD_DIR = "svc-artisans/uploads/";

  @PostConstruct
  public void init() {
    File uploadDir = new File(UPLOAD_DIR);
    if (!uploadDir.exists()) {
      uploadDir.mkdir();
    }
  }

}
