package com.ssbc.loanportal.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




class JwtTokenProviderTest {
    JwtTokenProvider provider = new JwtTokenProvider();
    @Test
    void testSomething() {
        assertTrue(true);
    }

    @Test
    void init() {

    }

    @Test
    void generateAccessToken() {
        provider.init();
        String token = provider.generateAccessToken("this_is_a_demo_secret_for_ssbc_loan_portal_12345");

        assertNotNull(token);
    }

    @Test
    void generateRefreshToken() {
    }

    @Test
    void getSubject() {
    }
}