package com.example.newWiki.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.newWiki.model.Wiki;

@Repository
public interface WikiRepo extends JpaRepository<Wiki, Long>{

	Wiki findByTopic(String topic);

	Optional <Wiki> findByTitle(String title);
}

