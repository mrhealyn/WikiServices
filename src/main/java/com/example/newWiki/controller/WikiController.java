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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.newWiki.model.Wiki;
import com.example.newWiki.repository.WikiRepo;
import com.example.newWiki.service.WikiService;

@RestController // return JSON format
@RequestMapping("/wiki") // define web service endpoints
public class WikiController {

	@Autowired // allows to automatically wire/inject dependencies
	private WikiService service;

	@Autowired
	private WikiRepo wikiRepo;

	@PostMapping("/api/saveWiki") // create content
	public ResponseEntity<Wiki> saveWiki(@RequestBody Wiki wiki) {
		return new ResponseEntity<>(wikiRepo.save(wiki), HttpStatus.CREATED);

	}

	@GetMapping("/api/getWiki") // retrieve list of all contents
	public ResponseEntity<List<Wiki>> getWiki() {
		return new ResponseEntity<>(wikiRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping("/api/getWiki/{id}") // retrieve specific content by id
	public ResponseEntity<Wiki> getWikiId(@PathVariable long id) {
		Optional<Wiki> wiki = wikiRepo.findById(id);
		if (wiki.isPresent()) {
			return new ResponseEntity<>(wiki.get(), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
	}

	@PutMapping("/{wikiId}/topic/{topicId}") // assign topic
	public Wiki assignWikiToTopic(@PathVariable Long wikiId, @PathVariable Long topicId) {
		return service.assignTopicToWiki(wikiId, topicId);
	}

	@PutMapping("/api/editWiki/{id}") // edit specific content by id
	public ResponseEntity<Wiki> updateWikiId(@PathVariable long id, @RequestBody Wiki wik) {
		Optional<Wiki> wiki = wikiRepo.findById(id);
		if (wiki.isPresent()) {
			wiki.get().setTitle(wik.getTitle());
			wiki.get().setContent(wik.getContent());
			return new ResponseEntity<>(wikiRepo.save(wiki.get()), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
	}

	@DeleteMapping("/api/deleteWiki/{id}") // delete specific content by id
	public ResponseEntity<Void> deleteWiki(@PathVariable long id) {
		Optional<Wiki> wiki = wikiRepo.findById(id);
		if (wiki.isPresent()) {
			wikiRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
	}
}
