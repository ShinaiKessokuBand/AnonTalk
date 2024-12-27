package org.shinaikessokuband.anontalk.service;

import java.io.IOException;

public interface MessageService {

    void exportChatHistory(String filePath) throws IOException;
}
