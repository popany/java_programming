package org.example;

import com.beust.jcommander.JCommander;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class UseJcommanderTest {

    @Test
    public void testArgParse() {
        String[] argv = { "-log", "2", "-groups", "unit1,unit2,unit3", "-debug", "-Doption=value", "a", "b", "c" };

        Config config = new Config();
        JCommander.newBuilder().addObject(config).build().parse(argv);

        Assert.assertEquals(2, config.verbose.intValue());
        Assert.assertEquals("unit1,unit2,unit3", config.groups);
        Assert.assertEquals(true, config.debug);
        Assert.assertEquals("value", config.dynamicParams.get("option"));
        Assert.assertEquals(Arrays.asList("a", "b", "c"), config.parameters);
    }
}
