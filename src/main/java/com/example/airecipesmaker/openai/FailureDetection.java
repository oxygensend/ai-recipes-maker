package com.example.airecipesmaker.openai;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class detects whether chat completion failed or not
 */
@Service
public class FailureDetection {
    private final Pattern pattern;

    FailureDetection() {
        List<String> keywords = Arrays.asList("niestety", "nie umiem", "przykro mi", "przepraszam, ale", "nie potrawie", "nie mam możliwości", "zbyt mało informacji", "nie podano", "nie jest wystarczający", "sorry, i could not", "sorry, i can not");
        String stringPattern = String.join("|", keywords);
        this.pattern = Pattern.compile("(?i)(" + stringPattern + ")");
    }

    public boolean detect(String text) {
        return pattern.matcher(text).find();
    }
}
