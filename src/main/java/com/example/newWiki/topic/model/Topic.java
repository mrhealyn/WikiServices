package com.example.newWiki.topic.model;

import java.util.HashSet;
import java.util.Set;

import com.example.newWiki.model.Wiki;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TOPIC")
public class Topic {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long topicId;
    private String topicName;
   
    
    
    @JsonIgnore
	@ManyToMany(mappedBy = "assignedTopics")
	private Set<Wiki> wikiSet = new HashSet<>();



	public Topic() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Topic(Long topicId, String topicName, Set<Wiki> wikiSet) {
		super();
		this.topicId = topicId;
		this.topicName = topicName;
		this.wikiSet = wikiSet;
	}



	public Long getTopicId() {
		return topicId;
	}



	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}



	public String getTopicName() {
		return topicName;
	}



	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}



	public Set<Wiki> getWikiSet() {
		return wikiSet;
	}



	public void setWikiSet(Set<Wiki> wikiSet) {
		this.wikiSet = wikiSet;
	}
    
    

}
