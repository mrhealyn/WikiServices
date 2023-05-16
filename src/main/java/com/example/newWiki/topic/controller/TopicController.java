package com.example.newWiki.topic.controller;

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

import com.example.newWiki.topic.model.Topic;
import com.example.newWiki.topic.repository.TopicRepo;

@RestController
@RequestMapping("/topic")
public class TopicController {

	@Autowired
	private TopicRepo topicRepo;
	
	@PostMapping("/api/saveTopic") //create topic
	public ResponseEntity<Topic> saveTopic(@RequestBody Topic topic) {
		return new ResponseEntity<>(topicRepo.save(topic),HttpStatus.CREATED);
		
	}
	
	@GetMapping("/api/getTopic") //list of all topic
	public ResponseEntity<List<Topic>> getTopic(){
		return new ResponseEntity<>(topicRepo.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/api/getTopic/{id}") //search topic by id
	public ResponseEntity<Topic> getTopicId(@PathVariable long id) {
		Optional<Topic> topic = topicRepo.findById(id);
		if(topic.isPresent()) {
			return new ResponseEntity<>(topic.get(),HttpStatus.OK);
			
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	
	@PutMapping("/api/editTopic/{id}") //edit topic by id
	public ResponseEntity<Topic> updateTopicId(@PathVariable long id,@RequestBody Topic tpc) {
		Optional<Topic> topic = topicRepo.findById(id);
		if(topic.isPresent()) {
			topic.get().setTopicName(tpc.getTopicName());
			return new ResponseEntity<>(topicRepo.save(topic.get()),HttpStatus.OK);
			
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	@DeleteMapping("/api/deleteTopic/{id}") //delete topic by id
	public ResponseEntity<Void> deleteTopic(@PathVariable long id) {
		Optional<Topic> topic = topicRepo.findById(id);
		if(topic.isPresent()) {
			topicRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
	}
	
}
