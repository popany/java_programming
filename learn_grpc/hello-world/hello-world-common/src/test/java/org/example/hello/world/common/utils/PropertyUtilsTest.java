package org.example.hello.world.common.utils;

import static org.junit.Assert.assertNotNull;

import org.example.hello.world.common.Constants;
import org.junit.Test;

public class PropertyUtilsTest {
    @Test
    public void getString() {
        assertNotNull(PropertyUtils.getString(Constants.NODE_NAME));
    }   
}
