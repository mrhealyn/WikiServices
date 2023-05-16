package com.example.newWiki.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.newWiki.model.Wiki;
import com.example.newWiki.repository.WikiRepo;
import com.example.newWiki.topic.model.Topic;
import com.example.newWiki.topic.repository.TopicRepo;

import jakarta.transaction.Transactional;

@Service
public class WikiService {

	@Autowired
	private WikiRepo wikiRepo;
	
	@Autowired
	private TopicRepo topicRepo;
	
	
	//save wiki
	public void saveWiki(Wiki wikiObjc) {
		wikiRepo.save(wikiObjc);
	}
	
	//display list of contents
	public List<Wiki> getWikiInfo(Long wikiId){
		if (null != wikiId) {
			return wikiRepo.findAllByWikiId(wikiId);//if not null display id info
		} else {
			return wikiRepo.findAll();//else display all wiki
		}
	}
	
	// delete wiki
	public void deleteWiki(Long wikiId) {
		wikiRepo.deleteById(wikiId);
	}

	//assign topic
	
	@Transactional
	public Wiki assignTopicToWiki(Long wikiId, Long topicId) {
		Set<Topic> topicSet = null;
		Wiki wiki = wikiRepo.findById(wikiId).get();
		Topic topic = topicRepo.findById(topicId).get();
		topicSet = wiki.getAssignedTopics();
		topicSet.add(topic);
		wiki.setAssignedTopics(topicSet);
		return wikiRepo.save(wiki);
	}
	
	//update wiki specific fields
	public Wiki updateWikiFields(Long wikiId, Map<String, Object> fields) {
		Optional<Wiki> wiki = wikiRepo.findById(wikiId);
	
		if(wiki.isPresent()) {
		fields.forEach((key, value)->{
			Field field = ReflectionUtils.findField(Wiki.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, wiki.get(), value);
		});
		return wikiRepo.save(wiki.get());
	}
	return null;
	}

	
}
