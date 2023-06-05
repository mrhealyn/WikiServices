package com.example.newWiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.newWiki.model.Wiki;


@Repository
public interface WikiRepo extends JpaRepository<Wiki, Long>{

	
	List<Wiki> findAllByWikiId(Long wikiId);
	
	List<Wiki> findByTitleContaining(String title);
	
	List<Wiki> findByContentContaining(String content);
	
}

