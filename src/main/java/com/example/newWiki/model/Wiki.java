package com.example.newWiki.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Wiki {

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String topic;
    private String title;
    private String content;

    public Wiki() {
    	super();
		
	}


	public Wiki(Long id, String topic, String title, String content) {
		super();
		this.id = id;
		this.topic = topic;
		this.title = title;
		this.content = content;
	}

	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
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


	@Override
	public String toString() {
		return "Wiki [id=" + id + ", topic=" + topic + ", title=" + title + ", content=" + content + "]";
	}


	
	
	
}
