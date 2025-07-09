package com.VolunTrack.demo.IAM.Infrastructure.Tokens;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;

public interface TokenService {
    String generateToken(UserDetails userDetails, Map<String, Object> extraClaims);
    String generateToken(UserDetails userDetails);
    boolean validateToken(String token);
    String extractUsername(String token);
}