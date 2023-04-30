package com.example.airecipesmaker.unit.openai;

import com.example.airecipesmaker.exception.CannotGenerateRecipeException;
import com.example.airecipesmaker.openai.ChatCompletion;
import com.example.airecipesmaker.openai.FailureDetection;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ChatCompletionTest {
    private ChatCompletion chatCompletion;

    @Autowired
    private FailureDetection failureDetection;
    @Mock
    private OpenAiService openAiService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.chatCompletion = new ChatCompletion(openAiService, failureDetection);
    }

    @Test
    public void testGenerateRecipeQuestion() throws CannotGenerateRecipeException {
        List<String> products = new ArrayList<>();
        products.add("banana -> 2 quantity");
        products.add("flour -> 1kg");
        products.add("milk -> 1kg");
        products.add("sugar -> 1kg");

        ChatCompletionChoice chatChoice = new ChatCompletionChoice();
        chatChoice.setMessage(new ChatMessage("bot", "Recipe: pancakes"));

        List<ChatCompletionChoice> chatChoices = new ArrayList<>();
        chatChoices.add(chatChoice);

        ChatCompletionResult chatCompletionResult = new ChatCompletionResult();
        chatCompletionResult.setChoices(chatChoices);

        doReturn(chatCompletionResult).when(openAiService).createChatCompletion(any(ChatCompletionRequest.class));

        String response = chatCompletion.generateRecipeQuestion(products);

        verify(openAiService, times(1)).createChatCompletion(any(ChatCompletionRequest.class));
        assertEquals("Recipe: pancakes", response);
    }

    @Test()
    public void testGenerateRecipeQuestionWithFailure() throws CannotGenerateRecipeException {
        List<String> products = new ArrayList<>();
        products.add("strawberry");
        products.add("onion");

        ChatCompletionChoice chatChoice = new ChatCompletionChoice();
        chatChoice.setMessage(new ChatMessage("bot", "Sorry, I could not generate a recipe with the given ingredients."));

        List<ChatCompletionChoice> chatChoices = new ArrayList<>();
        chatChoices.add(chatChoice);

        ChatCompletionResult chatCompletionResult = new ChatCompletionResult();
        chatCompletionResult.setChoices(chatChoices);

        doReturn(chatCompletionResult).when(openAiService).createChatCompletion(any(ChatCompletionRequest.class));

        assertThrows(CannotGenerateRecipeException.class, () -> chatCompletion.generateRecipeQuestion(products));
    }
}
