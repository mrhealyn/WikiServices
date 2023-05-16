package com.example.newWiki;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.newWiki.topic.repository.TopicRepo;
import com.example.newWiki.topic.service.TopicService;

@ExtendWith(MockitoExtension.class)

public class TopicTest {

	@InjectMocks
	private TopicService service;
	
	@Mock
	private TopicRepo repo;
	
	
}
