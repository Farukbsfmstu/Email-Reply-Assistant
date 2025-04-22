package com.email.Email_Reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class Email_Controller {
	
	@Autowired
	private  Email_Service emailService ;
	
	@PostMapping("/genarete")
	public ResponseEntity<String> genaretEntityEmail(@RequestBody Email_Entity request){
		System.out.println(request.getEmail_Content()) ;
		request.setTone("Now according to this mail text ,provide professional reply to him.Don't mistake first mail its me");
		String reponse= emailService.Email_Reply_Service(request);
		return ResponseEntity.ok(reponse) ;
}
}