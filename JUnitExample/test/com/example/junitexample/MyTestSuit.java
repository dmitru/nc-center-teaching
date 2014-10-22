/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.junitexample;

import com.sun.corba.se.spi.extension.ZeroPortPolicy;
import java.util.logging.Logger;
import javafx.scene.control.SplitPane;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Dmitry
 */
public class MyTestSuit {
    
    public MyTestSuit() {
        System.out.println("constructor MyTestSuit() is called");
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("setUpClass() - before any tests");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("tearDownClass() - after all tests");
    }
    
    @Before
    public void setUp() {
        System.out.println("setUp() - before each test");
    }
    
    @After
    public void tearDown() {
        System.out.println("teadDown() - after each test");
    }
    
    @Test
    public void testSum() {
        int a = 1;
        int b = 1;
        assertEquals(2, 1 + 1);
    }
    
    @Test
    public void testLogic() {
        assertTrue(false || true);
    }
    
    @Test(expected = NullPointerException.class)
    public void testWithException() {
        throw new NullPointerException("for no reason");
    }
    
    @Test
    public void testFailing() {
        int a = 1;
        int b = 2;
        assertEquals(a, b);
    }
}
