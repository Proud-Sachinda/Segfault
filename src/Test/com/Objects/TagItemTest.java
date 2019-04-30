package com.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagItemTest {

    TagItem tagItem = new TagItem();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTagId() {
        tagItem.setTagId(5);
        assertEquals(5,tagItem.getTagId());
    }

    @Test
    void setTagId() {
        tagItem.setTagId(5);
        assertEquals(5,tagItem.getTagId());
    }

    @Test
    void getQuestionId() {
        tagItem.setQuestionId(5);
        assertEquals(5,tagItem.getQuestionId());
    }

    @Test
    void setQuestionId() {
        tagItem.setQuestionId(5);
        assertEquals(5,tagItem.getQuestionId());
    }

    @Test
    void getTagName() {
        tagItem.setTagName("myTag");
        assertEquals("myTag", tagItem.getTagName());
    }

    @Test
    void setTagName() {
        tagItem.setTagName("myTag");
        assertEquals("myTag", tagItem.getTagName());
    }
}