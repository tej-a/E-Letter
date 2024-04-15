package com.adp.dto;

import java.io.File;
import java.time.LocalDate;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class CandidateInfoDTO {
	 
//	const candidate = { email, name, dateOfBirth, mobileNumber, secondaryMobileNumber,houseNumber,pcity,
//            permanentState, zipCode,currentHouseNumber,currentCity, currentState,
//            cZipCode, panNumber, bankName, accountNumber, ifsc}

	
	private String email;
	
	private String name;
	 
	private String gender;
	
	private LocalDate dateOfBirth;
	
	private Long mobileNumber;
	 
	private Long secondaryMobileNumber;
	
	private String houseNumber;
	
	private String pcity;
	
	private String permanentState;
	
	private String zipCode;
	
	private String currentHouseNumber;
	
	private String currentCity;
	
	private String currentState;
	
	private String cZipCode;
	
	private String panNumber;
	 
	private String bankName;
	 
	private String accountNumber;
	 
	private String ifscCode;
	 
	private String offerLetterStatus;
	 
	private String approvalStatus;
	 
	private String verificationStatus;
	
	private byte[] documentUploadOG;
	
	private File documentUpload;
	 
	private byte[] orginalOfferLetter;
	
	private File offerLetter;
	
	private String comments;
	
	private String mcomments;
	

}
