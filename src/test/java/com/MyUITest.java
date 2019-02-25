package com;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyUITest {

    @Test
    void multiply() {
        MyUI ui = new MyUI();

        Assert.assertEquals(ui.multiply(5, 2), 10);
    }
}