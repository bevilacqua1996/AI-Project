package com.bevilacqua1996.ai.chat.services.impl;

import com.bevilacqua1996.ai.chat.services.ClickUpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
public class ClickUpServiceImpl implements ClickUpService {

    private final WebClient webClient;

    public ClickUpServiceImpl(@Value("${clickup.token}") String token, WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.clickup.com/api/v3")
                .defaultHeader(HttpHeaders.AUTHORIZATION, token)
                .build();
    }
    @Override
    public List<String> getDocs(String workspaceId) {
        return webClient.get()
                .uri("/workspaces/{workspaceId}/docs", workspaceId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(resp -> {
                    Object array = resp.get("docs") != null ? resp.get("docs") : resp.get("tasks");
                    if (array instanceof List<?> list) {
                        return list.stream()
                                .filter(Map.class::isInstance)
                                .map(item -> (Map<String, Object>) item)
                                .map(item -> String.valueOf(item.get("id")))
                                .toList();
                    }
                    return List.<String>of();
                })
                .block();



    }

    @Override
    public List<Map<String, Object>> getDocPages(String workspaceId) {
        List<String> docIds = getDocs(workspaceId);

        return Flux.fromIterable(docIds)
                .flatMap(docId ->
                        webClient.get()
                                .uri("/workspaces/{workspaceId}/docs/{docId}/pages", workspaceId, docId)
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                                .onErrorReturn(List.of())
                )
                .flatMapIterable(list -> list)
                .map(page -> Map.of(
                        "id", String.valueOf(page.get("id")),
                        "content", page.get("content")
                ))
                .collectList()
                .block();

    }
}
