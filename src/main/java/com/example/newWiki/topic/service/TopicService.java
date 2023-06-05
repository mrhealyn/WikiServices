package com.example.newWiki.topic.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.newWiki.model.Wiki;
import com.example.newWiki.topic.model.Topic;
import com.example.newWiki.topic.repository.TopicRepo;

@Service
public class TopicService {
	
	@Autowired
	private TopicRepo topicRepo;
	

	
	public void saveTopic(Topic topicObj) {
		topicRepo.save(topicObj);
	}
	
	public List<Topic> getTopicName(Long topicId){
		if (null != topicId) {
			return topicRepo.findAllByTopicId(topicId);
		} else {
			return topicRepo.findAll();
		}
	}
	
	public void deleteTopic(Long topicId) {
		topicRepo.deleteById(topicId);
	}

	public Topic updateTopicName(Long topicId, Map<String, Object> fields) {
		Optional<Topic> topic = topicRepo.findByTopicId(topicId);
		
		if(topic.isPresent()) {
		fields.forEach((key, value)->{
			Field field = ReflectionUtils.findField(Topic.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, topic.get(), value);
		});
		return topicRepo.save(topic.get());
	}
		return null;
	}

	

}
