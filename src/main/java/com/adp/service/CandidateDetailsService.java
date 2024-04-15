package com.adp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adp.entity.BasicCandidate;
import com.adp.repository.BasicCandidateRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class CandidateDetailsService {
	private BasicCandidateRepository basicCandidateRepository;
	
	
	
	public void saveCandidatesToDatabase(MultipartFile file) {
		if(BasicCadidateService.isValidExcelFile(file)) {
			try {
				List<BasicCandidate> basicCandidates=BasicCadidateService.getBasicCadidateDetailsFromExcel(file.getInputStream());
				this.basicCandidateRepository.saveAll(basicCandidates);
			} catch (IOException e) {
				throw new IllegalArgumentException("the file is not a valid excel file");
			}
		}
		else {
			return;
		}
	}
	
	public List<BasicCandidate> getBasicCadidate(){
		return basicCandidateRepository.findAll();
	}
	
	public void updateLinkStatus(List<String> emails){
		for(String email: emails) {
			BasicCandidate candidateInfo = basicCandidateRepository.getReferenceById(email);
			candidateInfo.setLinkStatus("Sent");
			basicCandidateRepository.save(candidateInfo);
		}
	}

	public String getTotalBasicCandidates() {
		return String.valueOf(basicCandidateRepository.findAll().size());
	}

	public String getTotalSentLinks() {
		return String.valueOf(basicCandidateRepository.findByLinkStatus("Sent").size());
	}

	public String getTotalNotSentLinks() {
		return String.valueOf(basicCandidateRepository.findByLinkStatus("Not Sent").size());
		
	}

	public void updateComments(String comments, String email) {
	BasicCandidate basicCandidate=basicCandidateRepository.getReferenceById(email);
	basicCandidate.setComments(comments);
	basicCandidateRepository.save(basicCandidate);
		
	}
	
}
