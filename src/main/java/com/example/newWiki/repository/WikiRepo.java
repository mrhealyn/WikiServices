package com.example.newWiki.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.newWiki.model.Wiki;

@Repository
public interface WikiRepo extends JpaRepository<Wiki, Long>{

	
	
	
}

