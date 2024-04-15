package com.adp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adp.entity.BasicCandidate;

@Repository
public interface BasicCandidateRepository extends JpaRepository<BasicCandidate,String> {

	List<BasicCandidate> findByLinkStatus(String string);

	 
	
}
