package com.Objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExportItemTest {

    ExportItem exportItem = new ExportItem();


    @Test
    void getCoursecode() {
        exportItem.setCoursecode("1");
        assertEquals("1",exportItem.getCoursecode());
    }

    @Test
    void setCoursecode() {
        exportItem.setCoursecode("1");
        assertEquals("1",exportItem.getCoursecode());
    }

    @Test
    void getTopicname() {
        exportItem.setTopicname("Software process");
        assertEquals("Software process",exportItem.getTopicname());
    }

    @Test
    void setTopicname() {
        exportItem.setTopicname("Software process");
        assertEquals("Software process",exportItem.getTopicname());
    }

    @Test
    void getDate() {
        exportItem.setDate("19/05/2019");
        assertEquals("19/05/2019",exportItem.getDate());
    }

    @Test
    void setDate() {
        exportItem.setDate("19/05/2019");
        assertEquals("19/05/2019",exportItem.getDate());
    }

    @Test
    void getYos() {
        exportItem.setYos("3");
        assertEquals("3", exportItem.getYos());
    }

    @Test
    void setYos() {
        exportItem.setYos("3");
        assertEquals("3", exportItem.getYos());
    }

    @Test
    void getDegree() {
        exportItem.setDegree("Computer Science");
        assertEquals("Computer Science", exportItem.getDegree());
    }

    @Test
    void setDegree() {
        exportItem.setDegree("Computer Science");
        assertEquals("Computer Science", exportItem.getDegree());
    }

    @Test
    void getFaculties() {
        exportItem.setFaculties("Science");
        assertEquals("Science", exportItem.getFaculties());

    }

    @Test
    void setFaculties() {
        exportItem.setFaculties("Science");
        assertEquals("Science", exportItem.getFaculties());
    }

    @Test
    void getInternalexaminer() {
        exportItem.setInternalexaminer("Hima Vadapalli");
        assertEquals("Hima Vadapalli",exportItem.getInternalexaminer());
    }

    @Test
    void setInternalexaminer() {
        exportItem.setInternalexaminer("Hima Vadapalli");
        assertEquals("Hima Vadapalli",exportItem.getInternalexaminer());
    }

    @Test
    void getExternalexaminer() {
        exportItem.setExternalexaminer("David Anderson");
        assertEquals("David Anderson", exportItem.getExternalexaminer());

    }

    @Test
    void setExternalexaminer() {
        exportItem.setExternalexaminer("David Anderson");
        assertEquals("David Anderson", exportItem.getExternalexaminer());
    }

    @Test
    void getMaterial() {
        exportItem.setMaterial("Engineering");
        assertEquals("Engineering", exportItem.getMaterial());

    }

    @Test
    void setMaterial() {
        exportItem.setMaterial("Engineering");
        assertEquals("Engineering", exportItem.getMaterial());
    }

    @Test
    void getTime() {
        exportItem.setTime("14:00");
        assertEquals("14:00",exportItem.getTime());

    }

    @Test
    void setTime() {
        exportItem.setTime("14:00");
        assertEquals("14:00",exportItem.getTime());
    }

    @Test
    void getMark() {
        exportItem.setMark("5");
        assertEquals("5",exportItem.getMark());
    }

    @Test
    void setMark() {
        exportItem.setMark("5");
        assertEquals("5",exportItem.getMark());
    }

    @Test
    void getInstructions() {
        exportItem.setInstructions("Please read through all questions carefully");
        assertEquals("Please read through all questions carefully",exportItem.getInstructions());
    }

    @Test
    void setInstructions(){
        exportItem.setInstructions("Please read through all questions carefully");
        assertEquals("Please read through all questions carefully",exportItem.getInstructions());
    }
}