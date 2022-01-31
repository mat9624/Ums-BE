package com.example.pixeltek.REST.service;

import org.junit.Assert;
import org.junit.Test;

public class SpringEnvironmentTest extends AbstractUmsApplicationTests{

    private final static int expectedPort=8480;
    @Test
    public void testEnvironment(){
        Assert.assertEquals(port,expectedPort);
    }
}