package com.adp.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.adp.dto.CandidateInfoDTO;
import com.adp.dto.OfferGenerationDetails;
import com.adp.exception.UserException;


public interface CandidateInfoService {

	public void addToDataBase(CandidateInfoDTO candidateInfoDTO) throws UserException, java.io.IOException;
	public List<CandidateInfoDTO> getCandidateInfo() throws UserException;
	public List<CandidateInfoDTO> getGeneratedCandidateInfo() throws UserException;
	public List<CandidateInfoDTO> getNotGeneratedCandidateInfo() throws UserException;
	public List<CandidateInfoDTO> getNotApprovedCandidateInfo() throws UserException;
	public List<CandidateInfoDTO> getApprovedCandidateInfo() throws UserException;
	public void updateOfferLetterGenerationStatus(String string) throws UserException;
	public void updateApprovalStatus(List<String> emails) throws UserException;
	public void generateOfferLetters( OfferGenerationDetails details) throws UserException;
	public boolean saveOfferLetter(File file, String email)throws UserException;
	public List<CandidateInfoDTO> getNotVerified() throws UserException;
	public void updateVerificationStatus(List<String> emails);
	public void updateSentStatus(List<String> emails);
	public void saveDocsToDatabase(MultipartFile file,String email);
	public byte[] getEdDocsBlob(String email) throws UserException;
	public byte[] getOfferLBlob(String email);
	public List<CandidateInfoDTO> getSentForApprovedCandidateInfo() throws UserException;
	public void updateApprovalStatus2(List<String> emails) throws UserException;
	public void updateComments(String comments, String email);
	public String totalCandidates();
	public String getTotalVerifiedCandaidates();
	public String getTotalNotVerifiedCandaidates();
	public String getTotalGeneratedCandaidates();
	public String getTotalNotGeneratedCandaidates();
	public String getTotalApprovedCandaidates();
	public String getTotalSentForApprovalCandaidates();
	public String getTotalNotApprovedCandaidates();
	public void updateMComments(String comments, String email);

}
