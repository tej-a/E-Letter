package com.adp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adp.entity.CandidateInfo;

@Repository
public interface CandidateInfoRepository extends JpaRepository<CandidateInfo,String>{
	
	List<CandidateInfo>  findByOfferLetterStatus(String offerLetterStatus);
	List<CandidateInfo>  findByApprovalStatus(String approvalStatus);
	List<CandidateInfo> findByVerificationStatus(String verificationStatus);
	List<CandidateInfo> findByOfferLetterStatusAndVerificationStatus(String offerLetterStatus, String verificationStatus);
	List<CandidateInfo> findByOfferLetterStatusOrVerificationStatus(String string, String string2);
	List<CandidateInfo> findByOfferLetterStatusAndApprovalStatus(String string, String string2);

}
