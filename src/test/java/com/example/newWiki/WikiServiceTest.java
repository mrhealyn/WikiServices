package com.example.newWiki;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.newWiki.model.Wiki;
import com.example.newWiki.repository.WikiRepo;
import com.example.newWiki.topic.model.Topic;
import com.example.newWiki.topic.repository.TopicRepo;

import jakarta.transaction.Transactional;

import com.example.newWiki.service.WikiService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WikiServiceTest {

	@InjectMocks //inject mock objects into the fields of the tested class
	private WikiService wikiService;

	@Mock //create mock objects for these dependencies
	private WikiRepo wikiRepo;

	@Mock
	private TopicRepo topicRepo;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void should_save_one_wiki() {
		// Create new wiki
		Wiki wiki = new Wiki();
		wiki.setTitle(" sample");
		wiki.setContent("test");

		// Call save method
		wikiService.saveWiki(wiki);

		// Verify that the save method of the mock WikiRepository object was called once
		// with the sample wiki object
		Mockito.verify(wikiRepo, Mockito.times(1)).save(wiki);
	}

	@Test
	public void should_find_wiki_info_with_id() { // retrieves the wiki information by ID
		Long wikiId = 102L; //Define Test Data
		Wiki wiki = new Wiki();
		List<Wiki> expected = Arrays.asList(wiki);
		when(wikiRepo.findAllByWikiId(wikiId)).thenReturn(expected); //Set up mock behavior

		List<Wiki> actual = wikiService.getWikiInfo(wikiId); //Invoke the method under test

		assertEquals(expected, actual); //Verify the Result
	}

	@Test
	public void should_delete_Wiki() {// delete wiki by id
		Wiki wiki = new Wiki();
		wikiService.deleteWiki(wiki.getWikiId());
		Mockito.verify(wikiRepo, Mockito.times(1)).deleteById(wiki.getWikiId());
	}

	@Test
	@Transactional
	public void should_assign_topic_to_wiki() {
		// Mock repository behavior
		Long wikiId = 1L;
		Long topicId = 2L;

		// Create wiki object and set its ID
		Wiki wiki = new Wiki();
		wiki.setWikiId(wikiId);

		// Create topic object and set its ID
		Topic topic = new Topic();
		topic.setTopicId(topicId);

		// Set up repository behavior
		when(wikiRepo.findById(wikiId)).thenReturn(Optional.of(wiki));
		when(topicRepo.findById(topicId)).thenReturn(Optional.of(topic));
		when(wikiRepo.save(wiki)).thenReturn(wiki);

		// Call the method under test
		Wiki result = wikiService.assignTopicToWiki(wikiId, topicId);

		// Verify repository method calls
		Mockito.verify(wikiRepo).findById(wikiId);
		Mockito.verify(topicRepo).findById(topicId);
		Mockito.verify(wikiRepo).save(wiki);

		// Assert the result
		assertEquals(wiki, result);
		assertTrue(wiki.getAssignedTopics().contains(topic));
	}

	@Test
	public void should_update_wiki_content_() { // edit specific fields
		Wiki wiki = new Wiki();
		wiki.setWikiId(1L);
		when(wikiRepo.findById(1L)).thenReturn(Optional.of(wiki));
		Map<String, Object> fields = new HashMap<>();
		fields.put("title", "content");
		when(wikiRepo.save(wiki)).thenReturn(wiki);
		assertEquals(wiki, wikiService.updateWikiFields(1L, fields));
	}

	public Object saveWiki(Wiki wiki) {
		// TODO Auto-generated method stub
		return null;
	}

}