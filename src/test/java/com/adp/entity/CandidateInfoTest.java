package com.adp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.clearAllCaches;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class CandidateInfoTest {
	
	@Test
	public CandidateInfo createBasicCandidate() {
		
		CandidateInfo candidateInfo = new CandidateInfo();
		
		candidateInfo.setEmail("test@example.com");
		candidateInfo.setName("john");
		candidateInfo.setDateOfBirth(LocalDate.of(1990, 1, 1));
		candidateInfo.setGender("male");
		candidateInfo.setMobileNumber(1234567899L);
		candidateInfo.setSecondaryMobileNumber(198765432L);
		candidateInfo.setHouseNumber("1-2-3");
		candidateInfo.setPcity("city");
		candidateInfo.setPermanentState("state");
		candidateInfo.setZipCode("12345");
		candidateInfo.setCurrentHouseNumber("4-5-6");
		candidateInfo.setCurrentCity("current city");
		candidateInfo.setCurrentState("current state");
		candidateInfo.setCZipCode("67899");
		candidateInfo.setPanNumber("ABCd234981Dd");
		candidateInfo.setBankName("bank");
		candidateInfo.setAccountNumber("123454566KHlk0");
		candidateInfo.setIfscCode("IFSC1234");
		candidateInfo.setOfferLetterStatus("generated");
		candidateInfo.setApprovalStatus("approved");
		
		byte[] offerLetter = "OfferLetterContent".getBytes();
		byte[] documentUpload = "DocumentContent".getBytes();
		candidateInfo.setOfferLetter(offerLetter);
		candidateInfo.setDocumentUpload(documentUpload);
		return candidateInfo;
	}
	
		
		@Test
		public void testBasicAttribute() {
			
			CandidateInfo candidateInfo = createBasicCandidate();
			
		assertEquals("test@example.com", candidateInfo.getEmail());
		assertEquals("john", candidateInfo.getName());
		assertEquals(LocalDate.of(1990, 1, 1), candidateInfo.getDateOfBirth());
		assertEquals("male", candidateInfo.getGender());
		assertEquals(1234567899L, candidateInfo.getMobileNumber());
		assertEquals(198765432L, candidateInfo.getSecondaryMobileNumber());
		assertEquals("1-2-3", candidateInfo.getHouseNumber());
		assertEquals("city", candidateInfo.getPcity());
		assertEquals("state", candidateInfo.getPermanentState());
		assertEquals("12345", candidateInfo.getZipCode());
		assertEquals("4-5-6", candidateInfo.getCurrentHouseNumber());
		assertEquals("current city", candidateInfo.getCurrentCity());
		assertEquals("current state", candidateInfo.getCurrentState());
		assertEquals("67899", candidateInfo.getCZipCode());
		assertEquals("ABCd234981Dd", candidateInfo.getPanNumber());
		assertEquals("bank", candidateInfo.getBankName());
		assertEquals("123454566KHlk0", candidateInfo.getAccountNumber());
		assertEquals("IFSC1234", candidateInfo.getIfscCode());
		assertEquals("generated", candidateInfo.getOfferLetterStatus());
		assertEquals("approved", candidateInfo.getApprovalStatus());
		assertNotNull( candidateInfo.getOfferLetter());
		assertNotNull( candidateInfo.getDocumentUpload());
		
		assertNotNull(candidateInfo.toString());
		}
		
		
		@Test
		public void testEqualityWithNull() {
			CandidateInfo candidateInfo = createBasicCandidate();
			assertNotEquals(candidateInfo, null);
		}

	
	@Test
	public void testEquality() {
		CandidateInfo candidateInfo =createBasicCandidate();
		CandidateInfo sameCandidateInfo = createBasicCandidate();
		CandidateInfo differCandidateInfo = new CandidateInfo();
		
		assertEquals(candidateInfo, sameCandidateInfo);
		assertNotEquals(candidateInfo, differCandidateInfo);

	}
		
	
	@Test
	public void TestNullValues() {
		CandidateInfo nullCandidateInfo = new CandidateInfo();
		
		nullCandidateInfo.setApprovalStatus(null);
		nullCandidateInfo.setOfferLetterStatus(null);
		
		assertNotNull(nullCandidateInfo.toString());
		
	}
	
	@Test
	public void testStringRepr() {
		CandidateInfo candidateInfo= createBasicCandidate();
		
		assertNotNull(candidateInfo.toString());
		
	}
	
	@Test
	public void testDifferObjectsWithSameValues() {
		CandidateInfo candidateInfo = createBasicCandidate();
		CandidateInfo sameValueCandidateInfo= createBasicCandidate();
		
		assertEquals(candidateInfo, sameValueCandidateInfo);
	}
	
	@Test
	public void testByteArrays() {
		CandidateInfo candidateInfo = createBasicCandidate();
		
		assertNotNull(candidateInfo.getOfferLetter());
		assertNotNull(candidateInfo.getDocumentUpload());
	}
	
	@Test
	public void testVariousAttr() {
		
		CandidateInfo candidateInfo = createBasicCandidate();
		
		candidateInfo.setMobileNumber(9999999999L);
		assertNotEquals(1234567890L, candidateInfo.getMobileNumber().longValue());
	}
	
	@Test
	public void testGeneratedOfferletter() {
		CandidateInfo candidateInfo = createBasicCandidate();
		
		candidateInfo.setOfferLetterStatus("generated");
		assertEquals("generated", candidateInfo.getOfferLetterStatus());
		assertEquals("approved", candidateInfo.getApprovalStatus());
	}

	}






//CandidateInfo sameCandidateInfo = new CandidateInfo();
//sameCandidateInfo.setEmail("test@example.com");
//assertEquals(candidateInfo, sameCandidateInfo);
//
//CandidateInfo differCandidateInfo = new CandidateInfo();
//differCandidateInfo.setEmail("another@example.com");
//assertNotEquals(candidateInfo, differCandidateInfo);
//
//assertEquals(candidateInfo.hashCode(), sameCandidateInfo.hashCode());
//assertEquals(candidateInfo.hashCode(), differCandidateInfo.hashCode());
//
//
//CandidateInfo nullCandidate = new CandidateInfo();
//assertNotNull(nullCandidate.toString());
//
//
//
//CandidateInfo generatedLetterCandidate = new CandidateInfo();
//generatedLetterCandidate.setEmail("generate@example.com");
//generatedLetterCandidate.setOfferLetterStatus("generated");
//generatedLetterCandidate.setApprovalStatus("approved");
//
//
//CandidateInfo notGeneratedCandidate = new CandidateInfo();
//notGeneratedCandidate.setEmail("notGenerated@example.com");
//notGeneratedCandidate.setOfferLetterStatus("not generated");
//notGeneratedCandidate.setApprovalStatus("not approved");
//
//assertEquals("not generated", notGeneratedCandidate.getOfferLetterStatus());
//assertEquals("not approved", notGeneratedCandidate.getApprovalStatus());
//
//assertEquals("generated", notGeneratedCandidate.getOfferLetterStatus());
//assertEquals("approved", notGeneratedCandidate.getApprovalStatus());
//
//
//CandidateInfo sameGeneratedLetterCandidate = new CandidateInfo();
//sameGeneratedLetterCandidate.setEmail("test@example.com");
//sameGeneratedLetterCandidate.setOfferLetterStatus("generated");
//sameGeneratedLetterCandidate.setApprovalStatus("approved");
//assertEquals(generatedLetterCandidate, sameGeneratedLetterCandidate);
//
//CandidateInfo differGeneratedOfferCandidate = new CandidateInfo();
//differGeneratedOfferCandidate.setEmail("differ@example.com");
//differGeneratedOfferCandidate.setOfferLetterStatus("generated");
//differGeneratedOfferCandidate.setApprovalStatus("not approved");
//assertNotEquals(generatedLetterCandidate, differGeneratedOfferCandidate);
//
//
//assertEquals(generatedLetterCandidate.hashCode(), sameGeneratedLetterCandidate.hashCode());
//assertNotEquals(generatedLetterCandidate, differGeneratedOfferCandidate);
//
