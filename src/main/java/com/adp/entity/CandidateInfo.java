package com.adp.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@Data
@Entity
@Table(name="candidate_info2")
public class CandidateInfo {
	
	@Id
	private String email;

	private String name;
	
	@Column(name="date_of_birth")
	private LocalDate dateOfBirth;
	
	private String gender;
	
	@Column(name="mobile_no")
	private Long mobileNumber;
	
	@Column(name="secondary_mobile_no")
	private Long secondaryMobileNumber;

	@Column(name="house_number")
	private String houseNumber;
	
	@Column(name="permanent_city")
	private String pcity;
	
	@Column(name="permanent_state")
	private String permanentState;
	
	private String zipCode;
	
	@Column(name="current_House_number")
	private String currentHouseNumber;
	
	@Column(name="current_city")
	private String currentCity;
	
	@Column(name="current_state")
	private String currentState;
	
	private String cZipCode;
	
	@Column(name="pan_number")
	private String panNumber;
	
	@Column(name="bank_name")
	private String bankName;
	
	@Column(name="account_number")
	private String accountNumber;
	
	@Column(name="ifsc_code")
	private String ifscCode;
	
	@Column(name="offer_letter_status")
	private String offerLetterStatus;
	
	@Column(name="approval_status")
	private String approvalStatus;

	@Lob
	@Column(name="offer_letter")
	private byte[] offerLetter;
	
	@Lob
	@Column(name="pccm_od")
	private byte[] documentUpload;
	
	
	@Column(name="verification_status")
	private String verificationStatus;
	
	@Column(name="comments")
    private String comments;
	
	@Column(name="mcomments")
    private String mcomments;
}
