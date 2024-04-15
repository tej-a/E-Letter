package com.adp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adp.ELetterApplication;
import com.adp.entity.BasicCandidate;



@DataJpaTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ELetterApplication.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BasicCandidateRepoTest{
	
	@Autowired
	private BasicCandidateRepository basicCandidateRepository;
	
	@Test
	public void  whenFindByEmail_thenReturnBasicCandidate() {
		BasicCandidate candidate = new BasicCandidate();
		candidate.setEmail("test@example.com");
		candidate.setName("john");
		
		basicCandidateRepository.save(candidate);
		
		BasicCandidate found= basicCandidateRepository.getReferenceById(candidate.getEmail());
		
		assertThat(found.getEmail()).isEqualTo(candidate.getEmail());
		
	}
	
	@Test
	public void whenInvalidEmail_thenReturnNull() {
		BasicCandidate fromDb = basicCandidateRepository.getReferenceById("does not exist");
		assertThat(fromDb).isNull();
	}
	
	@Test
	public void whenFindAll_thenReturnAllCandidates() {
		BasicCandidate candidate1 = new BasicCandidate();
		candidate1.setEmail("test1@example.com");
		candidate1.setName("alice");
			
		BasicCandidate candidate2 = new BasicCandidate();
		candidate2.setEmail("test2@example.com");
		candidate2.setName("bob");
		
		List<BasicCandidate> allCandidatesBefore = basicCandidateRepository.findAll();
		
		basicCandidateRepository.save(candidate1);
		basicCandidateRepository.save(candidate2);
		
		List<BasicCandidate> allCandidatesAfter = basicCandidateRepository.findAll();
		
		assertThat(allCandidatesAfter).hasSize(allCandidatesBefore.size()+2);
		
		
		
		
	}
	
	
	
}