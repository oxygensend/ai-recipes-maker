package com.example.airecipesmaker.openai;

import com.example.airecipesmaker.exception.CannotGenerateRecipeException;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ChatCompletion implements GenerateRecipeInterface {

    private final OpenAiService openAiService;
    private final FailureDetection failureDetection;
    private final List<ChatMessage> chatMessageList;
    private final Logger logger;

    public ChatCompletion(OpenAiService openAiService, FailureDetection failureDetection) {

        this.openAiService = openAiService;
        this.failureDetection = failureDetection;
        this.chatMessageList = new ArrayList<>();
        this.logger = Logger.getLogger(ChatCompletion.class.getName());
    }

    public String generateRecipeQuestion(List<String> products, int intances) throws CannotGenerateRecipeException {

        String message;
        if (intances == 1) {
            message = "Stwórz prosze przepis z podanych produktów: " + products;
        } else {
            message = "Stwórz " + intances + " propozycji na przepis z podanych produktów: " + products + "Odpowiedź zwróć w formacie json {\"przepis_1\" : \"tresc przepisu\",\"przepis_1\" : \"tresc przepisu\"} prosze";
        }

        logger.info("Sending question: " + message + " to chat completion");
        String response = getResponseFromChat(message);
        logger.info("Getting response from chat for message: " + message);

        if (failureDetection.detect(response)) {
            throw new CannotGenerateRecipeException("Recipe cannot be generated due to missing data");
        }

        return response;
    }


    private void addMessage(String message) {
        ChatMessage chatMessage = new ChatMessage("user", message);
        chatMessageList.add(chatMessage);
    }

    private ChatCompletionRequest buildRequest() {
        return ChatCompletionRequest
                .builder()
                .messages(chatMessageList)
                .model("gpt-3.5-turbo")
                .build();
    }

    private String getResponseFromChat(String message) {
        addMessage(message);
        ChatCompletionRequest completionRequest = this.buildRequest();

        return openAiService
                .createChatCompletion(completionRequest)
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }

}
