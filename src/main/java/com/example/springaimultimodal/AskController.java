package com.example.springaimultimodal;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AskController {

  private final ChatClient chatClient;

  @Value("classpath:/capturehappy.jpeg")
  Resource forecastImageResource;

  public AskController(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  @GetMapping("/ask")
  public Answer ask() throws Exception {
    return chatClient.prompt()
        .user(userSpec -> userSpec
            .text("Is this person happy?")
            .media(MimeTypeUtils.IMAGE_JPEG, forecastImageResource))
        .call()
        .entity(Answer.class);
  }

}
