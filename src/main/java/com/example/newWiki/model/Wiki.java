package com.example.newWiki.model;

import java.util.HashSet;
import java.util.Set;

import com.example.newWiki.topic.model.Topic;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data

public class Wiki {

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long wikiId;
    private String title;
    private String content;

    @ManyToMany
	@JoinTable(name = "wiki_topic",
			joinColumns = @JoinColumn(name = "wiki_id"),
			inverseJoinColumns = @JoinColumn(name = "topic_id")
	)
	private Set<Topic> assignedTopics = new HashSet<>();

   
    public Wiki() {
		
	}

   
	public Wiki(Long wikiId, String title, String content, Set<Topic> assignedTopics) {
		super();
		this.wikiId = wikiId;
		this.title = title;
		this.content = content;
		this.assignedTopics = assignedTopics;
	}



	public Long getWikiId() {
		return wikiId;
	}



	public void setWikiId(Long wikiId) {
		this.wikiId = wikiId;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}


	public Set<Topic> getAssignedTopics() {
		return assignedTopics;
	}


	public void setAssignedTopics(Set<Topic> assignedTopics) {
		this.assignedTopics = assignedTopics;
	}


	

	


	
		
	}

