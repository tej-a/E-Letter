package com.adp.controller;

import java.io.IOException;
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

import com.adp.dto.CandidateInfoDTO;
import com.adp.dto.OfferGenerationDetails;
import com.adp.exception.UserException;
import com.adp.service.CandidateInfoService;

import jakarta.validation.Valid;

@RestController

@RequestMapping("/Info")
@CrossOrigin(origins = "http://localhost:3000")
public class CandidateInfoController {
	
	@Autowired
	CandidateInfoService candidateInfoService;
	
	@PostMapping("/post-candidate-info")
	public ResponseEntity<?> postCandidateInfo(@Valid @RequestBody CandidateInfoDTO candidateInfoDTO) throws UserException, IOException{
			candidateInfoService.addToDataBase(candidateInfoDTO);
			return ResponseEntity.ok(Map.of("Message","added successfully"));
	}
	
	@GetMapping("/display-candidate-info")
	public ResponseEntity<List<CandidateInfoDTO>> getCandidateInfo() throws UserException{
		return new ResponseEntity<>(candidateInfoService.getCandidateInfo(),HttpStatus.FOUND);
	}
	
	@GetMapping("/get-generated-candidate-info")
	public ResponseEntity<List<CandidateInfoDTO>> getGeneratedCandidateInfo() throws UserException{
		return new ResponseEntity<>(candidateInfoService.getGeneratedCandidateInfo(),HttpStatus.FOUND);
	}
	
	@GetMapping("/get-not-generated-candidate-info")
	public ResponseEntity<List<CandidateInfoDTO>> getNotGeneratedCandidateInfo() throws UserException{
		return new ResponseEntity<>(candidateInfoService.getNotGeneratedCandidateInfo(),HttpStatus.FOUND);
	}
	
	@GetMapping("/get-not-approved-candidate-info")
	public ResponseEntity<List<CandidateInfoDTO>> getNotApprovedCandidateInfo() throws UserException{
		return new ResponseEntity<>(candidateInfoService.getNotApprovedCandidateInfo(),HttpStatus.FOUND);
	}
	
	@GetMapping("/get-approved-candidate-info")
	public ResponseEntity<List<CandidateInfoDTO>> getApprovedCandidateInfo() throws UserException{
		return new ResponseEntity<>(candidateInfoService.getApprovedCandidateInfo(),HttpStatus.FOUND);
	}
	
	
	@PostMapping("/generatePDF/")
	public void generatePDF(@RequestBody OfferGenerationDetails details) {
		try {
			candidateInfoService.generateOfferLetters(details);
		} catch (UserException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/get-not-verified-candidate-info")
	public ResponseEntity<List<CandidateInfoDTO>> getNotVerified() throws UserException {
		try {
			return new ResponseEntity<>(candidateInfoService.getNotVerified(),HttpStatus.OK);
		} catch (UserException e) {
			throw e;
		}
	}
	
	@PostMapping("/get-verified-emails")
	public void getVerifiedEmails(@RequestBody List<String> emails){
		candidateInfoService.updateVerificationStatus(emails);
	}
	
	@PostMapping("/upload-documents")
	public ResponseEntity<?> uploadBasicCandidateDetails(@RequestParam("file") MultipartFile file,@RequestParam("email") String email){
		candidateInfoService.saveDocsToDatabase(file, email);
		return ResponseEntity.ok(Map.of("Message","added successfully"));
	}
	
	@GetMapping("/get-edDocs-blob")
	public byte[] getEdDocsBlob(@RequestParam("email") String email ) throws UserException {
		return candidateInfoService.getEdDocsBlob(email);
	}
	
	@GetMapping("/get-offerL-blob")
	public byte[] getOfferLBlob(@RequestParam("email") String email ) {
		return candidateInfoService.getOfferLBlob(email);
	}
	
	@PostMapping("/get-send-approved-emails")
	public void getSendApprovedEmails(@RequestBody List<String> emails) throws UserException{
		candidateInfoService.updateApprovalStatus(emails);
	}
	
	@GetMapping("/get-send-approved-candidate-info")
	public ResponseEntity<List<CandidateInfoDTO>> getSendApprovedCandidateInfo() throws UserException{
		return new ResponseEntity<>(candidateInfoService.getSentForApprovedCandidateInfo(),HttpStatus.FOUND);
	}
	
	@PostMapping("/get-approved-emails")
	public void getApprovedEmails(@RequestBody List<String> emails) throws UserException{
		candidateInfoService.updateApprovalStatus2(emails);
	}
	@PostMapping("/update-comments")
	public ResponseEntity<?> updateComments(@RequestParam("comment") String comments,@RequestParam("email") String email) {
		candidateInfoService.updateComments(comments,email);
		return ResponseEntity.ok(Map.of("Message","comments added"));
	}
	
	@PostMapping("/update-mcomments")
	public ResponseEntity<?> updateMComments(@RequestParam("comment") String comments,@RequestParam("email") String email) {
		candidateInfoService.updateMComments(comments,email);
		return ResponseEntity.ok(Map.of("Message","comments added"));
	}
	
	@GetMapping("/get-total-candidates")
	public ResponseEntity<String> totalCandaidates(){
		return new ResponseEntity<> (candidateInfoService.totalCandidates(),HttpStatus.OK);
	}
	
	@GetMapping("/get-total-verified-candidates")
	public ResponseEntity<String> getTotalVerifiedCandaidates(){
		return new ResponseEntity<> (candidateInfoService.getTotalVerifiedCandaidates(),HttpStatus.OK);
	}
	
	@GetMapping("/get-total-not-verified-candidates")
	public ResponseEntity<String> getTotalNotVerifiedCandaidates(){
		return new ResponseEntity<> (candidateInfoService.getTotalNotVerifiedCandaidates(),HttpStatus.OK);
	}
	
	@GetMapping("/get-total-generated-candidates")
	public ResponseEntity<String> getTotalGeneratedCandaidates(){
		return new ResponseEntity<> (candidateInfoService.getTotalGeneratedCandaidates(),HttpStatus.OK);
	}
	
	@GetMapping("/get-total-not-generated-candidates")
	public ResponseEntity<String> getTotalNotGeneratedCandaidates(){
		return new ResponseEntity<> (candidateInfoService.getTotalNotGeneratedCandaidates(),HttpStatus.OK);
	}
	
	@GetMapping("/get-total-approved-candidates")
	public ResponseEntity<String> getTotalApprovedCandaidates(){
		return new ResponseEntity<> (candidateInfoService.getTotalApprovedCandaidates(),HttpStatus.OK);
	}
	
	@GetMapping("/get-total-sent-for-approval-candidates")
	public ResponseEntity<String> getTotalSentForApprovalCandaidates(){
		return new ResponseEntity<> (candidateInfoService.getTotalSentForApprovalCandaidates(),HttpStatus.OK);
	}
	
	@GetMapping("/get-total-not-approved-candidates")
	public ResponseEntity<String> getTotalNotApprovedCandaidates(){
		return new ResponseEntity<> (candidateInfoService.getTotalNotApprovedCandaidates(),HttpStatus.OK);
	}
	
	
	@PostMapping("/post-sent-status")
	public void updateSentStatus(@RequestBody List<String> emails) {
		candidateInfoService.updateSentStatus(emails);
	}
	
}
