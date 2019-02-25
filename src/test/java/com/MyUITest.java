package com;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.junit.*;
//import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import static org.junit.jupiter.api.Assertions.*;

/*
class MyUITest {

    @Test
    void multiply() {
        MyUI ui = new MyUI();

        Assert.assertEquals(ui.multiply(5, 2), 10);
    }
}

*/

//http://localhost:8000/Segfault_war_exploded/       :::::This our welcome page address

public class MyUITest{
  /*  @Before
    public  void prepare() {
      //  setBaseUrl("http://localhost:8080/test");

    }
*/
    @Test
    public void testBasic(){
        MyUI ui = new MyUI();
        Assert.assertEquals(ui.multiply(5, 2), 10);
    }

    @Test
    void init() {
        MyUI ui = new MyUI();
        Assert.assertTrue(ui.isEnabled());
    }

    @Test
    void addContentPanel() {
        Assert.assertTrue(true);
    }

    @Test
    void multiply() {
        Assert.assertTrue(true);
    }

    @org.junit.Test
    public void firstTest() {
        Assert.assertTrue(true);
    }
}