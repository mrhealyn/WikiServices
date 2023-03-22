package com.example.newWiki.controller;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.newWiki.model.Wiki;
import com.example.newWiki.repository.WikiRepo;
@RestController
public class WikiController {

	@Autowired
	WikiRepo wikiRepo;
	
	@PostMapping("/api/wiki") //create content
	public ResponseEntity<Wiki> saveWiki(@RequestBody Wiki wiki) {
		return new ResponseEntity<>(wikiRepo.save(wiki),HttpStatus.CREATED);
		
	}
	
	@GetMapping("/api/wiki") //list of all contents
	public ResponseEntity<List<Wiki>> getWiki(){
		return new ResponseEntity<>(wikiRepo.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/api/wiki/{id}") //search content by id
	public ResponseEntity<Wiki> getWikiId(@PathVariable long id) {
		Optional<Wiki> wiki = wikiRepo.findById(id);
		if(wiki.isPresent()) {
			return new ResponseEntity<>(wiki.get(),HttpStatus.OK);
			
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	
	@PutMapping("/api/wiki/{id}") //edit content by id
	public ResponseEntity<Wiki> updateWikiId(@PathVariable long id,@RequestBody Wiki wik) {
		Optional<Wiki> wiki = wikiRepo.findById(id);
		if(wiki.isPresent()) {
			wiki.get().setTopic(wik.getTopic());
			wiki.get().setTitle(wik.getTitle());
			wiki.get().setContent(wik.getContent());
			return new ResponseEntity<>(wikiRepo.save(wiki.get()),HttpStatus.OK);
			
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	@DeleteMapping("/api/wiki/{id}") //delete content by id
	public ResponseEntity<Void> deleteWiki(@PathVariable long id) {
		Optional<Wiki> wiki = wikiRepo.findById(id);
		if(wiki.isPresent()) {
			wikiRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
	}
}
