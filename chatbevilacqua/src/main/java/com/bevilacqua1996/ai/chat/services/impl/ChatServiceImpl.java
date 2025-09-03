package com.bevilacqua1996.ai.chat.services.impl;

import com.bevilacqua1996.ai.chat.services.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public ChatServiceImpl(ChatClient.Builder chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient.build();
        this.vectorStore = vectorStore;
    }

    @Override
    public String answer(String question) {
        List<Document> documents = vectorStore.similaritySearch(question);

        String context = documents.stream()
                .map(Document::getFormattedContent)
                .collect(Collectors.joining("\n"));

        String prompt = String.format("Answer the question based on the following context:\n%s\nQuestion: %s", context, question);

        return chatClient.prompt(prompt)
                .user(question)
                .call()
                .content();
    }
}
