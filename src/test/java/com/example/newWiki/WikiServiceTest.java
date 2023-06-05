package com.example.newWiki;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
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
import com.example.newWiki.topic.service.TopicService;

import jakarta.transaction.Transactional;
import junit.framework.Assert;

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
	public void should_assign_topic_to_wiki() {
		// Mock repository behavior
		Long wikiId = 1L;
		Long topicId = 2L;

		// Create wiki object and set its ID
		Wiki wiki = new Wiki();
		wiki.setWikiId(10L);

		// Create topic object and set its ID
		Topic topic = new Topic();
		topic.setTopicId(2L);

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
	
	//negative path
	
	@Test
	public void should_fail_to_save_wiki() {
		// Create a mock of the TopicRepository
		WikiRepo wikiRepo = Mockito.mock(WikiRepo.class);

		// Create an instance of the TopicService and inject the mock repository
		WikiService wikiService = new WikiService();

		// Create a test topic
		Wiki wiki = new Wiki();
		wiki.setTitle("");
		wiki.setContent(null);// Setting name as null to simulate invalid content

		// Perform the method invocation and assert that the expected exception is
		// thrown
		assertThrows(Exception.class, () -> wikiService.saveWiki(wiki));

		// Verify that the save method was not called on the mock repository
		Mockito.verify(wikiRepo, Mockito.never()).save(wiki);

		// Verify that there are no further interactions with the mock repository
		Mockito.verifyNoMoreInteractions(wikiRepo);
	}
	
	
	@Test
	public void should_find_wikiId_Info_without_wikiId() {
		List<Wiki> expectedWikiList = new ArrayList<>();
		Mockito.when(wikiRepo.findAll()).thenReturn(expectedWikiList);

		List<Wiki> result = wikiService.getWikiInfo(null);

		Mockito.verify(wikiRepo, Mockito.times(1)).findAll();
		Assertions.assertEquals(expectedWikiList, result);

	}
	
	@Test
	public void should_not_assign_topic_to_wiki() {
	    Long wikiId = 52L;
	    Long topicId = 1L;
	    Set<Topic> topicSet = new HashSet<>();
	    Wiki wiki = new Wiki();
	    Topic topic = new Topic();
	    topicSet.add(topic);

	    Mockito.when(wikiRepo.findById(wikiId)).thenReturn(Optional.empty());
	    Mockito.when(topicRepo.findById(topicId)).thenReturn(Optional.of(topic));

	    Wiki result = wikiService.assignTopicToWiki(wikiId, topicId);

	    Assertions.assertNull(result);
	    Assertions.assertNotEquals(topicSet, wiki.getAssignedTopics());

	    Mockito.verify(wikiRepo, Mockito.times(1)).findById(wikiId);
	    Mockito.verify(topicRepo, Mockito.times(1)).findById(topicId);
	    Mockito.verify(wikiRepo, Mockito.never()).save(Mockito.any(Wiki.class));
	}



	
	@Test
    public void should_not_update_wiki_with_invalid_wikiId() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock the behavior of wikiRepo.findById() to return an empty Optional
        Long wikiId = 123L;
        Mockito.when(wikiRepo.findById(wikiId)).thenReturn(Optional.empty());

        // Create fields to update
        Map<String, Object> fields = new HashMap<>();
        fields.put("title", "New Title");
        fields.put("content", "New Content");

        // Call the method under test
        Wiki result = wikiService.updateWikiFields(wikiId, fields);

        // Verify that the result is null
        Assertions.assertNull(result);

        // Verify that wikiRepo.findById() was called once with the provided wikiId
        Mockito.verify(wikiRepo, Mockito.times(1)).findById(wikiId);

        // Verify that wikiRepo.save() was not called
        Mockito.verify(wikiRepo, Mockito.never()).save(Mockito.any(Wiki.class));
    }

	



}