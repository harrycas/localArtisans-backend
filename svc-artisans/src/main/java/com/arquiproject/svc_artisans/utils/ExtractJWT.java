package com.arquiproject.svc_artisans.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;

public class ExtractJWT {

  public static String payloadJWTExtraction(String token, String key) {
    try {
      // Dividir el token en partes: header, payload, signature
      String[] splitToken = token.split("\\.");
      Base64.Decoder decoder = Base64.getUrlDecoder();
      String payload = new String(decoder.decode(splitToken[1]));

      // Parsear el payload como JSON
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(payload);

      // Extraer el valor correspondiente a la clave
      return jsonNode.get(key).asText();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}

