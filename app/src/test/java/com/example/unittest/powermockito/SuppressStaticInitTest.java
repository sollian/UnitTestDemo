package com.example.unittest.powermockito;

import com.example.unittest.data.Dao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author shouxianli on 2020/9/24.
 */
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.example.unittest.data.Dao")
public class SuppressStaticInitTest {

    @Test
    public void test() {
        new Dao();
    }
}
