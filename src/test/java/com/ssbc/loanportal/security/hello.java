package com.ssbc.loanportal.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


public class hello {

    public int add( int a, int b)
    {
        return a + b;
    }
    @Test
    public void add(){
        int result = add(4,5);

        Assertions.assertEquals(9,result);

    }

}
