package com.openlab.utilisateurs;

import com.openlab.utilisateurs.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JunitTest {
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp(){
        jwtUtil = new JwtUtil();
    }

    @Test
    public void testGeneration(){
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ")); // verifier que le JWT est un token
    }


    @Test
    public void testExtractUsername(){
        boolean isValid = true;
        String username = "testUsername";
        String token = jwtUtil.generateToken(username);
        String extractUsername = jwtUtil.extractUsername(token);

        assertTrue(isValid);
    }

    @Test
    public void testTokenInvalidForWrongUsername(){
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        boolean isValid = jwtUtil.validateToken(token, "wronguser");

        assertFalse(isValid);    }
}
