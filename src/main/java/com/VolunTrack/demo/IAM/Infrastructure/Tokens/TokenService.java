// src/main/java/com/VolunTrack/demo/IAM/Infrastructure/Tokens/TokenService.java
package com.VolunTrack.demo.IAM.Infrastructure.Tokens;

import java.util.Map;

public interface TokenService {
    String generateToken(String username, Map<String, Object> extraClaims);
    String generateToken(String username);
    boolean validateToken(String token);
    String extractUsername(String token);
}