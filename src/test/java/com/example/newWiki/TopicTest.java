package com.example.newWiki;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.newWiki.topic.model.Topic;
import com.example.newWiki.repository.WikiRepo;
import com.example.newWiki.topic.repository.TopicRepo;
import com.example.newWiki.topic.service.TopicService;

import junit.framework.Assert;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TopicTest {

	@InjectMocks
	private TopicService topicService;

	@Mock
	private WikiRepo wikiRepo;

	@Mock
	private TopicRepo topicRepo;
	private Topic topic;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		topic = new Topic();
	}

	@Test
	public void should_save_one_topic() {
		// Create new topic
		topic.setTopicName(" Froilan");

		// Call save method
		topicService.saveTopic(topic);

		// Verify that the save method of the mock TopicRepo object was called once with
		// the sample topic
		Mockito.verify(topicRepo, Mockito.times(1)).save(topic);
	}

	@Test
	public void should_find_topic_name_with_id() { // display specific topic by id
		Long topicId = 102L;
		List<Topic> expected = Arrays.asList(topic);
		when(topicRepo.findAllByTopicId(topicId)).thenReturn(expected);

		List<Topic> actual = topicService.getTopicName(topicId);

		assertEquals(expected, actual);
	}

	@Test
	public void should_delete_topic() { // delete topic by id
		Topic topic = new Topic();
		topic.getTopicId();
		topicService.deleteTopic(topic.getTopicId());
		Mockito.verify(topicRepo, Mockito.times(1)).deleteById(topic.getTopicId());
	}

	@Test
	public void should_update_topic_name() {// edit topic name
		// Mock repository behavior
		Long topicId = 1L;
		String updatedName = "New Topic Name";
		Map<String, Object> fields = new HashMap<>();
		fields.put("topicName", updatedName);

		// Create a topic object
		Topic topic = new Topic();
		topic.setTopicId(topicId);
		topic.setTopicName("Old Topic Name");

		// Set up repository behavior
		when(topicRepo.findByTopicId(topicId)).thenReturn(Optional.of(topic));
		when(topicRepo.save(topic)).thenReturn(topic);

		// Call the method under test
		Topic result = topicService.updateTopicName(topicId, fields);

		// Verify repository method calls
		Mockito.verify(topicRepo).findByTopicId(topicId);
		Mockito.verify(topicRepo).save(topic);

		// Assert the result
		assertEquals(updatedName, result.getTopicName());
	}

	// negative path

	@Test
	public void should_fail_to_save_topic() {
		// Create a mock of the TopicRepository
		TopicRepo topicRepo = Mockito.mock(TopicRepo.class);

		// Create an instance of the TopicService and inject the mock repository
		TopicService topicService = new TopicService();

		// Create a test topic
		Topic topic = new Topic();
		topic.setTopicName(""); // Setting name as null to simulate invalid topic

		// Perform the method invocation and assert that the expected exception is
		// thrown
		assertThrows(Exception.class, () -> topicService.saveTopic(topic));

		// Verify that the save method was not called on the mock repository
		Mockito.verify(topicRepo, Mockito.never()).save(topic);

		// Verify that there are no further interactions with the mock repository
		Mockito.verifyNoMoreInteractions(topicRepo);
	}

	@Test
	public void should_find_topicId_Name_without_topicId() {
		List<Topic> expectedTopicList = new ArrayList<>();
		Mockito.when(topicRepo.findAll()).thenReturn(expectedTopicList);

		List<Topic> result = topicService.getTopicName(null);

		Mockito.verify(topicRepo, Mockito.times(1)).findAll();
		Assertions.assertEquals(expectedTopicList, result);

	}

	@Test
	public void should_Notupdate_topicName_with_invalid_topicId() {
		// Arrange
		Long topicId = 123L;
		Map<String, Object> fields = new HashMap<>();
		fields.put("name", "New Topic Name");

		// Mock the behavior of topicRepo.findByTopicId() to return an empty Optional
		Mockito.when(topicRepo.findByTopicId(topicId)).thenReturn(Optional.empty());

		// Act
		Topic result = topicService.updateTopicName(topicId, fields);

		
		Assert.assertNull(result); // Expecting null since the topic was not found

		// Verify that topicRepo.findByTopicId() was called once with the provided
		// topicId
		Mockito.verify(topicRepo, Mockito.times(1)).findByTopicId(topicId);

		// Verify that topicRepo.save() was not called
		Mockito.verify(topicRepo, Mockito.never()).save(Mockito.any(Topic.class));
	}

}
