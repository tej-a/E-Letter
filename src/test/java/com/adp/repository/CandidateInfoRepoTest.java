package com.adp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adp.ELetterApplication;
import com.adp.entity.CandidateInfo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ELetterApplication.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class CandidateInfoRepoTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CandidateInfoRepository candidateInfoRepository;
	
	@Test
	public void whenFindByEmail_thenReturnCandidateInfo() {
		CandidateInfo candidateInfo = new CandidateInfo();
		candidateInfo.setEmail("test@example.com");
		
		entityManager.persistAndFlush(candidateInfo);
		CandidateInfo found = candidateInfoRepository.getReferenceById(candidateInfo.getEmail());
		
		assertThat(found.getEmail()).isEqualTo(candidateInfo.getEmail());
		
	}
	
	@Test
	public void whenInvalidEmail_thenReturnNull() {
		CandidateInfo fromDb = candidateInfoRepository.getReferenceById("doesnotexist@example.com");
		assertThat(fromDb).isNull();
	}
	
	@Test
	public void givenSetOgCandidateInfos_whenFindAll_thenReturnAllCandidateInfos() {
		CandidateInfo candidate1 = new CandidateInfo();
		candidate1.setEmail("test@example.com");
		
		CandidateInfo candidate2 = new CandidateInfo();
		candidate2.setEmail("test2@example.com");
		
		List<CandidateInfo> allCandidateInfosBefore = candidateInfoRepository.findAll();
		
		entityManager.persist(candidate1);
		entityManager.persist(candidate2);
		
		entityManager.flush();
		
		List<CandidateInfo> allCandidatesInfosAfter = candidateInfoRepository.findAll();
		
		assertThat(allCandidatesInfosAfter).hasSize(allCandidateInfosBefore.size()+2);
		
	}
	
	@Test
	public void givenSetOfCandidateInfos_whenFindAll_thenReturnAllCandidateInfos() {
		
		CandidateInfo candidate1 = createCandidateInfo();
		CandidateInfo candidate2 = createCandidateInfo();
		
		candidate2.setEmail("test2@example.com");
		List<CandidateInfo> allCandidateInfosBefore = candidateInfoRepository.findAll();
		
		entityManager.persist(candidate1);
		entityManager.persist(candidate2);
		
		entityManager.flush();
		
		List<CandidateInfo> allCandidateInfosAfter = candidateInfoRepository.findAll();
		
		assertThat(allCandidateInfosAfter).hasSize(allCandidateInfosBefore.size()+2);
	}
	
	private CandidateInfo createCandidateInfo() {
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
	public void whenFindByOfferLetterStatus_thenReturnCandidateInfos() {
		
		CandidateInfo candidateInfo1 = createCandidateInfo();
		CandidateInfo candidateInfo2 = createCandidateInfo();
		candidateInfo2.setEmail("test2@example.com");
		entityManager.persistAndFlush(candidateInfo1);
		entityManager.persistAndFlush(candidateInfo2);
		
		List<CandidateInfo> result=
			 candidateInfoRepository.findByOfferLetterStatus("generated");
	
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getOfferLetterStatus()).isEqualTo("generated");
	}
	
	@Test
	public void findByOfferLetterStatus_WhenValidStatus_ReturnMatchingCandidates() {
		CandidateInfo generatedCandidate = createCandidateInfo();
		CandidateInfo notGeneratedCandidate = createCandidateInfo();
		notGeneratedCandidate.setEmail("test2@example.com");
		notGeneratedCandidate.setOfferLetterStatus("not generated");
		
		entityManager.persistAndFlush(generatedCandidate);
		entityManager.persistAndFlush(notGeneratedCandidate);
		
		List<CandidateInfo> generatedCandidates = candidateInfoRepository.findByOfferLetterStatus("generated");
		
		assertThat(generatedCandidates).hasSize(1);
		assertThat(generatedCandidates.get(0).getOfferLetterStatus()).isEqualTo("generated");
	}
	
	@Test
	public void findByOfferLetterStatus_WhenNotGenerated_ReturnEmptyList() {
		List<CandidateInfo> candidates = candidateInfoRepository.findByOfferLetterStatus("not generated");
		
		assertThat(candidates).isEmpty();
	}
	
	
	@Test
	public void findByApprovalStarus_WhenApproved_ReturnMatchingCandidates() {
		
		CandidateInfo approvedCandidate = createCandidateInfo();
		CandidateInfo notApprovedCandidate= createCandidateInfo();
		notApprovedCandidate.setEmail("test2@example.com");
		notApprovedCandidate.setOfferLetterStatus("not generated");
		notApprovedCandidate.setApprovalStatus("not approved");
		
		entityManager.persistAndFlush(approvedCandidate);
		entityManager.persistAndFlush(notApprovedCandidate);
		
		List<CandidateInfo> approvedCandidates = candidateInfoRepository.findByApprovalStatus("approved");
		
		assertThat(approvedCandidates).hasSize(1);
		assertThat(approvedCandidates.get(0).getApprovalStatus()).isEqualTo("approved");
	}
	
	@Test
	public void findByApprovalStatus_ReturnEmptyList() {
		
		List<CandidateInfo> candidates = candidateInfoRepository.findByApprovalStatus("not approved");
		assertThat(candidates).isEmpty();
	}
}
