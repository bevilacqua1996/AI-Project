package com.bevilacqua1996.ai.chat.services;

import java.util.List;
import java.util.Map;

public interface ClickUpService {

    List<String> getDocs(String workspaceId);

    List<Map<String, Object>> getDocPages(String workspaceId);


}
