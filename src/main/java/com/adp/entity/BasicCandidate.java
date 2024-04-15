package com.adp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Basic_Details")
public class BasicCandidate {
	
	@Id
	String email;
	
	String name;
	
	@Column(name="phn_number")
	Long phnNumber;
	
	@Column(name="college_name")
	String collegeName;
	
	@Column(name="link_status")
	String linkStatus;
	
	String comments;
}
