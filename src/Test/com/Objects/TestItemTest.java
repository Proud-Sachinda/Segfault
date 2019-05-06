package com.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestItemTest {

    TestItem test = new TestItem();

    @BeforeEach
    void setUp() {
    }

    @Test
    void getTestId() {
        test.setTestId(5);
        assertEquals(5,test.getTestId());
    }

    @Test
    void setTestId() {
        test.setTestId(5);
        assertEquals(5,test.getTestId());
    }

    @Test
    void isTestIsExam() {
        test.setTestIsDraft(true);
        assertTrue(test.isTestIsDraft());
    }

    @Test
    void setTestIsExam() {
        test.setTestIsExam(true);
        assertTrue(test.isTestIsExam());
    }

    @Test
    void isTestIsDraft() {
        test.setTestIsDraft(true);
        assertTrue(test.isTestIsDraft());
    }

    @Test
    void setTestIsDraft() {
        test.setTestIsDraft(true);
        assertTrue(test.isTestIsDraft());
    }

    @Test
    void getTestDraftName() {
        test.setTestDraftName("coms1");
        assertEquals("coms1",test.getTestDraftName());
    }

    @Test
    void setTestDraftName() {
        test.setTestDraftName("coms1");
        assertEquals("coms1",test.getTestDraftName());
    }

    @Test
    void getCourseId() {
        test.setCourseId(2);
        assertEquals(2,test.getCourseId());
    }

    @Test
    void setCourseId() {
        test.setCourseId(2);
        assertEquals(2,test.getCourseId());
    }

    @Test
    void getLectureId() {
        test.setLectureId(2);
        assertEquals(2,test.getLectureId());
    }

    @Test
    void setLectureId() {
        test.setLectureId(2);
        assertEquals(2,test.getLectureId());
    }
}