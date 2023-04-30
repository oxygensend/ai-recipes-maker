package com.example.airecipesmaker.unit.openai;

import com.example.airecipesmaker.openai.FailureDetection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class FailureDetectionTest {

    @Autowired
    private FailureDetection failureDetection;

    @Test
    void testShouldReturnTrueWhenTextContainsKeywords() {
        String text = "Przykro mi, nie mam takiej możliwości";
        assertTrue(failureDetection.detect(text));
    }

    @Test
    void testShouldReturnFalseWhenTextDoesNotContainKeywords() {
        String text = "Stwórz mi coś z tego";
        assertFalse(failureDetection.detect(text));
    }
}