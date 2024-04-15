package com.adp.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.adp.dto.CandidateInfoDTO;
import com.adp.dto.OfferGenerationDetails;
import com.adp.entity.CandidateInfo;
import com.adp.exception.UserException;
import com.adp.repository.CandidateInfoRepository;

import jakarta.validation.Valid;


@Service
@Transactional
public class CandidateInfoServiceImpl implements CandidateInfoService{
	
	@Autowired
	CandidateInfoRepository candidateInfoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	GenerateOfferLettersService generateOfferLettersService;
	
	static final String APPROVED= "Approved";
	static final String NOT_APPROVED= "Not Approved";
	static final String NOT_GENERATED= "Not Generated";
	static final String GENERATED= "Generated";
	static final String NOT_VERIFIED= "Not Verified";
	static final String VERIFIED= "Verified";
	

	@Override
	public void addToDataBase(@Valid CandidateInfoDTO candidateInfoDTO) throws UserException, IOException{
		try {
			candidateInfoDTO.setApprovalStatus(NOT_APPROVED);
			candidateInfoDTO.setOfferLetterStatus(NOT_GENERATED);
			candidateInfoDTO.setVerificationStatus(NOT_VERIFIED);
			
			candidateInfoRepository.save(modelMapper.map(candidateInfoDTO, CandidateInfo.class));
		} catch (DataAccessException e) {
			throw new UserException(e.getMessage(), e);
		}
	
	}

	@Override
	public List<CandidateInfoDTO> getCandidateInfo() throws UserException {
		return candidateInfoRepository.findAll()
				.stream().map(a->modelMapper.map(a, CandidateInfoDTO.class)).toList();
	}

	@Override
	public List<CandidateInfoDTO> getGeneratedCandidateInfo() throws UserException {
		
		return candidateInfoRepository.findByOfferLetterStatusAndApprovalStatus(GENERATED,NOT_APPROVED)
				.stream().map(a->modelMapper.map(a, CandidateInfoDTO.class)).toList();
	}

	@Override
	public List<CandidateInfoDTO> getNotGeneratedCandidateInfo() throws UserException {
		return candidateInfoRepository.findByOfferLetterStatusAndVerificationStatus(NOT_GENERATED,VERIFIED)
				.stream().map(a->modelMapper.map(a, CandidateInfoDTO.class)).toList();
		
	}
	
	@Override
	public List<CandidateInfoDTO> getNotApprovedCandidateInfo() throws UserException {
		return candidateInfoRepository.findByApprovalStatus(NOT_APPROVED)
				.stream().map(a->modelMapper.map(a, CandidateInfoDTO.class)).toList();	
	}

	@Override
	public List<CandidateInfoDTO> getApprovedCandidateInfo() throws UserException {
		return candidateInfoRepository.findByApprovalStatus(APPROVED)
				.stream().map(a->modelMapper.map(a, CandidateInfoDTO.class)).toList();
	}

	@Override
	public void updateOfferLetterGenerationStatus(String email) throws UserException {
		

			CandidateInfo candidateInfo = candidateInfoRepository.getReferenceById(email);
			candidateInfo.setOfferLetterStatus(GENERATED);
			candidateInfoRepository.save(candidateInfo);
		
		
	}

	@Override
	public void updateApprovalStatus(List<String> emails) throws UserException {
		for(String email: emails) {
			CandidateInfo candidateInfo = candidateInfoRepository.getReferenceById(email);
			candidateInfo.setApprovalStatus("Sent for Approval");
			candidateInfoRepository.save(candidateInfo);
		}
	}

	@Override
	public void generateOfferLetters( OfferGenerationDetails details) throws UserException {
			try {
				generateOfferLettersService.generateOfferLetter(details.getEmails(), details);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	@Override
    public boolean saveOfferLetter(File file, String email) {
    
        CandidateInfo candidateInfo = candidateInfoRepository.getReferenceById(email);
        try {
            candidateInfo.setOfferLetter(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        if(candidateInfoRepository.save(candidateInfo) != null) {
        	
            return true;
        }
            return false;
        
    }

	@Override
	public List<CandidateInfoDTO> getNotVerified() throws UserException {
		return candidateInfoRepository.findByVerificationStatus(NOT_VERIFIED).stream().map(a->modelMapper.map(a, CandidateInfoDTO.class)).toList();
	}

	@Override
	public void updateVerificationStatus(List<String> emails) {
		for (String email : emails) {
			CandidateInfo candidateInfo= candidateInfoRepository.getReferenceById(email);
			candidateInfo.setVerificationStatus(VERIFIED);
			candidateInfoRepository.save(candidateInfo);
		}
		
	}
	
	@Override
	public void saveDocsToDatabase(MultipartFile file,String email) {
			CandidateInfo candidateInfo=candidateInfoRepository.getReferenceById(email);
			try {
		
	            candidateInfo.setDocumentUpload(file.getBytes());
	            candidateInfoRepository.save(candidateInfo);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}

	@Override
	public byte[] getEdDocsBlob(String email) throws UserException {
		CandidateInfo candidateInfo= candidateInfoRepository.getReferenceById(email);
		
		return candidateInfo.getDocumentUpload();
	}

	@Override
	public byte[] getOfferLBlob(String email) {
		CandidateInfo candidateInfo= candidateInfoRepository.getReferenceById(email);
		return candidateInfo.getOfferLetter();
	}
	@Override
	public List<CandidateInfoDTO> getSentForApprovedCandidateInfo() throws UserException {
		return candidateInfoRepository.findByApprovalStatus("Sent for Approval")
				.stream().map(a->modelMapper.map(a, CandidateInfoDTO.class)).toList();	
	}
	
	
	@Override
	public void updateApprovalStatus2(List<String> emails) throws UserException {
		for(String email: emails) {
			CandidateInfo candidateInfo = candidateInfoRepository.getReferenceById(email);
			candidateInfo.setApprovalStatus(APPROVED);
			candidateInfoRepository.save(candidateInfo);
		}
	}

	@Override
	public void updateComments(String comments,String email) {
		CandidateInfo candidateInfo=candidateInfoRepository.getReferenceById(email);
		candidateInfo.setComments(comments);
		candidateInfoRepository.save(candidateInfo);
	}

	@Override
	public String totalCandidates() {
		return String.valueOf(candidateInfoRepository.findAll().size());
	}

	@Override
	public String getTotalVerifiedCandaidates() {
		return String.valueOf(candidateInfoRepository.findByVerificationStatus(VERIFIED).size());
	}

	@Override
	public String getTotalNotVerifiedCandaidates() {
		return String.valueOf(candidateInfoRepository.findByVerificationStatus(NOT_VERIFIED).size());
		
	}

	@Override
	public String getTotalGeneratedCandaidates() {
		return String.valueOf(candidateInfoRepository.findByOfferLetterStatus(GENERATED).size());
		
	}

	@Override
	public String getTotalNotGeneratedCandaidates() {
		return String.valueOf(candidateInfoRepository.findByOfferLetterStatus(NOT_GENERATED).size());
		
	}

	@Override
	public String getTotalApprovedCandaidates() {
		return String.valueOf(candidateInfoRepository.findByApprovalStatus(APPROVED).size());
		
	}

	@Override
	public String getTotalSentForApprovalCandaidates() {
		return String.valueOf(candidateInfoRepository.findByApprovalStatus("Sent For Approval").size());
		
	}

	@Override
	public String getTotalNotApprovedCandaidates() {
		return String.valueOf(candidateInfoRepository.findByApprovalStatus(NOT_APPROVED).size());
		
	}

	@Override
	public void updateMComments(String comments, String email) {
		CandidateInfo candidateInfo=candidateInfoRepository.getReferenceById(email);
		candidateInfo.setMcomments(comments);
		candidateInfoRepository.save(candidateInfo);
		
	}

	public void setModelMapper(ModelMapper modelMapper2) {
		this.modelMapper=modelMapper2;
		
	}

	@Override
	public void updateSentStatus(List<String> emails) {
		for(String email: emails) {
			CandidateInfo candidateInfo = candidateInfoRepository.getReferenceById(email);
			candidateInfo.setApprovalStatus("Sent");
			candidateInfoRepository.save(candidateInfo);
		}
		
	}
	
	
}
		
		
	

	
	
	


