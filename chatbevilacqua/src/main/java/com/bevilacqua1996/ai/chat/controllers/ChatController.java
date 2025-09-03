package com.bevilacqua1996.ai.chat.controllers;

import com.bevilacqua1996.ai.chat.services.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    public final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public String ask(@RequestBody Map<String, String> content) {
        return chatService.answer(content.get("question"));
    }

}
