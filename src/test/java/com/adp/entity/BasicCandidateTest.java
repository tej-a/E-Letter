package com.adp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.adp.repository.BasicCandidateRepository;
import com.adp.service.BasicCadidateService;

public class BasicCandidateTest {
	
	@Mock
	private BasicCandidateRepository candidateRepositoryMock;
	
	@InjectMocks
	private BasicCadidateService basicCandidateService;
	
	@Test
	public void testCreatebasicCandidate() {

		BasicCandidate candidate = new BasicCandidate();
		candidate.setEmail("test@example.com");
		candidate.setName("john");
		candidate.setPhnNumber(12345678657L);
		candidate.setCollegeName("abc college");
		candidate.setLinkStatus("sent");
		
		assertEquals("test@example.com", candidate.getEmail());
		assertEquals("john", candidate.getName());
		assertEquals(12345678657L, candidate.getPhnNumber());
		assertEquals("abc college", candidate.getCollegeName());
		assertEquals("sent", candidate.getLinkStatus());
		
	}
	
	
	@Test
	void createBasicCandidate() {
		BasicCandidate candidate = new BasicCandidate();
		assertNotNull(candidate);
		assertNull(candidate.getEmail());
		assertNull(candidate.getName());
		assertNull(candidate.getPhnNumber());
		assertNull(candidate.getCollegeName());
		assertNull(candidate.getLinkStatus());
		
	}

	
	@Test
	public void testSaveCandidate() {
		BasicCandidate candidate = new BasicCandidate();
		candidate.setEmail("test@example.com");
		candidate.setName("john");
		
		when(candidateRepositoryMock.save(any())).thenReturn(candidate);
		
		BasicCandidate savedCandidate = candidateRepositoryMock.save(candidate);
		
		
		assertNotNull(savedCandidate);
		assertEquals("test@example.com", savedCandidate.getEmail());
		assertEquals("john", savedCandidate.getName());
		
	}
	
	@Test
	public void testGetCandiateByEmail() {
		BasicCandidate candidate = new BasicCandidate();
		candidate.setEmail("test@example.com");
		candidate.setName("john");
		candidateRepositoryMock.save(candidate);
		
		when(candidateRepositoryMock.getReferenceById("test@example.com")).thenReturn(candidate);
		
		BasicCandidate retrievedCandidate = candidateRepositoryMock.getReferenceById("test@example.com");
		
		assertNotNull(retrievedCandidate);
		assertEquals("test@example.com", retrievedCandidate.getEmail());
		assertEquals("john", retrievedCandidate.getName());
	}
	
	@Test
	public void testEquality() {
		BasicCandidate candidate1 = new BasicCandidate();
		candidate1.setEmail("test@example.com");
		candidate1.setName("john");
		
		BasicCandidate candidate2 = new BasicCandidate();
		candidate2.setEmail("test@example.com");
		candidate2.setName("john");
		
		BasicCandidate candidate3 = new BasicCandidate();
		candidate3.setEmail("test@example.com");
		candidate3.setName("john");
		
		
		assertEquals(candidate1, candidate2);
		assertEquals(candidate1, candidate3);
	}

}
