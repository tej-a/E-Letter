package com.adp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adp.entity.BasicCandidate;
import com.adp.exception.UserException;
import com.adp.service.CandidateDetailsService;

@RestController
@RequestMapping("/basic")
@CrossOrigin(origins = "http://localhost:3000")
public class BasicCandidateController {
	
	@Autowired
	private CandidateDetailsService candidateDetailsService;
	
	@PostMapping("/upload-data")
	public ResponseEntity<?> uploadBasicCandidateDetails(@RequestParam("file") MultipartFile file){
		candidateDetailsService.saveCandidatesToDatabase(file);
		return ResponseEntity.ok(Map.of("Message","added successfully"));
	}
	
	@GetMapping("/candidates")
	public ResponseEntity<List<BasicCandidate>> getCandidates(){
		return new ResponseEntity<>(candidateDetailsService.getBasicCadidate(),HttpStatus.FOUND);
	}
	
	
	@PostMapping("/get-emails")
	public void getEmails(@RequestBody List<String> emails) throws UserException{
		candidateDetailsService.updateLinkStatus(emails);
	}
	
	@GetMapping("/get-total-basic-candidates")
	public ResponseEntity<String> getTotalBasicCandidates(){
		return new ResponseEntity<>(candidateDetailsService.getTotalBasicCandidates(),HttpStatus.OK);
	}
	
	@GetMapping("/get-total-sent-links")
	public ResponseEntity<String> getTotalSentLinks(){
		return new ResponseEntity<>(candidateDetailsService.getTotalSentLinks(),HttpStatus.OK);
	}
	
	@GetMapping("/get-total-not-sent-links")
	public ResponseEntity<String> getTotalNotSentLinks(){
		return new ResponseEntity<>(candidateDetailsService.getTotalNotSentLinks(),HttpStatus.OK);
	}
	
	@PostMapping("/update-comments")
	public ResponseEntity<?> updateComments(@RequestParam("comment") String comments,@RequestParam("email") String email) {
		candidateDetailsService.updateComments(comments,email);
		return ResponseEntity.ok(Map.of("Message","comments added"));
	}
}
