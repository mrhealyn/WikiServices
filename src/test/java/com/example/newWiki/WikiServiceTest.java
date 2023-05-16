package com.example.newWiki;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.newWiki.model.Wiki;
import com.example.newWiki.repository.WikiRepo;
import com.example.newWiki.service.WikiService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WikiServiceTest {
	
	@InjectMocks
	private WikiService service;
	
	@Mock
	private WikiRepo repo;
	
	//save
	@Order(1)
	@Test
	public void saveWikiTest() {
		Wiki wiki= new Wiki(1L, "title", "content", null);

		when(repo.save(wiki)).thenReturn(wiki);
		assertNotNull(wiki, service.saveWiki(wiki));
	}

	//display list
	public void getWikiTest() {
		when(repo.findAll()).thenReturn(Stream.of(new Wiki(1L, "title", "content", null)).collect(Collectors.toList()));
		assertEquals(1, repo.findAllByWikiId(null).size());
	}

	//update
	
	//assign topic
	
	
	//delete
	@Test
	public void deleteWiki() {
		Wiki wiki = new Wiki(1L, "bhagat", "superAdmin", null);
		service.deleteWiki(wiki.getWikiId());
		verify(repo, long(1)).deleteById(wiki.getWikiId());
	}

}

	
	
