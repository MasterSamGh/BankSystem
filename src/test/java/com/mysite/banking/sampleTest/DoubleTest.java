package com.mysite.banking.sampleTest;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class DoubleTest {
    @Ignore
    @Test
    public void caseA(){
        double amount1 = 10.1;
        double amount2 = 20.1;
        double expectedSum = 30.3;
        double result = amount1 + amount2;
        assertEquals(expectedSum,result);

    }
    @Test
    public void caseB(){
        double amount1 = 10.1;
        double amount2 = 20.1;
        double expectedSum = 30.3;
        double result = amount1+amount2;
        System.out.println("expectedSum: "+expectedSum);
        System.out.println("result: "+result);

    }
    @Test
    public void caseC(){
        double amount1 = 0.1;
        double amount2 = 0.2;
        double result = amount1+amount2;
        System.out.println("0.1 + 0.2  = "+result);
    }
    @Test
    public void caseD(){
        System.out.println("0.1 + 0.2  = "+(0.1 + 0.2));
    }
    @Test
    public void caseBD(){
        BigDecimal amount1 =BigDecimal.valueOf(10.1);
        BigDecimal amount2 = BigDecimal.valueOf(20.2);
        BigDecimal expectedSum = BigDecimal.valueOf(30.3);
        BigDecimal result = amount1.add(amount2);
        assertEquals(expectedSum,result);
    }
}
