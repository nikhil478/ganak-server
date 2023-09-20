package com.auth.ganak.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
@Transactional
public class JwtService {


    public String extractJwtToken(String authorizationHeader) {
        // Remove the "Bearer " prefix from the token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public String extractLoginIdFromToken(String jwtToken) {
        // JWT tokens are typically base64-encoded, so we need to split and decode the payload part
        String[] parts = jwtToken.split("\\.");
        if (parts.length == 3) {
            String payloadBase64 = parts[1];
            byte[] payloadBytes = Base64.getDecoder().decode(payloadBase64);
            String payloadJson = new String(payloadBytes);

            // Parse the payload JSON as a JsonObject
            JsonParser jsonParser = new JsonParser();
            JsonObject payloadObj = jsonParser.parse(payloadJson).getAsJsonObject();

            // Extract the subject (sub) claim
            if (payloadObj.has("sub")) {
                return payloadObj.get("sub").getAsString();
            }
        }

        return null;
    }
}
