package com.example.newWiki;

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

import com.example.newWiki.topic.model.Topic;
import com.example.newWiki.repository.WikiRepo;
import com.example.newWiki.topic.repository.TopicRepo;
import com.example.newWiki.topic.service.TopicService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TopicTest {

	@InjectMocks
	private TopicService topicService;

	@Mock
	private WikiRepo wikiRepo;

	@Mock
	private TopicRepo topicRepo;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void should_save_one_topic() {
		// Create new topic
		Topic topic = new Topic();
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
		Topic topic = new Topic();
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

}
