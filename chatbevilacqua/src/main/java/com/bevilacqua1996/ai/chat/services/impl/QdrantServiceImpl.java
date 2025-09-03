package com.bevilacqua1996.ai.chat.services.impl;

import com.bevilacqua1996.ai.chat.services.QdrantService;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QdrantServiceImpl implements QdrantService {

    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public QdrantServiceImpl(EmbeddingModel embeddingClient, VectorStore vectorStore) {
        this.embeddingModel = embeddingClient;
        this.vectorStore = vectorStore;
    }

    @Override
    public void saveDoc(String id, String text) {
        // Cria Document com texto e metadados
        Document doc = new Document(text, Map.of("id", id));
        // Adiciona ao VectorStore (Qdrant, por exemplo)
        vectorStore.add(List.of(doc));
    }
}
