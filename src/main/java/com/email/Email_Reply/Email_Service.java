package com.email.Email_Reply;

import java.security.PublicKey;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



@Service
public class Email_Service {
	@Value("${gemini.api.url}")
	private String geminiApiUr ;
	@Value("${gemini.api.key}")
	private String geminiApiKey ;
	
	private final WebClient client;
	
public Email_Service(WebClient.Builder client) {
		this.client = client.build();
	}


public String Email_Reply_Service(Email_Entity request ) {
	
		//build a prompt
	    String prompt= buildPrompt(request);
		//craft a request
	    Map<String, Object> requestBody = Map.of(
	    	    "contents", new Object[]{
	    	        Map.of(
	    	            "parts", new Object[]{
	    	                Map.of("text", prompt)
	    	            }
	    	        )
	    	    }
	    	);
	    
		//do request and get response 
	    String response =client.post()
                .uri(geminiApiUr + "?key=" + geminiApiKey)
                .header("Content-Type","Application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
		//return response 
		return extractResposeContent(response) ;

	}
	

private String extractResposeContent(String response) {
	try {
		ObjectMapper mapper=new ObjectMapper();
		JsonNode rootNode=mapper.readTree(response);
		return rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
		
	}catch (Exception e) {
		return "Error processing request"+e.getMessage();
	}
	
}


private String buildPrompt(Email_Entity request) {
	 StringBuilder prompt=new StringBuilder();
	 prompt.append("Genarete a pofessonal E-mail reply for the following email content. please don't provide subject line");
	 if(request.getTone() != null && request.getTone().isEmpty()) {
		 prompt.append("Use a ").append(request.getTone()).append("tone");
	 }
	 
	 prompt.append("\n orginal email \n").append(request.getEmail_Content());
		
		System.out.println(request.getTone());
	 return prompt.toString() ;
}
}
