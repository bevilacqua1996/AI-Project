package com.bevilacqua1996.ai.chat.runners;

import com.bevilacqua1996.ai.chat.services.ClickUpService;
import com.bevilacqua1996.ai.chat.services.QdrantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Runner implements CommandLineRunner {

    private final ClickUpService clickUpService;
    private final QdrantService qdrantService;

    @Value( "${clickup.workspaceId}")
    private String workspaceId;

    public Runner(ClickUpService clickUpService, QdrantService qdrantService) {
        this.clickUpService = clickUpService;
        this.qdrantService = qdrantService;
    }

    @Override
    public void run(String... args) {

        List<Map<String, Object>> docs = clickUpService.getDocPages(workspaceId);
        docs.stream()
                .filter(doc -> doc.get("id") != null && doc.get("content") != null)
                .forEach(doc ->
                        qdrantService.saveDoc(
                                doc.get("id").toString(),
                                doc.get("content").toString()
                        )
                );

    }
}

