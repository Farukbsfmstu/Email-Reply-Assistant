package com.email.Email_Reply;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Email_Entity {
	private String email_Content;
	private String tone ;
	
	@JsonCreator
    public Email_Entity(@JsonProperty("email_Content") String email_Content, 
                        @JsonProperty("tone") String tone) {
        this.email_Content = email_Content;
        this.tone = tone;
    }

	public String getEmail_Content() {
		return email_Content;
	}

	public void setEmail_Content(String email_Content) {
		this.email_Content = email_Content;
	}

	public String getTone() {
		return tone;
	}

	public void setTone(String tone) {
		this.tone = tone;
	}
	
	
	

}
