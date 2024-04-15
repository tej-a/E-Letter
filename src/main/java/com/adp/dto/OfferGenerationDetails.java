package com.adp.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OfferGenerationDetails {
	
	private String grade;
	
	private String compensation;
	
	private String designation;
	
	private String place;
	
	
	
	private LocalDate joiningDate;
	
	private List<String> emails;

}
